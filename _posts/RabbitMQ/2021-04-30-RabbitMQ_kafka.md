---
layout: post

title: Rabbitmq와 kafka 간단 비교

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [rabbitmq, message queue]

featuredImage: 

img: 

categories: [Study, RabbitMQ]

date: '2021-04-30'

extensions:

  preset: gfm
---

<br>

## 공통점
* 메시지 큐잉 시스템. 따라서 메시지 큐의 장점을 가진다.
* 로그 시스템을 구축하기 위한 좋다고 한다.
* API 송수신과 비동기처리를 할 수 있다.
* 분산 처리 가능

<br>

### 메시지 큐의 장점
- 비동기 처리로 Application과 분리되어 동작한다.
- 일부 실패하더라도 전체에 영향이 없고, 회복이 가능하다.
- 작업 처리 확인 가능하다. RabbitMQ의 경우 대시보드도 잘 지원된다.
- 처리율: 다수 process에 대한 메시지 처리 가능하다.

<br>

## RabbitMQ
- 구성이 쉽고 편하다.
- 비동기 가능하며 처리율이 높다.
- 성숙도: 오래되어 레퍼런스가 많고 편리한 도구가 많다.
- 분산이 필요한 Traffic을 처리할 것 아니라면 Kafka말고 간단하게 이걸 써도 좋다.

<br>

## Kafka
- Subscription, 비동기식
- pulling 방식으로 고성능
- 분산처리에 효율적

<br>

### kafka vs rabbitMQ

* rabbitMQ : broker가 consumer에 메시지를 **push**
* kafka : consumer가 broker로부터 메시지를 **pull**하는 방식
  * 메시지를 disk에 저장하기 때문에 이러한 방식이 가능하다.
