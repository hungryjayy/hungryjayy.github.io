---
layout: post

title: OWT server 토큰 생성 과정 분석

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [webrtc]

featuredImage: 

img: 

categories: [WebRTC]

date: '2021-06-01'

extensions:

  preset: gfm

---

: 토큰 생성과정 이해되지 않을 때 아래의 과정 참조하기

<br>

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
   
7. dataAccess에 token 추가 후 tokenString 클라이언트에게 전달

