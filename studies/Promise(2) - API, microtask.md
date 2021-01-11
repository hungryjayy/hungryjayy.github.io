## Promise API <br>
<br>

### Promise.all<br>

Promise 배열을 받고, 모두 처리되면 각 Promise의 결과를 담은 배열이 Promise 로 반환 됨.<br>

```
Promise.all([ 
	new Promise(resolve => setTimeout(() => resolve(1), 3000)), // 1 
	new Promise(resolve => setTimeout(() => resolve(2), 2000)), // 2 
	new Promise(resolve => setTimeout(() => resolve(3), 1000)) // 3 ]).then(alert); // 프라미스 전체가 처리되면 “순서대로” 1, 2, 3이 반환됩니다. 각 프라미스는 배열을 구성하는 요소가 됩니다.
```

* 이 때 Promise 하나라도 거부되면 `Promise.all`은 거부됨<br>
	* 에러 발생 시 다른 promise 무시됨.<br>
  
* Iterable 객체 아닌 `[1, 2, 3]` 과 같은 ‘일반’ 값도 전달 가능. 이 때 요소가 그대로 결과 배열로 전달 됨.<br>
<br>

### Promise.allSettled<br>

`Promise.all` 과 달리 실패 시에도 모든 프라미스 처리를 기다리고, 이후 결과는 각각 Promise 상태 `fulfilled 또는 rejected`와 `value 또는 error`를 받는다.<br>

* polyfill: 브라우저 `Promise.allSettled` 지원 안 될 경우 폴리필 구현<br>

* Promise.race: 가장 먼저 처리되는 Promise 결과 반환<br>
<br>

### Promise.resolve/reject<br>

`async/await` 문법 후로 거의 사용하지 않음<br>

* `Promise.resolve`: 결과가 `value`인 fulfilled Promise 생성<br>

* `Promise.resolve`: 결과 `error`인 rejected Promise 생성<br>

<br>

### Microtask
* Promise handler는 항상 비동기적으로 실행<br>

```
let promise = Promise.resolve();

promise.then(() => alert("프라미스 성공!"));

alert("코드 종료");
```

<br>

*이 코드에서 “코드 종료”가 먼저 출력. 그 이유는?<br>*
- 현재 코드에서 자유로운 상태가 되었을 때 JS engine은 queue(microtask queue)에서 작업을 꺼내 실행(이행된 Promise의 핸들러 `.then`부분)<br>

```
Promise.resolve()
	.then(() => alert("프라미스 성공!"))
	.then(() => alert("코드 종료"));
```

이와 같이 변경하면 의도대로 출력 가능.<br>


*c.f) 처리되지 못한 거부에서 `unhandledrejection` event는 microtask queue에 있는 모든 작업이 완료되고서도 error가 잡히지 않는다면 발생한다. 따라서 setTimeout으로 error를 잡아 처리해준다면 `unhandledrejection` 가 트리거 된다.*<br>
<br>

### Reference
https://ko.javascript.info/promise-api
