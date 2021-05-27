# Rest api

: API를 설계 할 때 자원을 나타내는 URI가 있고, http method를 통해 자원을 어떻게 처리할 지 설계하는 방식의 아키텍처

* ```
  자원(resource) - URI / 행위 - HTTP method / 표현 representations



## 특징

* URI로 지정된 리소스에 대한 조작을 한정적인 Interface를 통해 수행하는 아키텍처
* **Stateless**
  * client의 상태정보를 따로 저장 안하고 단순히 들어온 요청만을 처리 -> 불필요한 정보 관리 x
  * 만약 stateful하다면 서버가 클라이언트의 현재 상태를 저장해야한다. 따라서 클라이언트의 상태는 서버에 종속된다.
    * 이 경우의 로드밸런싱을 하는 경우에 서버가 클라이언트의 상태를 공유할수있는 redis같은 시스템이 필요.
  * Stateless하기 때문에 어느 서버가 처리하던 클라이언트의 요청은 동일하게 처리 가능.
* client-server 구조: 서버는 API를 제공, 클라이언트는 로그인 정보를 관리. 각각의 역할이 구분되고 의존성이 줄어들게 됨

### 장점

* 원하는 타입으로 데이터를 주고받을 수 있다. -> 보통 JSON
* http method 타입과 URI만 읽어도 해당 인터페이스가 어떠한 기능과 연결되는지 파악이 용이
* client-server구조. api로 들어오는 요청을 처리해 요청한대로 보내주기만 하면 된다. 각자의 역할이 명확히 분리 

### 단점

* 복잡한 비즈니스에서 http method의 한계로 인해 모든 경우를 cover할 수 없을 것 같다.
* 공식화 된 Rest API 표준이 존재하지 않다는 점도 아쉬운 점 중 하나.



## REST API 6가지 원칙

1. **Uniform Interface**

* URI로 지정한 리소스에 대한 조작을 일관된 인터페이스로 수행

2. **Stateless**

* 세션이나 쿠키를 별도로 저장하고 관리하지 않음. 요청만 처리
* 서비스의 자유도가 높아지고 **구현이 단순**해짐

3. **Caching**

* HTTP 웹 표준을 그대로 사용하기 때문에 HTTP 웹 기존 인프라를 활용 가능.
  * 따라서 캐시 기능 가능

4. **Client-Server**

* REST 서버는 API 제공, 클라이언트는 사용자 인증이나 컨텍스트(세션, 로그인 정보)등을 관리하는 구조

5. **Hierarchical system**

* 다중 계층으로 구성 가능.
  * Proxy, 게이트웨이 등의 네트워크 중간 매체도 사용할 수 있음
  * 보안, LB, 암호화 계층도 추가 가능

6. **Self-descriptiveness**

* REST API 메시지만 보고도 쉽게 이해할 수 있도록 자기서술적이다.

  ```http
  HTTP/1.1 200 OK
  \[{ “op” : “remove”, “path” : “a/b/c"}\]
  ```
  이러한 메시지에서

  ```http
  HTTP/1.1 200 OK
  Content-Type: application/json-patch+json
  \[{ “op” : “remove”, “path” : “a/b/c"}\]





#### Reference)

#### https://sabarada.tistory.com/9

#### https://meetup.toast.com/posts/92

#### https://mizzo-dev.tistory.com/entry/RESTfulAPI

#### https://www.rocketpunch.com/cards/post/436146