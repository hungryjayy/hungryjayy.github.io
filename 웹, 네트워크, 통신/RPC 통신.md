# RPC



## RPC (vs restapi)

* IDL(Interface Definition Language)을 활용해 통신은 신경쓰지 않고 원격의 프로그램을 로컬처럼 이용
* request parameter, reseponse parameter를 알아야한다.
  * 따라서 양쪽의 인터페이스 규약을 정의한 후, Skeleton, Stub 코드를 이용.
  * 서로 다른 주소공간을 사용하므로 stub을 통해 매개변수를 변환해주어야 함.
* client, server간 연관관계가 돈독해진다(?), 즉 의존성 증가
* 계층간 함수명 등이 그대로 노출되는 구조라서 마이크로 서비스 내부 시스템, 디자인이 그대로 노출된다.
* **아래의 Stub, Skeleton을 통한 정보 전달 과정은 네트워크를 통한 자료고환을 포함하기 때문에 Local에서의 일반적인 method call 보다는 더 많은 시간을 소모.**



### Stub

* 클라이언트 보조객체
* 클라이언트는 stub의 method를 불러내면 stub이 대신하여 원격 개체의 method 수행
  * marshalling하여 원격 서버로 전달하고 결과를 기다림
  * 원격으로부터 결과를 받으면 unmarshalling하여 클라이언트에게 되돌려줌.
* client가 스텁의 비즈니스 method를 호출하면 호출된 method명과 매개변수로 전달된 값들이 stream 형태로 skeleton에 전달



### Skeleton

* 서버 보조객체
* 마샬된 파라미터를 unmarshaling해 실제 원격 개체에 전달하고 호출 대상 메소드를 불러냄
  * 이후 메소드로부터 받은 결과를 skeleton에서 marshalling 해 전달
* 스텁으로부터 Stream을 받으면 이를 분석해 어떤 메소드가 요청되었는지 파악, 서버에 있는 객체의 비즈니스 메소드 호출





#### Reference)

#### https://velog.io/@bmh8993/REST%EC%99%80-RPCgRPC%EA%B0%80-%EB%93%B1%EC%9E%A5%ED%95%98%EA%B8%B0%EA%B9%8C%EC%A7%80

#### https://mindock.github.io/network/rest-rpc/

#### https://www.slideshare.net/WonchangSong1/rpc-restsimpleintro

#### https://vascocenter.tistory.com/entry/%EB%B6%84%EC%82%B0%EA%B0%9C%EC%B2%B4-%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98%EC%97%90%EC%84%9C%EC%9D%98-%EC%8A%A4%ED%85%81stub-%EC%8A%A4%EC%BC%88%EB%A0%88%ED%86%A4Skeleton