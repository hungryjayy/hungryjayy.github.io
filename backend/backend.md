### 일급 컬렉션

```java
public class GameRanking {

    private Map<String, String> ranks;

    public GameRanking(Map<String, String> ranks) {
        this.ranks = ranks;
    }
}
```



* 컬렉션을 wrapping하면서 그 외 다른 멤버변수가 없는 상태
  * 비즈니스에 종속적인 자료구조
    * e.g. 어떠한 객체에 대한 검증로직같은 것이 필요할 때 해당 객체를 쓸 때마다 검증로직을 구성해야하는데 일급 컬렉션을 통해 이를 방지 할 수 있음.
  * 불변성이 필요하다면 Collection 내부 각각들의 불변성(필요하다면 불변이 아니어도 됨)
  * 상태, 행위를 이곳에서 관리
    * 해당 컬렉션을 사용하는 클래스에서는 말그대로 사용만 하면 됨.



* Car라는 클래스 객체 3개를 모두 관리해야할 때 Cars 쓰는것처럼

  * cars 하나의 인스턴스로 비즈니스로직 관리 가능



### Rest api

: API를 설계 할 때 자원을 나타내는 URI가 있고, http method를 통해 자원을 어떻게 처리할 지 설계하는 방식의 아키텍처

* 구성

  * 자원(resource) - URI / 행위 - HTTP method / 표현 representations




* 특징

  * URI로 지정된 리소스에 대한 조작을 한정적인 Interface를 통해 수행하는 아키텍처
  * Stateless
    * 작업을 위한 (client의)상태정보를 따로 저장 안함
    * 단순히 들어온 요청만을 처리 -> 불필요한 정보 관리 x
    * 만약 stateful하다면 서버가 클라이언트의 현재 상태를 저장해야한다. 따라서 클라이언트의 상태는 서버에 종속된다.
      * 이 경우의 로드밸런싱을 하는 경우에 서버가 클라이언트의 상태를 공유할수있는 redis같은 시스템이 필요.
    * Stateless하기 때문에 어느 서버가 처리하던 클라이언트의 요청은 동일하게 처리 가능.
  * client-server 구조: 서버는 API를 제공, 클라이언트는 세션이나 로그인 정보를 관리. 각각의 역할이 구분되고 의존성이 줄어들고 확장성은 높아진다.




* 장점

  * 원하는 타입으로 데이터를 주고받을 수 있다.
  * http method 타입과 uri만 읽어도 해당 인터페이스가 어떠한 기능과 연결되는지 파악이 용이하다
  * client-server구조. api로 들어오는 요청을 처리해 요청한대로 보내주기만 하면 된다. 각자의 역할이 명확히 분리 




* 단점

  * 복잡한 비즈니스에서 http method의 한계로 인해 모든 경우를 cover할 수 없을 것 같다.
  * 공식화 된 Rest API 표준이 존재하지 않다는 점도 아쉬운 점 중 하나.



#### 좋은 restapi를 구성하기

* resource는 복수형, 명사 - 의미상 이게 명확
  * `/rooms/{roomId}` : 모든 rooms 중 roomId에 해당하는 room
  * `/rooms/` : 모든 rooms
* get, post, put, delete 각각의 http method 각각의 성격을 위반 x
  * e.g. get 메서드는 어떠한 상태를 변경하지 않음.
  * post는 값을 읽기만 하지는 않음.
    * 보안상 취약하다고 이걸 사용하지 말고 https 통신을 하도록 해서 해결하는 것이 바람직.
* API version을 갖게 하는 것도 관리 측면에서 좋음
* camel case보다는 dash나 snake case가 좋아보임
  * 그러나 팀원과의 상의 필요
  * 개발자 뿐 아니라 일반 사용자들도 html을 통해 접근 할수 있음을 염두
  * restapi는 애초에 원활한 커뮤니케이션을 목적으로 하기 때문
* HATEOAS - restful하게 만드는 특징
  * client는 link를 통해서 api에 접근 가능.
  * server에서 URI를 변경해도 문제가 생기지 않음
    * client와 server의 분리
* filtering, sorting, field 선택, paging 등을 제공
* Error 처리
  * 200, 300, 400, 500대 http 상태 코드 숙지



##### Reference)

##### https://www.slideshare.net/brotherjinho/restful-api-64494716

##### https://www.rocketpunch.com/cards/post/436146





#### http api vs rest api

* http api

  * http를 사용해서 서로 정해둔 스펙으로 통신
  * 넓은 의미의 restapi

* rest api(restful api)

  : http api와 같으나 추가적으로 **restful한 네가지 특징**을 갖는다.

  * 자원 식별: 각각의 자원은 URI를 통해 식별 가능해야한다.

    `GET /room/1`

  * 표현을 통한 리소스 조작: 자원을 묘사한 표현을 전송한다.

    * GET, POST, PUT, DELETE

    * 따라서 서버 코드에 얽매이지 않고 client 구현 가능.

    * 서버의 수정에도 영향을 받지 않는다.

      e.g) `GET http://www.example.com/v2/apple`

  * 자기 서술형 메시지: 수신자가 이해하기 위한 모든 정보를 가지고 있어야 한다.

    * 메시지를 이해하기 위해 내용까지 살펴봐야 한다면 자기서술적이지 않다.

  * **hateoas : 클라이언트가 리소스에 접근하기를 원한다면, server가 응답을 줄 때 hyperlink를 추가해서 보내준다. 다음에 클라이언트가 어떤 API를 호출해야하는지는 해당 링크를 통해 받을 수 있다. **

    * **이 원칙을 통해 클라이언트와 서버는 완전하게 분리됨.**

    * **URI 등이 바뀌어도 클라이언트에서는 자동으로 연결됨**

      * **클라이언트에게 자원을 보내면서 다음에 연결할 URL을 링크로 같이 보내기 때문에**

      > 이 원칙이 중요한 것은 이렇게 함으로서 클라이언트와 서버간의 완전한 분리가 이루어지게 됩니다. 만약 서버의 자원을 나타내는 URI 가 변경되었을 경우 클라이언트는 서버의 변화에 종속적으로 그 정보를 클라이언트 정보에 추가하게 됩니다. (SPA 상에서 href 데이터를 바꾸어 줘야함) 하지만 HATEOAS를 제대로 적용했을 시 아래와 같이 _links.profile 에 대한 href정보만 조회해주면 되므로 서버에서 URI정보가 바뀌어도 클라이언트 측에서 소스 변경없이 그대로 사용할 수 있게 됩니다.
    >
      > 출처: https://engkimbs.tistory.com/855 [새로비]
  
    **(ex)**

```
"_links" : {
  "self" : {
    "href" : "http://localhost:8080/api/events/46"
  },
  "profile" : {
    "href" : "/docs/index.html#resources-events-get"
  }
}
```



출처: https://engkimbs.tistory.com/855 [새로비]



##### reference) 

##### https://www.inflearn.com/questions/126743

##### http://amazingguni.github.io/blog/2016/03/REST%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4-1



### RPC (vs restapi)

* request parameter, reseponse parameter를 알아야한다.
  * 따라서 양쪽의 인터페이스 규약을 정의한 후, Skeleton, Stub 코드를 이용.	
* client, server간 연관관계가 돈독해진다(?), 즉 의존성 증가
* 계층간 함수명 등이 그대로 노출되는 구조라서 마이크로 서비스 내부 시스템, 디자인이 그대로 노출된다.



### TDD

test 주도형 개발. 기능 추가 전 테스트 먼저 작성.

*cf) given, when, then 기법*



* 장점:
  * 리팩토링에 용이. 크기가 커진 함수를 여러 함수로 나누는 과정에서 테스트 코드를 통해 계속 확인을 해가며 리팩토링을 해 중심을 잡을 수 있다.
  * 새로운 기능을 추가했을 때 해당 기능에 대한 테스트는 물론, 기존의 테스트들도 다 잘 돌아가는지 확인해 새로운 기능에 대해 신뢰를 할 수있다.



* 단점
  * 생산성이 줄어든다.
  * 모든 기능에 대해 100% 테스트코드를 작성할 수 없는 상황이 발생할 수 있다. 이 때 전략패턴과 같은 기법을 통해 개선할 수 있지만, 테스트를 위해 본 메서드를 변경하는 것 자체가 생산성을 저하시킨다.



### 디미터 법칙

* 객체가 자기 자신을 책임지는 자율적인 존재이다.
* 따라서 객체 내부 구조를 묻지 말고 무언가를 시켜라.



#### 디미터 법칙을 어긴 코드

``` kotlin
object.getChild().getContent().getItem().getTitle()
```

* 기차 충돌(train wreck) : 이와 같이 `getter` 가 줄줄이 이어진 코드
  * 이러한 설계는 객체들이 어떻게 연결되어있는지를 보여준다.
  * 객체 구조(연결)이 변경될 수 있으므로 프로그램은 불안정해진다.



#### 디미터 법칙이 하나의 .을 강제하는 규칙은 아니다.

```kotlin
IntStream.of(1, 15, 2)
  .filter(x -> x > 10)
  .distinct()
  .count();
```

* 이와 같은 코드가 기차 충돌을 초래하기 때문에 디미터 법칙을 위반한다고 생각할 것이다.
  * 하지만 `of`, `filter`, `distinct` 메서드는 모두 `IntStream` 이라는 동일한 클래스 인스턴스를 반환한다. 즉 이들은 `IntStream` 인스턴스를 또다른 `IntStream`인스턴스로 변환한다.
  * 따라서 이 코드는 디미터 법칙을 위반하지 않는다.



#### 디미터 법칙은 오직 결합도와 관련 된 것.

* 객체의 내부구조가 외부로 노출되는 경우에 해당됨.
* e. g) 이 관점에서 보면 racingCar 프로젝트에서 car의 위치들을 비교해 winner를 구하는 로직 또한 Cars가 아니라 Car에서 하는게 맞는 것.






