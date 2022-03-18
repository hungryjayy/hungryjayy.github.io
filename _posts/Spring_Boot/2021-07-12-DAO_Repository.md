---
layout: post

title: DAO vs Repository

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [spring, spring boot, 스프링, 스프링부트]

featuredImage: 

img: 

categories: [Study, Spring boot]

date: '2021-07-12'

extensions:

  preset: gfm

---

<br>

## Repository

* DDD에서 나왔다고 한다. aggregate 하나당 repository 하나
* **객체-지향적인 인터페이스를 제공. domain layer에 속하는 순수한 도메인 모델 객체**
  * 의존성 제거를 위해 Interface, persistence(구현부) 로직으로 분리.
    * 인터페이스(JPA Interface): domain layer
    * 구현부(Hibernate): persistence layer
  * `Separated interface` : DIP(dependency Inversion Principle)에 기반한 방법
* ORM 사용시 객체 단위로 테이블 관리하고, 이때 repository가 DAO 역할 수행

<br>

## DAO

* **persistence layer에 속함. persistence layer에 대한 facade 역할을 수행**
* 위의 repository와 같이 도메인 로직과 persistence 로직을 분리해 `separation of concerns` 원리 충족의 목적.
  * 하부의 persistence 메커니즘이 DB라는 사실을 숨기지 않는다.
* DAO의 인터페이스는 CRUD 쿼리와 1:1 매핑되는 세밀한 오퍼레이션 제공
  * **DB 쿼리 단위.(service == 트랜젝션 단위, controller == 업무 단위)**
  * 단일 데이터 접근.
  * 실제 비즈니스 로직 처리 : 하나 이상의 DAO 조합 == 트랜젝션 단위
* 결과적으로 repository의 하나의 오퍼레이션은 DAO의 여러 오퍼레이션에 매핑되는 것이 일반적

<br><br>

#### Refence)

http://egloos.zum.com/aeternum/v/1160846

https://velog.io/@leyuri/DAO%EC%99%80-Repository-DTO-VO