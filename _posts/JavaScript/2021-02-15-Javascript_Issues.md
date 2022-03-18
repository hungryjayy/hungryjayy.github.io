---
layout: post

title: JavaScript와 관련해 겪은 이슈들, 간단한 메모

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [javascript, js]

featuredImage: 

img: 

categories: [Study, JavaScript]

date: '2021-02-15'

extensions:

  preset: gfm
---

<br>

## 200215

* typeof (ABC) (x) → typeof ABC

<br>

## 200223

* 나머지 매개변수는 가장 마지막 인자에 들어와야 한다.

  ``` javascript
  function foo(...rest) {} // O
  
  function foo2(arg1, ...rest, arg2) {} // X 에러 발생
  ```

<br>

## 200324

* javascript에서 `{` 를 선언문과 같은 줄에서 시작해야 하는 이유
  * https://stackoverflow.com/questions/3641519/why-do-results-vary-based-on-curly-brace-placement