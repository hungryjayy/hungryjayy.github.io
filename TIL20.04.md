# 200405

* 코딩테스트 준비

  * c++ string replace All

  ```c++
  while((pos = str.find(" ")) != string::npos) {
      str.replace(pos, size, str2); // str2로 대체
  }
  ```
  * 백트랙킹 문제에서 굳이 visit 하나하나 바꾸지 말자. check할때 하나하나 접근하는게 이득



# 200407

* 코딩테스트 준비
  * 이분탐색에서 기본적으로 while 조건문은 left <= right 



* lint vs prettier

  * lint: 코드 정적 분석
    * 코딩 컨벤션에 어긋나는지
    * 안티패턴 검출
    * 간단한 code formatting

  *여태 사용하던건 lint에 prettier의 포메팅 기능을 포함했던 것.* 

  * prettier: 코드가 예쁘게 보이는지
    * 코드 최대 길이
    * 작은 따옴표 or 큰 따옴표

> use prettier for formatting and linters for catching bugs!

* #### eslint-plugin-prettie

  * Prettier를 ESLint 플러그인으로 추가한다. 즉, Prettier에서 인식하는 코드상의 포맷 오류를 ESLint 오류로 출력해준다.



## Kafka

* 특징
  * Messaging system.
  * pub/sub 모델 기반 동작. Producer, consumer, broker 구성.
    * 특정 수신자에게 직접 보내주는 시스템이 아님.
  * publisher는 메시지를 topic을 통해 카테고리화
  * consumer는 해당 topic을 subscribe해 메시지를 읽어올 수 있음.
  * 클러느터 내의 broker 분산은 ZooKeeper가 담당
* 장점
  * 메시지를 파일시스템에 저장 - durability 보장.
  * consumer가 broker로부터 pull 하는 방식
  * Producer 중심적. 많은 양의 데이터를 파티셔닝에 기반.



## RabbitMQ

* 특징
  * 여러가지 매커니즘 존재
  * 개방형 프로토콜을 위한 AMQP 구현을 위해 개발
* 장점
  * 유연한 routing 가능
  * Broker 중심적. producer / consumer 간의 보장되는 메시지 전달에 초점
  * 데이터 처리보단 관리적 측면이나 다양한 기능 구현을 위한 서비스를 구축할 때 사용

# 200408

## RPC Module => NodeJS Stream Binder

- RPC Layer Interface -> [RabbitMQ, Kafka, PO]
  - Kafka는 Messaging system. RPC는 service X가 Y에 대해 알고 있어야 한다.
- RabbitMQ, Kafka 잘 알아보기 (Kafka Cluster 구축)
- 신기한 Kafka 기능도 지원하기



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



