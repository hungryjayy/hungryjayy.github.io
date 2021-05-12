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
  * 이 때, 사용부인 service에서 일일히 하는 방법, repository에서 미리 선언해주는 방법.

* **JPA 제공 기본 메소드 구현부** 
  * JpaRepository, CrudRepository 인터페이스 말고 SimpleJpaRepository.java 확인 



#### Reference)

#### https://stackoverflow.com/questions/39827054/spring-jpa-repository-transactionality

#### https://freedeveloper.tistory.com/159





### Transaction 적용이 안되는 몇가지 문제

* Private - 오버라이딩 문제
  * Transaction 처리 과정에서 프록시 객체로 등록하기 때문에 private 불가
  * 마찬가지로 final이 적용된 메서드도 불가능
    * Kotlin에서 allOpen을 통해, 혹은 Open을 통해 가능



* inner Method
  * Transaction 적용되지 않은 메소드에서 @Transactional적용된 내부 메소드 호출 과정은 proxy 객체를 이용해 호출하는 것이 아니라, 내부 호출로 되기 때문에 transaction 적용 불가
  * 이 때 savePost를 호출하는 메소드로 @Transactional 를 옮겨가면 해결
    * 이 때 @Transactional 처리 된 메서드 내부에서 부르는 모든 곳은 atomic하게 처리 됨.(백기선님 블로그 참고)

```java
@Transactional
public void savePost() {
    Post post = new Post();
    post.setTitle("keesun");

    Post newPost = postRepository.save(post);
    System.out.println(postRepository.findById(newPost.getId()));
    System.out.println(entityManager.contains(newPost));
}

// 출처: https://www.whiteship.me/spring-transactional-and-spring-aop/
```



* ReadOnly Method에서 Read-write method 호출
  * read-write 내부에서도 transaction은 readOnly로 동작하게 됨.
  * 반대의 경우도.. 라는데 확인할 필요가 있음



#### Reference)

#### https://www.whiteship.me/spring-transactional-and-spring-aop/

#### https://www.whiteship.me/jpa-entitymanager-contains/

#### https://handr95.tistory.com/3

#### https://kapentaz.github.io/spring/Spring-Transaction-%EC%A0%81%EC%9A%A9-%EB%B2%94%EC%9C%84-%EC%A0%9C%EC%96%B4-%EB%B0%A9%EB%B2%95/#

#### https://netframework.tistory.com/entry/Spring-Transactional%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC





### Default @Transactional

* 기본적으로 SingleJpaRepository (JPA interface 구현체부분)에 가보면 전체가 Transaction으로 감싸져있음.

* 그러나, 커스텀 메소드(query method)에는 @Transactional을 선언해줘야 함.

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

