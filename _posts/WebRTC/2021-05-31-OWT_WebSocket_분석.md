---
layout: post

title: OWT Websocket (socketIOServer) 코드 분석 및 시나리오별 정리

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [webrtc]

featuredImage: 

img: 

categories: [Study, WebRTC]

date: '2021-05-31'

extensions:

  preset: gfm



---

: 로직 흐름 이해할 때 아래의 과정 참고하기

<br>

1. `socket.on('login')` (V11Client에 대한 로직)
2. protocol 버전에 따라 client를 ws 초기화된 portal에 LegacyClient / V11Client / V10 Client로 초기화 
   
   * 이 Client에 대한 socket 만들고, 그 소켓들에 대한 eventListener들 등록
   
3. validation 후, login_info에서 token 뽑아서 `client.join` -> `portal.join(id, token)`

   1. 토큰, 시그니처 비교 validation

   2. 토큰 생성할 때 **dataAccess**에 저장해놓았던 해당 토큰 지우고 login

   3. 해당 유저 방에 대해 getController 

      1. clusterManager를 통해 scheduler에게 전해지고, `schedulers["conference"].schedule`
         * scheduler는 해당 **roomId**에 대해(Token 발급시에는 TokenCode) worker 할당, worker, info 콜백
      2. 해당 worker(==controllerAgent) -> `agent/nodeManager.getNode()`
         1. roomId에 할당된 node 찾고, 준비되면 addTask
         2. addTask에서 해당 nodeId에 해당 room task가 없다면 할당(agent/index.addTask), nodeId return
      3. 위 과정에서 찾은 node(=controller) 로 `join(controller, room_id, participant)` rpc Call

   4. conference의 `join`

      1. room init -> 없으면 init, 있거나 만들어지고 있으면 냅두고 아니면 새로 만듦

      2. 방이 꽉찼거나 room role이 안맞는지 검사 후 참가(`addParticipant`)
         * 여기서 `participants[participantInfo.id]` 에 추가, 다른 참가자들에게 message 전송

         2. 이후 permission, room 콜백

   5. portal의 `participants[participanteID]`에 `in_room: room_id`와 `controller: room_controller` (현재 방 컨트롤러) 등록

   6. return tokenCode, data(`userInfo, role, permission, joinResult.room`)

4. 해당 결과 that에 roomId, tokenCode 저장, that return
   
5. socketIO에서 reconnectionTicket 발급 `dock.onClientJoined(client_id, client)`
   
   1. `observer.onJoin(client.tokenCode)` 호출, clusterWorker에서 tasks에 해당 **tokenCode** **등록**
   5. reconnectionTicket과 함께 결과 전송

<br>

2. `socket.on('relogin')`
   1. reconnection ticket 검사 후 티켓의 참가자Id를 통해 client 찾아옴
   2. client.connection.reconnect
      * reconn_timer(disconnect시 set) 초기화 후 return pendingMsg, clientId, protocolVersion, **reconnection**
   3. reconnection 가능한지, Client로 부터 받은 ticket에 써있는 participantId가 같은지 validation
   4. pendingMsg, clientId,  protocolVersion set, `resetConnection`(V11 or legacy)
      * connection 새로 set, listenAt (새로운 connection의 socket에 대해 eventListener 등록)
   5. reconnectionTicket 생성, pending 상태의 메시지 다시 전송

<br>

3. `socket.on('disconnect')` - state에 따라 다르게 처리

   1. (1)'**connecting**'이거나 (2)'**connected**'인데 reconnection은 불가능할 때
      
      * `forceClientLeave()`
      
   2. '**connected**` state에서 reconnection이 가능한 상태일 때

      * 이 경우 : **1. 예기치 못하게 나가졌을 때, 2. mobile client로부터 connection이 초기화 되었을 때**

      1. 해당 소켓 disconnect
      2. waiting_for_reconn 타이머 set. -> 추후 reconnection을 위해
         * `forceClientLeave()`

   * `forceClientLeave()` 로직
     1. client id를 통해 client를 받아오고, 현재의 connection 정보 === that 이라면
     2. onClientLeft -> clusterWorker의 tasks에서 이 client의 tokenCode에 해당하는 task 제거

<Br>

### State, reconnection 변화

1. login
   * 성공: initialized -> connecting -> connected / reconnection 가능
   * 실패: initialized -> connecting -> initialized
     * 로그인 도중 disconnect 사용자가 나갈 때 // 예기치 못한 실패 -> 일단 validation 통과했다면 reconnection 가능
     * validation 실패 -> reconnection 불가
2. relogin 
   * 성공: initialized -> connecting -> connected / reconnection 가능
   * 실패: initialized -> connecting -> initialized
     * 티켓 만료 / 유효하지 않은 티켓 시그니처 / ticket의 client 미존재 / reconnection 불가능 / 티켓의 client와 참가자 불일치

3. logout
   * connected -> initialized / reconnection 불가능

<br>













