# 테스트 자동화와 Mocha

: 개발한 내용의 신뢰성을 높여 궁극적으로 코드 전반의 신뢰도를 높이는 작업

<br>

## BDD ?

* 핵심: **테스트**, **문서**, **예시**를 갖도록
* **테스트**: 함수가 의도하는 동작을 제대로 수행하는지
  * 하나의 테스트 패턴 - Given, When, Then 구조
  * 시나리오 기반으로 테스트를 작성. 모든 시나리오를 작성하도록
* **문서(스펙)**
  * 함수가 어떤 동작을 수행하는지(`describe`, `it` 에 스펙 설명)
  * 기존 함수 변경 시, 개발자는 **스펙을 기준으로** 함수를 보다 안전하게 변경
  * **가장 중요한 것** - **기존에 구현된 기능들에 영향을 주지 않는다는 것**을 보장할 수 있게 됨.
* **예시**
  * 실제 동작하는 예시를 이용해, 함수를 실제로 어떻게 사용할 수 있는지 설명하는 역할

<br>

## 테스트 실행 도구

* Mocha

  * Node.js 프로그램에서 사용되는 JavaScript test 프레임워크. 
  * `describe`, `it` 과 같은 테스트 실행 관련 주요 함수 제공
  * 브라우저의 지원을 받으며, 비동기 테스트, 커버리지 report, assertion 라이브러리들을 지원

* Chai

  * Assertion 함수 제공

    * `assert.equal`, `assert.strictEqual`, `assert.notEqual`, `assert.isTrue` 등. 문서 참고해 작성하기

      *Chai - https://www.chaijs.com/api/assert/*

* Sinon

  * 내장함수라던지, 실제로 만들기 어려운 가짜 객체를 만들어 테스트에 용이하도록

  * Mock 객체를 이용하는 것이 불가피한 경우가 있지만, 아닌 경우엔 최대한 지양하는게 좋다는 의견이 많음.

    *"100% 실제 환경과 같을 수 있는가?" 를 생각한다면, 아닌 경우도 분명 존재하기 때문*

<br>

## 명세서(Specification == Spec)

```javascript
describe("foo", function() {
  brfore(() => console.log("before test"));
  after(() => console.log("after test"));
  
  beforeEach(() => console.log("before unit test"));

  it("bar", function() {
    assert.equal(myFunc(2), 10);
  });

});
```

* `describe("title", function() {...})`
  * 구현하고자 하는 기능에 대한 설명.
  * 비슷한 유즈 케이스에 관한 `it` 유닛 테스트들을 한데 모아놓음
  * 중첩 `describe` 를 통해 하위 그룹을 정의할 수 있다.
    * 각 중첩 describe 컨텍스트에서 정의한 변수, 함수들에는 다른 곳에서 접근X
* `it("use case", function() {...})`
  * 이 `it` 테스트가 테스트 할 하나의 유즈 케이스를 명시
  * 하나의 유닛 테스트라고 생각
  * **이 하나의 유닛 테스트에서 하나만 확인할 것**
  * `.only`를 통해 해당 키워드를 준 유닛테스트들만 따로 테스트 가능
* `assert`
  * 예상한대로 동작하는지 확인하는 것
* `before`, `after`
  * 전체 테스트 시작 전, 후
  * 주로 초기화 용도
* `beforeEach`, `afterEach`
  * 각 unit test 시작 전, 후
  * 주로 초기화 용도

<br>

## 개발 순서

1. 명세서 초안 작성: 가능한 모든 시나리오를 구상해 작성
2. 명세서 코드 작성
3. Mocha 프레임워크를 이용해 명세서 실행 후 모두 통과하는지 확인
4. 테스트 추가, 커버리지 높이면서 테스트 성공하는지 확인

```javascript
describe("foo", () => {
  beforeEach(() => {
    
  })
  it("bar1", ()=> {
    // ...
  })
  it("bar2", () => {
    // ...
  })
  it("bar3", () => {
    
  });
});

```

<br><br>

#### Reference) 모던 JavaScript 튜토리얼 https://ko.javascript.info/intro

