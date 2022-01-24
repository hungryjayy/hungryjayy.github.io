---
layout: post

title: 모던 JavaScript 튜토리얼 - Promise 2 - Error 처리

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

<br>

## Promise error handling

: Chaining에서 여러 then 중 Promise가 하나라도 거부된다면 `.catch` 트리거

* 따라서, 체이닝 되어있는 모든 then에서의 에러를 한 catch에서 처리 할 수 있다.
  * 세부적인 에러 처리를 위해 각 task(then)마다 catch 하나씩 둘 수도 있다.

<br>

## 암시적 try...catch
* 예외가 발생하면 예외를 잡고, reject처럼 다룸(거부상태의 promise로 변경시킴)
  * executor 주위에는 **보이지 않는(암시적)** `try ... catch`가 있다. 따라서, 예외가 발생하면 `try ... catch`에서 예외를 잡고 이를 reject처럼 다룬다.

```typescript
new Promise((resolve, reject) => {
	throw new Error("에러 발생!"); 
}).catch(alert); // Error: 에러 발생!
```
`throw`를 사용해 에러를 던지거나 **모든 종류의 에러**가 발생하면 거부된 Promise를 의미하기 때문에 아래와 똑같이 동작

```typescript
new Promise((resolve, reject) => {
	reject(new Error("에러 발생!"));
}).catch(alert); // Error: 에러 발생!
```
<br>

### 동기적 에러, 비동기적 에러

```javascript
new Promise(function(resolve, reject) {
  setTimeout(() => {
    throw new Error("에러 발생!");
  }, 1000);
}).catch(alert);
```

* 위의 예제에서 catch는 트리거되지 않는다.
  * **동기적 에러**: 윗 윗 예시처럼, 암시적 `try ... catch`가 함수 코드를 감싸고 있으므로 처리가 잘 된다. 
  * **비동기적 에러**: **executor(실행자, 실행 함수)**가 **실행되는 동안**이 아니라 **나중에 에러가 발생**한다. (executor의 실행동안 `try ... catch`가 잡아주는데, 그 이후에 발생한다.)
    * 이는 Node.js의 콜백 규칙에서 보았듯 **실행 스택**과 **에러가 발생한 스택**이 다르기 때문.
    * 에러는 이벤트 루프로 그대로 이동하고 `uncaughtException` (or `unhandledrejection`)

<br>

## 에러 다시 던지기

```javascript
new Promise((resolve, reject) => {
  throw new Error("에러 발생!"); // 혹은 reject 처리
}).catch(function(error) {
  // Error 처리
  alert("에러가 잘 처리되었습니다. 정상적으로 실행이 이어집니다.");
}).then(() => alert("다음 핸들러가 실행됩니다."));
```

* `catch { ... }` 에서 잘 처리 -> then
* `catch { ... }` 에서 처리되지 않는 에러라서 또다시 `throw` -> 해당 에러를 받는 또다른 `catch { ... } `에서 처리

<br>

## 처리되지 못한 에러(혹은 reject)

* 처리할 수 없는 에러 일 때 `.catch` 안에서 `throw`를 사용하면 제어 흐름이 가장 가까운 error handler로 넘어감. 
→ 에러 처리 시 가장 가까운 `.then` handler로 제어가 넘어감.

* **처리되지 못한 거부**: `.catch`를 추가하지 않고 에러가 발생할 때
	* 일단은 가장 가까운 rejection handler로 넘어감.
	* 그러나 만약 이 때 처리할 rejection handler가 없으면 **에러가 갇히고**, **전역 에러**가 발생해 **script가 죽게 됨.**
	→ 브라우저 환경에서 `unhandledrejection` event(HTML의 표준 이벤트)
	

<br><br>

#### Reference)

모던 JavaScript 튜토리얼 https://ko.javascript.info