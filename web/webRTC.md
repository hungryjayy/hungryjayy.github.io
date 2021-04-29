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
| Room                 | The logical entity of a conference in which all conference members (participants) exchange their video/audio streams, and where mixed streams are generated. Every room must be assigned with a unique identification **(RoomID)** in the MCU scope. |
| User                 | The service account defined in the third-party integration application. The user ID and user role must be specified when asking for a token. |
| Participant          | The logical entity of a user when participating in a conference with a valid token. Every participant must be assigned with a unique identification **(ParticipantID)** in the room scope, and must be assigned a set of operation permissions according to its role. |
| Token                | The credential when a particular participant requests to join a particular room. It must contain a unique identification, the hostname of the target portal which can be used to connect, and the checksum to verify its validity. |
| Stream               | The object represents an audio and/or video media source which can be consumed (say, be subscribed, recorded, pushed to a live stream server) within a room. A stream can be either a participant published one which is called a **Forward Stream**, or a room generated one which is called a **Mixed Stream**. A stream object must be assigned with a unique identification **(StreamID)** in the room scope, Participants can select a particular stream to subscribe according to StreamID. A stream must also contain the audio information of the codec, sample rate, channel number, and video information about codec for Forward Stream or about codec, resolution, frame rate, key frame interval, quality level for Mixed Stream. Participants can determine whether the stream fulfill their expectation based on such information. |
| Subscription         | The activity a participant consuming a stream, such as receiving a stream via a WebRTC PeerConnection, recording a stream to a MCU storage, pushing a stream to a live stream server. A unique identification **(SubscriptionID)** in the room scope must be assigned to the subscription once its request is accepted by MCU. Participants will use the identification to update/cancel/stop a specific subscription. |
| Session              | The entity in which a real-time audio/video communication takes place. Typically, participants establish WebRTC sessions between a WebRTC client and MCU (accurately, the webrtc-agent) to publish or subscribe streams. Since a stream ID will be assigned when publishing a stream into a room and a subscription ID will be assigned when subscribing a stream (or a pair of stream if audio and video come from different source), the stream ID and subscription ID in these two cases are re-used to identify the corresponding session, and MCU must guarantee that the stream IDs and subscription IDs will not conflict within a room. |



#### Reference)

#### https://github.com/duan-xiande/owt-server/blob/master/doc/Client-Portal%20Protocol.md