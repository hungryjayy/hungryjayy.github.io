---
layout: post

title: Kafka 이해하기 (vs rabbitMQ)

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [kafka, infra]

featuredImage: 

img: 

categories: [Study, Kafka]

date: '2021-02-22'

extensions:

  preset: gfm

---

<br>

#### RabbitMQ와 가장 큰차이: Consumer가 Broker로부터 메시지를 **pull**하는 방식

* single consumer가 아닌 multi consumer를 염두에 두고 설계되었기 때문에 그러한 환경에서 rabbitMQ보다 성능이 좋다.

  * RabbitMQ에서는 하나의 일을 하나의 consumer가 처리하기 위해 work queue 정책


- Pull 방식으로 Consumer가 처리할 수 있을 때 메시지를 가져오므로 자원을 효율적으로 사용
  - Kafka는 메시지를 disk에 저장(영속성?)하고, 과거의 offset을 통해 움직일 수 있으므로 batch 작업에서 자원의 낭비나 지연이 발생하지 않음
  - 메시지를 쌓아두었다가 처리하는 batch Consumer 구현도 가능

* Messaging system이라고 할 수 있다.

<br>

### 동작 방식 

* pub/sub 모델 기반 동작. Producer, consumer, broker 구성.
  * 특정 수신자에게 직접 보내주는 시스템이 아님.
* publisher는 메시지를 topic을 통해 카테고리화
* consumer는 해당 topic을 subscribe해 메시지를 읽어올 수 있음.

<br>

### Zookeeper

* 클러스터 내의 broker 분산은 ZooKeeper가 담당
* 카프카의 노드 관리.
* Broker는 클러스터로 구성되어 동작
* 과반수 투표방식. 홀수로 구성

<br>

### 장점

* 메시지를 파일시스템에 저장 - durability 보장.
* Producer 중심적. 많은 양의 데이터를 파티셔닝에 기반.

<br>

## RabbitMQ

: Message Broker가 Consumer에게 메시지를 **push**하는 방식

* Broker는 Consumer의 처리여부에 관계없이 push
  * 따라서 메시지 소비 속도보다 생산 속도가 빠를 경우 Consumer에 부하를 주게 됨
* RabbitMQ는 DRAM을 사용하므로 buffer를 사용하지만, DRAM을 다 사용하면 disk에 저장한다. 따라서 batch 같이 큰 작업에서는 disk로 메시지를 읽어올 경우 지연이 발생합니다.
* RPC, Queue, 여러 서버에 Routing 등등 여러가지로 활용 가능
* 개방형 프로토콜을 위한 AMQP 구현을 위해 개발

<br>

#### RabbitMQ 장점

* 유연한 routing 가능
* 데이터 처리보단 관리적 측면이나 다양한 기능 구현을 위한 서비스를 구축할 때 사용

<br><br>

#### Reference)

https://www.popit.kr/kafka-%EC%9A%B4%EC%98%81%EC%9E%90%EA%B0%80-%EB%A7%90%ED%95%98%EB%8A%94-%EC%B2%98%EC%9D%8C-%EC%A0%91%ED%95%98%EB%8A%94-kafka/