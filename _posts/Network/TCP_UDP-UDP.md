# TCP/IP vs UDP - UDP

: User Datagram Protocol

<br>

## UDP

<img src="https://github.com/WeareSoft/tech-interview/raw/master/contents/images/udp-datagram.png" alt="img" style="zoom:50%;" /> 

* 데이터그램 단위로 처리하는 **비연결형 서비스**
  * 연결을 위해 할당되는 논리적 경로 없음
  * 패킷은 각각 독립적 관계(독립적으로 처리)
* ACK와 같은 절차 없음
  * 신뢰성 낮음
  * 덕분에 빠르긴 함
  * 메시지 크기도 TCP에 비해 엄청 작다. 송신, 수신 port, 패킷 length, checksum이 끝.
  * 빠른 특징으로 인해 HTTP/3.0에서 채택되었다. 정확하게는 UDP 기반 QUIC 채택으로 신뢰성있는 빠른 통신을 할 수 있다고한다. 현재 curl 최신도 HTTP/3.0을 사용한다고한다.
* UDP 헤더의 **checksum**필드로 최소한의 오류만을 검출
* 패킷 순서에 맞게 **조립**, **흐름제어**, **혼잡제어** **다 하지 않음.**
* 신뢰성 없고, **연속성**이 중요한 서비스에 적합
  * e.g. 실시간 스트리밍
  * 그러나 개발자가 개발 레벨에서 어느정도 신뢰성을 줄 수는 있다고 함

<br>

### UDP 서버의 특징

* 연결 자체가 없고 서버소켓 클라이언트소켓 구분 X
* 소켓 대신 IP 기반 데이터 전송
* 1:1, 1:n, n:n 다 가능. (**멀티캐스트**, **브로드캐스트** 다 가능). P2P 통신은 이러한 특징에서 peer간 1:1통신만을 가져가는 것.
* 데이터그램 단위 전송 (65535 바이트 크기)

<br><br>

#### Reference)

#### HTTP 완벽 가이드

#### https://github.com/WeareSoft/tech-interview/blob/master/contents/network.md#tcp%EC%9D%98-3-way-handshake%EC%99%80-4-way-handshake

#### https://velog.io/@hidaehyunlee/%EB%8D%B0%EC%9D%B4%ED%84%B0%EA%B0%80-%EC%A0%84%EB%8B%AC%EB%90%98%EB%8A%94-%EC%9B%90%EB%A6%AC-OSI-7%EA%B3%84%EC%B8%B5-%EB%AA%A8%EB%8D%B8%EA%B3%BC-TCPIP-%EB%AA%A8%EB%8D%B8

#### https://mangkyu.tistory.com/15

#### https://engineer-mole.tistory.com/140

#### https://velog.io/@hidaehyunlee/TCP-%EC%99%80-UDP-%EC%9D%98-%EC%B0%A8%EC%9D%B4