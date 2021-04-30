# JPA



## 연관관계

객체 연관관계와 DB 연관관계를 어떻게 매핑할지를 고민

* 이를 통해 객체지향과 DB사이의 패러다임 불일치 해결



### 필요한 이유?

* 객체지향적으로 설계하기 위해
* 테이블에 맞춰 데이터중심으로 모델링하는 경우




* 객체 관점, DB 관점 존재
  * DB관점에서는 외래키 하나로 양쪽 테이블을 조인한다. 따라서 나눌 필요 X
  * DB관점은 방향성이 없고 객체 관점은 방향성이 있다.



### 단방향 연관관계

* 한 객체만 참조용 field를 갖는다.
* 객체와 테이블의 연관관계
  * 참조를 사용하는 객체 연관관계는 단방향
  * FK를 사용하는 테이블 연관관계는 양방향
* `@ManyToOne` : N : 1 관계라는 매핑 정보.
  * 다대일 관계에서 항상 "다" 쪽이 외래키를 가진다.
* `@JoinColumn` : 외래 키 매핑할 때 사용
  * 이 annotation을 통해 연관관계 맵핑



### 양방향 연관관계

* 두 객체 모두 참조용 field를 갖는다.

  * 단, 테이블에서는 한쪽 테이블만(다대일에서 다) FK 하나만 가진다.

  

* 단순히 단방향보다 조회를 편하게 하기 위한 용도
  
  * 어차피 조회할 수 있는 기능만 있다.(주인 개념때문에)
  
  
  
  #### **주인**: 연관 관계에서의 FK를 관리하는 주체(주인만이 FK 변경 가능)
  
  * 비즈니스(도메인)적으로 중요하지 않다.
  
  * 주인은 테이블 등록, 수정, 삭제 등 제어의 권한 있음
  
  * 주인이 아니면 read only(DB관점)
    
    * 객체 관점에서는 수정같은게 있을 때 둘다 수정해 데이터 동기화를 해줘야 한다.
    
  * **외래 키가 있는 곳을 주인으로 설정**
  
  * 다대일 관계에서 다가 갖고있는 일의 외래키부분이 연관관계 주인.
    
    * 이렇게 해야 성능 이슈가 없다.
    
  * **주인이 아닌쪽은 읽기만 가능**
  
    ​	**e.g. 1) Member에 Team값을 업데이트 하면 mappedBy로 매핑된 Team의 members로 업데이트 된 DB의 멤버들을 읽을 수 있다는 이야기이다.**
  
    ​	**e.g. 2)Member에 Team 정보 update 했는데, Team에서 getMembers로 가져왔는데 반영 안됐네? -> 연관관계 설정 안했구나.. 라고 바로 알 수 있다.**
  
    출처: https://ict-nroo.tistory.com/122 [개발자의 기록습관]
  
    
  
* 이걸 많이쓰면 복잡해질 수 있음
  
* 객체 관점에서는 양쪽 객체 모두에게 참조를 설정해줘야 한다.
    * 여기서 실수가 나면 양방향이 깨지는 관계가 존재하게 됨.
  * 모두 양방향으로 설정하게 되면 User같은 엔티티는 아주 많은 테이블과 연관 관계를 맺게 됨.
  
  
  
* `@OneToMany(mappedBy = " ")` 를 통해 연관관계 매핑

  * 주인은 mappedBy 속성 사용 x @JoinColumn 사용
  * 주인이 아니면 mappedBy로 주인(필드) 지정



**좋은 것은 일단 단방향 매핑으로 하고 나중에 역방향 객체 탐색이 꼭 필요할 때 추가해주기**



#### Reference)

#### https://velog.io/@conatuseus/%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84-%EB%A7%A4%ED%95%91-%EA%B8%B0%EC%B4%88-1-i3k0xuve9i

#### https://jeong-pro.tistory.com/231

#### https://ict-nroo.tistory.com/122





## Annotation



### `@Id`

* 직접 할당을 원한다면 @Id에 set
* 직접 할당 아니라면 자동 생성(`@GeneratedValue`), 전략 네 가지 중 하나 선택



### `@GeneratedValue`

* Generate 방식에는 네가지 전략 존재
  * IDENTITY
    * 기본 키 생성을 DB에 위임(DB에 의존적)
    * DB에 값을 저장한 후 기본 키 값을 얻을 수 있다.
  * SEQUENCE
    * 시퀀스를 이용해 기본 키 할당(DB 의존)
  * TABLE
    * 키 생성 테이블을 사용하는 방법 - 모든 DB 적용 가능
    * 테이블에 이름과 값으로 사용할 컬럼을 만드는 방법
  * AUTO
    * DB에 따라 알아서 하나 선택해주는 방법



#### Reference)

#### https://coding-start.tistory.com/71

#### https://ithub.tistory.com/24



## JPA + Kotlin



### data class

* JPA 사용 시 Kotlin에서는 entity를 data class로 선언하지 않음
  1. 코틀린은 기본적으로 상속을 막아놓고 `open` keyword를 추가해 상속 가능 class로 변경 가능. 그런데, data class에서는 open keyword 조차 불가
     * JPA의 지연 로딩
       * 지연 로딩 객체를 실제 참조하기 전까지는 Proxy객체로 참조, 이 Proxy 객체는 Entity를 상속해 만들어짐.
  2. Data class construct 시점에 제공받는 `hashCode()`
     * ID는 더 추후에 JPA가 세팅해준다.
  3. 양방향 연관관계일 경우에는 `hashCode()`, `equals()` 를 사용 할 경우 순환참조 문제도 발생



## Springboot + Kotlin + JPA 고려 할 것들

* Entity는 public, protected의 매개변수가 없는 "기본 생성자(No-argument 생성자)"를 가지고 있어야 한다.
  * 클래스 상태를 초기화 할 때 기본 생성자를 이용하기 때문.
  * 코틀린에서는 `@Entity` / `@Embeddable` / `@MappedSuperClass` annotation이 붙어있을 때 자동으로 no-arg 생성자를 생성해준다.



* Entity 클래스 뿐 아니라 클래스 내부 모든 메서드, persist되는 프로퍼티는 final로 지정 불가
  * open class로 만들어주어 상속이 가능하도록 해야함
  * Lazy loading 이라는 특성 때문. 런타임에 proxy 객체를 통해 entity를 상속하고 이를 통해 참조해야함.



* Entity의 상태는 JavaBean style에 부합하는 인스턴스 변수로 표현. 인스턴스 변수는 Entity 자기 자신에 의해서만 접근 되어야 함.
  * 따라서 Kotlin에서는 프로퍼티에 protected set 과 같은 키워드로 제한
    * 이전에 설정해놓은 open이 프로퍼티에도 적용되어, private set으로 할 경우 에러가 발생
    * 따라서 set을 해야하는 경우 protected set, set이 필요 없는경우 val로 지정
  * update logic을 수행해야 할 때는 entity 내부의 메서드를 이용



#### Reference)

#### https://blog.junu.dev/37





### 다대다 연관관계

* n:m 테이블 보다는 연결 테이블을 설정해 관리해야함
  * RDBMS에서는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없기 때문
  * 한계: 개발 과정에서 추가 데이터가 필요할 수 있으나, 맵핑 정보 외에 추가 정보를 넣는 것이 불가능



* 연결 테이블을 Entity로 승격
  * 양 테이블의 FK를 묶어서 PK로 사용 할 수 있음
  * 실제로는 독립적인 연결 엔티티만의 id를 사용하는 것이 권장.
    * ID가 두 테이블에 종속되지 않을 수 있음.



#### Reference) 

#### https://ict-nroo.tistory.com/127



## In Clause

* `findBy~~In` 의 인자로 List를 받으면 자동으로 in clause로 변경 됨

  * Query로 치면 `where ~~ in list` 와 같은 형태라고 보면 됨

* `select ... from Table where id in (id1, id2, id3)` 이와 같은 형태로

  * 그런데 여기서 ids는 고정된 크기의 객체가 아님

  * e.g) 5개씩 잘라서 받으며, 12개의 id를 받는다고 가정한다면 아래와 같은 형태로 받게 될 것

    ``` mysql
    select .... from Sample where id in (? ,? ,?, ?, ?);
    select .... from Sample where id in (? ,? ,? ,? ,?);
    select .... from Sample where id in (? ,? );
    ```

  * 이러한 경우 11, 12, 13, 14, 15개의 id의 리스트를 받을 때를 고려하면 총 5개의 쿼리가 생겨버림
  * 문제점
    * DB 관점에서 preparedStatement 효과를 누리지 못하게 됨
    * 힙 메모리 점유됨
    * 1000개 단위로 fetch해 온다면, 1000개의 다른 execution plan cache를 발생하게 될 것
  * 해결
    * in_clause_parameter_padding 옵션 지정 해주기 -> 2의 제곱 단위로 padding
    * 직접 padding
    * execution plan cache 사이즈 조정 이 경우에 2048부터해서 줄여가며 메모리 상황을 보아야 함



#### Reference)

#### https://meetup.toast.com/posts/211