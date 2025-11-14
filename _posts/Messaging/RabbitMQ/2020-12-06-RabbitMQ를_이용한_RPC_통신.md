---
layout: post

title: RabbitMQ를 이용한 RPC 통신

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [rabbitmq, message queue, rpc]

featuredImage: 

img: 

categories: [Messaging, RabbitMQ]

date: '2020-12-06'

extensions:

  preset: gfm



---

<br>

<img src = "https://hungryjayy.github.io/assets/img/RabbitMQ/rpc.png">

- 서버 투 서버에서의 queue 역할인 RPC가 죽는 경우를 고려해 에러 처리를 잘해야 한다.
- 아래의 작업들은 모두 `RabbitTemplate`을 통해 자동으로 동작한다.

<br>

#### 과정

1. `Configuration`이 새로운 `DirectExchange` 와 client set up
2. client는 `convertSendAndReceive` 메서드를 통해 exchange name, routing Key, message를 queue(`rpc_queue`)로 보낸다.
3. RPC worker(server)는 request를 기다리다가 queue에서 request가 오면 작업을 수행하고, queue를 통해 결과 message를 `reply_to`에 명시되어있는 client에게 보낸다.
4. client는 callback queue를 기다리다가 message가 오면 `correlationId` 를 체크한다. request에서의 value와 일치한다면 application으로 response를 return한다.

<br><br>

#### Reference)

https://www.rabbitmq.com/tutorials/tutorial-six-javascript.html
