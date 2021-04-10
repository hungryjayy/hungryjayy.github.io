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



### Annotation

* `@RequestParam`
  * Get mapping에서 url의 parameter를 받음



* `@RequestBody`
  * post mapping에서 Body로 받을 때



### DTO(data transfer object)

* Layer간 데이터 전송을 위함.
* Domain model 객체를 그대로 두고, 복사하여 다양한 presentation logic을 추가한 정도
* 가변적.



#### DTO와 엔티티를 분리하는 이유

* 만약 프론트에서 dto 객체의 구성이나 네이밍등의 변경사항이 있을경우 엔티티를 그대로 쓰면 DB 컬럼명까지 까지 수정해줘야하는 경우가 생길 수 있으니 분리한다.



#### DTO에 관한 고찰

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



#### 나의 정리

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



### Domain, entity, VO

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



### 패키지 구조

* 크게 계층형과 도메인형이 존재할 듯.



#### 계층형

* dao, controller, service 등 각 계층별로 구분
* 전체 구조를 빠르게 파악 가능
* 하나의 디렉터리에 너무 많은 클래스가 모이게 된다.



#### 도메인형

* 약간 DDD 스타일

* 관련 코드가 응집되어있다.
  * 한 도메인에 대해 비슷한 스타일로 개발하게 됨?
* 해당 프로젝트 관련 지식이 없는 개발자는 전체적인 구조를 파악하기 어렵다.
  * 장기 프로젝트에서는 퇴사자, 입사자가 있기에, 정말 좋은 도큐먼트를 남길 자신이 없다면 이 구조는 지양



#### Reference) https://cheese10yun.github.io/spring-guide-directory/



### DAO vs Repository

#### Repository

* 특정 객체 컬렉션을 관리
* 메모리에 로드되어있다고 가정하고, 메모리 내 특정 객체, 객체 집합, 전체 객체에 접근하기 위함
* 집합의 생명주기 관리를 위해 persistence 로직 사용.
* 이때 도메인 객체가 persistence 로직에 의존
  * 의존성 제거를 위해 Interface, persistence(구현부) 로직으로 분리.
    * 인터페이스 -> 도메인 layer에 속함.
    * 구현부 -> persistence layer에 속함. 
  * `Separated interface` : DIP(dependency Inversion Principle)에 기반한 방법
* ORM 사용시 객체 단위로 테이블 관리하고, 이때 repository가 DAO 역할 수행



#### DAO

* 위의 repository와 같이 도메인 로직과 persistence 로직을 분리해 `separation of concerns` 원리 충족의 목적.
  * 하부의 persistence 메커니즘이 DB라는 사실을 숨기지 않는다.
* DAO의 인터페이스는 CRUD 쿼리와 1:1 매핑되는 세밀한 오퍼레이션 제공
  * DB SQL 단위.(service == 트랜젝션 단위, controller == 업무 단위)
  * 단일 데이터 접근.
  * 실제 비즈니스 로직 처리 : 하나 이상의 DAO 조합 == 트랜젝션 단위



#### Refence)

#### http://egloos.zum.com/aeternum/v/1160846

#### https://velog.io/@leyuri/DAO%EC%99%80-Repository-DTO-VO

