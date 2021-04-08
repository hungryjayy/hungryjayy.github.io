### Javascript vs TypeScript

* JS
  * 타입 시스템이 없는(정적) 동적 프로그래밍 언어. 따라서 런타임 에러가 많이 발생할 수 있다.
    * RTE: 프로그램에서 수행할 수 없는 동작을 시도할 때 발생
    * 컴파일 error: 컴파일 타임에 발생. 주로 문법적 오류
  * 비교적 유연하게 개발할 수 있는 환경 제공



* TS
  * TS는 JS 기반으로 만들어졌다.
  * 정적 타입을 지원해서 컴파일 단계에서 오류를 포착 가능.
    * 타입을 명시함으로써 가독성 또한 높다.
  * 객체지향 언어로, 데이터 추상화에 중심을 두는 언어.



### Hoisting

- 사전적 의미: 끌어올리기. 선언되는 모든 변수는 호이스트된다.(끌어올려진다)
- 따라서 변수의 정의가 그 범위에 따라 선언과 할당으로 분리 되는것.
- 함수 선언이 함수를 실행하는 부분보다 뒤에 있어도 JS엔진은 함수 선언을 끌어올리기 때문에 가능하다.
- 다만 변수의 값은 끌어올리지 않으므로 선언보다 앞에서 출력하면 `undefined` 된다.



### Closure

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

  #### reference) https://developer.mozilla.org/ko/docs/Web/JavaScript/Closures

  

* 이러한 맥락에서 객체지향과 비슷하다.(어떠한 데이터 자체는 숨기고, 그 데이터를 조작하는 메서드는 노출한다)

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





### this

* JS의 모든 함수는 실행될 때마다 함수 내부에 this 객체가 추가됨.

1. 객체의 메서드를 호출할 때 `A.B` 에서 this는 A 객체를 가리킨다.
2. 함수를 호출할 때 this는 전역 객체에 바인딩 된다.
3. 생성자 함수를 통해 객체를 생성할 때 -> 일반적인 생성자와 같게 생각하면 됨
   1. 내부에서 객체 자체를 this
4. apply, bind, call
   1. apply: func.apply(this, [param1, param2])
   2. bind: func {}.bind(this, param1, param2)
   3. call: func.call(this, param1, param2)



### arrow function

* 간결하다

  ``` javascript
  materials.map(function(material) { 
    return material.length; 
  }); // [8, 6, 7, 9]
  
  materials.map((material) => {
    return material.length;
  }); // [8, 6, 7, 9]
  
  materials.map(({length}) => length); // [8, 6, 7, 9]
  ```

* 상위 스코프

  ```javascript
  function Person(){
    this.age = 0;
  
    setInterval(() => {
      this.age++; // |this|는 person 객체를 참조
    }, 1000);
  }
  
  var p = new Person();
  ```



### var, let, const

* var: function scope

* let: block scope,

* const: block scope, 상수 (재할당 불가)

* var는 함수 내부라면 블록 밖이라도 참조 가능 그러나,

  

#### 예-1) var hoisting

var는 함수 스코프

```javascript
function counter () {
  for(var i=0; i<10; i++) {
    console.log('i', i)
  }
}
counter()
console.log('after loop i is', i) 
// ReferenceError: i is not defined
```

#### 예-2) IIFE (immediately-invoked function expression)

​		*IIFE: 정의하자마자 즉시 실행함수, `()`로 씌워 만들수 있다*

```js
// IIFE를 사용하면
// i is not defined가 뜬다.
(function() {
  // var 변수는 여기까지 hoisting이 된다.
  for(var i=0; i<10; i++) {
    console.log('i', i)
  }
})()
console.log('after loop i is', i) 
// ReferenceError: i is not defined

//////////
// var i 전역변수화 됨 IIFE
(function() {
  for(i=0; i<10; i++) {
    console.log('i', i)
  }
})()
console.log('after loop i is', i) // after loop i is 10
```



### foreach() vs map()

* foreach
  * 값 반환 x
  * 각 요소에 대한 콜백을 수행. (현재 배열을 변경해서 반환). map보다 빠르다.
* map
  * 값 반환
  * 각 요소에서 함수를 호출하고, 결과로 새로운 배열을 만들어냄.



### Type

* 대문자는 Wrapper object이고, 소문자는 primitive type이다.
* 따라서 소문자 권장



