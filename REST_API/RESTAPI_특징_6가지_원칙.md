# Rest api 특징과 6가지 원칙

: API를 설계 할 때 자원을 나타내는 URI가 있고, http method를 통해 자원을 어떻게 처리할 지 설계하는 방식의 아키텍처

```
자원(resource) - URI / 행위 - HTTP method / 표현 representations
```



## 특징

* URI로 지정된 리소스에 대한 조작을 한정적인 Interface를 통해 수행하는 아키텍처
* **Stateless**
  * client의 상태정보를 따로 저장 안하고 단순히 들어온 요청만을 처리 -> 불필요한 정보 관리 x
  * 만약 stateful하다면 서버가 클라이언트의 현재 상태를 저장해야한다. 따라서 클라이언트의 상태는 서버에 종속된다.
    * 이 상황에서 LB같은걸 하는 경우에 서버 간 클라이언트의 상태를 공유할수있는 redis같은 별도 시스템이 필요.
  * Stateless하기 때문에 어느 서버가 처리하던 클라이언트의 요청은 동일하게 처리 가능.
* Client-Server 구조: 서버는 API를 제공, 클라이언트는 로그인 정보(토큰)를 관리. 각각의 역할이 구분되고 의존성이 줄어들게 됨



### 장점

* 원하는 타입으로 데이터를 주고받을 수 있다. -> 보통 JSON
* http method 타입과 URI만 읽어도 해당 인터페이스가 어떠한 기능과 연결되는지 파악이 용이
* Client-Server구조. api로 들어오는 요청을 처리해 요청한대로 보내주기만 하면 된다. 각자의 역할이 명확히 분리 



### 단점

* 복잡한 비즈니스에서 HTTP method의 한계로 인해 모든 경우를 cover할 수 없을 것 같다.
* 공식화 된 Rest API 표준이 존재하지 않다는 점도 아쉬운 점 중 하나.



## REST API 6가지 원칙

1. **Uniform Interface**
   * URI로 지정한 리소스에 대한 조작을 **일관된 인터페이스**로 수행
   * 이러한 인터페이스를 정하는 **원칙들**이 있다.
   * 이게 잘 지켜지면, URI등 인터페이스에 관계없이 서버와 클라이언트가 **독립적으로 진화** 가능
     * 수정사항이 생겨도, 웹을 망가트리지 않고 수정 가능
2. **Stateless**
   * 세션이나 쿠키를 별도로 저장하고 관리하지 않음. 그저 요청만 처리
     * Token을 사용해 브라우저에 저장하도록
   * 서비스의 자유도가 높아지고 **구현이 단순**해짐
3. **Cacheable**
   * HTTP 웹 표준을 그대로 사용하기 때문에 HTTP 웹 기존 인프라를 활용.
   * 따라서 캐시 기능 가능. 응답 메시지에 해당 요청에 대한 캐시 가능여부를 명시해서 준다.
     * 개발자 도구에서 Res 헤더에 `cache-control` 를 통해 캐시 여부 명시
4. **Client-Server**
   * Client(UI)와 Server(데이터 스토리지, 비즈니스 로직) 등 작업을 명확하게 분리
     * 이식성: UI는 여러 플랫폼에서 이식될 수 있다.
     * 확장성: 서버의 확장성(Extensibility)
   * Server: API 제공
   * Client: 사용자 인증이나 컨텍스트(세션, 로그인 정보)등을 관리하는 구조
5. **Hierarchical system**
   * 다중 계층으로 구성 가능.
     * DB서버, API서버, 인증서버 등을 따로 띄울 수 있도록
       * 각 레이어는 본인이 통신하는 레이어 외에는 알 수도, 볼 수도 없다
  * Proxy, 게이트웨이 등 네트워크 중간 매체도 사용할 수 있음
     * 보안, LB, 암호화 계층도 추가 가능
6. **Code on demand (optional)**
   - 보통 Xml, Json과같은 정적 데이터(IDL)을 client로 보내주고 client가 이것을 가공하는데, **code on demand**라는 것은 Client에 보내는 데이터를 바로 실행 가능한 코드를 보내서 이것을 Client에서 **그냥 실행하는 것**을 말한다.
   - 클라이언트의 구현을 간소화
   - 선택적 사항



#### Reference)

#### https://sabarada.tistory.com/9

#### https://meetup.toast.com/posts/92

#### https://mizzo-dev.tistory.com/entry/RESTfulAPI

#### https://www.rocketpunch.com/cards/post/436146