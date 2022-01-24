---
layout: post

title: Transaction 적용이 안되는 몇가지 문제

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [jpa, spring boot jpa]

featuredImage: 

img: 

categories: [JPA]

date: '2021-06-14'

extensions:

  preset: gfm
---

<br>

* Private - 오버라이딩 문제
  * Transaction 처리 과정에서 프록시 객체로 등록하기 때문에 private 불가
  * 마찬가지로 final이 적용된 메서드도 불가능
    * Kotlin에서 allOpen을 통해, 혹은 Open을 통해 가능

<br>

* inner Method
  * 같은 클래스 내의 Transaction 적용되지 않은 메소드에서 `@Transactional`적용된 메소드은 Transactional로 생성된 proxy 객체를 통해 호출하는 것이 아니라, 내부 호출로 되기 때문에 transaction 적용 불가
  * **해결 방법**: `@Transactional` 을 Callee에서 Caller로 옮겨가면 된다.

<br>

* ReadOnly Method에서 Read-write method 호출
  * read-write 내부에서도 transaction은 readOnly로 동작하게 됨.
  * 반대의 경우도.. 라는데 확인할 필요가 있음

<Br><br>

#### Reference)

https://www.whiteship.me/spring-transactional-and-spring-aop/

https://www.whiteship.me/jpa-entitymanager-contains/

https://handr95.tistory.com/3

https://kapentaz.github.io/spring/Spring-Transaction-%EC%A0%81%EC%9A%A9-%EB%B2%94%EC%9C%84-%EC%A0%9C%EC%96%B4-%EB%B0%A9%EB%B2%95/#

https://netframework.tistory.com/entry/Spring-Transactional%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC
