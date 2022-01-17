# Redis의 자료구조와 Full scan

: Redis에는 불가피하게 full scan을 해야하는 API를 제공한다. `getKeys`(`hscan()`)와 같은 것이 그런 것에 속한다. 싱글스레드 기반의 redis를 block시키는 것은 큰 문제를 발생시킬 수 있어, **해결 방안 중 하나로 많은 곳에서 cursor 매커니즘을 이야기한다.**

<br>

#### cursor 매커니즘

* Redis는 open addressing 방식의 해시테이블(버킷)로 구성되어있다.

* **Cursor의 한 턴은 한 버킷의 단위**이다. 따라서, 각 cursor는 한 bucket의 linked list를 한번 순회하면 그 다음 bucket의 주소를 가리키고, 멈춘다.

  ```typescript
  // 아래와같이 hashScan(별도 구현)을 통해 얻어온 key값들을 저장하고, cursor를 새롭게 갱신한다.
  
  let cursor = '0';
  const keys: string[] = [];
  try {
      do {
          const [nextCursor, ...result] = await hashScan(redisClient, hash, cursor);
          cursor = nextCursor;
      } while (cursor !== '0');
  } catch (error) {
      // ..
  }
  ```

* 위와 같은 방법을 통해 full scan에서의 문제를 해결한다.

* 이걸 사용한다 하더라도, 내부구조가 해시테이블이 아니고 그냥 리스트라면 같은 문제가 고스란히 발생

* 지나간 자리는 다시 스캔하지 않는다.

* 반환된 Key로 뭔가를 하려한다면 주의해야 한다.

<br>

## Redis의 자료구조

* 해시테이블을 사용한다. 1 버킷: 1 리스트의 형태이다.
* 초기 사이즈는 버킷 4개이고, 각 **버킷 인덱스**는 **비트값**이다. (초기에는 00, 01, 10, 11)
  * `해싱 결과` & `sizemask` 비트연산을 수행해, 어떤 bucket으로 맵핑할지를 결정한다.
* 일정 수준 이상으로 충돌이 많이발생하면, Rehashing을 수행한다.

<br>

### Rehashing

: 일정 수준 이상의 충돌이 발생할 때 **버킷 크기를 두배**로 늘리고 **다시 해싱**하는 것

* O(n) 번의 리해싱.
* cursor 매커니즘 사용: **한 cursor당 하나의 버킷 rehashing**
* **리해싱 중**일 때는 해싱 테이블 두개 존재하게 된다.
  * **생성**, **변경**, **삭제** 데이터는 새로운 테이블로 처리
  * **조회**는 두 테이블을 다 순회

<br><br>

#### Reference)

#### https://tech.kakao.com/2016/03/11/redis-scan/