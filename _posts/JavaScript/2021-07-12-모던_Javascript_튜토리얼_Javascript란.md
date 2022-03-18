---
layout: post

title: 모던 JavaScript 튜토리얼 - Javascript란?

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js]

featuredImage: 

img: 

categories: [Study, JavaScript]

date: '2021-07-12'

extensions:

  preset: gfm
---

: html 안에 "스크립트"로 작성하고, **브라우저**에서 실행할 수 있는 언어.

<br>

* **Javascript 엔진**: 브라우저 뿐 아니라, **자바스크립트 엔진**이 있는 서버, Chrome(V8 엔진), Firefox(스파이더몽키) 등에서 모두 동작 가능.
  * 엔진은 스크립트를 읽고(**파싱**), 기계어로 전환(**컴파일**)하고, 실행시킨다.
* 브라우저를 대상으로 하는 언어이기 때문에 메모리, CPU 등 low-level 영역 조작을 허용하지 않음
* 공식 명세: ECMA-262, 매뉴얼: MDN, 튜토리얼: 모던 자바스크립트 튜토 참고하면 좋음

<br>

## 강점

* 모든 브라우저에서 지원하고, 기본 언어로 사용되기 때문에 웬만한 브라우저(프론트) 연관 기술은 자바스크립트

* 간단한 서버를 구성하면 한가지(javascrtip)언어만으로 간단하게 구성 가능

* html, css와 통합 가능 - 아래처럼 script 태그에 자바스크립트를 삽입해 간단하게 작성도 가능

  * 혹은 `<script src="/etc/path/exam.js"></script>` 처럼 링크로하면 내부코드는 무시
  * 그런데, 별도로 만든 스크립트 파일을 브라우저가 받아서 **캐시**에 저장하기 때문에 성능상 이점이 있다. 따라서, 별개의 파일로 만들어 저장하는 것이 좋음.
  
  ```html
  <!DOCTYPE HTML>
  <html>
  <body>
  
    <script>
      alert( 'Hello, world!' );
    </script>
  
  </body>
  </html>
  ```

<br>

## Javascript로 브라우저에서 할 수 있는 일

* **Node.js 환경**에서는 네트워크 요청을 하거나, 임의의 파일을 읽거나 쓸 수 있다.
* HTML 조작 가능(추가, 수정 등)
* **AJAX**같은 기술을 통해 서버에 요청보내기, 파일 업로드, 다운로드 등 가능
* 쿠키 설정, 브라우저(로컬 스토리지 등)에 데이터 저장, CORS 제어를 통해 탭간 정보 공유 등
  * CORS같은건 보안때문에 원래는 안됨

<br><br>

#### Reference)

모던 JavaScript 튜토리얼 https://ko.javascript.info/intro