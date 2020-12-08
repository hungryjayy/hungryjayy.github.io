# RabbitMQ-RPC with Kotlin 이슈 및 기록

RabbitMQ를 이용해 메시지 큐 구현하기. RPC 역할 <br><br>

## 201204 only Test #1
### RabbitMQ 통신에서의 Kotlin문법, Flux 사용, JSON으로 인한 주 이슈<br>

* Kotlin의 좋은 기능: 가변인수 vararg<br>

* Kotlin에서는 JSON이 불편하다. put을 사용해야하고 receiver 입장에선 JSON의 구조를 모름.<br>
* `server`와의 핵심 연결 역할을 하는 `convertSendAndReceive()` JSON으로 전달 안 됨.<br>
  * 자꾸 Null이 반환되는 이유를 겨우 찾았다.
* `sendAndReceive`는 message로 자동 convert 지원하지 않는다.<br>
* Kotlin -> JSON 쉽게 변환하는 방법 `""" """` 이용<br>
* 가변인자로 받은 경우 각각 parsing시 `as Int`, `as String` 등 명시<br>
* Flux가 생각보다 공부할게 많다.<br>
* `RabbitTemplate`가 비동기 처리를 알아서 해주는줄 알았는데 `AsyncRabbitTemplate`이 있는걸보니 아닌가보다.<br>

## 201207 AsyncRabbit, Rabbit 차이 비교
<br><br><br>
Rabbittemplate만으로도 비동기처리가 가능한건가..
async를 억지로 우겨넣었지만 아직 비동기적의미가 없음. send와 receive를 분리하고, corrID를 활용하면 좋을 것 같다


튜토리얼보다 Stackoverflow 도움을 많이 받은거같다.

-----------
Tutorial https://www.rabbitmq.com/tutorials/tutorial-six-spring-amqp.html

