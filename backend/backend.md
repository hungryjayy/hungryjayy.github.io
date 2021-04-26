## 일급 컬렉션

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



### Java(Kotlin) + Rest

* ResponseEntity
  * Http 응답에 해당하는 header, body를 포함하는 클래스
  * HttpEntity를 상속, Body Type 지정 가능.



#### Reference ) https://devlog-wjdrbs96.tistory.com/182



## CORS

: Cross-Origin Resource Sharing

* CORS란
  * HTTP 헤더를 사용해 애플리케이션이 다른 origin 리소스에 접근할 수 있게 하는 매커니즘.
  * 또한, 다른 origin에서 나의 resource에 함부로 접근하지 못하게 하기 위해 사용
* 동작 방식
  * 브라우저가 리소스 요청 시 헤더에 추가 정보를 담아 보냄
    * 내 origin은 무엇이고, 어떤 메소드를 사용해서 요청을 할 것이고, 어떤 헤더들을 포함할 것인지
  * 서버는 서버가 응답할 수 있는 origin들을 헤더에 담아서 브라우저에게 보냄
    * 브라우저가 헤더를 보고 해당 origin에서 요청할 수 있다면 리소스 전송을 허용하고 만약 불가능하다면 에러를 발생
* 필요 이유: 보안상의 이유
  * 서비스하고 있지 않은 브라우저에서 세션을 요청해 획득한다면 악의적인 행동을 할 수 있음



### 헤더 목록

* Request(서버에게)
  * Origin
  * Access-Control-Request-Method(`preflight` 과정에서)
    - 실제 요청에서 어떤 메서드를 사용할 것인지
  * Access-Control-Request-Headers(`preflight` 과정에서)
    - 실제 요청에서 어떤 header를 사용할 것인지
* Response(서버에서)
  - Access-Control-Allow-Origin
    - 브라우저가 해당 origin이 자원에 접근할 수 있도록 허용
    - 혹은 `*`은 credentials이 없는 요청에 한해서 모든 origin에서 접근이 가능하도록 허용
  - Access-Control-Expose-Headers
    - 브라우저가 액세스할 수있는 서버 화이트리스트 헤더를 허용
  - Access-Control-Max-Age
    - 얼마나 오랫동안 `preflight`요청이 캐싱 될 수 있는지
  - Access-Control-Allow-Credentials
    - `Credentials`가 true 일 때 요청에 대한 응답이 노출될 수 있는지를 나타냄
    - `preflight`요청에 대한 응답의 일부로 사용되는 경우 실제 요청을 수행 할 수 있는지를 나타냅니다.
    - 간단한 GET 요청은 `preflight`되지 않으므로 자격 증명이 있는 리소스를 요청하면 헤더가 리소스와 함께 반환되지 않으면 브라우저에서 응답을 무시하고 웹 콘텐츠로 반환하지 않음
  - Access-Control-Allow-Methods
    - `preflight`요청에 대한 대한 응답으로 허용되는 메서드들을 나타냄
  - Access-Control-Allow-Headers
    - `preflight`요청에 대한 대한 응답으로 실제 요청 시 사용할 수 있는 HTTP 헤더를 나타냄



### 멱등(idempotent)

* Get (멱등)
  * Preflight x
  * 클라이언트, 서버간 간단한 통신.
  * CORS 헤더를 사용해 권한 처리
* 비멱등
  * 요청에 압서 Preflight(사전전달)을 해야한다.
    * Handshake 절차.



#### Reference)

#### https://hannut91.github.io/blogs/infra/cors

#### https://zzossig.io/posts/web/what_is_cors/

#### https://developer.mozilla.org/ko/docs/Web/HTTP/CORS

#### https://medium.com/@woody_dev/cors-cross-origin-resource-sharing-cea401fb79b6



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



### Was vs Web Server

* Was(Web application server)
  * 사용자 요청에 맞게 동적인 컨텐츠를 전달
  * Web server + Web container
  * DB 서버가 같이 수행
  * 비즈니스 로직 처리



* Web server(e.g. nginx)
  * 정적인 컨텐츠를 반환한다.(html, css 등)
  * http 프로토콜 기반으로 클라이언트 요청을 서비스
  * 클라이언트 요청에 대해 가장 앞에서 요청 처리



#### Was는 Web server가 하는 일을 다 할 수 있다.

* 효율적인 분산처리를 위해 Web server도 사용해야 함
* 보통 Web server에 Was가 플러그인같이 붙은 형태



#### Reference)

##### https://gmlwjd9405.github.io/2018/10/27/webserver-vs-was.html

##### https://jeong-pro.tistory.com/84