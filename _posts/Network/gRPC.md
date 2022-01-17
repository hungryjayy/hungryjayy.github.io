# gRPC

## GRPC (vs RabbitMQ)

* Google의 RPC framework. TCP/IP 프로토콜, HTTP/2 프로토콜을 사용.
  
  * 메시지 헤더 압축, server push 등으로 성능 좋음
  
* 기본적으로 IDL로는 protocol buffer(PB)를 사용. JSON 등 다른 직렬화 방식도 사용 가능

* 장점

  1. 경량. 작은 리소스만으로도 좊은 성능
  2. 효율: protobuf 를 통해 구조화된 데이터를 직렬화 함.

  * 직렬화: 객체 또는 data를 Byte형태 스트림으로 변경해 보냄

  ![img](https://media.vlpt.us/images/kyusung/post/058599e3-3486-4b0b-9600-59a827f2d40a/gRPC%20%E2%80%93%20Guides%202020-03-11%2018-21-22.png)

* 대기 시간이 짧고 처리량이 높은 통신에 필요(효율성이 중요한 마이크로 서비스)

* 클라이언트 응용 프로그램을 서버에서 바로 호출 가능 -> 분산 MSA 쉽게 구현 가능

* Best example: 대표적으로 많이 쓰이는 IoT 기기들

  1. voice controller, 2. smart light switch, 3. smoke alarms lock, 4. camera 

* RPC 특성상, 아키텍처가 아니라 server와 client의 관계에서 교류가 이루어짐

* fresh take on old method aka RPC

* LB를 지원받을 수도 있음



### 직렬화

* PB compiler가 `.proto` 파일을 컴파일하면 메시지는 직렬화되어 output stream 으로 나가고, parsing되어 input stream으로 들어옴



## 왜 gRPC를 사용하는가?

* Protocol buffers를 이용해 직렬화 된 바이트 스트림으로 통신해 JSON 기반의 통신보다 가벼움
* internal 통신이 빈번한 MSA에서 gRPC를 적용 시 latency 감소, 더 많은 트래픽 처리
* 많은 서비스간 API 호출로 인한 성능 저하를 개선
* MSA 기반, 분산처리를 위해 필요한 보안, API gateway, 로그 추적, 모니터링, 상태체크 등의 기능 추가개발 용이



#### Reference ) 

https://blog.banksalad.com/tech/production-ready-grpc-in-golang/?gclid=Cj0KCQjw9_mDBhCGARIsAN3PaFMi15aVSQKdivgRBWUOygiy8oVfmp5IlsU0PA0eUQAwGLCGbwjskF8aAq4HEALw_wcB9437610

https://medium.com/devops-dudes/graphql-vs-rest-vs-grpc-411a0a60d18d

https://m.blog.naver.com/PostView.nhn?blogId=neos_rtos&logNo=30185472655&proxyReferer=https:%2F%2Fwww.google.com%2F

https://velog.io/@kyusung/grpc-web-example

https://chacha95.github.io/2020-06-15-gRPC1/

https://chacha95.github.io/2020-06-16-gRPC2/

https://brownbears.tistory.com/512