# RabbitMQ - RPC

<img src = "/home/joowon/lab/정리/TIL/RabbitMQ/images/rpc.png">

- API 서버와 분석 서버를 연결하는 queue 역할이기 때문에 언제 죽을지 모름. 그러므로 오랜 시간 RPC가 다운될 경우를 고려한다면 에러 처리를 잘해야 한다.
- 아래의 작업들은 모두 `RabbitTemplate`을 통해 자동으로 동작한다.

1. `Configuration`이 새로운 `DirectExchange` 와 client set up

2. client는 `convertSendAndReceive` 메서드를 통해 exchange name, routing Key, message를 queue(`rpc_queue`)로 보낸다.

3. RPC worker(server)는 request를 기다리다가 queue에서 request가 오면 작업을 수행하고, queue를 통해 결과 message를 `reply_to`에 명시되어있는 client에게 보낸다.

4. client는 callback queue를 기다리다가 message가 오면 `correlationId` 를 체크한다. request에서의 value와 일치한다면 application으로 response를 return한다.<br><br><br>

#### Reference)

#### https://www.rabbitmq.com/tutorials/tutorial-six-javascript.html