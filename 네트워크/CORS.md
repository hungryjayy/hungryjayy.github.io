# CORS: Cross-Origin Resource Sharing

<br>

## CORS란?

: **HTTP 헤더**를 사용해 애플리케이션이 **다른 origin 리소스에 접근할 수 있게**, **다른 origin이 나의 resource에 함부로 접근하지 못하게** 하기 위해 사용하는 메커니즘

* 정확하게는 CORS란, SOP(Same origin policy, 동일 출처 정책)에 의해 원래는 허용이 안되는 Cross origin 접근을 허용해주는 정책
* javascript로 만든 크로스 오리진 요청에는 기본적으로 쿠키나 HTTP 인증같은 자격 증명(Credential)이 함께 전송되지 않는다.
  * credential과 함께 전송되는 요청은 영향력이 강하기 때문에, javascript로 민감한 정보에 접근할 수 있기 때문
* 처음 전송되는(받는) Origin에 대한 요청은 **cross-origin HTTP**에 의해 처리됨.
* **필요 이유: 보안상의 이유**
  * 서비스하고 있지 않은 브라우저에서 세션을 요청해 획득한다면 악의적인 행동을 할 수 있음
* **Preflight ?**
  * 사전 전달 메시지, 이걸 통해 "허가"를 받게 되면 그때부터 실제 정보 요청
  * `OPTIONS` 메서드를 통해
* **브라우저**에서는 Request가 허용된 도메인으로만 보내게 할 의무가 있다.
* server to server에선 cors가 필요 없다. 서버에서 요청을 보낼 때 http 헤더에 origin을 명시할 의무도 없다.

<br>

### SOP(Same Origin Policy): 동일 출처 원칙

: 모르는 출처의 스크립트에서 사용자가 서버로 요청할 경우, 어떤 스크립트가 실행될지 모른다(XSS). 따라서, 동일한 출처의 브라우저에 대해서만 요청을 허가한다.

* 따라서, 브라우저에서는 Request가 허용된 도메인으로만 요청을 보내도록 할 의무가 있다.

<br>

#### Origin과 domain의 차이

* domain: 도메인(naver.com)

* origin:**스킴, 도메인, 포트** 

  * e.g) https://naver.com:8080

* 정확한 문법

  ```http
  Origin: <scheme> "://" <hostname> [ ":" <port> ]
  ```

<br>

## 동작 방식

* 브라우저(크롬이)가 리소스 요청 시 헤더에 추가 정보를 담아 보냄
  * 내 origin은 무엇이고, 어떤 메소드를 사용해서 요청을 할 것이고, 어떤 헤더들을 포함할 것인지
* 서버는 서버가 응답할 수 있는 origin들을 헤더에 담아서 브라우저에게 보냄
  * 브라우저가 헤더를 보고 해당 origin에서 요청할 수 있다면 리소스 전송을 허용하고 만약 불가능하다면 에러를 발생

<img src="https://mdn.mozillademos.org/files/17214/simple-req-updated.png" alt="img" style="zoom: 67%;" />

<br>

## Simple request

* 일부 요청은 Preflight를 트리거하지 않는다. 다음의 조건들을 **모두** 만족하는 **Simple Request** 여야 한다.
  1. GET, HEAD, POST (데이터 변화시킬 위험 없는 **안전한 메서드**)
     * POST의 경우 Origin 헤더를 포함해야 한다. GET, HEAD는 포함시킬 필요 없다.
  2. User Agent가 자동으로 설정한 헤더 + CORS-safelisted request 헤더
  3. Content-type 헤더는 아래의 세개만 가능
     * `application/x-www-form-urlencoded`, `multipart/form-data` , `text/plain` 


e.g) preflight 트리거한 예제

<img src="https://mdn.mozillademos.org/files/16753/preflight_correct.png" alt="img"  /> 

* main 요청은 Cross-Origin 요청이기 때문에 `Origin:` 이 붙는다.

1. `Post` 메서드 요청
2. `Content-type` 이 `application/xml`
3. 그런데, **사용자 정의 헤더**이기 때문에 **preflighted** 처리.

<br>

## 헤더 목록

*위의 예시 or MDN 자세한 예시 참고*

<br>

### Request(서버에게)

* **Origin** : foo.com (나의 origin)
* Access-Control-Request-Method(`preflight` 과정에서) : 실제 요청에서 **어떤 메서드**를 사용할 것인지
* Access-Control-Request-Headers(`preflight` 과정에서) : 실제 요청에서 **어떤 header**를 사용할 것인지

<br>

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

<br>

## COR의 역사

* **한 사이트의 스크립트**에서 **다른 사이트에 있는 콘텐츠에 접근할 수 없다**는 제약이 있었다.
  * 따라서 `hacker.com`에서 `gmail.com`에 접근할 수 없었다. - 해커의 접근 방지
* 제약을 피하는 **트릭**: 제약 때문에 웹 페이지는 자유롭지 못했고, **웹 개발자들이 강력한 기능을 원하면서** 트릭을 만들어 냄.

<br>

### 트릭

1. Form 사용 : `<form>` 태그에 `<iframe>` 를 넣어 전송해 네트워크(GET, POST) 요청

   * 그러나, 다른 사이트에서  `<iframe>` 의 콘텐츠를 읽는 것이 금지되었어서, 응답을 읽는 것 불가능했음

2. Script 사용 : `<script>` 태그에 `src` 속성값을 이용해 보내는 것

   ```javascript
   // 1. 날씨 데이터를 처리하는데 사용되는 함수를 선언
   function gotWeather({ temperature, humidity }) {
     alert(`temperature: ${temperature}, humidity: ${humidity}`);
   }
   ```

   ```javascript
   // 2. 다음과 같은 script 태그를 만듦. 스크립트는 동적으로 생성
   let script = document.createElement('script');
   script.src = `http://another.com/weather.json?callback=gotWeather`;
   document.body.append(script);
   ```

   ```javascript
   // 실행 결과
   gotWeather({ temperature: 25, humidity: 78 });
   ```

   * 리모트 서버에서 받아온 스크립트가 실행되면 `gotWeather` 함수가 호출됨. 이 함수는 현재 페이지에서 만들었기도 하고, 리모트 서버(`weather.com`)에서 받은 데이터도 있기 때문에 원하는 결과를 얻음.

3. 위의 과정들을 거쳐, 명시적으로 cross origin 요청 허가를 알려주는 cors가 생겨나게 됨

<br><br>

<br><br>

#### Reference)

#### 모던 Javascript 튜토리얼 - https://ko.javascript.info/fetch-crossorigin

#### https://developer.mozilla.org/ko/docs/Web/HTTP/CORS

#### https://hannut91.github.io/blogs/infra/cors

#### https://velog.io/@josworks27/CORS-%EA%B8%B0%EC%B4%88-%EA%B0%9C%EB%85%90

#### https://zzossig.io/posts/web/what_is_cors/

#### https://medium.com/@woody_dev/cors-cross-origin-resource-sharing-cea401fb79b6