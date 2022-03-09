---
layout: post

title: WebRTC & OWT

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [webrtc]

featuredImage: 

img: 

categories: [WebRTC]

date: '2021-05-02'

extensions:

  preset: gfm

---

: web real-time communication. 웹 애플리케이션과 사이트가 중간자 없이 실시간으로 데이터를 교환, 영상이나 오디오를 스트림해주는 기술

* **P2P 통신**: Third party software(서버와 같은 중간자) 없이 **P2P(Pear to Pear) 데이터 통신**을 통해 빠르게 전송해, 실시간 화상회의 등 가능하게 함.
  * ~~서버를 거치지 않아 빠른 속도~~
  * **(210820) P2P 통신은 중간자가 없다는 것이 아니라, 멀티캐스트 없이 1:1로 통신한다는 의미라고 함**
  * 기본적으로 **udp 통신**은 **멀티캐스트**를 지원하고, **webRTC**는 **udp** 통신을 하는데 **멀티캐스트가 아닌 p2p 통신**을 하는 것.
  
* **STUN 서버**에 의해 peer의 public IP주소와 Port를 알 수 있고, P2P 통신이 가능해진다. 그러나, 아래와 같은 문제가 있을 때 **TURN을 사용**
  * Symmetric NAT의 경우 문제: 패킷을 보내는 외부 서버마다 NAT 맵핑을 다르게 가져가는 것
  * 두 Peer가 같은 NAT 환경에 있을 경우 동작 불가
  
  * HTTPS가 강제되는 요쇼이기 때문에 보안 또한 보장
  
* **TURN 서버**(릴레이 서버) : STUN의 역할을 수행하지만, Peer간 직접 통신이 실패할 경우 TURN이 peer 사이에서 데이터 Relay 수행

  * 직접 통신하지 않고 TURN이라는 중개서버를 통해 relay하는 방식. -> 엄격하게 말하면 P2P 통신을 포기하게 되는 것.
  * 불가피하게 오버헤드가 발생한다. -> 최후의 수단으로 사용해야 한다.

<br>

## OWT-server

<br>

| Name                 | Definition                                                   |
| -------------------- | ------------------------------------------------------------ |
| Portal               | The MCU component listening at the Socket.io server port, accepting signaling connections initiated by Clients, receive/send signaling messages from/to Clients. |
| Client               | The program running on end-user’s device which interacts with MCU Server by messages defined in this documentation. |
| Signaling Connection | The signaling channel between Client and Portal established by Client to send, receive and fetch signaling messages. |
| Room                 | The **logical entity** of a conference in which all conference members (participants) exchange their video/audio streams, and where **mixed streams** are generated. Every room must be assigned with a unique identification **(RoomID)** in the MCU scope. |
| User                 | The service account defined in the third-party integration application. The user ID and user role must be specified when asking for a token. |
| Participant          | The **logical entity** of a user when participating in a conference with a valid token. Every participant must be assigned with a unique identification **(ParticipantID)** in the room scope, and must be assigned a set of operation permissions according to its role. |
| Token                | The credential when a particular participant requests to join a particular room. It must contain a unique identification, the hostname of the target portal which can be used to connect, and the checksum to verify its validity. |
| Stream               | **The object represents an audio and/or video media source** which can be consumed (say, be subscribed, recorded, pushed to a live stream server) within a room. A stream can be either a **participant published one** which is called a **Forward Stream**, or a **room generated one** which is called a **Mixed Stream**. A stream object must be assigned with a unique identification **(StreamID)** in the room scope, Participants can select a particular stream to subscribe according to StreamID. A stream must also contain the audio information of the codec, sample rate, channel number, and video information about codec for Forward Stream or about codec, resolution, frame rate, key frame interval, quality level for Mixed Stream. Participants can determine whether the stream fulfill their expectation based on such information. |
| Subscription         | The activity a participant consuming a stream, such as receiving a stream via a WebRTC PeerConnection, recording a stream to a MCU storage, pushing a stream to a live stream server. A unique identification **(SubscriptionID)** in the room scope must be assigned to the subscription once its request is accepted by MCU. Participants will use the identification to update/cancel/stop a specific subscription. |
| Session              | The entity in which **a real-time audio/video communication** takes place. Typically, participants establish WebRTC sessions between a WebRTC client and MCU (accurately, the webrtc-agent) to publish or subscribe streams. Since **a stream ID will be assigned when publishing a stream into a room** and **a subscription ID will be assigned when subscribing a stream** (or a pair of stream if audio and video come from different source), the stream ID and subscription ID in these two cases are **re-used to identify the corresponding session**, and MCU must guarantee that the stream IDs and subscription IDs will not conflict within a room. |

<br>

## Socket.io signaling

### Format

*Signaling message : 서로 다른 네트워크에 있는 2개의 디바이스를 위치시키기 위해서는 각 디바이스의 위치를 발견하는 방법, 미디어 포맷 협의 필요. 이 프로세스를 Signaling이라고 함.*

* Client -> Portal
  * Client가 Portal과 연결되어 send/recv 할 준비가 되면, Client는 socket객체의 `emit()` 을 통해 Portal에게 모든 signaling message를 보내야 한다.
* Portal -> Client
  * Portal에 Client가 연결되어 socket 객체가 send/recv 할 준비가 되면, Portal은 `emit()`을 통해 Client에게 모든 signaling mesage를 보내야 한다.

<Br>

### Connection Maintenance

* Client Connects
  * Portal은 Client의 연결을 받기 위해 각각의 socket.io 서버 포트를 listen할 수 있어야 한다.
  * Secure socket.io 서버가 가능해지면, SSL certificate과 PK store path가 올바르게 정해져야한다. (portal.toml 의 config를 통해)
* Client Keeps Alive (`refreshReconnectionTicket`)
  * Socket.io 서버는 keep-alive 메커니즘을 갖고있기 때문에 app level의 heart-beat는 불필요.
  * 그러나, 서버가 죽는 경우를 대비해서 Client는 현재의 티켓이 만료되기 전에 주기적으로 reconnection 티켓을 refresh해야한다.
* Client Disconnects
  * 서버 사이드에서의 연결된 socket.io 객체는 disconnect event를 받는다.
  * 다음의 조건들이 만족된다면, disconnect event 이후에 reconnecting 타이머`(Timer100)`가  시작된다.
    1. Participant leaving 가 발생하지 않았을 때(의도적으로 나간게 아닐 때)
    2. Conenction이 mobile client로부터 초기화되었을 때
* Client Reconnects
  * `RequestName` : "relogin"
  * `RequestData` : ReconnectionTicket 객체
  * `ResponseData` : base64-encoded ReconnectionTicket 객체(ResponseStatus가 "ok" 일 때)

<br><br>

#### Reference)

https://github.com/duan-xiande/owt-server/blob/master/doc/Client-Portal%20Protocol.md

https://wormwlrm.github.io/2021/01/24/Introducing-WebRTC.html

https://developer.mozilla.org/ko/docs/Web/API/WebRTC_API

https://jomuljomul.tistory.com/category/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%20%EA%B3%B5%EB%B6%80/WebRTC

https://github.com/duan-xiande/owt-server/blob/master/doc/Client-Portal%20Protocol.md
