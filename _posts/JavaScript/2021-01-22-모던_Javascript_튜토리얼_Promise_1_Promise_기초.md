---
layout: post

title: 모던 JavaScript 튜토리얼 - Promise 1 - Promise 기초

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js, promise]

featuredImage: 

img: 

categories: [JavaScript]

date: '2021-01-22'

extensions:

  preset: gfm

---

: 처리 성공 여부에 따라 resolve or reject 호출

<br>

## Basic
* Promise는 **성공 또는 실패**만 한다. 이 때 변경된 상태는 더이상 변하지 않는다.
* resolve(value): 일이 성공적으로 끝난 경우 `value`(하나 혹은 없음)와 함께 호출
* reject(error): 에러 시 `error`(`Error` 객체 혹은 `Error` 상속받은 객체)와 함께 호출
* 내부 프로퍼티(개발자가 접근 불가)
	* state: 초기 `pending`/ `resolve()` 시 `fulfilled`, `reject` 시 `rejected`
	* result: 초기 `undefined` / `resolve()` 시 `value`, `reject` 시 `error`
	<img src = "https://hungryjayy.github.io/assets/img/JavaScript/promise.png">

<br>

## then, catch, finally

: 각각의 Handler들.(항상 비동기적 실행)

<br>

### then
: `.then(result, error)` 에서 Promise 이행 시 `result`, 거부 시 `error`

e.g)
```typescript
let promise = new Promise(function(resolve, reject) {
 setTimeout(() => resolve("done!"), 1000); 
}); // resolve 함수는 .then의 첫 번째 함수(인수)를 실행합니다.

promise.then(
result => alert(result), // 1초 후 "done!"을 출력
error => alert(error) // 실행되지 않음 
);
```
- 성공적으로 처리된 경우만 다룬다면 인자 하나

<br>

### catch
에러가 발생한 경우 `then(null, errorHandlingFunction)`, `.catch(errorHandlingFunction)` 이와같이 사용

<br>

### finally
* `.finally(f)`는 `.then(f, f)`와 유사. Promise가 처리(**이행되건 거부되건**)된다면 `f`가 항상 실행됨.
* `finally` 핸들러에는 인수가 없음. Promise의 이행 여부도 알 수 없음(보편적인 동작 만을 수행)
* `finally`는 자동으로 다음 handler에게 Promise의 결과, 에러 전달

<br>

## Chaining
* 순차적으로 처리해야하는 비동기 작업이 여러개 있을 때 사용(중첩 Callback을 대체 가능)

* Handler가 Promise를 반환하면, 나머지 체인은 Promise가 처리될 때까지 대기하다가 처리가 완료되면 `result`(값 또는 에러)가 다음 체인으로 전달됨

* `.then`은 Promise를 반환

* 하나의 Promise 이후 여러개의 handler(`.then()`)에 전달 가능. 이 때 각각은 독립적으로 수행

* 추후 손쉬운 확장을 위해 항상 Promise를 반환하도록 개발하는 것이 좋음

<br><br>

#### Reference)

모던 JavaScript 튜토리얼 https://ko.javascript.info