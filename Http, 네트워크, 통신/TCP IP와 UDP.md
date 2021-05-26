# TCP/IP vs UDP

### TCP / IP : Transfer Control Protocol / Internet Protocol

### UDP : User Datagram Protocol



### 프로토콜이란?

: 장치(컴퓨터)간 통신 할 때 그냥 전기 신호가 전달되는 것이 아니라 표준화된 절차, 약속에 따라서 전달됨.

* 보내는 쪽에서는 데이터를 안전하고 정확하게 **규격화**하는 방법이 필요

* 받는 쪽에서는 정확하게 **해석**하는 방법이 필요

  

![TCP/IP와 OSI 7계층](https://madplay.github.io/img/post/2018-02-04-network-tcp-udp-tcpip-1.png) 

* 위의 그림에서 **전송 계층**은 IP에 전달되는 패킷의 오류를 검사하고 재전송 요구 등의 제어를 담당.
* **전송 계층**에서 TCP, UDP라는 두 종류의 프로토콜이 사용 됨 



## TCP

* 가상 회선방식을 제공하는 연결형 서비스(3 way, 4 way handshake 방식을 사용)
* UDP보다는 느림
* 신뢰성 있는 전송이 중요할 때 사용



### 특징

* **흐름 제어**
  * 데이터 처리 속도 조절로 수신자 buffer overflow 방지
  * 수신자가 Window size로 수신량을 정함
* **혼잡 제어**
  * 네트워크 패킷 수가 너무 증가하지 않도록 방지
  * 소통량 과다 -> 패킷을 덜 전송해 혼잡 줄임
* **신뢰성** 보장
  * 정상적인 상황이라면 ACK값이 연속적으로 전송되어야 함.
  * Dupack-based retransmission : ACK 중복 시 패킷 재전송 요청
  * Timeout-based retransmission : ACK 수신 못할 시 재전송 요청
  * 재전송해 받은 패킷들에는 순서가 있음. 이 순서를 조립해 데이터를 정확하게 받을 수 있음
* 전이중(Full-Duplex) : 전송은 양방향으로 동시에 일어날 수 있음
* 점대점(P2P) : 연결은 2개의 엔드포인트가 있음
  * 따라서 multicasting이나 broadcasting 지원 x



### TCP 서버의 특징

* 서버 소켓은 연결만을 담당
* server client는 1:1 연결
* 스트림 전송으로 전송 데이터 크기가 무제한
* 패킷 응답 때문에 느리고, CPU소보가 크다
* Streaming에 사용되지 않음
  * 손실 시 재요청해 정확한 데이터를 주고받는 것보다 지연되지 않는 stream이 유리하기 때문



## UDP

* 데이터그램 방식을 제공하는 비연결형 서비스
* ACK와 같은 절차 없음
  * 신뢰성 낮음
  * 덕분에 빠르긴 함
* UDP 헤더의 checksum필드로 최소한의 오류만 검출
* 패킷 순서에 맞게 조립, 흐름제어, 혼잡제어 다 하지 않음.
* 신뢰성 없고, **연속성**이 중요한 서비스에 적합
  * e.g. 실시간 스트리밍
  * 그러나 개발자가 개발 레벨에서 어느정도 신뢰성을 줄 수는 있다고 함



### UDP 서버의 특징

* 연결 자체가 없고 서버소켓 클라이언트소켓 구분 X
* 소켓 대신 IP 기반 데이터 전송
* 1:1, 1:n, n:n 다 가능
  * 멀티캐스트, 브로드캐스트 다 가능
* 데이터그램 단위 전송 (65535 바이트 크기)





#### Reference)

#### https://velog.io/@hidaehyunlee/%EB%8D%B0%EC%9D%B4%ED%84%B0%EA%B0%80-%EC%A0%84%EB%8B%AC%EB%90%98%EB%8A%94-%EC%9B%90%EB%A6%AC-OSI-7%EA%B3%84%EC%B8%B5-%EB%AA%A8%EB%8D%B8%EA%B3%BC-TCPIP-%EB%AA%A8%EB%8D%B8

#### https://mangkyu.tistory.com/15

#### https://velog.io/@hidaehyunlee/TCP-%EC%99%80-UDP-%EC%9D%98-%EC%B0%A8%EC%9D%B4