# Kafka (vs rabbitMQ)

차이: Consumer가 Broker로부터 메시지를 **pull**하는 방식

* single consumer가 아닌 multi consumer를 염두에 두고 설계되었기 때문에 그러한 환경에서 rabbitMQ보다 성능이 좋다.

  * RabbitMQ에서는 하나의 일을 하나의 consumer가 처리하기 위해 work queue 정책

  

- Pull 방식으로 Consumer가 처리할 수 있을 때 메시지를 가져오므로 자원을 효율적으로 사용
  - Kafka는 메시지를 disk에 저장(영속성?)하고, 과거의 offset을 통해 움직일 수 있으므로 batch 작업에서 자원의 낭비나 지연이 발생하지 않음
  - 메시지를 쌓아두었다가 처리하는 batch Consumer 구현도 가능



* Messaging system임



* 동작 방식 
  * pub/sub 모델 기반 동작. Producer, consumer, broker 구성.
    * 특정 수신자에게 직접 보내주는 시스템이 아님.
  * publisher는 메시지를 topic을 통해 카테고리화
  * consumer는 해당 topic을 subscribe해 메시지를 읽어올 수 있음.



* 클러스터 내의 broker 분산은 ZooKeeper가 담당
  * Broker는 클러스터로 구성되어 동작



* 장점
  * 메시지를 파일시스템에 저장 - durability 보장.
  * Producer 중심적. 많은 양의 데이터를 파티셔닝에 기반.



## RabbitMQ

Message Broker가 Consumer에게 메시지를 **push**하는 방식

* Broker는 Consumer의 처리여부에 관계없이 push
  * 따라서 메시지 소비 속도보다 생산 속도가 빠를 경우 Consumer에 부하를 주게 됨



* RabbitMQ는 DRAM을 사용하므로 buffer를 사용하지만, DRAM을 다 사용하면 disk에 저장합니다. 따라서 batch 같이 큰 작업에서는 disk로 메시지를 읽어올 경우 지연이 발생합니다.



* 여러가지 매커니즘 존재



* 개방형 프로토콜을 위한 AMQP 구현을 위해 개발



* 장점
  * 유연한 routing 가능
  * Broker 중심적. producer / consumer 간의 보장되는 메시지 전달에 초점
  * 데이터 처리보단 관리적 측면이나 다양한 기능 구현을 위한 서비스를 구축할 때 사용



### 라인에서 Kafka를 사용하는 법

1. 분산 queue system
   * resource를 많이 사용해야하는 업무 발생시
     * 내부 처리X. 다른 프로세스에서 작동중인 백그라운드에 위임
2. 데이터 hub
   * 데이터 업데이트 발생 시 해당 데이터를 사용하는 다른 서비스들에게 전파
     * e.g ) A 사용자가 B 사용자를 친구로 추가했을 때 해당 업데이트를 Kafka의 topic에 이벤트로 입력.
     * 이 이벤트는 통계 시스템이나 타임라인 등 서비스로 전파
   * 데이터가 하나의 클러스터에 집중.
     * 데이터를 사용하는 서비스가 해당 데이터를 쉽게 찾을 수 있음

#### reference) https://engineering.linecorp.com/ko/blog/how-to-use-kafka-in-line-1/



## Topic과 Partition

* Topic
  * 하나의 관심사. 이 것을 구독하여 사용

* Partition
  * topic를 쪼갠 작은 단위
  * HA를 위해 replication 설정을 할 경우 partition 단위로 각 서버들에 분산되어 복제
    * 장애 발생 시 partition 단위로 fail over

### Partition 분산

* Producer가 어떤 partition으로 메시지를 전송할지는 partition 분배 알고리즘에 의해
  * Round robin, 메시지 키를 통해 패턴 매핑, CRC32를 통해 modulo 연산 등



### Partition replication?

![img](https://t1.daumcdn.net/cfile/tistory/2655FB425509181D07)

* 위는 replication factor을 3으로 설정한 상태의 클러스터 
* Replication factor의 수에 따른 상황(replication factor == n인 상황)
  * n개의 replica는 1개의 leader(빨강), n-1개의 follower(파랑) 로 구성
  * 읽기와 쓰기는 모두 leader에서
  * follower는 leader를 복제
  * leader 장애시 follower가 leader로 승격





## Messaging model

1. #### Queue model

   * 메시지가 쌓여있는 큐로부터 메시지를 가져와 consumer pool에 있는 consumer 중 하나에 메시지를 할당하는 방식
     * 서버 scaled 상황에서 이게 적절할 듯

2. #### Pub-Sub model

   * topic을 구독하는 모든 consumer에게 메시지 브로드캐스팅



### Consumer Group?

* 이 개념을 통해 위의 두 모델을 pub-sub 모델로 일반화 하였음.

* 

  

#### Refenence) https://epicdevs.com/17