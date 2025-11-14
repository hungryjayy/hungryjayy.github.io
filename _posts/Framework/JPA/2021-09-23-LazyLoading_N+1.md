---
layout: post

title: Lazy Loading과 N + 1

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [jpa, spring boot jpa]

featuredImage: 

img: 

categories: [Framework, JPA]

date: '2021-09-23'

extensions:

  preset: gfm

---

: JOIN 쿼리를 통해 두 엔티티를 조회할 때, 엔티티 로딩 시점이 달라, DB에 쿼리가 한번 더 나가게 된다.

* JPA에서는 **글로벌 페치 전략**을 지연 로딩에서 즉시 로딩으로 변경해 해결할 수 있다.
* JPQL (queryDSL)을 사용한다면 **글로벌 페치 전략을 참고하지 않고 그냥 쿼리를 생성**하기 때문에 여전히 문제이다.

<br>

## JPQL에서의 해결방법

<br>

#### 1. **Fetch join**: 조인 대상(연관된 엔티티)까지 함께 조회해온다.

* 이때 글로벌 페치 전략은 무의미해진다.
* **단점**: 메소드가 화면에 맞춰 무분별하게 증가한다.
  * 예를 들어 화면 A에선 order만 필요로 하고, B에선 order, member를 필요로 할 때 결국 fetch를 하거나 하지 않는 다른 메소드를 사용하게 될 것이고, **뷰와 레포지토리가 논리적 의존관계**를 갖게 된다.
  * Facade 패턴을 이용하면 계층간 논리적 의존관계를 제거할 수 있다고는 하나, 계층 하나가 더 끼어버린다는 점에서 트레이드 오프를 고려해야 할 것 같다.
* 전부 즉시 로딩을 하도록 하면 또 예상치 못한 SQL 실행이 될 수 있다. 일단은 지연 로딩을 하고, 성능 최적화가 필요한 곳에 즉시 로딩을 하는 방법이 좋다.
  * ManyToOne, OneToOne: 기본 페치 전략이 즉시 로딩
  * OneToMany, ManyToMany: 기본 페치 전략은 지연 로딩

<br>

#### 2. EntityGraph: 아래와 같은 방법으로 attribute 하나만 Eager하게 가져온다.

```java
@EntityGraph(attributePaths = "name")
```

<br>

## 카테시안 곱

: Fetch는 inner join, EntityGraph는 Outer join인데, 두 방법 모두 카테시안 곱이 발생한다. 따라서 중복된 데이터가 많아지게 된다.

* 해결방안
  1. Set 자료구조 사용(중복 허용하지 않도록)
  2. `select distinct ~~~ 쿼리 사용`

<br><br>

#### Reference)

자바 ORM 표준 JPA 프로그래밍 

