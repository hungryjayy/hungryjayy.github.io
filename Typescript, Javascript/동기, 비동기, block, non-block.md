# Sync, Async, blocking, non-blocking

## 각 조건들

* 동기 조건: 두개 이상의 작업이 시작 종료시간이 같거나 시작과 동시에 종료
* 비동기의 조건: 시작, 종료 시간을 다른 작업과 맞추지 말 것
* block 조건: 다른 작업 진행되는 동안 기다릴 것 
* non block 조건: 다른 작업 진행 동안 기다리지 말 것



![img](https://media.vlpt.us/images/wonhee010/post/be726da1-3605-475c-b7ab-93fe89636a8e/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202020-08-16%20%EC%98%A4%ED%9B%84%205.04.49.png)

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

* JS 프라미스 이행을 기다리거나, await해서 기다려주는 것



## Async + non-blocking

* I/O 요청 후 return해 다른 작업을 수행.
* 완료시 **이벤트**가 발생하거나 미리 등록한 **callback**을 통해 이후 작업 진행.



#### Reference)

#### https://deveric.tistory.com/99

#### https://velog.io/@wonhee010/%EB%8F%99%EA%B8%B0vs%EB%B9%84%EB%8F%99%EA%B8%B0-feat.-blocking-vs-non-blocking
