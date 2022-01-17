# Domain, entity, VO

## Domain

* 내가 개발하고자하는 영역

* e. g) 쇼핑몰을 개발한다고 할 때 아래와같은 그림, 네가지 모두가 도메인 객체 

  ![img](https://t1.daumcdn.net/cfile/tistory/2573BF335976884611)

* Domain은 Entity와 VO로 나뉜다.



## Entity

* 식별자를 갖는다. (id)

* DB 말고도 객체지향적인 개념으로도 쓰인다.

  * 다만 객체지향의 entity는 행위를 갖는다.
  * DB는 그저 값들을 들고있는 구조체에 불과.

* 로직을 포함할 수 있다.




## VO(value object)

* 값 자체가 의미가 있다..? == 어느 상황에도 **동일하게 취급 되는 것**이 핵심
  * e. g. 1) 서비스에서 사용하는 색상클래스 RED가 `new Color(255,0,0);` 이고, 이 서비스 내부에서 red 색상을 사용하고자 할 때 `Color.RED` 를 사용하면 되는 것과 같은?
  * e. g. 2) 돈으로 따지면 `100원`은 어딜가도 `100원`. 
* 식별자를 갖지 않는다.
* 핵심 역할은 `equals()`와 `hashCode()`를 갖는 것.
* 로직을 가질 수 있다.
* read only(불변하다)



#### Reference)

#### https://multifrontgarden.tistory.com/186

#### https://velog.io/@gillog/Entity-DTO-VO-%EB%B0%94%EB%A1%9C-%EC%95%8C%EA%B8%B0