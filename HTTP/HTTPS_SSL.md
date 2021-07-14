# HTTPS와 SSL



## HTTP의 문제점

* 암호화 하지 않음 - 단순 텍스트를 주고받음(Http 메시지)

  ```http
  GET / HTTP/1.1
  Host: developer.mozilla.org
  Accept-Language: fr
  ```

* 1. 통신 상대 확인하지 않음 - Client, server 확신 불가(위장 가능성), 2. 데이터 변조 가능성

  * 따라서, CA를 통해 받은 인증서 등으로 검증을 받고 신뢰성 있는 통신



## HTTPS

: HTTP의 보안 취약점을 해결하기 위한 프로토콜.

* HTTPS는 기존 HTTP 통신을 SSL 프로토콜(**443 Port**)을 통해 평문을 **암호화**하고, 통신하는 것이라고 보면 된다.



## SSL

: 응용, 전송계층 사이에 **보안**계층이라는 독립적인 계층을 만들어, `HTTP + SSL (or TLS)`로 보내는, (보안)통신 프로토콜



### CA(Certificate Autority) 로부터 인증서 받아오는 과정

: 통신을 어떻게 할건지, CA에게 의뢰(?)를 통해 **인증서** 발급, **대칭키** 발급, **암호화 알고리즘** 결정, SSL / TLS **프로토콜** 결정 과 같은 일을 하는 과정

* **SSL 인증서** : client, server 통신을 제3자가 보증해주는 것. 이 서버가 진짜임을 나타냄

1. Server -> CA: 서버가 **공개 키**와 **서버 정보**를 CA에게 보냄
2. CA -> Server: CA는 서버 정보, 서버 공개 키, 공개 키 암호화 방법을 담은 **SSL 인증서**를 만들고 **개인 키**를 통해 암호화해 서버에 전달



### SSL 핸드쉐이크

* SSL은 443 port를 사용하는 TCP 프로토콜이기 때문에, 당연히 이 핸드셰이크 이전에 TCP 3-way 핸드셰이크 선행



1. Client -> Server: 브라우저 지원 가능한 암호화 방식 제안, Random data 전달
2. Server -> Client: 암호화 방식 중 하나 선택하고, **인증서** 전달, Random data 전달
   * 이 인증서에 서버의 **공개 키** 포함되어 있음
3. Client는 CA의 **공개 키**를 통해 인증서를 복호화해 서버의 **공개 키** 얻고, 진짜 서버인 것을 확인.
   * CA는 세계적으로 신뢰성있는 기업이라 이미 **CA의 공개 키**가 브라우저에 저장 되어있음.
4. Client -> Server(키교환): 미리 주고받은 random data를 참고해 암호화 시 사용할 **대칭 키(비밀 키)** 생성 후, 서버의 **공개 키**로 암호화 해, 서버에게 전달.
5. Finished: 서로 finished 메시지 보내고, 이 후 클라이언트한테 받은 이 클라이언트의 **대칭(비밀) 키**를 통해 암호화해 통신



* HTTPS라도, `안전하지 않은 사이트`와 같은 경우 자체 인증 발급인 경우일 수 있고, 무조건 안전한 것은 아님



### 키 종류

#### 공개 키

* 모두에게 공개하는 키(해커가 알아도 되는 키)
* 공개키 암호화: 공개키로 암호화하고, 개인키로 복호화



#### 개인 키

* 나만 가지고 있는 키
* 공인인증서의 문제: 이 개인키를 컴퓨터에 그냥 일반 파일로 저장하도록 함
* 개인키 암호화: 개인키로 암호화, 공개키로 복호화



#### 대칭 키

* 암호화, 복호화에 동일한 하나의 키를 사용



#### Reference)

#### https://aws-hyoh.tistory.com/entry/HTTPS-%ED%86%B5%EC%8B%A0%EA%B3%BC%EC%A0%95-%EC%89%BD%EA%B2%8C-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-2Key%EA%B0%80-%EC%9E%88%EC%96%B4%EC%95%BC-%EB%AC%B8%EC%9D%84-%EC%97%B4-%EC%88%98-%EC%9E%88%EB%8B%A4?category=768734

#### https://opentutorials.org/course/1334/4894

#### https://blog.naver.com/skinfosec2000/222135874222

#### https://velog.io/@okstring/http-vs-https

#### https://github.com/gyoogle/tech-interview-for-developer/blob/master/Computer%20Science/Network/HTTP%20%26%20HTTPS.md