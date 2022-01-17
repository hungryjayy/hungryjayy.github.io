# 모던 JavaScript 튜토리얼 - Promise 5 - MicroTask

<br>

## Microtask

* Promise handler는 항상 비동기적으로 실행

```typescript
let promise = Promise.resolve();

promise.then(() => alert("프라미스 성공!"));

alert("코드 종료");
```

<br>

*이 코드에서 “코드 종료”가 먼저 출력. 그 이유는?*

- 현재 코드에서 자유로운 상태(**콜스택이 비었을 때**)가 되었을 때 JS engine은 queue(microtask queue)에서 작업을 꺼내 실행(이행된 Promise의 핸들러 `.then`부분)

```typescript
Promise.resolve()
	.then(() => alert("프라미스 성공!"))
	.then(() => alert("코드 종료"));
```

이와 같이 변경하면 의도대로 출력 가능.

<br>

## 처리되지 못한 거부

* `unhandledrejection` event는 **microtask queue에 있는 모든 작업**이 완료되고서도 error가 잡히지 않는다면 발생한다.

  ```javascript
  let promise = Promise.reject(new Error("프라미스 실패!"));
  setTimeout(() => promise.catch(err => alert('잡았다!')), 1000);
  
  // Error: 프라미스 실패!
  window.addEventListener('unhandledrejection', event => alert(event.reason));
  ```

  * catch에서 에러를 잡는데도 `unhandledrejection`가 발생하는 이유: "프라미스 실패"가 발생하면 마이크로태스크 큐가 비워지게 되는데, 이 때 `unhandledrejection` 핸들러가 트리거 되고, 이후에 catch가 스택에 올라가면서 "잡았다!"가 발생하는 것

<br><br>

#### Reference) 모던 JavaScript 튜토리얼 https://ko.javascript.info