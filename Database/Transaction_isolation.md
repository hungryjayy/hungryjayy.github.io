# Transaction과 격리수준

: 데이터베이스에 접근해 로직을 처리하는 하나의 논리적 단위

<br>

## Transaction의 네가지 성질 (ACID)

* 원자성(Atomic): 트랜잭션은 오직 커밋 or 롤백된다. 일부만 성공하는 것은 없다.
* 일관성(Consistent): 일관성 있는 DB.(data integrity 만족). String이던 데이터가 갑자기 Date타입으로 변경되는 일 없음
* 격리성(Isolated): 동시 실행되는 트랜젝션이 서로 영향이 없도록 격리
* 지속성(Durability): 트랜잭션을 성공적으로 마치면 결과가 항상 지속된다.(영원하다)

<br>

## Lock의 종류

* **Shared lock** - 읽기 잠금. Read 가능, Write 불가. N개의 트랜잭션이 동시에 걸 수 있음. 이게 걸려있으면 다른 트랜잭션은 Exclusive lock을 걸지 못한다.
* **Exclusive lock** - 쓰기 잠금. R,W 둘다 가능. 다만 한 개의 트랜잭션이 트랜잭션을 걸 수 있음
* **Update lock** - 처음엔 Shared lock처럼 동작. Update할 준비가 되면(**본인 외의 다른 shared lock들이 할당 해제되면**) Exclusive lock으로 변하면서 해당 레코드에 update하게 된다.

<br>

## 다수의 트랜잭션 경쟁시 발생하는 문제

1. **Dirty read**:  **다른 트랜젝션에 의해 수정되어 값이 달라졌지만**, 아직 커밋은 되지 않은 데이터를 **읽는 것**
   * e.g) 트랜잭션 A가 어떤 값을 1에서 2로 변경하고 커밋이 안됐을 때 B가 해당 값을 읽으면 2가 조회 된다. 이 때 A가 롤백되면 B는 잘못된 값을 읽은게 됨
   * 다른 문제들에 비해 발생 확률이 높다.
2. **Non-repeatable read**: 선행 트랜잭션이 읽은 데이터를 후행 트랜잭션이 **수정, 삭제**. 따라서, **한 트랜잭션에서 여러 스냅샷이 사용**되는 경우

   * e.g) A가 값 1을 읽고 또 해당 쿼리를 실행하기 전에 B가 값을 2로 바꾸고 커밋하면 A의 쿼리 결과가 달라짐.

   * Dirty read에 비해 확률 적다.

3. **Phantom Read**: 한 트랜잭션 조회에서 다른 레코드를 **삽입**할 것 방지.
* e.g) 2번과 비슷한 상황. B가 테이블에 값을 추가하면 A의 두 쿼리는 결과가 달라짐

<br>

## 격리 수준의 종류

* **level 0** - **read uncommited**
  * Shared lock이 걸리지 않아 데이터를 읽는 도중 다른 트랜잭션이 데이터를 변경할 수 있다.
  * **Dirty read**, **non-repeatable read**, **phantom read** 발생
* **level 1** - **read commited**
  * Shared lock이 걸린 상태. 변경사항이 커밋되어 확정된 데이터만 읽는다.
  * **non-repeatable read**, **phantom read** 발생
  * 대부분의 DBMS가 이걸 디폴트로 한다고 한다.
* **level 2** - **repeatable read**
  * **선행 트랜잭션이 읽는(select하는)** 모든 데이터에 **shared lock**. 다른 트랜잭션은 해당 영역 수정 및 삭제 불가해짐.
  * **phantom read** 발생
* **level 3** - **serializable**
  * 선행 트랜잭션이 읽는 데이터에 새로운 레코드 **삽입**까지 불가능 - **update lock**
  * 완전한 단계의 LOCK



