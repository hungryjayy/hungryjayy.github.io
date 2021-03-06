---
layout: post

title: TCP/IP vs UDP - TCP/IP

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [network, tcp/ip, udp]

featuredImage: 

img: 

categories: [Study, Network]

date: '2021-04-06'

extensions:

  preset: gfm

---

: Transfer Control Protocol / Internet Protocol

 <br>

### 프로토콜이란?

: 장치(컴퓨터)간 통신 할 때 그냥 전기 신호가 전달되는 것이 아니라 표준화된 절차, 약속에 따라서 전달됨.

* 보내는 쪽에서는 데이터를 안전하고 정확하게 **규격화**하는 방법이 필요하고 받는 쪽에서는 정확하게 **해석**하는 방법이 필요하다.
* OSI 계층에서 **전송 계층**은 IP에 전달되는 패킷의 오류를 검사하고 재전송 요구 등의 제어를 담당하는데, 여기서 TCP, UDP 두 프로토콜이 있고 두 프로토콜에서 제공하는 서비스가 다르다.
* TCP와 UDP는 별도의 포트 주소 공간을 관리하기 때문에 같은 포트를 사용해도 다른 포트로 간주된다.

|                  | port        | description                                                 |
| ---------------- | ----------- | ----------------------------------------------------------- |
| well-known       | 0~1023      | IANA에서 정한 포트. 22, 53(dns), 80, 443,                   |
| registered       | 1024~49151  | IANA에 등록되어있는 포트. 3306, 8080 등                     |
| dynamic, private | 49152~65535 | 클라이언트쪽의 어플리케이션에 자동적으로 할당되는 포트 번호 |

<br>

## TCP

* 연결형 서비스: 3 way, 4 way handshake
* UDP보다 느리다.
* 신뢰성이 필요한 전송에 사용

<br>

### 특징

* **흐름 제어**: 데이터 송신하는 곳과 수신하는 곳의 데이터 처리 속도를 조절해 buffer overflow 방지
  * **Stop and Wait**: 전송 패킷에 대한 ack를 받으면 다음 패킷 전송.
  * **슬라이딩 윈도우**: 수신자가 설정한 window 크기만큼에 대해서는, ack를 받지 않아도 전송을 하도록 한다. window에 포함된 패킷들이 전송되고 ack를 받으면, 그 만큼 윈도우를 **slide**하는 방식
    * window크기는 동적으로 변화 가능하다.
* **혼잡 제어** : 네트워크 내의 패킷 수가 너무 증가하지 않도록 방지.
  * **느린 시작**: "혼잡 윈도우를 연다." 처음에는 패킷 하나 전송, ack를 받으면 두개 전송, 네개 전송, ...
  * **AIMD**(Additive Increase, multiple decrease): 합 증가, 곱 감소 방식. ack를 받으면 윈도우 크기 1 증가하고, 전송이 실패했다고 판단하면 윈도우 크기 절반으로 줄인다.
* **신뢰성** 보장
  * 정상적인 상황이라면 ACK값이 연속적으로 전송되어야 함.
  * Dupack-based retransmission : **ACK 중복 시 패킷 재전송 요청**
  * Timeout-based retransmission : **ACK 수신 못할 시 재전송 요청**
  * 패킷들에는 순서가 있다. 이 **순서를 조립**해 완성하면 **하나의 데이터**가 되고, 이러한 방법으로 **데이터**를 정확하게 받을 수 있음
* 전이중(Full-Duplex) : 전송은 양방향으로 동시에 일어날 수 있다.
* 점대점(P2P) : 연결은 2개의 엔드포인트가 있다.
  * 따라서 multicasting이나 broadcasting 지원 x

<br>

### TCP 서버의 특징

* 서버 소켓은 연결만을 담당
* server client는 1:1 연결
* 스트림 전송으로 전송 데이터 크기가 무제한
* 패킷 응답 때문에 느리고, CPU소모가 크다
* Streaming에 사용되지 않음
  * 손실 시 재요청해 정확한 데이터를 주고받는 것보다 지연되지 않는 stream이 유리하기 때문

<br>

#### c f) 텔넷을 통한 원격 접속 방식에서의 TCP 커넥션

* **웹 서버**는 원격으로 접속한 사용자를 **웹 클라이언트**로 취급하고 TCP 커넥션을 통해 돌려주는 데이터를 볼 수 있다.

  ``` http
  1 % telnet www.joes-hardware.com 80
  2 Trying 128.121.66.211...
  3 Connected to joes-hardware.com.
  4 Escape character is '^]'.
  5 GET /tools.html HTTP/1.1
  6 Host: www.joes-hardward.com
  7 
  ```

  * line 1: 텔넷은 호스트명을 찾아 80번 포트로 대기중인 joes-hardware 웹 서버에 연결
  * line 2~4: **커넥션 수립**을 알리는 텔넷의 출력
  * line 5~7: 사용자가 직접 HTTP 요청 입력 후 Host 헤더로 전송하고, 한줄 더 띄우면 해당 웹 서버에서 리소스(`/tools.html`) 반환

<br><br>

#### Reference)

HTTP 완벽 가이드

https://github.com/WeareSoft/tech-interview/blob/master/contents/network.md#tcp%EC%9D%98-3-way-handshake%EC%99%80-4-way-handshake

https://velog.io/@hidaehyunlee/%EB%8D%B0%EC%9D%B4%ED%84%B0%EA%B0%80-%EC%A0%84%EB%8B%AC%EB%90%98%EB%8A%94-%EC%9B%90%EB%A6%AC-OSI-7%EA%B3%84%EC%B8%B5-%EB%AA%A8%EB%8D%B8%EA%B3%BC-TCPIP-%EB%AA%A8%EB%8D%B8

https://mangkyu.tistory.com/15

https://velog.io/@hidaehyunlee/TCP-%EC%99%80-UDP-%EC%9D%98-%EC%B0%A8%EC%9D%B4

