# Kotlin & RabbitMQ RPC 통신 서버 개발 기록

RabbitMQ를 통해 RPC 구현 <br><br>

## 201204 
### Kotlin<br>

* Kotlin 가변인수 vararg<br>
* Kotlin JSON - put을 사용?? receiver 입장에선 JSON의 구조를 모름.<br>

### RabbitMQ

* 핵심 연결 역할: `convertSendAndReceive()` JSON으로 전달 안 됨.<br>
  * `sendAndReceive`는 message로 자동 convert 지원하지 않는다.<br>
  * Kotlin -> JSON 쉽게 변환하는 방법 `""" """` 이용<br>
  * 가변인자로 받은 경우 각각 parsing시 `as Int`, `as String` 등 명시<br>
* `RabbitTemplate`vs `AsyncRabbitTemplate`<br>


-----------

## 201207 AsyncRabbit, Rabbit 차이 비교
<br>

* Queue name 설정하면 bind 안되는이유는?

좋은 예제 https://reflectoring.io/amqp-request-response/

https://cheese10yun.github.io/spring-rabbitmq/

https://devahea.github.io/2019/04/30/AMQ-%EB%AA%A8%EB%8D%B8%EA%B3%BC-Exchange-Queue-Binding-%EC%97%90-%EB%8C%80%ED%95%B4/

<br>

## 201210 연구 끝.<br>
* Service <-> MQ(broker) JSON
* MQ(broker)-> Agent(receiver) String
* Agent가 parsing 후 처리<br>

* `convertSendAndReceive()` JSON 전달 불가<br>
  
* `JSONObject().toString()` 전달<br>
  
* Unit test에 Coroutine 도입해야 완전한 비동기 테스트<br>

  

 ### RabbitTemplate vs AsyncRabbitTemplate<br>
Test 1 ) RabbitTemplate<br>
 <img src = "./images/rabbitTemplate.png"><br>

 - Unit test, Client == broker, server == agent

 Test 2) AsyncRabbitTemplate<br>
 <img src = "./images/asyncRabbitTemplate.png"><br>

 - Unit test, Client == broker, server == agent
