---
layout: post

title: 3 way, 4 way handshake

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [network, tcp/ip]

featuredImage: 

img: 

categories: [Study, Network]

date: '2021-04-01'

extensions:

  preset: gfm
---

: 이 과정을 통해 TCP 프로토콜의 **송 수신자 모두 준비가 되었다는 것을 보장**한다. UDP와 다르게 TCP 통신에서는 장치 사이에서 연결을 보장하고 명확한 전송을 하기 위해 conn을 맺고 끊을 때 3-way, 4-way handshake를 한다.

<br>

## 3 way handshake

: TCP 연결을 초기화 할 때 사용 

![img](https://hungryjayy.github.io/assets/img/Network/tcpopen3way.png)

1. SYN(n): 클라이언트가 서버에게 SYN 를 보낸다. 이 때 시퀀스넘버라는 난수를 생성해 SYN에 붙여서 보낸다.
   * 난수를 보내는 이유: 다른 SYN 요청과의 구분
   * 보낸 후 client는 **SYN_SENT** 상태가 됨
   * flag: 000010
2. ACK(n+1) + SYN(m): 서버가 클라이언트에게 ACK(요청 수락)와 SYN를 보낸다. 마찬가지로 시퀀스 넘버를 붙여서 보낸다.
   * 서버는 **SYN_RECEIVED** 상태가 됨 
   * flag: 010000
3. ACK(m+1): 클라이언트가 ACK를 보내고 **ESTABLISHED**가 되고, 서버는 받고서 **ESTABLISHED**가 되며 이후로 데이터가 오간다.

<br>

## 4 way handshake

: TCP 연결을 종료하기 위해 수행하는 절차.

* Client -> Server 방향으로 요청하는 것으로 표현되어있지만, 일반적으로 요청을 보내는 쪽이 Client라고 이해하면 된다. 실제로 지속 커넥션이 아니라면, 웹서버 입장에서는 사용자의 요청 메시지에 대한 응답을 다 보내고나서 커넥션을 닫는다고 한다.

![img](https://hungryjayy.github.io/assets/img/Network/tcpclose.png)

1. FIN: 클라이언트가 연결 종료 메시지 FIN플래그를 보낸다.
   * flag: 000001
2. ACK: 서버가 확인메시지를 보낸다. **CLOSE-WAIT** 상태
3. FIN: 서버가 통신 끝났다는 메시지인 클라이언트에게 FIN플래그를 보낸다.
4. ACK: 클라이언트가 ACK를 보낸다.
   * 클라이언트는 **TIME_WAIT** 상태가 되고, 2MSL동안 기다리게 된다.

<br>

#### 지연으로 패킷이 FIN보다 늦어지는 상황

* 세션 종료 후 전송되는 패킷은 drop, 데이터 유실 됨.
* **TIME-WAIT** 상태: 이러한 상황에 대비해 client는 FIN을 수신하더라도 세션을 남겨놓고 패킷을 기다림. 이 때, 일반적으로 2 MSL(double maximum segment lifetime)동안 기다린다.

<br>

#### MSL

: TCP 세그먼트가 internetwork(네트워크들이 연결된 상태의 네트워크)에서 살아있을 수 있는 시간.

```shell
   // linux 체계에서 아래와같이 확인 가능
   sysctl net.ipv4.tcp_fin_timeout
   cat /proc/sys/net/ipv4/tcp_fin_timeout
```

* RFC 명세에선 **MSL값**은 2분이 디폴트로 나온다. 리눅스 설정값을 보니 1분이 디폴트이다. **2 * MSL 기준**으로 보통 2~4 분으로 많이 설정하는 것 같다.
* 두배인 이유는 패킷 손실 시 회복을 보내는 시간까지 고려. 예상치 못하게 네트워크 트래픽이 많은 경우 최악의 케이스로 MSL이 발생한다고 한다면 재요청 패킷 또한 MSL까지 발생할 수 있을것이라고 판단

<br>

#### 연결 끊는 상황에서 3 way 아닌 4 way로 구성하는 이유

: Client 데이터 전송이 끝났어도 **Server쪽에서 아직 보낼 데이터가 남아있을 수 있기 때문에**이다. 따라서, 받은 FIN에 대한 ACK만 보내고, **보낼 것이 있으면 다 보내고 난 후** 서버도 FIN을 보낸다.

<br>

#### 시퀀스넘버를 난수로 날리는 이유

: Port를 통해 연결하고 데이터를 교환하고 다시 끊고 나서, 시간이 지나고서 같은 포트를 다시 또 사용한다. 따라서, 과거에 사용된 포트번호 쌍을 사용할 가능성이 존재하기 때문에, 과거의 SYN을 현재의 SYN으로 인식할 수 있다.(오해가 생길 수 있다) ACK의 난수는 해당 응답의 요청이었던(SYN) 시퀀스넘버의 +1

<br><br>

#### Reference)

코딩 인터뷰 완전분석

http://www.tcpipguide.com

https://mindnet.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%89%BD%EA%B2%8C-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-22%ED%8E%B8-TCP-3-WayHandshake-4-WayHandshake

https://github.com/WeareSoft/tech-interview/blob/master/contents/network.md#tcp%EC%9D%98-3-way-handshake%EC%99%80-4-way-handshake