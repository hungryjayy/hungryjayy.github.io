---
layout: post

title: 정규표현식(pattern matching)

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [regex]

featuredImage: 

img: 

categories: [Develop]

date: '2021-04-30'

extensions:

  preset: gfm

---

: 문자열 검색, 치환 or 회원가입, 이력서 **양식** 등에 **validation**을 위해 이용

<br>

### 표준 문법

* `.`: 임의의 한 문자.(아무거나)

* `[]` : 문자클래스 [ 와 ] 사이의 문자 중 하나를 선택

* `[^ ]` : 문자클래스 내부를 제외한 나머지. 

  ​	e.g. `[^a-z]` : a~z를 제외한 모든 것

* `^` : 처음 (행의 처음)

* `$` : 끝

* `()` : 하위식. 여러 식을 묶을 수 있음

  ​	`"abc|adc"` 와 `"a(b|d)c"` 는 같은 의미를 가짐

* `\n` : 일치하는 n번째 패턴 (1 <= n <= 9)

* `*` : 0회 이상.

  ​	`"a*b"` == b, ab, aab, aaab, ...

* `+` : 1회 이상

* `{m, n}` : m회 이상 n회 이하

* `?` : 앞 문자가 0 or 1

<Br>

#### 예시

``` 
1) 모든 숫자 : ^[0-9]*$
2) 모든 영문자 : ^[a-zA-Z]*$
3) 모든 한글 : ^[가-힣]*$
4) 모든 영어와 숫자 : ^[a-zA-Z0-9]*$
```

<br>

### Regex test

: https://regexr.com/ 를 통해 테스트

<br>

<br>

#### Reference)

https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D

https://highcode.tistory.com/6

https://uznam8x.tistory.com/62