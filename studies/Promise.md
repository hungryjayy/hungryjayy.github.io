# Promise<br>
처리 성공 여부에 따라 resolve or reject 호출(Promise는 성공 또는 실패만 함)<br>

## Basic
* resolve(value): 일이 성공적으로 끝난 경우 `value`(하나 혹은 없음)와 함께 호출<br>

* reject(error): 에러 시 `error`(`Error` 객체 혹은 `Error` 상속받은 객체)와 함께 호출<br>

* 내부 property(개발자가 접근 불가, `try` `catch` `finally` 사용시 가능)
	* state: 초기 `pending`/ `resolve()` 시 `fulfilled`, `reject` 시 `rejected`
	* result: 초기 `undefined` / `resolve()` 시 `value`, `reject` 시 `error`
<img src = "./images/promise.png">


## then, catch, finally<br>
### then<br>
`.then(result, error)` 에서 Promise 이행 시 `result`, 거부 시 `error` <br>

예시)
```
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

### catch<br>
에러가 발생한 경우 `then(null, errorHandlingFunction)`, `.catch(errorHandlingFunction)` 이와같이 사용 <br>
<br>

### finally<br>
* `.finally(f)`는 `.then(f, f)`와 유사. Promise가 처리(이행, 거부)된다면 `f`가 항상 실행됨.<br>
* `finally` 핸들러에는 인수가 없음. Promise의 이행 여부도 알 수 없음(보편적인 동작 만을 수행)<br>
* `finally`는 자동으로 다음 handler에게 Promise의 결과, 에러 전달<br>
