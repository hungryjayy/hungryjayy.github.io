# Http 통신과 Socket 통신

: Client, server간 데이터를 주고받기 위한 통신



## Socket

* OSI에서의 4계층(TCP 또는 UDP를 이용하기 위한 수단. 일종의 창구 역할)
* 전통적인 통신 방식. 통신의 end point
* 바이트 스트림으로 통신
  * client-server 통신 과정을 직접 구현해야함.
  * 주거나 받은 데이터를 개발자들이 알아서 formatting(parsing)해야함.
* **연결이 되어있는 상태에서 통신을 주고 받음**.
  * 따라서 **양방향 통신**이라고 함
  * **실시간** 통신에 적합
    * e.g) 채팅서버, 게임
    * 실시간 streaming 서비스를 Http로 구현한다면? 계속해서 연결을 요청하기 때문에 오버헤드 
  * <-> Http 통신은 Client가 필요한 경우에 요청을 보내는 점에서 다름



## Http

* Rest, RPC, gRPC, ...

* TCP에서 Application 계층

* 어플리케이션 개발에 주로 사용

* Client의 Request가 있을 때 Server에서 Response 전달

  * Server가 Client에게 요청을 보낸수는 없음

  * 응답 받고서는 바로 **연결** **종료**, 이러한 점에서 **단방향 통신**

    * e.g. ) F5 누르면 해당 페이지 다시 요청하는 것

      *c.f) DDOS 공격: F5를 계속 누르듯, 대량의 요청을 계속 보내 서버를 다운*

  * 실시간이 아니고 필요한 경우에만(**부하를 줄이기 위해**)

  * Http통신에 적합한 곳을 Socket통신을 통해 구현하게 되면 연결을 게속 유지해 오버헤드



#### Reference)

#### https://velog.io/@bmh8993/REST%EC%99%80-RPCgRPC%EA%B0%80-%EB%93%B1%EC%9E%A5%ED%95%98%EA%B8%B0%EA%B9%8C%EC%A7%80

#### https://m.blog.naver.com/jevida/140189691025

#### https://mangkyu.tistory.com/48

#### https://hwanine.github.io/network/Socket-Http/

#### https://juyoung-1008.tistory.com/13