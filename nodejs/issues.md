# Node js 겪은 이슈들

## 200216

* 내가 npm으로 무언가을 도입하거나 업데이트했을 때 변경 이력을 보니 package.json에 생소한 모듈이 보인다 .
  * npmjs에서 내가 도입한 무언가와의 의존성을 찾아보자.



## 200222

* 비동기 처리에 대한 고민

```javascript
func().then(async () => {
  if (foo()) {
   await promise() // first
  }
  await promise() // second
 })
```

​	이와 같은 코드에서 first → second 순서 보장. If() 조건절의 판별은 그 즉시 일어남



