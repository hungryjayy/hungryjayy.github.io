# AMQP 라이브러리 Option

* ack, noAck
  * `noAck: true` 
    * RabbitMQ가 고객에게 메시지를 전달하면 바로 삭제 표시.
    * 작업자가 죽으면 메시지 손실
* durable, persistent
  * ​	durable - rabbitMQ가 예기치 않게 종료되어도 queue를 잃지(잊지) 않게 됨.
* prefetch
  * prefetch count가 1로 설정되어있으면 소비자로부터 ack를 받지 못한 메시지가 1개라도 있을 때 해당 소비자에게는 메시지를 전달하지 않음.
  * 즉, prefetch count는 소비자에게 동시에 전달되는 메시지의 양