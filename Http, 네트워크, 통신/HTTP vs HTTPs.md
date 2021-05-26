# HTTP vs HTTPS



## HTTP

* Port 80에서 서비스 대기중, 연결하면 요청에 대한 처리를 하고 응답을 하는 프로토콜



## 문제점

* 암호화 하지 않음 - 단순 텍스트를 주고받음(Http 메시지)

  ```http
  GET / HTTP/1.1
  Host: developer.mozilla.org
  Accept-Language: fr
  ```

* 통신 상대 확인하지 않음 - Client, server 확신 불가. 위장할 수 있어서.

* 누군가 중간에서 변조 가능



## HTTPS - HTTP의 보안 취약점을 해결하기 위한 프로토콜

* 기존 HTTP 레이어에서 SSL(과거)이나 TLS 프로토콜을 통해 평문 데이터를 암호화



#### TLS 프로토콜

* 이메일, 웹 브라우저 등 다른 프로토콜들의 감청을 통한 정보의 변형을 방지.
* cryptographic 프로토콜을 사용(SSL 때도 마찬가지)



#### Reference)

#### https://developer.mozilla.org/ko/docs/Glossary/https

#### https://velog.io/@okstring/http-vs-https