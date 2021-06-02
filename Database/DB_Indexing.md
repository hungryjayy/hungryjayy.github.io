# DB Indexing

* DB 조회시 더 빠르게 할 수 있는 방법. 책 맨 첫페이지에 있는 목차 느낌
* column(속성), 해당 레코드가 저장된 주소를 Key - value 쌍으로 인덱스를 만들어 두는 것





## 인덱스의 장단점

* 장점
  * 테이블 조회 속도 향상
* 단점
  * 인덱스 관리를 위해 별도의 (약 10%) 관리공간 추가적 필요
  * 추가 작업 필요
  * **잘못 사용하면 오히려 역효과 발생할 수 있음**





## Index를 사용할 때의 성능, 주의할 점

* 항상 성능을 향상시키지는 않음



#### 성능 저하

* INSERT, DELETE, UPDATE가 많은 곳(**DML(데이터 조작 언어) 자주 일어나는 곳**)
  * INSERT - 인덱스에 대한 데이터 하나 추가해야 함
  * DELETE - 인덱스를 삭제하지 않고 사용하지 않음 처리
    * 만약 데이터가 10만건, 인덱스가 100만건 있다고 가정하면 오히려 역효과
  * UPDATE - INSERT와 DELETE의 문제점 동시에 수반

* **더 중요한 것** : 컬럼을 이루는 데이터의 형식에 따라 인덱스에 악영향(**데이터 중복이 큰 곳**)
  * e.g. 이름, 나이, 성별 필드를 갖는 테이블의 경우 : 이름에 대해서만 인덱싱을 생성하면 효율적
    * 10000건의 데이터에 2000 단위로 인덱싱했다고 가정하면, 성별의 경우 range가 적어 한번 인덱스를 읽고 다시한번 디스크 I/O가 발생할 것이기 때문

* 규모가 작고, join이나 where, orderby 자주 사용되는 곳, 데이터 중복 적은 곳





## Index 자료구조

#### B +- Tree 인덱스 알고리즘

* 일반적으로 사용되는 인덱스 알고리즘.
* 컬럼의 값을 변경하지 않고 (앞 부분만 잘라서 관리 : 전방 일치) 원래의 값을 이용해 인덱싱
* Select 질의에 일반적으로 부등호 연산이 있기 때문에 이걸 많이 사용



#### Hash 인덱스 알고리즘

* 컬럼의 값으로 해시 값을 계산해서 인덱싱 - 속도가 빠름 O(1)
* 값을 변경해서 인덱싱하기 때문에, 특정 문자로 전방 일치를 찾아 검색하고자 하는 경우 사용 불가
* 메모리 기반 DB에서 많이 사용
* 부등호 연산에 문제가 발생(hash table은 동일(=) 연산에 특화되어 있음)





## Clustered index

* 비슷한 것들을 묶어서 저장하는 형태.
  * 주로 비슷한 값들을 동시에 조회하는 경우가 많기 때문
* PK 값이 비슷한 레코드끼리 묶어서 저장하는 것
* PK값에 의해 저장 위치가 결정. 따라서, PK 변경 시 저장 위치도 변경됨
  * 신중한 선택이 필요
* 테이블 당 하나 생성 가능 <-> non clustered는 한 테이블에 여럿 생성 가능





## Composite Index (결합 인덱스)

* 두개의 컬럼을 결합해 자주 사용되는 곳에서.
* 그런데, 첫번째 인덱스, 두번째 인덱스 순으로 결합했는데 두번째로 검색을 한다면 효과없음
* 쿼리문을 어떻게 작성할지도 중요





#### Reference)

#### https://github.com/gyoogle/tech-interview-for-developer/blob/master/Computer%20Science/Database/%5BDB%5D%20Index.md

#### https://github.com/WeareSoft/tech-interview/blob/master/contents/db.md#index%EB%9E%80

#### https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/Database

#### https://mangkyu.tistory.com/96