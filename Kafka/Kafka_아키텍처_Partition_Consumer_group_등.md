# Kafka 아키텍처

## Topic과 Partition

* Topic
  * 하나의 관심사. 이 것을 구독하여 사용

* Partition
  * topic를 쪼갠 작은 단위
  * HA를 위해 replication 설정을 할 경우 partition 단위로 각 서버들에 분산되어 복제
    * 장애 발생 시 partition 단위로 fail over
  * partition 내부의 데이터 순서대로 consumer가 받을 것을 보장
    * 그러나 각각의 partition간 순서는 보장하지 않음.



### Partition 분산

* Producer가 어떤 partition으로 메시지를 전송할지는 partition 분배 알고리즘에 의해
  * Round robin, 메시지 키를 통해 패턴 매핑, CRC32를 통해 modulo 연산 등



### Partition replication?

![img](https://t1.daumcdn.net/cfile/tistory/2655FB425509181D07)

* 위는 replication factor을 3으로 설정한 상태의 클러스터 
  * 위처럼 세개의 브로커에 leader가 균등하게 분배.
  * 따라서 read,write를 모두 leader가 수행함에도 불구하고 부하가 균등 분배 됨
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

* 목적
  * 1. HA를 위함(그룹 내 서버 하나 죽어도 나머지로 task 처리 가능)
    2. 컨슈머그룹간 각자만의 offset을 관리하기 위해 
* 컨슈머 인스턴스들을 대표하는 그룹
  * consumer instance == 하나의 Process(server)
  * 각 컨슈머 인스턴스들은 offset을 이용해 본인이 어디까지 데이터를 가져갔는지 관리
  * 그룹 내 서버들끼리는 서로의 정보를 공유
* 이 개념을 통해 위의 두 모델을 pub-sub 모델로 일반화.
* Topic을 나눈 단위인 partition은 CG(consumer group) 당 하나의 consumer 접근만을 허용
  * 이 때의 consumer는 partition owner. 한번 구성되면 broker, consumer 변동이 있지 않는 한 계속 유지
    * consumer 추가 시 CG 내 재분배
    * broker 추가 시 전체에서 재분배
  * 동일한 CG의 consumer는 동일한 partition 접근 불가
* CG에 다수의 consumer가 할당되면 각 consumer마다 별도의 partition으로부터 메시지를 받아오기 때문에 CG는 큐 모델로 동작



#### Consumer  Group과 파티션 수의 관계

* 파티션 수 < 컨슈머 인스턴스 이면 안됨(비효율적 사용)
  * 1:1 맵핑이 가장 이상적
  * 그러나 파티션을 무작정 늘리는 것은 좋지 않음
    * 파티션은 토픽 생성 후 언제든 늘릴수 있지만 줄일수는 없음



#### Refenence) 

#### https://epicdevs.com/17

#### https://www.popit.kr/kafka-consumer-group/
