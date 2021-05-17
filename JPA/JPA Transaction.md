# @Transactional : 선언적 트랜잭션

* 클래스나 메서드에 이 어노테이션이 추가되면 트랜잭션 기능이 적용된 프록시 객체가 생성
* 이 프록시 객체는 `@Transaction` 이 포함된 메소드 호출시 `PlatformTransactionManager`를 사용해 트랜잭션 시작
  * 이후 commit or rollback
* 비즈니스를 하나의 트랜잭션 단위로.
  * 트랜잭션의 성질
    * 원자성: 한 트랜잭션 내의 작업은 하나로 간주. commit or roll back
    * 일관성: 일관성 있는 DB.(data integrity 만족)
    * 격리성: 동시 실행되는 트랜젝션이 서로 영향이 없도록 격리
    * 지속성: 트랜잭션을 성공적으로 마치면 결과가 항상 저장.

### 다수의 트랜잭션 경쟁시 발생하는 문제

1. Dirty read: 트랜잭션 A가 어떤 값을 1에서 2로 변경하고 커밋이 안됐을 때 B가 해당 값을 읽으면 2가 조회 됨.
   * 이 때 A가 롤백되면 B는 잘못된 값을 읽은게 됨
2. Non-repeatable read: A가 값 1을 읽고 또 해당 쿼리를 실행하기 전에 B가 값을 2로 바꾸고 커밋하면 A의 쿼리 결과가 달라짐.
   * Dirty read에 비해 확률 적다
3. Phantom Read: 2번과 비슷한 상황. B가 테이블에 값을 추가하면 A의 두 쿼리는 결과가 달라짐



### 옵션

1. Isolation(격리 수준) 

* transaction에서 일관성 없는 데이터를 허용하는 수준.
* 격리 수준이 올라갈수록 성능 저하 이슈
* 종류
  * DEFAULT : 기본격리수준
  * READ_UNCOMMITED(0): 커밋되지 않는 데이터 읽기 허용(더티캐시 발생가능)
  * READ_COMMITED(1): 커밋된 데이터만 읽음(더티캐시 방지)
  * REPEATABLE_READ(2): 트랜잭션 완료시 까지 select가 사용되는 모든 데이터에 shared lock. 다른 사용자는 해당 영역 수정 불가해짐.(Non-repeatable read 방지)
  * SERIALIZABLE(3): 수정 및 입력까지 불가능(Phantom read 방지)

2. propagation
   * 트랜잭션 도중 다른 트랜잭션 호출시 선택 옵션
3. readOnly 속성
   * 트랜잭션 읽기 전용으로 설정
     * insert, update, delete 등 발생 시 예외 발생 
   * 성능 최적화를 위해
   * e.g. ) `@Transactional(readOnly = true)`
4. 트랜잭션 롤백 예외
   * Runtime Exception (UnCheckedException) 발생시 롤백
     * 바꿔 말하면 CheckedException 발생 시에는 롤백되지 않음. 따라서 모든 예외에 대해서 모두 롤백하고 싶다면 아래와같이 명시해줘야 함
   * rollbackFor 속성: 특정 예외 발생시 강제로 Rollback
     * `@Transactional(rollbackFor=Exception.class)`
   * noRollbackFor 속성: 예외 발생시 rollback 처리 x
     * `@Transactional(noRollbackFor=Exception.class)`
5. Timeout 속성
   * `@Transactional(timeout=10)`



#### Reference) https://goddaehee.tistory.com/167

#### https://pjh3749.tistory.com/269





### JPA에서 디폴트 @Transactional

* JPA에서 제공하는 기본 메서드는 디폴트로 @Transaction이 선언되어있어 atomic하게 수행됨
* 따라서, 레포지토리의 여러 메서드를 사용하는 부분이 아니라면 굳이 다시 @Transactional 선언 해줄 필요는 없다.
* custom 메소드에 한해서는 모두 @Transactional 선언을 해줄 필요가 있다.
  * 이 때, 사용부인 service에서 일일히 하는 방법, **repository에서 미리 선언해주는 방법**.
* 기본적으로 SingleJpaRepository (JPA interface 구현체부분)에 가보면 전체가 Transaction으로 감싸져있음.

```java
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByLastname(String lastname);

  @Modifying
  @Transactional
  @Query("delete from User u where u.active = false")
  void deleteInactiveUsers();
}
```
* 전체에 readOnly를 감싸주고, writable하게 할 부분에(C,U,D) @Transactional 을 붙여 오버라이딩하면 writable해진다.



#### Reference)

#### https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactional-query-methods

#### https://stackoverflow.com/questions/39827054/spring-jpa-repository-transactionality

#### https://freedeveloper.tistory.com/159