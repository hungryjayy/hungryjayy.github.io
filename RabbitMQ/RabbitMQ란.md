## Rabbitmq란?
오픈소스 메시지 브로커이다. 가볍고, 쉬우며 다양한 messaging protocol을 지원한다.

## 특징
* Async messaging을 지원한다.
* Docker 환경에 적합.
* 높은 성숙도(기능이 많다), 처리율 높은 편
* Authentication mechanisms (TLS, LDAP)
<br>

## 주요 개념
<img src = "./images/rabbitMQ.png">

#### Producer
- 메시지 생성, 발송하는 역할
- 메시지가 Queue에 저장되고, Producer는 Exchange를 통해 Queue에 접근.<br>

#### Consumer
- 메시지 수신하는 역할
- Queue에서 직접 가져옴.<br>

#### Queue
- Producer에 의해 발송된 메시지가 저장되는 곳
- 미들웨어 역할
- Server 종료시 queue 리셋 (유지하려면 Durable = true / PERSISTENT_TEXT_PLAIN옵션 설정)<br>

#### Exchange
- Queue에 종류가 있기 때문에 여기서 적절한 Queue로 메시지 전달(Routing)
- Router 역할.<br>

     ##### Exchange 종류
      * Direct
          Routing key를 통해 보냄. 하나의 큐에 여러 routing key 가능.
      * Topic
          Routing pattern을 이용.
      * Headers
          Topic과 비슷. header를 쓴다.
      * Fanout
          Exchange에 등록된 모든 Queue에 전송.

#### Binding
- Exchange에게 메시지를 routing 할 규칙 지정. <br>
<br>
<br>



#### Reference

#### https://blog.dudaji.com/general/2020/05/25/rabbitmq.html