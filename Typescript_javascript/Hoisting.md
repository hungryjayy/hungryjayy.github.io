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

- 함수의 경우도 마찬가지.

  ```javascript
    var foo2; // 변수값 선언
  
    function foo() { // 함수 선언
        console.log("hello");
    }
  
    foo();
    foo2(); // Error: foo2 is not a function.
  
    foo2 = function() { // 함수 선언 (호이스팅 x)
        console.log("hello2");
    }
  ```

<br><br>

#### Reference)

#### https://developer.mozilla.org/ko/docs/Glossary/Hoisting

#### https://gmlwjd9405.github.io/2019/04/22/javascript-hoisting.html