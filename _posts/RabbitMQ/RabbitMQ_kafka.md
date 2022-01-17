# Rabbitmq와 kafka 간단 비교

## 공통점
메시지 큐. 로그 시스템을 구축하기 위한 좋은 기술. API 송수신과 비동기처리를 할 수 있다. 분산 처리 또한 가능.<br><br>
> *c.f) WAS에 포함되어있는 message service(message queue)가 이러한 것들* 

### 메시지 큐의 장점
- 비동기 처리,
- Application과의 분리
- 일부 실패 시 전체 영향 X
- 실패 시 재실행 가능
- 작업 처리 확인 가능
- 다수 process → Q 메시지 가능

## RabbitMQ
- 구성이 쉽고 편하다.
- 비동기 가능하며 처리율이 높다.
- 성숙하다.
- 분산이 필요한 Traffic을 처리할 것 아니라면 Kafka말고 이걸 쓰자.

## Kafka
- Subscription, 비동기식
- 고성능
- 분산처리에 효율적

### kafka vs rabbitMQ

* rabbitMQ : broker가 consumer에 메시지를 push
* kafka : consumer가 broker로부터 메시지를 pull하는 방식
  * 애초에 메시지를 disk에 저장하기 때문에 이러한 방식이 가능한 것
