---
layout: post

title: Kotlin & RabbitMQ를 이용한 RPC 통신 서버 개발 기록

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [rabbitmq, message queue, kotlin]

featuredImage: 

img: 

categories: [RabbitMQ]

date: '2020-12-04'

extensions:

  preset: gfm



---

<br>

## 201204 
### Kotlin<br>

* Kotlin 가변인수 활용을 위해 vararg 적절히 이용
* Kotlin에서의 JSON: receiver 입장에선 JSON의 구조를 모른다. ~~`put`을 사용했지만,~~ 별도 객체를 구성하는 것이 좋다.

<br>

### RabbitMQ

* 핵심 연결 역할: `convertSendAndReceive()` JSON으로 전달 안된다. 나중에 다시 확인할 필요가 있어보인다.
  * `sendAndReceive`는 message로 자동 convert 지원하지 않는다.
  * Kotlin -> JSON 쉽게 변환하는 방법 `""" """` 이용
  * 가변인자로 받은 경우 각각 parsing시 `as Int`, `as String` 등 명시
* `RabbitTemplate`vs `AsyncRabbitTemplate` 차이점 비교

<br>


-----------

## 201207 AsyncRabbit, Rabbit 차이 비교
<br>

* Queue name 설정하면 bind 안되는이유는?

좋은 예제 https://reflectoring.io/amqp-request-response/

https://cheese10yun.github.io/spring-rabbitmq/

https://devahea.github.io/2019/04/30/AMQ-%EB%AA%A8%EB%8D%B8%EA%B3%BC-Exchange-Queue-Binding-%EC%97%90-%EB%8C%80%ED%95%B4/

<br>

## 201210 연구 끝.<br>
* Service <-> MQ(broker) `JSON`
* MQ(broker)-> Agent(receiver) `String`
* Agent가 parsing 후 처리
* `convertSendAndReceive()` JSON 전달 불가
* `JSONObject().toString()` 전달
* Unit test에 Coroutine 사용해야 완전한 비동기 테스트


<br>

 ### RabbitTemplate vs AsyncRabbitTemplate<br>

#### Test 1 ) RabbitTemplate

 <img src = "https://hungryjayy.github.io/assets/img/RabbitMQ/rabbitTemplate.png"><br>

<br>

#### Test 2) AsyncRabbitTemplate

 <img src = "https://hungryjayy.github.io/assets/img/RabbitMQ/asyncRabbitTemplate.png">

<br><br>

#### Reference)

 https://reflectoring.io/amqp-request-response/

https://cheese10yun.github.io/spring-rabbitmq/

https://devahea.github.io/2019/04/30/AMQ-%EB%AA%A8%EB%8D%B8%EA%B3%BC-Exchange-Queue-Binding-%EC%97%90-%EB%8C%80%ED%95%B4/
