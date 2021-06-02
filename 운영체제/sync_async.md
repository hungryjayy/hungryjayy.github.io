# 동기 vs 비동기(Sync vs Async)

## 동기(sync)

* task를 순서대로하는 것
* 메소드를 실행시키고, 반환 값을 기다림. 이때 blocking

## 비동기(async)

* task 완료되기까지 기다리지 않고 다른 것 수행하고 있는 것
* blocking 되지 않음. task를 이벤트 큐에 넣거나 백그라운드 스레드에 위임하고 다음 코드를 수행
  * JS 이벤트 루프에서는 Microtask Queue / Task Queue에 넣어 놓음



#### Reference)

#### https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/OS#%EB%8F%99%EA%B8%B0%EC%99%80-%EB%B9%84%EB%8F%99%EA%B8%B0%EC%9D%98-%EC%B0%A8%EC%9D%B4