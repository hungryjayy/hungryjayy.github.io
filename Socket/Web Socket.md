# Web Socket

: HTML 5 표준 기술로, 하나의 HTTP 접속을 통해 클라이언트, 서버 간 실시간 양방향 연결 채널을 구성.

* 양방향 통신이기 때문에 서버쪽이 업데이트 되는 상황에서도 client쪽 화면을 refresh해줄 수 있음
  * 기존에는 Long polling or Ajax같은걸 사용
  * 그러나, 이 또한 요청에 대해 HTTP 응답을 보내는 방식의 단방향 통신을 유지하는 선에서 구현된 방식
* Steteful protocol. 한번 연결되면 같은 라인을 사용해 통신. -> HTTP 연결 트래픽 감소
* HTTP와 같은 포트 사용
* 연결은 HTTP를 통해 이루어지고, 이후에는 WS 프로토콜. 일정 시간 뒤 HTTP 연결 disconnect



## HTTP 통신과의 차이점

* WebSocket 통신은 접속에 HTTP를 사용, 그 후는 WS 프로토콜을 이용
* 헤더가 작아 오버헤드 적다.
* 데이터 송/수신 마다가 아니라 하나의 커넥션이 이루어지면 송수신 가능
* Stateful. (Http는 Stateless)



## Socket.io

* WS가 비교적 최근에 나온 기술이라 구 버전의 웹 브라우저는 웹 소켓을 지원하지 않음
* Node.js 기반, WS를 안정적으로 사용하기 위한 것



#### HTML5

* 플러그인을 사용하지 않고 웹 서비스 제공(멀티미디어 등 많은 기능)
  * Plug-in은 서비스 제공자의 편의성이 매우 높다는 장점
  * 사용자는 Active-X와 같은 플러그인 설치로 서비스 이용 가능
  * Plugin은 i.e에서만 사용 가능.
* 여기서 웹소켓도 사용 가능



## Ajax와의 비교

* 채팅 시스템이라고 한다면, Ajax에서는 10초마다 데이터를 갱신해 확인하는 방식
  * 데이터 과부하적인 측면
* WS에서는 서버에서 Client를 인지하는 상태
  * 브라우저에서 호출해서 데이터를 가져가는 기능이 있기 때문에 서버측에서 PUSH 가능





#### Reference)

#### https://edu.goorm.io/learn/lecture/557/%ED%95%9C-%EB%88%88%EC%97%90-%EB%81%9D%EB%82%B4%EB%8A%94-node-js/lesson/174379/web-socket%EC%9D%B4%EB%9E%80

#### https://duckdevelope.tistory.com/19

#### https://webclub.tistory.com/491