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



## 200420

* kafka-node 모듈 설치 중 cannot find module 'kafka-node' 에러 발생
  * 설치 에러?
    * npm install --save kafka-node 이후에도 같은 에러 발생
  * 가장 먼저 오탈자 있는지 확인
  * 없으면
    1. `rm -rf node_module` && `rm -rf package-lock.json` 일단 다 지우기
    2. 캐시 비우기 `npm cache clean --force`
    3. 다시 설치 `npm install`
    4. 이래도 안되면 npm 이슈 가보기

