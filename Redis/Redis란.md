# Redis

<br>

## 장점

- **성능**: In memory data store라서 초고속 성능을 제공한다. Disk 참조 하지 않기 때문에 엔진 대기 시간이 마이크로초 단위까지 줄어든다.
- **유연한 데이터 구조**: Key value쌍의 다양한 Data structure: 문자열, 목록, 세트, 해시 비트맵 등
- **단순성**: 다른 DB의 쿼리구조와 다르게, 단순하다. `get` `set` `del` 등의 직관적인 API를 제공한다.
- **복제**: 비동기식 복제(Replication)를 지원한다. 따라서, 서버 장애 상황에서 요청이 분산될 수 있다. 
- **지속성**: Redis가 특정 시점에 백업(Redis 데이터 집합을 디스크로 복사)를 지원한다.
- **확장성 및 고가용성**: 클러스터링이 가능하고, 기본 복제 아키텍처가 제공된다. 클러스터링에서는 스케일업, 인, 아웃이 가능하다.
- **오픈소스**이다.
- **그 외**: (Node환경)Socket.io 패키지 -> Redis가 브로커 역할 수행해 스케일링에 적합한 웹소켓 환경 구성 가능하다.

<Br>

## 특징

### Single Thread

- 싱글 스레드 기반이다. 따라서, I/O가 많아 의미있는 block이 발생할만한 작업을 조심해야한다. Redis 장애는 `getKeys()` , `flush()` 처럼 다건의 레코드 처리 커멘드때문에 발생하는 경우일 때가 꽤 있다고 한다.
  - 이러한 다건의 문제를 해결하기 위해서는 cursor 매커니즘을 활용한다. scan을 이용해 일정 개수만큼만 조회하고, task 쌓여있으면 그거부터 처리하는 방식.

<br>

### 복제

* **Sentinel**: Master - Slave 매커니즘 제공. 장애 상황에서 회복이 빨라질 수 있다.
* **Clustering**: 클러스터링(분산) 환경에서도 기본 복제 아키텍처가 제공된다.

<br>

### 지속성(Persistence)

: In memory로 언제든 휘발할 가능성이 있다. 지속성의 특징을 위해선 Disk에 저장해야한다.

- ##### **RDB**(Redis Database)

  - **특정 시점의 스냅샷을 디스크에(파일시스템에 binary 파일로) 저장한다.** redis.conf 에서 `dbfilename dump.rdb` 설정으로 파일 이름을 설정할 수 있고, 설정을 통해 언제 저장할지 시점도 설정 가능하다.
  - **파일 로드**: 파일 압축이 가능하다. 일반적으로 AOF보다 파일 사이즈가 작아 파일 로딩 속도가 빠르다고 한다.
  - **성능 이슈**: redis는 싱글스레드 기반인데 저장 작업이 오래걸리고, 그동안 block상태라 이것때문에 redis 장애가 발생하는 경우가 많다고 한다.
  - 디스크에 저장하다가 실패해도 다른 모든 동작을 정상적으로 처리하던지, 혹은 조치를 기다리던지를 수행해야한다.(conf 수정)

- **AOF(Append Only File)**

  - **입력, 수정, 삭제(조회 제외) 때마다 계속 기록된다.** AOF를 사용해도 **성능이 크게 안나빠지기 때문에 AOF가 default.**
  - `appendonly.aof` 파일에 저장된다.
  - AOF 계속 추가 기록하는데, **특정 시점에는 데이터 전체를 rewrite**한다. 계속 추가만 하다보면 파일이 무한히 커지는데, rewrite를 하면 이전 쓸데없는 diff는 사라지고, 최종 데이터가 기록된다.
  - rewrite도 conf로 설정 가능하다.
  - Replication환경에서 master는 AOF를 사용하는 것이 안전하다. 왜냐하면, 자동 재시작 후, 몇 분 전의 RDB(기록 유실)만 있다면 slave들도 마스터와 같이 해당 RDB를 받는다.

<br>

## Memcached와의 비교

* 둘다 key-value이다
* Memcached는 멀티스레드이다. global cache lock을 통해 thread safety를 가능하게 한다. 

<br>

#### Reference)

#### https://github.com/redis/redis

#### https://aws.amazon.com/ko/redis/

#### http://redisgate.kr/redis/configuration/persistence.php