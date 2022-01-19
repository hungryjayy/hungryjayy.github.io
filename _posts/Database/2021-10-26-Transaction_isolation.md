---

layout: post

title: Transaction과 격리수준

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [database, db, 데이터베이스, isolation, 격리수준, acid, transaction, 트랜잭션]

featuredImage: 

img: 

categories: [Database]

date: '2021-10-26'

extensions:

  preset: gfm


---

: 데이터베이스에 접근해 로직을 처리하는 하나의 논리적 단위

<br>

## Transaction의 네가지 성질 (ACID)

* 원자성(Atomic): 트랜잭션은 오직 커밋 or 롤백된다. 일부만 성공하는 것은 없다.
* 일관성(Consistent): 일관성 있는 DB.(data integrity 만족). String이던 데이터가 갑자기 Date타입으로 변경되는 일 없음
* 격리성(Isolated): 동시 실행되는 트랜젝션이 서로 영향이 없도록 격리
* 지속성(Durability): 트랜잭션을 성공적으로 마치면 결과가 항상 지속된다.(영원하다)

<br>

#### ACID를 엄격히 지키면 동시성이 떨어진다.

* 동시성을 얻고 안정성을 해결하기 위한 방법: transaction에 isolation 단계 설정

<br>

## Inno DB lock의 종류

***InnoDB이란? MySql을 위한 데이터베이스 엔진***

<br>

#### Lock의 종류

* **Shared lock** - 읽기 잠금. Read 가능, Write 불가. **N개의 트랜잭션이 동시에** 걸 수 있음. 이게 걸려있으면 다른 트랜잭션은 Exclusive lock을 걸지 못한다.
* **Exclusive lock** - 쓰기 잠금. R,W 둘다 가능. 다만 **한 개의 트랜잭션만이** 트랜잭션을 걸 수 있음
* **Update lock** - 처음엔 Shared lock처럼 동작. Update할 준비가 되면(**본인 외의 다른 shared lock들이 할당 해제되면**) Exclusive lock으로 변하면서 해당 레코드에 update하게 된다.

<Br>

#### record lock (row lock)

: index record에 락을 걸어버림. 다른 트랜잭션이 해당 row에 대한 변경을 하는 것을 방지

<Br>

#### gap lock

: Where 절 등에 존재하는 조건을 만족하는 새로운 record 삽입을 방지

* 경우에 따라서 테이블 전체가 될 수도 있다.

<br>

## 다수의 트랜잭션 경쟁시 발생하는 문제

1. **Dirty read**:  **다른 트랜젝션에 의해 수정되어 값이 달라졌지만**, 아직 커밋은 되지 않은 데이터를 **읽는 것**
   * e.g) 트랜잭션 A가 어떤 값을 1에서 2로 변경하고 커밋이 안됐을 때 B가 해당 값을 읽으면 2가 조회 된다. 이 때 A가 롤백되면 B는 잘못된 값을 읽은게 됨
   * 다른 문제들에 비해 발생 확률이 높다.
2. **Non-repeatable read**: 커밋된 데이터를 읽긴 하지만, 선행 트랜잭션이 읽은 데이터를 후행 트랜잭션이 **수정, 삭제**. 이후 같은 쿼리를 날리면 값이 달라져 있는 것. 따라서, **한 트랜잭션에서 여러 스냅샷이 사용**되는 경우
   * e.g) A가 값 1을 읽고 또 해당 쿼리를 실행하기 전에 B가 값을 2로 바꾸고 커밋하면 A가 같은 쿼리를 요청했을 때 쿼리 결과가 달라짐.
   * Dirty read에 비해 발생 확률 적다.
3. **Phantom Read**: 트랜잭션에서 쿼리를 두번 날리면 첫번째에 없던 데이터가 두번째에 **생성**되는 것
   * e.g) 2번과 비슷한 상황. B가 테이블에 값을 추가하면 A의 후행 쿼리에서는 없던 값이 생김

<br>

## 격리 수준

: 레벨이 올라갈 수록 **동시성이 떨어진다**. 문제가 발생할 확률도 떨어져 **안정성은 증가한다**.

<br>

* **Consistent Read**: 일반적으로 쿼리의 log를 저장하고, 어떠한 시점에 쿼리를 치면 이 log를 통해 스냅샷을 불러와 읽는다고 한다.

<br>

### 격리 수준 레벨 - 발생하는 이상현상으로 이해하기

* **level 0** - **read uncommited**
  * 찾고자 하는 데이터가 커밋되지 않아있어도 그냥 데이터를 읽어온다.
  * **Dirty read**, **non-repeatable read**, **phantom read** 발생
* **level 1** - **read commited**
  * shared lock이 사용되지 않은 것.
  * 변경사항이 커밋되어 확정된 데이터만 읽는다.
    * 사실 DB에는 커밋되지 않은 데이터도 적용된 상태이고, 트랜잭션이 한 레코드를 Read 할 때마다 DB 스냅샷을 새로 만든다. 따라서, 변경사항이 보이게 되는 것
  * **non-repeatable read**, **phantom read** 발생
  * 대부분의 DBMS가 이걸 디폴트로 한다고 한다.
* **level 2** - **repeatable read**
  * shared lock이 사용되는 것.
  * 한 트랜잭션 안에서는 처음 읽을 때 시간을 기록하고, 나중에 데이터를 다시 읽더라도 해당 시점의 데이터(스냅샷)를 읽어온다. - 이 때 로그를  사용
  * **phantom read** 발생
* **level 3** - **serializable**
  * `Select` 쿼리가 전부 `Select ... for share` 쿼리로 변환된다. - **gap lock**을 걸어버린다.
  * 테이블에 걸다보니 데드락에 쉽게 걸릴 수 있다.
  * phantom read도 방지. 완전한 단계의 lock

<br><br>

#### Reference)

https://dev.mysql.com/doc/refman/8.0/en/innodb-transaction-isolation-levels.html

https://suhwan.dev/2019/06/09/transaction-isolation-level-and-lock/