# Websocket (socketIOServer) 코드 분석 및 시나리오별 정리

1. `socket.on('login')` (V11Client에 대한 로직)
1. login 시 state '**initiated**' -> '**connecting**'
   
2. login_info.protocol 버전에 따라 client를 LegacyClient 혹은 V11Client 혹은 V10 Client로 초기화 
   
   * 이 Client에 대한 socket 만들고, 그 소켓들에 대한 eventListener들 등록
   
3. validation을 통해 login_info에서 token 뽑아서 `client.join` (V11 or Legacy or V10)-> `portal.join(id, token)`
   
   1. 토큰, 시그니처 비교 validation
   
   2. dataAccess에서 해당 토큰 지우고 login
   
   3. 해당 유저 방에 대해 getController 
   
      1. clusterManager를 통해 scheduler에게 전해지고, `schedulers["conference"].schedule`
         2. scheduler는 해당 roomId에 대해 worker 할당, worker, info 콜백
         3. 해당 worker(==controllerAgent) -> `agent/nodeManager.getNode()`
         4. roomId에 해당하는 node 찾고, 준비되면 nodeId return
   
   4. 위 과정에서 찾은 node(=controller) 로 `join(controller, room_id, participant)` rpc Call
   
   5. conference의 `that.join`
   
      1. 방이 꽉찼거나 room role이 안맞는지 검사 후 참가(`addParticipant`)
            * 여기서 `participants[participantInfo.id]` 에 추가, 다른 참가자들에게 message 전송
   
      2. 이후 permission, room 콜백
   
   6. portal의 `participants[participanteID]`에 `in_room: room_id`와 `controller: room_controller` (현재 방 컨트롤러) 등록
   
   7. return tokenCode, data(`userInfo, role, permission, joinResult.room`)
   
4. 해당 결과 that에 roomId, tokenCode 저장, that return
   
5. socketIO에서 reconnectionTicket 발급, state '**connected**' 변환, `dock.onClientJoined(client_id, client)`
   
   1. `observer.onJoin(client.tokenCode)` 호출, clusterWorker에서 tasks에 해당 **tokenCode** **등록**



2. `socket.on('relogin')`
   1. relogin 시 state '**initialized**' -> '**connecting**'
   2. reconnection ticket 검사 후 티켓의 참가자Id를 통해 client 찾아옴
   3. client.connection.reconnect
      * reconn_timer(disconnect시 set) 초기화 후 return pendingMsg, clientId, protocolVersion, **reconnection**
   4. 위에서 받은 reconnection 검사 후 pendingMsg, clientId,  protocolVersion set, `resetConnection`(V11 or legacy)
      1. connection 새로 set, listenAt (새로운 connection의 socket에 대해 eventListener 등록)
   5. reconnectionTicket 생성, state '**connected**', pending 상태의 메시지 다시 전송



3. `socket.on('disconnect')`
   1. (1)'**connecting**'이거나 (2)'**connected**'인데 reconnection은 불가능할 때
      * `forceClientLeave()`
   2. '**connected**` state에서 reconnection이 가능한 상태일 때
      1. 해당 소켓 disconnect
      2. waiting_for_reconn 타이머 set. -> 추후 reconnection을 위해
         * `forceClientLeave()`

   * `forceClientLeave()` 로직
     1. client id를 통해 client를 받아오고, 현재의 connection 정보 === that 이라면
     2. onClientLeft -> clusterWorker의 tasks에서 이 client의 tokenCode에 해당하는 task 제거

























