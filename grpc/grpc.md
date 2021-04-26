## GRPC : Google에서 만든 RPC

### vs RabbitMQ

* gRPC: RPC framework
  * 장점
    * 1. 경량. 작은 리소스만으로도 좊은 성능
      2. 효율: gRPC는 기본적으로 protobuf(프로토콜 버퍼)이라는 IDL을 사용(JSON 등 다른 직렬화 방식도 사용 가능). 이를 통해 구조화된 데이터를 직렬화 함.
         * 직렬화: 객체 또는 data를 Byte형태 스트림으로 변경해 보냄
         * IDL(Interface Definition language): 인터페이스를 묘사하기 위한 명세 언어. 타 언어간 통신을 가능하게 함.
           * e.g) c++을 사용한 컴포넌트와 자바를 사용한 컴포넌트 사이에서 국한되지 않고 인터페이스를 묘사
      
      ![img](https://media.vlpt.us/images/kyusung/post/058599e3-3486-4b0b-9600-59a827f2d40a/gRPC%20%E2%80%93%20Guides%202020-03-11%2018-21-22.png)
      
      
    
  * 대기 시간이 짧고 처리량이 높은 통신에 필요(효율성이 중요한 마이크로 서비스)
  
  * Best example: 대표적으로 많이 쓰이는 IoT 기기들
  
    1. voice controller, 2. smart light switch, 3. smoke alarms lock, 4. camera 
  
  * RPC 특성상, 아키텍처가 아니라 server와 client의 관계에서 교류가 이루어짐
  
  * fresh take on old method aka RPC
  
  * LB를 지원받을 수도 있음
* rabbitMQ: messaging broker. 다른 직렬화 방식을 사용할 수도 
  
  * 메시지를 send, recv할 수 있는 플랫폼을 제공



### 왜 gRPC를 사용하는가?

* Protocol buffers를 이용해 직렬화 된 바이트 스트림으로 통신해 JSON 기반의 통신보다 가벼움
* internal 통신이 빈번한 MSA에서 gRPC를 적용 시 latency 감소, 더 많은 트래픽 처리



#### Reference ) 

https://blog.banksalad.com/tech/production-ready-grpc-in-golang/?gclid=Cj0KCQjw9_mDBhCGARIsAN3PaFMi15aVSQKdivgRBWUOygiy8oVfmp5IlsU0PA0eUQAwGLCGbwjskF8aAq4HEALw_wcB9437610

https://medium.com/devops-dudes/graphql-vs-rest-vs-grpc-411a0a60d18d

https://m.blog.naver.com/PostView.nhn?blogId=neos_rtos&logNo=30185472655&proxyReferer=https:%2F%2Fwww.google.com%2F

https://velog.io/@kyusung/grpc-web-example