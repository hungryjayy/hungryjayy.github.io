# Sync, Async, blocking, non-blocking

## 각 조건들

* 동기 조건: 두개 이상의 작업이 시작 종료시간이 같거나 시작과 동시에 종료
* 비동기의 조건: 시작, 종료 시간을 다른 작업과 맞추지 말 것
* block 조건: 다른 작업 진행되는 동안 기다릴 것 
* non block 조건: 다른 작업 진행 동안 기다리지 말 것



<img src="https://media.vlpt.us/images/wonhee010/post/be726da1-3605-475c-b7ab-93fe89636a8e/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202020-08-16%20%EC%98%A4%ED%9B%84%205.04.49.png" alt="img" style="zoom: 50%;" />

## Sync
* 기본적인 mvc 패턴, application에서 I/O 요청한 후 완료되기 전까지 application block된다.
* 메서드에서 다른 메서드를 호출해, 결과값 기다리는 것



## Sync + non-blocking
* application에서 I/O 요청한 후 return하여 다른 작업 수행하다가 완료되었는지 틈틈히 확인해준다.
  * 이것이 polling 방식
* 작업 효율이 좋지 않다고 함



## Async + blocking

* 비효율적이다. Async + non-block 모델에서도 잘못된 코드로 인해 이와 같이 동작할 수 있다.

  e.g) Nodejs + Mysql. (mysql 드라이버는 blocking 방식이므로 이와 같은 문제 발생)

* ~~JS 프라미스 이행을 기다리거나, await해서 기다려주는 것~~

  * 다시 생각해보니, 이벤트 루프에서 **await을 만나는 시점에 해당 async함수 전체는 마이크로 태스크 큐로** 들어감. 따라서, 콜 스택에서는 해당 async함수 호출 이전에 처리하던 것을 처리할 것이므로 CPU낭비 되지 않을 것 같고, async + non-blocking에 해당.
  
  * 추가 210602: 해당 async 함수 입장에서는 blocking이라고 할 수 있으나, 실제로 인터프리터의 실행을 멈추는 것은 아님. "다른 이벤트가 이벤트 핸들러를 실행할 수 있는 상태"인 것이다.
  
    출처: https://yorr.tistory.com/20



## Async + non-blocking

* I/O 요청 후 return해 다른 작업을 수행.
* 완료시 **이벤트**가 발생하거나 미리 등록한 **callback**을 통해 이후 작업 진행.



#### Reference)

#### https://deveric.tistory.com/99

#### https://velog.io/@wonhee010/%EB%8F%99%EA%B8%B0vs%EB%B9%84%EB%8F%99%EA%B8%B0-feat.-blocking-vs-non-blocking

#### https://yorr.tistory.com/20