---
layout: post

title: 모던 JavaScript 튜토리얼 - Promise 3 - Promise API

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js, promise]

featuredImage: 

img: 

categories: [Study, JavaScript]

date: '2021-01-23'

extensions:

  preset: gfm

---

<br>

## Promise.all

* Promise 배열을 받고, 모두 처리되면 각 Promise의 결과를 담은 배열이 Promise 로 반환 됨.

```typescript
Promise.all([ 
	new Promise(resolve => setTimeout(() => resolve(1), 3000)), // 1 
	new Promise(resolve => setTimeout(() => resolve(2), 2000)), // 2 
	new Promise(resolve => setTimeout(() => resolve(3), 1000)) // 3
]).then(alert);
    
    // 프라미스 전체가 처리되면 “순서대로” 1, 2, 3이 반환됩니다. 각 프라미스는 배열을 구성하는 요소가 됩니다.
```

* 이 때 Promise 하나라도 거부되면 `Promise.all`은 거부됨
	* 에러 발생 시 다른 promise 무시됨.
* Iterable 객체 아닌 `[1, 2, 3]` 과 같은 ‘일반’ 값도 전달 가능. 이 때 요소가 그대로 결과 배열로 전달 됨.
* `Promise.race` : `Promise.all`과 비슷하지만, 가장 먼저 처리되는 프라미스 결과만을 반환한다.
  * 예측하기 어렵다.

<br>

## Promise.allSettled

`Promise.all` 과 달리 실패 시에도 모든 프라미스 처리를 기다리고, 이후 결과는 각각 Promise 상태 `fulfilled 또는 rejected`와 `value 또는 error`를 받는다.

* polyfill: 브라우저 `Promise.allSettled` 지원 안 될 경우 폴리필 구현
* Promise.race: 가장 먼저 처리되는 Promise 결과 반환

<br>

### allSettled 폴리필(polyfill)

* 브라우저가 `Promise.allSettled`를 지원하지 않는 버전이라면 아래와 같은 폴리필 구현(코드 이해하기)

```javascript
if(!Promise.allSettled) {
  Promise.allSettled = function(promises) {
    return Promise.all(promises.map(p => Promise.resolve(p).then(value => ({
      status: 'fulfilled',
      value
    }), reason => ({
      status: 'rejected',
      reason
    }))));
  };
}
```

<br>

## Promise.resolve/reject

`async/await` 문법 후로 거의 사용하지 않음

* `Promise.resolve`: 결과가 `value`인 fulfilled Promise 생성

* `Promise.resolve`: 결과 `error`인 rejected Promise 생성

<br><br>

#### Reference)

모던 JavaScript 튜토리얼 https://ko.javascript.info