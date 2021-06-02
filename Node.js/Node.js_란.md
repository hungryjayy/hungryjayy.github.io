# Node.js란?

* Javascript가 서버에서 동작될 수 있도록 하는 그러한 환경(플랫폼)
* 크롬 V8 JavaScript 엔진으로 빌드된 JS 런타임기



#### 특징

* Nods.js 라이브러리 내의 api는 모두 비동기
* 단일 스레드.
* 이벤트 메커니즘을 통해 서버가 멈추지 않으며 실시간 데이터 app, SPA, IO 잦은 앱개발에 효율적
  * 여기서 I/O란? 파일 시스템 접근, 네트워크 요청과 같은 작업.
* 하나의 작업 자체가 오래걸리면 전체 성능 저하. (싱글스레드 이기 때문)



## Node의 도구(모듈)

#### Http 모듈

* 인터넷에서 데이터를 주고 받을 수 있는 프로토콜

#### Express

* package.json : 모듈 버전, 의존 패키지 관리(npm 이용)
* Routing: app.js로 routing 역할 수행
  * URI 및 특정 HTTP 요청메소드인 특정 엔드포인트에 대한 요청에 application이 응답하는 방법을 결정
* 앱 구동

#### Sequelize

* DB와 연동할 때 객체와 테이블을 매핑해주는 ORM
* 이걸 이용하면 객체의 메서드를 통해 쿼리를 조작함. sql 문법을 모르더라도.
  * create(), findOne(), findAll(), update(), destroy() 등



#### Reference)

#### https://velog.io/@new_wisdom/Node.js-SOPT-%EB%A9%B4%EC%A0%91-%EC%A4%80%EB%B9%84