---
layout: post

title: SpringBoot 용어 및 기본 개념

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [spring, spring boot, 스프링, 스프링부트]

featuredImage: 

img: 

categories: [Study, Spring boot]

date: '2021-03-22'

extensions:

  preset: gfm

---

<br>

## 용어

* Controller : 비즈니스 로직 처리, 세분화가 필요할 경우 적절한 service에 전달
* Service: DAO로 데이터베이스에 접근, DTO로 전달
* DAO : Data Access Object
* DTO : Data Transfer Object

<br>

## spring boot vs spring

- 추상화: Spring Boot는 Spring을 잘 사용하기 위해 한번 더 추상화해 나온 것.
- starter로 디펜던시 편하게 관리.
- 빈에 대한 자동설정이 적용되어 생성된다. 스프링은 직접 빈 등록해야한다.

<br>

## DB 연계

#### Spring boot JPA

- 실무에서 많이 사용한다.
- JPA를 추상화 -> 그냥 인터페이스에 사용할 API 정의만 해주면 된다. 엔티티 매니저 등은 구현체에서 관리해준다.

#### Spring Data JDBC

- Spring boot JPA는 성능과 편의를 위해 많은 것이 추상화 되어있는데 그렇다보니 전체를 이해하기에 공부가 많이 필요하다. JDBC는 이러한 부분을 덜어내고 simplify하는 것에 초점을 두었다고 한다.

<br>

## Bean?

* POJO(plain old java object)
* IoC: 빈 컨테이너에 의해 인스턴스 관리(DI)되고 thread safety도 보장된다.
* 스프링에서는 XML파일로 관리되었다.

<br>

#### Reference) 

https://gmlwjd9405.github.io/2018/11/10/spring-beans.html

https://velog.io/@yhh1056/%EC%8A%A4%ED%94%84%EB%A7%81-Bean-Configuration-Singleton
