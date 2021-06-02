# 라인에서 Kafka를 사용하는 법

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