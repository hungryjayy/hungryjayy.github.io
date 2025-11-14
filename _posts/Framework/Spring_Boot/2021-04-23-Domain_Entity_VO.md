---
layout: post

title: Domain, entity, VO

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [spring, spring boot, 스프링, 스프링부트]

featuredImage: 

img: 

categories: [Framework, Spring_Boot]

date: '2021-04-23'

extensions:

  preset: gfm
---

<br>

## Domain

* 내가 개발하고자하는 관심 영역
* 일반적으로 Entity, Repository(interface)
* 도메인 로직 등이 있을 수 있다.

<br>

## Entity

* 식별자를 갖는다.
* DB 말고도 객체지향적인 개념으로도 쓰인다.

* 객체지향에서의 메시지를 생각해보면, 엔티티에 관련된 로직을 포함할 수 있다.

<br>


## VO(value object)

* 값 자체가 의미가 있다..? == 어느 상황에도 **동일하게 취급 되는 것**이 핵심
  * e. g. 1) 서비스에서 사용하는 색상클래스 RED가 `new Color(255,0,0);` 이고, 이 서비스 내부에서 red 색상을 사용하고자 할 때 `Color.RED` 를 사용하면 되는 것과 같은?
  * e. g. 2) 돈으로 따지면 `100원`은 어딜가도 `100원`. 
* 관련 value와 관련된 로직을 가질 수 있다.
* 값 자체이기 때문에 read only로 가져가는 것이 보통이다.
* DTO는 전달 자체에 초점을 두는 것과 VO에서는 차이가 있다.

<br><br>

#### Reference)

https://multifrontgarden.tistory.com/186

https://velog.io/@gillog/Entity-DTO-VO-%EB%B0%94%EB%A1%9C-%EC%95%8C%EA%B8%B0