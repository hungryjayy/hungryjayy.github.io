---
layout: post

title: OWT Stream publish, subscribe 코드 분석 및 시나리오

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [webrtc]

featuredImage: 

img: 

categories: [Study, WebRTC]

date: '2021-05-22'

extensions:

  preset: gfm


---

: 아래의 과정으로 코드 분석해보기

<br>

## Publish 과정

1. **Client**(엔드포인트에서 사용하는 사용자)

   1. 유효성검사
      1. RTCRtp인코딩 파라미터, 오디오, 비디오 인코딩 파라미터 validation
      2. audio, video가 MediaStream에 존재하는 트랙들과 일관된지
      3. options의 audio, video가 있는지
      4. audio,video boolean이나 array인지, 올바른 파라미터 들어와있는지
      5. +기타 유효성 검사
   2. peerConnection 생성
   3. signaling Message 보냄(publish로)

2. **V11Client** `on('publish') `

   1. `if(!that.inRoom)` -> 방에 join하지 않았다면 여기서 에러 
   2. streamID (난수)생성
   3. Validation 이후
   4. Portal의 publish call 
      * clientId == participantId, stream_id, req == pubInfo

3. **Portal** 

   1. portal/rpcRequest에 RPC call(publish)
      * participants[participantId].controller (여기선 conference Agent), participantId, streamId, pubInfo
   2. Conference Agent에게 요청됨(that.publish)

4. **Conference Agent** - 여기서 **Participants[participantId] 등록(addParticipant)**

   1. 유효성 검사
      1. 해당 시점 accessController, roomController 객체의 존재여부
      2. Portal로부터 전달된 participants[participantId] (== 해당 Client가 Join 했는지)
      3. auth, input 수, Stream 이미 존재, audio or video 금지여부 등
      4. sip or analytics 타입의 경우 stream 추가하고 끝
   2. initiateStream을 통해 streams 배열에 stream객체 새롭게 저장
      * **Pub할 때 streams[streamId] init, Sub할 때 Subscriptions[subscriptionId] init**
        * 생성 시점: **addStream**
        * 여기서 streamId, subscriptionId는 v11Client에서 생성한 Random Number
        * streams[stream]에는 info가 있음( `info : { owner: participantId, type: pubInfo.type }` )
   3. format_preference의 video, audio를 pubInfo의 video,audio로 set
   4. webrtc worker node를 얻기 위해 accessController에게 initiate 요청

5. **accessController**

   1. Session 배열에 session 객체 새롭게 저장 

      * **Pub / Sub에 관계 없이 전부 sessions[sessionId]에 추가.(이 때 conflict 방지 해야 함)**

   2. getWorkernode를 통해 workerAgent.Id, workerNode 얻음

      -> cluster_name, sessionOptions.type == pubInfo.type(webrtc),  {in_ room, sessionId}, origin == participants[participantId].getOrigin()

      1. clusterManager에게 scheduling 요청 -> scheduler가 workerAgent 전해주고 -> workerAgent에 해당하는 node를 node manager에게 요청 -> 준비된 node 할당

      2. workerAgent.id, workerNode 반환

         worker, workers[worker].info

      3. 이 결과를 locality 변수에 저장, 해당 세션ID에 이 worker 정보 저장

   3. initiate를 통해 worker node, session id 및 정보들 conference/rpcRequest의 RPC call

      1. conference/rpcRequest에서는 목적노드의 publish call -> worker node에게로
         * sessionId (=connectionId), sessionType (=connectionType), options

6. **Worker node(access node) (webrtc/index)**

   1. 중복 연결 방지 validation
   2. (connectionType == sessionType이 webrtc일 때) createdWebRTCConnection
      1. WrtcConnection 생성 로직
      2. 생성되면서 내부 변수들 세팅, wrtc 변수를 새로운 Connection으로 만들어주고, addmediaStream 진행 됨
      3. initWebRtcConnection -> wrtc 객체에 `status_event` 라는 event Handler 등록
         1. 이후 이벤트 발생시 이벤트로 전해지는 결과(evt) 타입이 `answer`, `candidate`, `ready`, `rid`, `firstrid`에 따라 결과 나뉨
         2. 상황에 따라 콜백으로 `on_status` or `on_mediaUpdate` 발생
   3. addConnection
      1. 중복 connection 객체 생성 방지, 새로 생성해 배열에 저장, return 'ok'
      2. accessController까지 내려가, sessions[sessionId].state를 connecting으로 변경 후 return 'ok'
      3. conference에서도 result 전달, -> portal -> v11Client -> Client
   4. **webRTC connection complete**

7. wrtc 객체 event 발생(status_event) 

   1. 정상일 때 ready 상태이므로 `on_status({type: 'ready', audio: ~~, video: ~~, simulcast: ~~})` 발생
   2. workder node (webrtc/index)에서 콜백으로 `notifyStatus`.
   3. options.controller에게 `onSessionProgress` remote 요청
   4. Conference Agent의 onSessionProgress에서 `accessController.onSessionStatus(sessionId, sessionStatus)`
   5. onSessionStatus에서 ready -> onReady
   6. audio, video 세팅 후 `on_session_established` (콜백)하면, Conference Agent에서 accessController 만들 때 콜백으로 등록해 놓은 onSessionEstablished 실행
   7. worker Agent, worker Node 정보가 등록되어있는 sessionInfo, session을 전달하며 `addStream`
   8. 참가자(participantId == client)에게 "progress" 메시지, sessionId와 status : ready를 함께 전송

<br>

## Subscribe 과정

1. Client
   1. send signaling message(subscribe)
2. V11Client
   1. Subscripbe Id 생성, validation,
   2. portal에게 subscribe 요청
3. Portal
   1. rpcRequest subscribe 요청(portal/rpcReq)
   2. Conference Agent가 받음
4. Conference Agent
   1. 여러 유효성체크, subs 정보 저장
   2. initiateSubscription -> subsc배열에 subs 객체 추가
   3. accessController.initiate
5. Access Controller
   1. Session 배열에 session 객체 저장
   2. getWorkernode를 통해 worker node 얻고, locality 변수에 저장
   3. 이후 worker node, session에 대한 정보들 conference/rpcRequest의 RPC call(initiate)
   4. conference/rpcReq 에서 direction === out이므로 목적노드의 subscribe 요청
6. Worker node(webrtc/index)
   1. webRTC connection 생성 (createdWebRTCConnection)
   2. 위의 메서드 내부에서 WrtcConnection 객체 생성, 이 때 pub을 위한 addMediaStream 또한 진행됨(미디어 스트림 생성)
      1. Wrtc 객체 생성하면서 Webrtc connection Ready되고, 여기서 Conference node에게 connection ready status를 알려야 함.( onSessionProgress 호출)
      2. accessController에서 onReady 상태가 되면, 미리 세팅해두었던 conference의 onSessionEstablished를 호출
7. Conference
   1. addSubscription 호출, media.video, audio 설정 후 roomController -> subscribe
   2. getAudioStream
      1. mixed Stream이라면 mixedStream을 생성하고 AudioStream을 얻음
   3. getVideoStream
      1. mixed Stream이고, 매치되는 mixed stream이 없다면 해당하는 Stream을 생성 후 얻음

<Br><br>

#### Reference)

Client - https://github.com/open-webrtc-toolkit/owt-client-javascript/blob/8fae8a8e1714109d0851391f8fd20ffe3775cb65/src/sdk/conference/channel.js#L146

Server - https://github.com/open-webrtc-toolkit/owt-server