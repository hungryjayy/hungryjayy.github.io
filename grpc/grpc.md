## GRPC : Google에서 만든 RPC

### vs RabbitMQ

* gRPC: RPC framework
  * 장점
    * 1. 경량. 작은 리소스만으로도 좊은 성능
      2. 효율: gRPC는 protobuf를 사용
         * protobufs: 구조화된 데이터를 직렬화함?
  * 많은 양과 주기적인 데이터 처리가 필요한 곳에 용이,  요청자는 low power 이거나 resource를 보존하고 싶을 때
  * Best example: 대표적으로 많이 쓰이는 IoT 기기들
    1. voice controller, 2. smart light switch, 3. smoke alarms lock, 4. camera 
  * RPC 특성상, 아키텍처가 아니라 server와 client의 관계에서 교류가 이루어짐
  * fresh take on old method aka RPC
  * LB를 지원받을 수도 있음
* rabbitMQ: messaging broker. 
  * 메시지를 send, recv할 수 있는 플랫폼을 제공

### 

### 왜 gRPC를 사용하는가?

* Protocol buffers를 이용해 직렬화 된 바이트 스트림으로 통신해 JSON 기반의 통신보다 가벼움
* internal 통신이 빈번한 MSA에서 gRPC를 적용 시 latency 감소, 더 많은 트래픽 처리
* 



#### Reference ) 

https://blog.banksalad.com/tech/production-ready-grpc-in-golang/?gclid=Cj0KCQjw9_mDBhCGARIsAN3PaFMi15aVSQKdivgRBWUOygiy8oVfmp5IlsU0PA0eUQAwGLCGbwjskF8aAq4HEALw_wcB9437610

https://medium.com/devops-dudes/graphql-vs-rest-vs-grpc-411a0a60d18d