# POJO(Plain Old Java Object)

: 오래된 방식의 자바 객체. 자바 언어가 갖는 **객체지향적** 설계와 개발의 장점을 잃지 않고 **특정 환경에 종속되지 않도록**. 

> 우리는 사람들이 자기네 시스템에 보통의 객체를 사용하는 것을 왜 그렇게 반대하는지 궁금하였는데, 간단한 객체는 폼 나는 명칭이 없기 때문에 그랬던 것이라고 결론지었다. 그래서 적당한 이름을 하나 만들어 붙였더니, 아 글쎄, 다들 좋아하더라고. - **마틴 파울러**



## 정의

: **어떠한 제한, 환경, 사양에 종속되지 않는** java object. 따라서 다음의 세 가지를 하지 않고 **객체지향적 원칙**에 충실한 것

1. extends를 통해 클래스를 확장한 것
2. implements를 통해 어떠한 인터페이스를 구현한 구현체
3. annotation을 포함하는 것



* e.g) java 객체가 hibernate 프레임워크를 직접적으로 의존하는 순간 POJO가 아니게 됨.
  * 직접 해당 프레임워크를 의존함으로써 확장성이 떨어짐



### **스프링 프레임워크는 POJO방식의 프레임워크**

* POJO를 이용한 애플리케이션 로직. IoC/DI, AOP, PSA
  * PSA란? Portable Service Abstraction
    * Spring은 표준 인터페이스인 JPA를 통해 Hibernate과 같은 구현체의 변화에 종속되지 않고 이용 가능
* 원격



## 장점

* 이를 통해 코드는 simple해진다. 개발자는 이용하고자 하는 기술의 low level을 깊이 알기보다 비즈니스 로직에 집중할 수 있음
* 테스트에 용이하고 유연함. 환경에 종속되면 자동화 테스트가 어려움
* 객체지향적 설계 가능, 따라서 객체지향적 이점을 가져갈 수 있음. 다형성, 상속 등



## POJO 예시

### POJO를 적용하지 않은 예

```java
public class ExampleListener implements MessageListener {
    
  public void onMessage(Message message) {
    if (message instanceof TextMessage) {
      try {
        System.out.println(((TextMessage) message).getText());
      }
      catch (JMSException ex) {
        throw new RuntimeException(ex);
      }
    }
    else {
      throw new IllegalArgumentException("Message must be of type TextMessage");
    }
  }
    
}
```

* 위 예제에서는 JMS를 위해 MessageListener를 구현하고 있음. 따라서, 해당 인터페이스에 종속된 것. 이 상황에서 종속성으로 인해, 다른 Messaging system으로 대체하고자 할 때 큰 비용이 발생할 수 있음



### POJO를 적용한 예

```java
@Component
public class ExampleListenerPojo {

    @JmsListener(destination = "myDestination")
    public void processOrder(String message) {
        System.out.println(message);
    }
}
```

* 위의 예제와 비교했을 때 굳이 해당 인터페이스를 implement 하지 않아도, 어노테이션을 통해 받았으므로, 추후 변경 시점에 해당 어노테이션을 교체하면 전체가 갈아 끼워짐. -> 비용 감소



#### Reference)

#### https://ko.wikipedia.org/wiki/Plain_Old_Java_Object

#### https://happyer16.tistory.com/entry/POJOplain-old-java-object%EB%9E%80

#### https://siyoon210.tistory.com/120

#### https://m.blog.naver.com/writer0713/220700687650

#### https://limmmee.tistory.com/8

#### http://asuraiv.blogspot.com/2017/07/spring-pojo.html