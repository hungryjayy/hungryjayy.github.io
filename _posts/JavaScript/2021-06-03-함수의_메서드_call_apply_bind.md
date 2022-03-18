---
layout: post

title: 함수의 메서드 (call, apply, bind)

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js]

featuredImage: 

img: 

categories: [Study, JavaScript]

date: '2021-06-03'

extensions:

  preset: gfm
---

: 함수에 존재하는 기본 **메소드**. 

* 첫번째 인자로 this를 대체할 수 있어, 실행 컨텍스트(window)를 다른 것으로 바꿀 때 사용가능.
* 이미 할당되어있는 "다른 객체의 함수나 메소드"를 "호출하는 해당 객체에 재할당"
  * 따라서 window를 현재의 객체를 참조하도록.
* **MDN예시 반복**해서 보기

<br>

## call

* 보통 함수와같이 **인자 목록**을 넣어서 전달

  * e.g) `func.call(this, a, b)`

<br>

### 쓰이는 예시

* 객체의 생성자 연결에 사용(생성자 체이닝)

  ```javascript
  function Product(name, price) {
    this.name = name;
    this.price = price;
  
    if (price < 10) {
      throw RangeError('Some range error');
    }
  }
  
  function Food(name, price) {
    Product.call(this, name, price);
    this.category = 'food';
  }
  
  function Toy(name, price) {
    Product.call(this, name, price);
    this.category = 'toy';
  }
  
  var cheese = new Food('feta', 5); // Error
  var fun = new Toy('robot', 20);

<br>

* 익명함수 호출에 사용

  ``` javascript
  let people = {
    name: 'AAA',
  }
  ...
  (function(i) {
      this.print = function() {
        console.log('#' + i + ': ' + this.name);
      }
      this.print();
  }).call(people, i);
  ...
  ```

  <br>

* this 원하는 컨텍스트 지정

  ```javascript
  function greet() {
    var reply = [this.animal, 'typically sleep between', this.sleepDuration].join(' ');
    console.log(reply);
  }
  
  var obj = {
    animal: 'cats', sleepDuration: '12 and 16 hours'
  };
  
  greet.call(obj);  // cats typically sleep between 12 and 16 hours

<br>

* 인수 지정 없이(window 전달)

  ```javascript
  // 'use strict'; strict 모드 사용 시 this는 undefined 값을 가지게 됨
  
  var sData = 'Wisen';
  function display(){
    console.log('sData value is %s ', this.sData);
  }
  
  display.call();  // sData value is Wisen

<br>

## apply

* `call`과 비슷하지만, `call` 은 **인수 목록**을, `apply()` 는 **배열 하나**를 전달한다는 점이 다르다.
  * e.g) `func.apply(this, [1, 2, 3])`

<br>

### 쓰이는 예시

* 배열관련 된 것들 처리할 때 좋음

  * JS엔진의 인수 길이 제한 오버플로우(65536)에 주의

  ```javascript
  1.
  var array = ['a', 'b'];
  var elements = [0, 1, 2];
  array.push.apply(array, elements); // ['a', 'b', 0, 1, 2]
  
  2.
  var numbers = [5, 6, 2, 3, 7];
  
  var max = Math.max.apply(null, numbers);
  
  var min = Math.min.apply(null, numbers);
  ```

  * +) 생성자 체이닝에도 마찬가지로 쓰임

<br>

## bind

* this만 바꿔 반환하고, 호출하지는 않음.
* `call(this, 1, 2, 3)`은 `bind(this)(1, 2, 3)` 와 같음.

<br>

### 쓰이는 예시

* 객체로부터 메소드 추출 뒤 함수 호출하면, 원본 객체는 그 함수의 `this`로 사용되는 것이 아님.

  ```javascript
  this.x = 9;
  var module = {
    x: 81,
    getX: function() { return this.x; }
  };
  
  module.getX(); // 81이 맞다.
  
  var retrieveX = module.getX;
  retrieveX();
  // 9 반환 - 함수가 전역 스코프에서 호출됐음
  
  // module과 바인딩된 'this'가 있는 새로운 함수 생성
  // 신입 프로그래머는 전역 변수 x와 module의 속성 x를 혼동할 수 있음
  var boundGetX = retrieveX.bind(module);
  boundGetX(); // 81
  ```

<br>

* setTimeout 과 사용 - `this` 는 window 객체로 설정됨

  ```javascript
  function LateBloomer() {
    this.petalCount = Math.ceil(Math.random() * 12) + 1;
  }
  
  // 1초 지체 후 bloom 선언
  LateBloomer.prototype.bloom = function() {
    window.setTimeout(this.declare.bind(this), 1000);
  };
  
  LateBloomer.prototype.declare = function() {
    console.log('I am a beautiful flower with ' +
      this.petalCount + ' petals!');
  };
  
  var flower = new LateBloomer();
  flower.bloom();
  // 1초 뒤, 'declare' 메소드 유발
  ```

<br>

<br>

#### Reference)

https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Function/call

https://www.zerocho.com/category/JavaScript/post/57433645a48729787807c3fd