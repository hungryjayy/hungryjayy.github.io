# SpringBoot

## 200220

# 용어

* Controller : 비즈니스 로직 처리, 세분화가 필요할 경우 적절한 service에 전달
* Service: DAO로 데이터베이스에 접근, DTO로 전달
* DAO : Data Access Object
* DTO : Data Transfer Object



### boot vs spring

- Boot는 Spring을 잘 사용하기 위해 나온 것
- starter를 통해 내부 디펜던시 관리.
- 빈에 대한 자동설정이 적용되어 생성됨(Auto-configuration)



### DB 연계

#### Spring boot JPA

- 실무에서 많이 사용.
- JPA를 추상화

#### Spring Data JDBC

- DDD를 더 잘 쓰기 위해서 나왔다 하더라
- Data JPA에서 불필요한 부분, 복잡한 부분을 덜어냈다고 하더라
- 조금 Data JPA에서 간소화시킨 버전?
  - 스펙이 작다. 기능이 없다. 이런 관점은 아님
  - 가볍게 만들었다? 정도??



## Bean?

* POJO(plain, old java object)
* IoC 컨테이너에 의해 인스턴스화, 관리, 생성 됨. ++ thread safety 보장
* 기본적으로 싱글톤으로 생성되는 것 + 기존 싱글톤의 문제를 해결
* XML파일로 관리



#### Reference) 

#### https://gmlwjd9405.github.io/2018/11/10/spring-beans.html

#### https://velog.io/@yhh1056/%EC%8A%A4%ED%94%84%EB%A7%81-Bean-Configuration-Singleton

### Restapi에서



## Annotation



### @Componenet : 클래스를 Bean으로 등록



### @Repository : @Component + DAO 관련 장점 (unchecked 예외 처리)



### @Service : @Component + 서비스 레이어 명시



### @Controller : @Componenet + 컨트롤러에서 사용할 어노테이션



#### Reference) https://m.blog.naver.com/writer0713/220695884239



### @Transactional : 선언적 트랜잭션

* 클래스나 메서드에 이 어노테이션이 추가되면 트랜잭션 기능이 적용된 프록시 객체가 생성
* 이 프록시 객체는 `@Transaction` 이 포함된 메소드 호출시 `PlatformTransactionManager`를 사용해 트랜잭션 시작
  * 이후 commit or rollback
* 비즈니스를 하나의 트랜잭션 단위로.
  * 트랜잭션의 성질
    * 원자성: 한 트랜잭션 내의 작업은 하나로 간주. commit or roll back
    * 일관성: 일관성 있는 DB.(data integrity 만족)
    * 격리성: 동시 실행되는 트랜젝션이 서로 영향이 없도록 격리
    * 지속성: 트랜잭션을 성공적으로 마치면 결과가 항상 저장.

#### 다수의 트랜잭션 경쟁시 발생하는 문제

1. Dirty read: 트랜잭션 A가 어떤 값을 1에서 2로 변경하고 커밋이 안됐을 때 B가 해당 값을 읽으면 2가 조회 됨.
   * 이 때 A가 롤백되면 B는 잘못된 값을 읽은게 됨
2. Non-repeatable read: A가 값 1을 읽고 또 해당 쿼리를 실행하기 전에 B가 값을 2로 바꾸고 커밋하면 A의 쿼리 결과가 달라짐.
   * Dirty read에 비해 확률 적다
3. Phantom Read: 2번과 비슷한 상황. B가 테이블에 값을 추가하면 A의 두 쿼리는 결과가 달라짐



#### 옵션

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
   * 런타임 예외 발생시 롤백
   * rollbackFor 속성: 특정 예외 발생시 강제로 Rollback
     * `@Transactional(rollbackFor=Exception.class)`
   * noRollbackFor 속성: 예외 발생시 rollback 처리 x
     * `@Transactional(noRollbackFor=Exception.class)`
5. Timeout 속성
   * `@Transactional(timeout=10)`



#### Reference) https://goddaehee.tistory.com/167



### 에러처리

### @ControllerAdvice

* 에러를 한 곳에서 처리(각각의 Controller의 에러 처리 로직은 비슷비슷 할 것이기 때문)
* @Restcontrolleradvice = @ControllerAdvice + @ResponseBody



### @ExceptionHandler

* @Controller, @RestController의 Bean에서 발생한 예외를 잡아서 한 메서드에서 처리
* 인자로 캐치하고 싶은 예외 클래스 등록
* 여러개도 가능
  * e.g. ) `@ExceptionHandler({ Exception1.class, Exception2.class })`



### @ResponseStatus 

* Controller나 Exception의 return에 status 정보를 설정하여 리턴해줌.



#### Reference) 

#### https://jeong-pro.tistory.com/195







### @RequestParam`

* Get mapping에서 url의 parameter를 받음



### @RequestBody

* post mapping에서 Body로 받을 때



## DTO(data transfer object)

* Layer간 데이터 전송을 위함.
* Domain model 객체를 그대로 두고, 복사하여 다양한 presentation logic을 추가한 정도
* 가변적.(추후 요구사항에 따라 값 자체가 변할 수는 있음)
* 컨테이너 역할(따라서 비즈니스 로직 등 behavior 갖지 않음)



### DTO와 엔티티를 분리하는 이유

* 만약 프론트에서 dto 객체의 구성이나 네이밍등의 변경사항이 있을경우 엔티티를 그대로 쓰면 DB 컬럼명까지 까지 수정해줘야하는 경우가 생길 수 있으니 분리한다.



### DTO에 관한 고찰

DTO 위치에 대해 정해진건 없다. 다만 service 혹은 controller에서 사용하는 것은 맞는 듯..

- Controller에서 DTO를 Entity로 변환시켜 Service에게 파라미터로 보내기.
- Controller에서 DTO 자체를 Service에게 파라미터로 보내고, Service가 Entity화 시키기.
- Service가 Controller로 응답을 보낼 때, Entity를 반환하고 Controller가 이를 DTO로 변환하여 사용하기.
- Service가 Controller로 응답을 보낼 때, Entity를 DTO로 변환시켜 반환하기.

#### Reference )

#### https://xlffm3.github.io/spring%20&%20spring%20boot/DTOLayer/

#### https://dbbymoon.tistory.com/4

#### https://github.com/HomoEfficio/dev-tips/blob/master/DTO-DomainObject-Converter.md



* 작은 프로젝트일 수록 DTO가 필요없을 수 있다.
* 큰 프로젝트 일 수록 DTO가 힘을 발휘할 것 같다.



### 나의 정리

* Service에서 Repo에 넘겨줄때
  * DTO to Entity(domain)
    * `fooEntity = FooEntity.of(fooDto);`  or `FooEntity.toEntity(fooDto);`
      * repository에 저장하는 entity는 request에 의존적이지 않고 entity 의존적이어야 한다.
    * service의 private 메서드에서 처리
    * 주로 빌더패턴(Kotlin에서는 딱히 불필요?)
      * Java에서 builder pattern
        * 코틀린에서는 인자에 넣을 때 인자가 많아도 어떤 인자에 맵핑되는지 확인 가능하기에 필요없어진다.



* DB에 저장
  * fooEntity = fooRepository.save(fooEntity);



* 저장 결과 DTO로 반환
  * Entity(domain) to DTO
    * `return FooDto.of(fooEntity);`
    * converter에서 담당(responseDTO에서 구현한 converter를 서비스에 주입해서 처리)
      * view나 client쪽으로(바깥으로) 노출하는 작업은 해당 responseDTO에 의존적이어야 한다.



* DTO는 단순한 자료구조로만 사용하는 것이 좋다.
  * 이러한 점에서 DTO에서 SQL이 실행되지 않을 것이다 라고 생각하기.
  * DTO가 Repository를 의존하면 unit test를 작성하기 어려워진다.



## Domain, entity, VO

1. Domain

   * 내가 개발하고자하는 영역

   * e. g) 쇼핑몰을 개발한다고 할 때 아래와같은 그림, 네가지 모두가 도메인 객체 

     ![img](https://t1.daumcdn.net/cfile/tistory/2573BF335976884611)

   * Domain은 Entity와 VO로 나뉜다.

2. Entity

   * 식별자를 갖는다. (id)

   * DB 말고도 객체지향적인 개념으로도 쓰인다.

     * 다만 객체지향의 entity는 행위를 갖는다.
     * DB는 그저 값들을 들고있는 구조체에 불과.

   * 로직을 포함할 수 있다.

     

3. VO(value object)

   * 값 자체가 의미가 있다..? == 어느 상황에도 **동일하게 취급 되는 것**이 핵심
     * e. g. 1) 서비스에서 사용하는 색상클래스 RED가 `new Color(255,0,0);` 이고, 이 서비스 내부에서 red 색상을 사용하고자 할 때 `Color.RED` 를 사용하면 되는 것과 같은?
     * e. g. 2) 돈으로 따지면 `100원`은 어딜가도 `100원`. 
   * 식별자를 갖지 않는다.
   * 핵심 역할은 `equals()`와 `hashCode()`를 갖는 것.
   * 로직을 가질 수 있다.
   * read only(불변하다)



## 패키지 구조

* 크게 계층형과 도메인형이 존재할 듯.



### 계층형

* dao, controller, service 등 각 계층별로 구분
* 전체 구조를 빠르게 파악 가능
* 하나의 디렉터리에 너무 많은 클래스가 모이게 된다.



### 도메인형

* 약간 DDD 스타일

* 관련 코드가 응집되어있다.
  * 한 도메인에 대해 비슷한 스타일로 개발하게 됨?
* 해당 프로젝트 관련 지식이 없는 개발자는 전체적인 구조를 파악하기 어렵다.
  * 장기 프로젝트에서는 퇴사자, 입사자가 있기에, 정말 좋은 도큐먼트를 남길 자신이 없다면 이 구조는 지양



#### Reference) https://cheese10yun.github.io/spring-guide-directory/



## DAO vs Repository

### Repository

* 특정 객체 컬렉션을 관리
* 메모리에 로드되어있다고 가정하고, 메모리 내 특정 객체, 객체 집합, 전체 객체에 접근하기 위함
* 집합의 생명주기 관리를 위해 persistence 로직 사용.
* 이때 도메인 객체가 persistence 로직에 의존
  * 의존성 제거를 위해 Interface, persistence(구현부) 로직으로 분리.
    * 인터페이스 -> 도메인 layer에 속함.
    * 구현부 -> persistence layer에 속함. 
  * `Separated interface` : DIP(dependency Inversion Principle)에 기반한 방법
* ORM 사용시 객체 단위로 테이블 관리하고, 이때 repository가 DAO 역할 수행



### DAO

* 위의 repository와 같이 도메인 로직과 persistence 로직을 분리해 `separation of concerns` 원리 충족의 목적.
  * 하부의 persistence 메커니즘이 DB라는 사실을 숨기지 않는다.
* DAO의 인터페이스는 CRUD 쿼리와 1:1 매핑되는 세밀한 오퍼레이션 제공
  * DB SQL 단위.(service == 트랜젝션 단위, controller == 업무 단위)
  * 단일 데이터 접근.
  * 실제 비즈니스 로직 처리 : 하나 이상의 DAO 조합 == 트랜젝션 단위



#### Refence)

#### http://egloos.zum.com/aeternum/v/1160846

#### https://velog.io/@leyuri/DAO%EC%99%80-Repository-DTO-VO

