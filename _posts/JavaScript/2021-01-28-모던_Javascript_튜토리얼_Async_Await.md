---
layout: post

title: 모던 JavaScript 튜토리얼 - async / await

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js, promise, async await]

featuredImage: 

img: 

categories: [Study, JavaScript]

date: '2021-01-28'

extensions:

  preset: gfm
---

<br>

## async

```javascript
async function f() {
	return 1;
}
```

* function 앞에 `async`를 붙이면 해당 함수는 반환 값을 fulfilled 상태 Promise로 감싸 반환

<br>

## await
: `async` 함수 안에서만 동작. JS는 `await` 키워드를 만나면 Promise가 처리될 때까지 기다림.

* `await`은 최상위 레벨 코드에서 사용 불가.(익명 async 함수로 감싸면 가능)

* `await`은 thenable 객체를 받을 수 있음.
	* 이 때 await는 일반 Promise executor가 하는 일과 동일

<br>

## Error handling
* Promise가 정상 이행 시 `await promise`는 Promise의 `result`에 저장된 값을 반환. 거부 시 `throw` 문 처럼 Error 던져짐. → `try...catch` 이용해 에러 잡을 수 있음.

* 아래처럼 catch를 추가하지 않거나, async함수에서 `try ... catch` 를 하지 않으면 **처리되지 못한 거부**가 발생

  ```javascript
  async function f() {
    let response = await fetch('http://유효하지-않은-url');
  }
  
  // f()는 거부 상태의 프라미스가 됩니다.
  f().catch(alert); // TypeError: failed to fetch // (*)
  ```

<br><br>

#### Reference)

모던 JavaScript 튜토리얼 https://ko.javascript.info

