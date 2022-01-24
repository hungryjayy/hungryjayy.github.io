---
layout: post

title: JPA와 관련해 겪은 이슈들, 간단한 메모

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [jpa, spring boot jpa]

featuredImage: 

img: 

categories: [JPA]

date: '2021-05-14'

extensions:

  preset: gfm


---

<br>

## 210514

### JPA repository 사용시 Optional<T> 이슈

* 기본적으로 자바에 맞춰져 있으므로 Optinal로 묶여있음

* Kotlin의 장점을 잘 살리려면 Nullable을 바로 체크할 수 있어야 함.

  * Extension 사용

    1. 함수를 재정의하기

    2. 이미 잘 구현되어있는 built-in extension 사용

       e.g) `findById()` -> `findByIdOrNull()`

       성능 이슈가 있다고 하지만, 무시할 수 있을정도의 미미한 성능차



* @JoinColumn 어노테이션의 옵션 name은 해당 엔티티 테이블에 어떤 컬럼명으로 저장할지를 지정
  * 실제로 FK가 맵핑되는 컬럼은 해당 타입(엔티티)의 PK이니까 자동으로 알아서 맵핑해줌.

<br>

### findAllBy~~ vs findBy~~~

* 둘은 이름만 다르지, 같은 쿼리를 만들어 냄.
*  복수형을 만들어내는 곳에선 All, 아닌 곳에선 findBy를 사용해 코드의 가독성을 높이면 됨

<br>

## 210525

### MongoDB 도입해 개발 도중 발생 에러

```
If you have database settings to be loaded from a particular profile you may need to activate it mongodb
```

* application.properties
  * 이 곳에서 datasource 명시 안했어서? -> 외부 속성 문제 X
* JPA + MongoDB 의존성으로 인한 문제. JPA 제거(MongoDB에서도 원래 사용하던 API 그대로 사용)







