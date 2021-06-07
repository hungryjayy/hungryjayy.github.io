# HTTPS(vs HTTP)



## HTTP의 문제점

* 암호화 하지 않음 - 단순 텍스트를 주고받음(Http 메시지)

  ```http
  GET / HTTP/1.1
  Host: developer.mozilla.org
  Accept-Language: fr
  ```

* 통신 상대 확인하지 않음 - Client, server 확신 불가. 위장할 수 있어서.

* 누군가 중간에서 변조 가능



## HTTPS

: HTTP의 보안 취약점을 해결하기 위한 프로토콜.

* HTTPS는 SSL 프로토콜 위에서 돌아가는 프로토콜이라고 보면 된다.
* 기존 HTTP 레이어에서 SSL을 통해(TLS는 SSL의 후속버전)을 통해 평문 데이터를 **암호화**
* Http와 다르게 **443** Port를 사용



## SSL

: 응용, 전송계층 사이에 보안계층이라는 독립적인 계층을 만들어, "HTTP + SSL (or TLS)"로 보내는, (보안)통신 프로토콜



### SSL 인증서

: client, server 통신을 제3자가 보증해주는 것.



### SSL 핸드셰이크

* 통신을 위해 어떤 방법을 사용할지를 사용.
* SSL은 443 port를 사용하는 TCP 프로토콜.
  * 따라서 이 핸드셰이크 이전에 TCP 3-way 핸드셰이크 수행

1. Client -> Server : 브라우저 지원 가능한 암호화 방식 제안, Random Data 전달
2. Server -> Client: 암호화 중 하나 선택하고, 인증서 전달, Random data 전달
   * 이 인증서에 서버의 공개 키 있음
3. Client -> Server(키교환) : 미리 주고받은 random data를 참고해 암소솨 시 사용할 키 생성 후 서버에게 전달.
   * 이 때 키는 서버로부터 받은 공개키로 암호화되어 보내짐
4. Finished : 서로 finished 메시지 보내고, 이 후 클라이언트가 생성한 키를 통해 암호화해 통신



#### 공개 키

* 모두에게 공개하는 키
* 공개키 암호화: 공개키로 암호화하고, 개인키로 복호화



#### 개인 키

* 나만 가지고 있는 키
* 개인키 암호화: 개인키로 암호화, 공개키로 복호화



#### Reference)

#### https://opentutorials.org/course/1334/4894

#### https://blog.naver.com/skinfosec2000/222135874222

#### https://velog.io/@okstring/http-vs-https