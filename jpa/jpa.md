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
     * 