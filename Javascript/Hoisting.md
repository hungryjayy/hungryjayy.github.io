# Hoisting

: 사전적 의미로 "끌어올리기". **선언되는 모든 변수는 호이스트된다**.(끌어올려진다)

<br>

## 특징

* ECMA2015 이후로 나온 것으로 알고 있음.
* 물리적으로 코드가 옮겨지는 것이 아니고, **컴파일 단계에서 변수 및 함수 선언이 메모리에 저장되는 것**

```javascript
foo("abc");

function foo(text) {
  console.log("print: " + text);
}
// 결과- print: abc
```

<br>

## 선언과 할당

- 변수의 정의가 그 범위에 따라 **선언**과 **할당**으로 분리된다.

  - **선언**은 호이스팅, **할당**은 호이스팅 X

- 따라서, 함수 선언이 함수를 실행하는 부분보다 뒤에 있어도 **JS엔진은 실행되기 전에 함수 선언을 끌어올리기 때문에** 가능한 것.

  ```javascript
  console.log(foo); // undefined
  
  var foo = 2;
  
  console.log(foo); // 2
  ```

- 함수의 경우도 마찬가지. 그러나, **선언문**과 **표현식**에서 차이가 있다.

  - **함수 선언문**: 자바스크립트 엔진은 스크립트 실행 전, 초기화 단계에서 전역에 선언된 함수 선언문을 찾고, 해당 함수를 생성.
  - **함수 표현식**: 실행 흐름이 `let foo = function...`의 우측 표현식에 도달했을 때 함수가 생성된다.
    - 이 방식대로 한다면, 함수가 할당된 변수가 존재하는 컨택스트라면 해당 함수를 사용 가능하다는 특징
    - 함수를 값처럼 할당, 복사
  
  ```javascript
    var foo2; // 변수값 선언
  
    foo(); // hello
    foo2(); // Error: foo2 is not a function.
  
    function foo() { // 함수 선언식
        console.log("hello");
    }
  
    foo2 = function() { // 함수 표현식 (호이스팅 x). 실제로 도달했을 때 함수 선언.
        console.log("hello2");
    }
  ```

* 함수 스코프 내 호이스팅 - 함수 내에서만 호이스팅 된다.

  ```javascript
  function foo() {
    for(var j = 0; j < 10; j++) {
      alert(j);
    }
  }
  foo();
  
  (function () { // IIFE
    for(var i = 0; i < 10; i++) {
      alert(i);
    }
  })()
  
  console.log(i); // Error: i is not defined
  console.log(j); // 실행도 안되고, 되어도 Error: j is not defined
  ```


<br><br>

#### Reference)

#### https://developer.mozilla.org/ko/docs/Glossary/Hoisting

#### https://gmlwjd9405.github.io/2019/04/22/javascript-hoisting.html