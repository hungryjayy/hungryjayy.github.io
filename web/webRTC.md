# WebRTC

: 웹 애플리케이션과 사이트가 중간자 없이 실시간으로 데이터를 교환, 영상이나 오디오를 스트림해주는 기술

* Third party software(서버와 같은 중간자) 없이 종단 간(Pear to Pear) 데이터 공유, 화상회의 등 가능하게 함.
  * 서버를 거치지 않아 빠른 속도
  * STUN 서버에 의해 단말이 자신의 공인 IP주소와 포트를 확인하고, 이렇게 알아오면 종단간 주소를 알게 되기 때문에 서버 없이도 통신이 가능.
    * 원래는 중간자(Router와 같은) 공인 IP를 알아내고 이를 통해 통신해야함
  * HTTPS가 강제되는 요쇼이기 때문에 보안 또한 보장
* 여러가지 목적으로 사용 가능.
  * Media Capture and Streams API와 많은 부분이 겹침
  * 둘은 상호작용을 통해 웹에 멀티미디어 기능 제공
  * 음성, 화상회의, 파일교환 등
* 피어들 간 connection 발생 -> 어떠한 플러그인도 필요하지 않음.



#### Reference)

#### https://wormwlrm.github.io/2021/01/24/Introducing-WebRTC.html

#### https://developer.mozilla.org/ko/docs/Web/API/WebRTC_API

#### https://jomuljomul.tistory.com/category/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%20%EA%B3%B5%EB%B6%80/WebRTC



## OWT-server



| Name                 | Definition                                                   |
| -------------------- | ------------------------------------------------------------ |
| Portal               | The MCU component listening at the Socket.io server port, accepting signaling connections initiated by Clients, receive/send signaling messages from/to Clients. |
| Client               | The program running on end-user’s device which interacts with MCU Server by messages defined in this documentation. |
| Signaling Connection | The signaling channel between Client and Portal established by Client to send, receive and fetch signaling messages. |
| Room                 | The logical entity of a conference in which all conference members (participants) exchange their video/audio streams, and where **mixed streams** are generated. Every room must be assigned with a unique identification **(RoomID)** in the MCU scope. |
| User                 | The service account defined in the third-party integration application. The user ID and user role must be specified when asking for a token. |
| Participant          | The **logical entity** of a user when participating in a conference with a valid token. Every participant must be assigned with a unique identification **(ParticipantID)** in the room scope, and must be assigned a set of operation permissions according to its role. |
| Token                | The credential when a particular participant requests to join a particular room. It must contain a unique identification, the hostname of the target portal which can be used to connect, and the checksum to verify its validity. |
| Stream               | The object represents an audio and/or video media source which can be consumed (say, be subscribed, recorded, pushed to a live stream server) within a room. A stream can be either a participant published one which is called a **Forward Stream**, or a room generated one which is called a **Mixed Stream**. A stream object must be assigned with a unique identification **(StreamID)** in the room scope, Participants can select a particular stream to subscribe according to StreamID. A stream must also contain the audio information of the codec, sample rate, channel number, and video information about codec for Forward Stream or about codec, resolution, frame rate, key frame interval, quality level for Mixed Stream. Participants can determine whether the stream fulfill their expectation based on such information. |
| Subscription         | The activity a participant consuming a stream, such as receiving a stream via a WebRTC PeerConnection, recording a stream to a MCU storage, pushing a stream to a live stream server. A unique identification **(SubscriptionID)** in the room scope must be assigned to the subscription once its request is accepted by MCU. Participants will use the identification to update/cancel/stop a specific subscription. |
| Session              | The entity in which **a real-time audio/video communication** takes place. Typically, participants establish WebRTC sessions between a WebRTC client and MCU (accurately, the webrtc-agent) to publish or subscribe streams. Since a stream ID will be assigned when publishing a stream into a room and a subscription ID will be assigned when subscribing a stream (or a pair of stream if audio and video come from different source), the stream ID and subscription ID in these two cases are re-used to identify the corresponding session, and MCU must guarantee that the stream IDs and subscription IDs will not conflict within a room. |



#### Reference)

#### https://github.com/duan-xiande/owt-server/blob/master/doc/Client-Portal%20Protocol.md





## Socket.io signaling

### Format

*Signaling message : 서로 다른 네트워크에 있는 2개의 디바이스를 위치시키기 위해서는 각 디바이스의 위치를 발견하는 방법, 미디어 포맷 협의 필요. 이 프로세스를 Signaling이라고 함. *

* Client -> Portal
  * Client가 Portal과 연결되어 send/recv 할 준비가 되면, Client는 socket객체의 `emit()` 을 통해 Portal에게 모든 signaling message를 보내야 한다.
* Portal -> Client
  * Portal에 Client가 연결되어 socket 객체가 send/recv 할 준비가 되면, Portal은 `emit()`을 통해 Client에게 모든 signaling mesage를 보내야 한다.



### Connection Maintenance

* Client Connects
  * Portal은 Client의 연결을 받기 위해 각각의 socket.io 서버 포트를 listen할 수 있어야 한다.
  * Secure socket.io 서버가 가능해지면, SSL certificate과 PK store path가 올바르게 정해져야한다. (portal.toml 의 config를 통해)
* Client Keeps Alive
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



#### Reference)

#### **https://github.com/duan-xiande/owt-server/blob/master/doc/Client-Portal%20Protocol.md**



# OWT 코드분석

## Publish 과정

1. Client(엔드포인트에서 사용하는 사용자)
   1. 여러 설정 후 publish
2. V11Client `on('publish') `
   1. safeCall을 통해 배열 객체로 만들어주고
   2. streamID (난수)생성
   3. Validation 이후
   4. Portal의 publish call
3. Portal
   1. portal/rpcRequest에게 RPC call(publish) 
   2. Conference Agent에게 요청됨
4. Conference Agent
   1. 여러 유효성 검사
   2. initiateStream을 통해 streams 배열에 stream객체 저장
   3. webrtc worker node를 얻기 위해 accessController에게 initiate 요청
5. accessController
   1. Session 배열에 session 객체 저장
   2. getWorkernode를 통해 worker node 얻고, locality 변수에 저장
   3. 이후 worker node, session에 대한 정보들 conference/rpcRequest의 RPC call(initiate)
   4. conference/rpcRequest에서는 목적노드의 publish call -> worker node에게로
6. Worker node(access node)
   1. webRTC connection 생성 (createdWebRTCConnection)
   2. 위의 메서드 내부에서 WrtcConnection 객체 생성, 이 때 pub을 위한 addMediaStream 또한 진행됨(미디어 스트림 생성)



#### Reference)

#### Client - https://github.com/open-webrtc-toolkit/owt-client-javascript/blob/8fae8a8e1714109d0851391f8fd20ffe3775cb65/src/sdk/conference/channel.js#L146

#### Server - https://github.com/open-webrtc-toolkit/owt-server