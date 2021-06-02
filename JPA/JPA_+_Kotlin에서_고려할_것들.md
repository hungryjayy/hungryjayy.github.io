# JPA + Kotlin에서 고려할 것들

## Data class

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