# Sync, Async, blocking, non-blocking

## 각 조건들

* 동기 조건: 두개 이상의 작업이 시작 종료시간이 같거나 시작과 동시에 종료 (끝났는지 안끝났는지 확인)
* 비동기의 조건: 시작, 종료 시간을 다른 작업과 맞추지 말 것
* block 조건: 다른 작업 진행되는 동안 기다릴 것 (코드상 Return 하지않음)
* non block 조건: 다른 작업 진행 동안 기다리지 말 것. (코드상 일단 Return)

<br>

<img src="https://media.vlpt.us/images/wonhee010/post/be726da1-3605-475c-b7ab-93fe89636a8e/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202020-08-16%20%EC%98%A4%ED%9B%84%205.04.49.png" alt="img" style="zoom: 50%;" />

## Sync
* 기본적인 mvc 패턴, application에서 I/O 요청한 후 완료되기 전까지 application block된다.
* 메서드에서 다른 메서드를 호출해, 결과값 기다리는 것

<br>

## Sync + non-blocking
* 논 블로킹 IO에 접근하는 가장 기본적인 패턴
* application에서 I/O 요청한 후 return하여 다른 작업 수행하다가 완료되었는지 틈틈히 확인해준다.
  * 이것이 **polling** 방식 (**busy-waiting**)
  * long polling : 응답주는 쪽에서 기다렸다가 지연 응답을 주는 것
* 작업 효율이 좋지 않다고 함

<br>

## Async + blocking

* 비효율적이다. Async + non-block 모델에서도 잘못된 코드로 인해 이와 같이 동작할 수 있다.

  e.g) Nodejs + Mysql. (mysql 드라이버는 blocking 방식이므로 이와 같은 문제 발생) 환경에서 쿼리 결과를 기다린다면 이러한 경우에 해당한다.

<br>

## Async + non-blocking

* I/O 요청 후 return해 다른 작업을 수행.
* **이벤트 루프** 동작 방식: 완료시 **이벤트**가 발생하거나 미리 등록한 **callback**을 통해 이후 작업 진행.
* HTTP 커넥션 입출력하는 웹 서버에서의 **다중 I/O 서버**를 생각해보면 된다. 이 때 모든 커넥션들은 각각 본인의 작업만 끝나면 **client에게 응답메시지**로 전송되고 커넥션은 종료된다. 물론, I/O를 처리해야하는 스레드는 같은 하나의 스레드이므로, 해당 스레드가 작업중일 때 다른 작업의 커넥션들은 run loop에서 **Block**되지만, 웹서버 입장에서는 스레드가 Async non blocking으로 잘 수행한다고 볼 수 있다.

<br><br>

#### Reference)

#### https://deveric.tistory.com/99

#### https://velog.io/@wonhee010/%EB%8F%99%EA%B8%B0vs%EB%B9%84%EB%8F%99%EA%B8%B0-feat.-blocking-vs-non-blocking

#### https://yorr.tistory.com/20