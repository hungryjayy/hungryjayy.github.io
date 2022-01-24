---
layout: post

title: 모던 JavaScript 튜토리얼 - Promise 4 - 프라미스화

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js, promise]

featuredImage: 

img: 

categories: [JavaScript]

date: '2021-01-26'

extensions:

  preset: gfm

---

: 콜백만 지원해주는 API 등 **콜백 함수**를 **프라미스를 반환**하도록 프라미스로 감싸는 것

<br>

## 프라미스화(Promisify)

* 콜백을 그대로 써도 되지만, 기존 코드가 프라미스 기반으로 작성되어있으면 프라미스로 바꾸는 것이 일관성있고 좋을 것 같다. 

* 특히, `Async/await` 패턴과 함께 사용하면 더 좋다.

* 그러나, 프라미스와는 다르게 콜백은 여러번 호출될 수 있기 때문에, 콜백을 **단 한번 호출하는 함수에만** 적용할 것

  e.g) redis api 프라미스화

  ![redis_callback](https://hungryjayy.github.io/assets/img/JavaScript/redis_callback.png) 

  * npm의 redis를 까보면 윗 이미지와 같이 Callback을 지원하도록 되어있다.
  * **Promisify**: 이러한 콜백기반 API를 Promise로 한번 더 감싸서 따로 메서드로 만들어 놓는 것. 사용할 때 훨씬 편하고 가독성이 좋다.

  ```typescript
  function redisGet(client: RedisClient, hash: string, key: string): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      client.hget(hash, key, (error: any, value: string) => {
        if (error) reject(error);
        resolve(value);
      });
    });
  }
  ```

<br>

## 헬퍼 함수

* 위와 같이 매번 Promise화 할 것인가? - 여러개의 함수를 프라미스화 해야한다면 **util성 헬퍼 함수**를 따로 구현해두는 것도 사용성 측면에서 좋을 것 같다. - 코드 이해하기

  ```javascript
  // 콜백의 성공 결과를 담은 배열을 얻게 해주는 promisify(f, true)
  function promisify(f, manyArgs = false) {
    return function (...args) {
      return new Promise((resolve, reject) => {
        function callback(err, ...results) { // f에 사용할 커스텀 콜백
          if (err) {
            reject(err);
          } else {
            // manyArgs가 구체적으로 명시되었다면, 콜백의 성공 케이스와 함께 이행 상태가 됩니다.
            resolve(manyArgs ? results : results[0]);
          }
        }
  
        args.push(callback);
  
        f.call(this, ...args);
      });
    };
  };
  
  // 사용법:
  f = promisify(f, true);
  f(...).then(arrayOfResults => ..., err => ...)
  ```

  * 추가적인 Promisify 도구 참고: https://github.com/mikehall314/es6-promisify

<br><br>

#### Reference)

모던 JavaScript 튜토리얼 https://ko.javascript.info