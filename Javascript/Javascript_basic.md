# Javascript 기초, 문법

<br>

## JavaScript

* 타입 시스템이 없는 동적 프로그래밍 언어. 따라서 런타임 에러가 많이 발생할 수 있다.
  * RTE: 프로그램에서 수행할 수 없는 동작을 시도할 때 발생
  * 컴파일 error: 컴파일 타임에 발생. 주로 문법적 오류
  
* 비교적 유연하게 개발할 수 있는 환경 제공

* 동적 타입 언어

  ```javascript
  // javascript는 동적 타입 언어이기 때문에 이러한 경우에도 에러 발생 x
  let message = "hello";
  console.log(message); // hello
  message = 123456;
  console.log(message); // 123456
  ```

<br>

## TypeScript

* TS는 JS 기반으로 만들어졌다.(컴파일 시 javascript 파일로 컴파일됨.)
* 정적 타입을 지원해서 **컴파일 단계에서 오류를 포착** 가능.
  * 타입을 명시함으로써 **가독성** 또한 높다.
* **객체지향** 언어로, 데이터 추상화에 중심을 두는 언어.

<br>

## Closure

* 반환된 내부함수(생명주기가 끝난)가 자신이 선언되었을 때의 환경인 스코프를 기억해 선언되었을 때의 환경 밖에서 접근할 수 있다.

  ```javascript
  var name = `Warning`;
  function outer() {
    var name = `closure`;
    return function inner() {
      console.log(name);
    };
  }
  
  var callFunc = outer();
  callFunc();
  // console> closure
  ```

  * 여기서 outer 내부의 name 변수를 자유변수라고 한다.
  
* 어떤 데이터(환경)과 그 데이터를 조작하는 함수를 연관시킨다.

  ``` javascript
  function makeAdder(x) {
    var y = 1;
    return function(z) {
      y = 100;
      return x + y + z;
    };
  }
  
  var add5 = makeAdder(5);
  var add10 = makeAdder(10);
  //클로저에 x와 y의 환경이 저장됨
  
  console.log(add5(2));  // 107 (x:5 + y:100 + z:2)
  console.log(add10(2)); // 112 (x:10 + y:100 + z:2)
  ```



* 이러한 맥락에서 객체지향과 비슷하다고도 말한다.

  
  * **캡슐화**: 어떠한 데이터 자체는 숨기고, 그 데이터를 조작하는 메서드는 노출
  
  ``` javascript
  function redTeam() {
  	score = 0;
      return function() {
      	return score++;
  	}
  }
  
  var goal = redTeam();
  console.log(goal());
  console.log(goal());
  console.log(goal());
  -------------------------
  결과:
  0
  1
  2
  ```

<br>

## forEach() vs map()

* forEach
  * **값 반환 X**
  * 각 요소에 대한 콜백을 수행. (현재 배열을 변경해서 반환). map보다 빠르다.
  * JS에서는 for-loop가 더 빠르고, kotlin에서는 forEach(Collection의 경우)가 더 빠르다.
  * **원래의 배열을 바꿀 염려** 있음. 주로 DB 저장과 같은 일에 사용됨
* map
  * **값 반환 O**
  * 각 요소에서 함수를 호출하고, 결과로 새로운 배열을 만들어냄.
  * 원래의 배열에 영향을 주지 않기 때문에 **함수형 프로그래밍**에 더 적합.

#### forEach와 map 각각은 서로가 할 일을 대체할 수 있음

<br>

## Type(대, 소문자 차이점)

* 대문자는 Wrapper object이고, 소문자는 primitive type이다.
* 따라서 소문자 권장

<br>

## !! (Double Exclamation)

* Not 연산자인 `!`는 입력값을 boolean으로 변경해, true -> false, false -> true로 변환해주는 논리연산자
* `!!`는 다른 타입의 데이터를 boolean 타입으로 명시적 형변환.

```javascript
var a;
console.log(a); // undefined
console.log(!a); // true;
console.log(!!a); // false;

var a = "test"; //a: "test" (조건문 적용시 true) 
var b = !"test"; //b: false 
var c = !!"test"; //c: true

출처:
https://ifuwanna.tistory.com/278 [IfUwanna IT]
https://hermeslog.tistory.com/279
```

* boolean 타입이 아니면서 false가 되는 경우(이외에는 모두 기본적으로 true)
  * "" : 빈 문자열
  * NaN : Not a Number
  * undefined
  * null
  * 0

<br><br>

#### Reference)

#### 모던 JavaScript 튜토리얼 https://ko.javascript.info/intro

#### Node.js 디자인패턴

#### https://moollang.tistory.com/10

#### https://ifuwanna.tistory.com/278

#### https://hermeslog.tistory.com/279