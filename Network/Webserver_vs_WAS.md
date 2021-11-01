# Was vs Web Server

* Was(Web application server)
  * 사용자 요청에 맞게 **동적인 컨텐츠**를 전달
  * Web server + Web container
  * DB 서버가 같이 수행
  * 비즈니스 로직 처리
  * Node.js 서버, Spring boot 서버가 여기에 해당.
    * 정확하게는 node의 express, 서블릿을 지원해주는 Tomcat

<br>

* Web server(e.g. nginx, apache)
  * **정적인 컨텐츠**를 반환한다.(html, css 등)
  * **리버스 프록시** 방식으로 동작한다. 클라이언트는 WAS의 위치, 존재 여부를 모른다.
  * 한 Web server는 여러 WAS로 전달될 수 있다.
  * 한 WAS가 scaled-out 되어있을 때 LB도 수행한다.
    * 이건 Proxy서버 또한 마찬가지: HAProxy에서 Redis 마스터, 슬레이브에 적절히 요청을 보내는 것을 생각하면 된다.
  * http 프로토콜 기반으로 클라이언트 요청을 서비스
  * 클라이언트 요청에 대해 가장 앞에서 요청 처리

<br>

#### Was는 Web server가 하는 일을 다 할 수 있다.

* 효율적인 분산처리를 위해 Web server도 사용해야 함
* 보통 Web server에 Was가 플러그인같이 붙은 형태

<br>

## NginX vs Apache

### NginX

- 이벤트, 비동기 기반.
  - Node.js 서버와 사용하면 좋은 퍼포먼스를 낼 수 있다.
  - Apache보다 적은 스레드를 사용해 처리할 수 있다.

- 다중 I/O처리에 적합하다. HTTP 요청 관점에서 한 스레드가 계속 돌면서 여러 요청들을 돌아가며 처리해주는것
- 점유율 2위. Apache에 비해 레퍼런스가 적다.(그래도 충분히 많다)

<br>

### Apache

- 멀티스레드의 스프링 부트처럼 한 요청에 한 스레드가 사용된다.
  - 이 경우 스레드 낭비가 있을 수 있다.(스레드 각각이 유휴시간을 활용 못한다.)
- 가장 높은 점유율을 차지한다. 따라서, 레퍼런스가 많다. 당연하게도 확장 모듈 또한 NginX에 비해 많다고 한다.

<br><br>

#### Reference)

##### https://gmlwjd9405.github.io/2018/10/27/webserver-vs-was.html

##### https://jeong-pro.tistory.com/84