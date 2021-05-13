# Scheduler의 schedule 로직 분석

* 배경
  * `reserveWorkerForTask` 를 통해 tasks[task]에 worker, reserve_time, reserve_timer 할당



## 토큰 받는 과정

1. Client
   * API -> `createToken(room, user, role, preference)`
     * 여기서 `preference = {isp: 'isp', region: 'region'};`
     * 받은 token을 `app.post('/token', () => {})` (`createToken` 메서드 호출부) 전달

2. Server - v1/index
   * 요청받아 tokenResource.create 전달

3. tokenResource
   1. req.user, role, body, origin(preference) 세팅 후 `generateToken`
   2. 토큰 생성 후 Portal 할당을 위해 `requestHandler.schedulePortal`

4. requestHandler
   * rpc Call -> cluster_name으로 `schedule` 메서드 호출. `1. portal, 2. tokenCode, 3. origin, 4. 60 * 1000`

5. clusterManager
   * `schedule`에서 `schedulers["portal"]`에게 `schedule(tokenCode, origin, 60 * 1000)`

6. scheduler - 실질적 토큰에 대한 worker 스케줄링 부분

   * `reserveWorkerForTask` 를 통해 `tasks[task]`에 worker, reserve time 등 할당. (해당 테스크에 워커 할당)
   * `that.add` 를 통해 `workers[worker]` 에 state, load, info, tasks 할당. (해당 워커 추가 == 초기화)
     * 메서드 호출부 : `clusterManager.workerJoin`
     * topicMessage를 통해 portal에 대한 topic을 subscribe할 때 portal에 대한 worker 할당

   1. **tasks[task] (현재 토큰에 대해 수행할 worker를 이미 할당받은경우)**

      * `newReserveTime` = 새로 입력받은 reserveTime과 비교해 많이 남은 쪽으로

      * 토큰에 대한 worker 초기화 되지 않았으면 tasks[task] 삭제후 **2. tasks[task] 존재하지 않던 경우**로 이동

      * 토큰에 대한 worker 이미 초기화 되어 있으면

        1. worker가 수행할 토큰들 중 해당 토큰 있다면 newReserveTime로 세팅하고 **on_ok**

        2. worker가 수행할 토큰들 중 해당 토큰 없다면 -> 현재의 토큰 수행할 worker 다시 할당(다시 초기화) 후 **on_ok**

   

   2. **tasks[task] 애초에 존재하지 않던 경우(현재 토큰에 대한 task 수행할 worker가 애초에 없던 경우)**

      : 두 가지 과정을 통해 가능한 worker 리스트를 만들어줌

      1. worker들 중 가용 worker를 모두 candidates에 push
         * 가용 : worker capable하고, state == undifined or 2 인 것들
         * 가능 worker 없으면 error(no worker available, all in full load)

      

      2. `matcher.match` (`portalMatcher.match`) 에서 worker origin(isps, regions)과 preference(사용자의 origin)가 적절한 것 선
         * isps는 무조건 적절한 것만. region은 최대한 적절한 것(적절하지 않아도 넣어줌).
         * 가능 worker 없으면 error(no worker matches the preference).

      

      * 1, 2 과정을 통해 리스트된 worker들 중 strategy 전략에 따라 **하나의 worker** 골라냄
        * 토큰에 대한 task 수행할 worker 할당 후 **on_ok**



## Websocket connection

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

























