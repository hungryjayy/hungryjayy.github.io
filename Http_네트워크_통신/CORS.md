# CORS: Cross-Origin Resource Sharing



## CORS란

* HTTP 헤더를 사용해 애플리케이션이 **다른 origin 리소스에 접근할 수 있게**, **다른 origin에서 나의 resource에 함부로 접근하지 못하게** 하기 위해 사용하는 메커니즘
* 처음 전송되는(받는) 도메인에 대한 요청은 **cross-origin HTTP**에 의해 처리됨.
* **필요 이유: 보안상의 이유**
  * 서비스하고 있지 않은 브라우저에서 세션을 요청해 획득한다면 악의적인 행동을 할 수 있음
* **Preflight ?**
  * 사전 전달 메시지, 이걸 통해 "허가"를 받게 되면 그때부터 실제 정보 요청
  * OPTIONS 메서드를 통해



## 동작 방식

* 브라우저가 리소스 요청 시 헤더에 추가 정보를 담아 보냄
  * 내 origin은 무엇이고, 어떤 메소드를 사용해서 요청을 할 것이고, 어떤 헤더들을 포함할 것인지
* 서버는 서버가 응답할 수 있는 origin들을 헤더에 담아서 브라우저에게 보냄
  * 브라우저가 헤더를 보고 해당 origin에서 요청할 수 있다면 리소스 전송을 허용하고 만약 불가능하다면 에러를 발생

<img src="https://mdn.mozillademos.org/files/17214/simple-req-updated.png" alt="img" style="zoom: 67%;" />



## Simple request

* 일부 요청은 Preflight를 트리거하지 않는다. 다음의 조건들을 만족하는 Simple Request 여야 한다.
  1. GET, HEAD, POST
  2. User Agent가 자동으로 설정한 헤더 + CORS-safelisted request 헤더
  3. Content-type 헤더는 아래의 세개만 가능
     * `application/x-www-form-urlencoded`, `multipart/form-data` , `text/plain` 

* 예시

<img src="https://mdn.mozillademos.org/files/16753/preflight_correct.png" alt="img"  /> 

1. Post요청과 함께 보냄, 2. Content-type이 `application/xml`, 3. 사용자 정의 헤더이기 때문에 preflighted 처리.



## 헤더 목록

*위의 예시 or MDN 자세한 예시 참고*



### Request(서버에게)

* **Origin** : foo.com (나의 origin)
* Access-Control-Request-Method(`preflight` 과정에서) : 실제 요청에서 **어떤 메서드**를 사용할 것인지
* Access-Control-Request-Headers(`preflight` 과정에서) : 실제 요청에서 **어떤 header**를 사용할 것인지



### Response(서버에서)

- **Access-Control-Allow-Origin** : foo.com (내가 허용한 origin)
  - 브라우저가 해당 origin이 자원에 접근할 수 있도록 허용
  - 혹은 `*`은 credentials이 없는 요청에 한해서 모든 origin에서 접근이 가능하도록 허용
- Access-Control-Allow-Methods
  - `preflight`요청에 대한 대한 응답으로 허용되는 메서드들을 나타냄
- Access-Control-Allow-Headers
  - `preflight`요청에 대한 대한 응답으로 실제 요청 시 사용할 수 있는 HTTP 헤더를 나타냄
- Access-Control-Max-Age
  - 또다른 preflight request를 보내지 않고, 얼마나 오랫동안 `preflight`요청이 캐싱 될 수 있는지
- Access-Control-Expose-Headers
  - 브라우저가 액세스할 수있는 서버 화이트리스트 헤더를 허용
- Access-Control-Allow-Credentials
  - `Credentials`가 true 일 때 요청에 대한 응답이 노출될 수 있는지를 나타냄
  - `preflight`요청에 대한 응답의 일부로 사용되는 경우 실제 요청을 수행 할 수 있는지를 나타냅니다.
  - 간단한 GET 요청은 `preflight`되지 않으므로 자격 증명이 있는 리소스를 요청하면 헤더가 리소스와 함께 반환되지 않으면 브라우저에서 응답을 무시하고 웹 콘텐츠로 반환하지 않음



## 멱등(idempotent) vs 비멱등

* Get (멱등)
  * Preflight 필요 없음
  * 클라이언트, 서버간 간단한 통신.
  * CORS 헤더를 사용해 권한 처리
* 비멱등
  * 요청에 앞서 Preflight(사전전달)를 해야한다. -> `OPTIONS` 사용
    * Handshake 절차.



#### Reference)

#### https://hannut91.github.io/blogs/infra/cors

#### https://zzossig.io/posts/web/what_is_cors/

#### https://developer.mozilla.org/ko/docs/Web/HTTP/CORS

#### https://medium.com/@woody_dev/cors-cross-origin-resource-sharing-cea401fb79b6