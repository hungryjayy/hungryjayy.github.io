# 모던 JavaScript 튜토리얼 - 'use strict'

: 엄격한 모드로, 기존에 무시해왔던 것들을 엄격하게 잡아낸다고 이해.

<br>

## About

* 2009년 ES5에서 새로운 기능이 추가되면서 **기존 기능 일부가 변경**되어 호환성 이슈가 있었다. JS에서는 그 **변경 사항**을 항상 활성화시키지 않고, `'use strict'`라는 지시자를 사용하는 경우에만 활성화되도록 했다.
  
  * 따라서, 해당 지시자를 사용하면서 좀더 자바스크립트를 **모던하게(현대적으로)**(모던 자바스크립트) 사용할 수 있게 함.
  
* 기본 모드는 sloppy mode(느슨한 모드)라고 부른다.

  * **전통방식**??

  ```javascript
  if (false) {
      var x = "hello";
  }
  console.log(x);
  ```

  * 이와같은 코드에서 block scope 내의 변수에 접근 시 error가 아닌 undefined가 띄워졌었다.

<br>

## 특징

* **항상 스크립트 최상단**이 아니라, **함수 본문 맨 앞**에 올 수도 있고, 이 경우 해당 함수만 엄격 모드.

  * 그렇지 않을땐 항상 스크립트 최상단.

    ```javascript
    (function() {
      'use strict';
      // 이 함수는 엄격 모드
    })()
    ```

* use strict를 선언하면, 취소할 방법은 없음.

* **Class**와 **Module**을 사용한다면 `'use strict'` 사용하지 않아도 이미 자동 적용됨.

<br>

#### 느슨한 모드와의 연결은 최대한 피해야한다.

* #### 관련 이슈 : https://bugzilla.mozilla.org/show_bug.cgi?id=579119

<br>

## use strict 사용시 어떻게 변화?

1. 기존 조용히 무시되던 에러들을 throw한다.
2. JS 엔진의 최적화를 어렵게 만드는 **실수들을 엄격히** 잡아냄 - 따라서 성능상 더 빨라질 수 있음 
3. 차기 ECMA Script 버전들에서 정의될 문법 금지

<br>

## 실제 변경사항

* 선언하지 않은 변수(전역변수) 불가능

* 사용불가능한 변수에 할당 불가능

  ```javascript
  "use strict";
  
  // 쓸 수 없는 프로퍼티에 할당
  var undefined = 5; // TypeError 발생
  var Infinity = 5; // TypeError 발생
  
  // 쓸 수 없는 프로퍼티에 할당
  var obj1 = {};
  Object.defineProperty(obj1, "x", { value: 42, writable: false });
  obj1.x = 9; // TypeError 발생
  
  // getter-only 프로퍼티에 할당
  var obj2 = { get x() { return 17; } };
  obj2.x = 5; // TypeError 발생
  
  // 확장 불가 객체에 새 프로퍼티 할당
  var fixed = {};
  Object.preventExtensions(fixed);
  fixed.newProp = "ohai"; // TypeError 발생
  ```

* **모든 프로퍼티, 파라미터 네이밍이 unique**해야함.

* delete 호출 막음

* primitive values의 프로퍼티 설정 불가능

* with 사용 불가

* 예약어, eval, artuments로 네이밍 불가

#### 이외의 것들은 더 MDN 문서 찾아볼것

<br><br>

#### Reference)

#### 모던 JavaScript 튜토리얼 https://ko.javascript.info

#### Node.js 디자인패턴

#### https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Strict_mode
