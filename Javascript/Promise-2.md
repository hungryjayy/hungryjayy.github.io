# Promise 2 - Error 처리

## Promise error handling

Chaining에서 여러 then 중 Promise가 하나라도 거부된다면 `.catch` 트리거

### 암시적 try...catch
예외가 발생하면 예외를 잡고, reject처럼 다룸(거부상태의 promise로 변경시킴)
```typescript
new Promise((resolve, reject) => {
	throw new Error("에러 발생!"); 
}).catch(alert); // Error: 에러 발생!
```
`throw`를 사용해 에러를 던지거나 **모든 종류의 에러**가 발생하면 거부된 Promise를 의미하기 때문에 아래와 똑같이 동작<br>
```typescript
new Promise((resolve, reject) => {
	reject(new Error("에러 발생!"));
}).catch(alert); // Error: 에러 발생!
```
<br>

* 처리할 수 없는 에러 일 때 `.catch` 안에서 `throw`를 사용하면 제어 흐름이 가장 가까운 error handler로 넘어감. 
→ 에러 처리 시 가장 가까운 `.then` handler로 제어가 넘어감.

* 처리되지 못한 거부: `.catch`를 추가하지 않고 에러가 발생할 때
	* 가장 가까운 rejection handler로 넘어감.
	* 만약 이 때 rejection handler가 없으면 에러가 갇히고, script가 죽게 됨.
	→ `unhandledrejection` event
	
### Reference
https://ko.javascript.info/promise-error-handling
