# async / await

### async<br>

```javascript
async function f() {
	return 1;
}
```

function 앞에 `async`를 붙이면 해당 함수는 반환 값을 fulfilled 상태 Promise로 감싸 반환<br>
<br>

### await<br>
`async` 함수 안에서만 동작. JS는 `await` 키워드를 만나면 Promise가 처리될 때까지 기다림.<br>

* `await`은 최상위 레벨 코드에서 사용 불가.(익명 async 함수로 감싸면 가능)<br>

* `await`은 thenable 객체를 받을 수 있음.
	* 이 때 await는 일반 Promise executor가 하는 일과 동일

<br>

### Error handling<br>
Promise가 정상 이행 시 `await promise`는 Promise의 `result`에 저장된 값을 반환. 거부 시 `throw` 문 처럼 Error 던져짐. → `try...catch` 이용해 에러 잡을 수 있음.<br>

<br>

### Reference
https://ko.javascript.info/async-await
