# Web Socket

: HTML 5 표준 기술로, 하나의 HTTP 접속을 통해 클라이언트, 서버 간 실시간 양방향 연결 채널을 구성.

* **Web hook**: 양방향 통신이기 때문에 서버쪽이 업데이트 되는 상황에서도 client쪽 화면을 refresh해줄 수 있다.
  * 기존에는 polling 방식의 AJAX(단방향) 사용했는데, socket을 통해 이 연결을 유지하는 방법으로 구현한 것이 웹 소켓
* Stateful protocol. 한번 연결되면 같은 라인을 사용해 통신. -> HTTP 연결 트래픽 감소하지만 연결을 유지해야한다.
* **HTTP와 같은 포트** 사용한다. 연결 시 핸드쉐이크는 HTTP를 통해 이루어지고, 이후에는 WS 프로토콜로 통신. 일정 시간 뒤 HTTP 연결 disconnect
* 일반적으로 문제 발생으로 연결이 끊길 때를 대비해 reconnect 매커니즘을 제공한다.

<br>

### Rest와의 비교

* REST에서는 많은 URI를 통해 application 설계되는데, 웹소켓은 하나의 URL로 Connection 생기고, 이후 해당 Connection으로만 통신
* Handshake 완료되고 Connection 유지
  * REST(Http 통신)에서는 요청-응답 후 Connection close된다. 따라서 이론상 하나의 server가 port수의 한계(n < 65535) 를 넘는 client 요청 처리 가능
* WebSocket 통신은 접속에 HTTP를 사용, 그 후는 WS 프로토콜을 이용
* 헤더가 작아 오버헤드 적다.

<br>

## 라이브러리

### SockJS

* 구버전 브라우저에서는 websocket을 지원하지 않는데, **spring**에서는(**Stomp를 사용할 때**) sockjs-client를 사용하는 client와 합을 맞춰 webSocket을 사용할 수 있다.

<br>

### Socket.io

* **Node.js** 기반, WS를 안정적으로 사용하기 위한 것
* WS가 비교적 최근에 나온 기술이라 구 버전의 웹 브라우저는 웹 소켓을 지원하지 않음

<br>

#### HTML5

* 플러그인을 사용하지 않고 웹 서비스 제공(멀티미디어 등 많은 기능)
  * Plug-in은 서비스 제공자의 편의성이 매우 높다는 장점
  * 사용자는 Active-X와 같은 플러그인 설치로 서비스 이용 가능
  * Plugin은 i.e에서만 사용 가능.
* 웹소켓 지원.

<br>

## Ajax와의 비교

* 채팅 시스템이라고 한다면, Ajax에서는 10초마다 데이터를 갱신해 확인하는 방식
  * 데이터 과부하적인 측면
* WS에서는 서버에서 Client를 인지하는 상태
  * 브라우저에서 호출해서 데이터를 가져가는 기능이 있기 때문에 서버측에서 PUSH 가능

<br><br>

#### Reference)

#### https://edu.goorm.io/learn/lecture/557/%ED%95%9C-%EB%88%88%EC%97%90-%EB%81%9D%EB%82%B4%EB%8A%94-node-js/lesson/174379/web-socket%EC%9D%B4%EB%9E%80

#### https://duckdevelope.tistory.com/19

#### https://webclub.tistory.com/491

#### https://supawer0728.github.io/2018/03/30/spring-websocket/