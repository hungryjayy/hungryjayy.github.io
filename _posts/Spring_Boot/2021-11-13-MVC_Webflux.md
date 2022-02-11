---
layout: post

title: Spring MVC vs Webflux

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [spring, spring boot, 스프링, 스프링부트]

featuredImage: 

img: 

categories: [Spring boot]

date: '2021-11-13'

extensions:

  preset: gfm

---

<br>


## Spring MVC
<img src = "https://hungryjayy.github.io/assets/img/Spring_Boot/mvc.png" width = "50%"> 

* 동기식 처리방식. 멀티 스레드 환경 Spring boot에서의 일반적인 모습
* 기본적으로 client로 부터 요청이 들어오면 queue를 통한다.(WAS의 대기열?)
* Thread pool이 수용할 만큼만 처리하고 나머지는 queue에서 대기한다.
* Resource를 줄이기 위해 Thread는 성능에 맞게 최대치를 정해놓고 효율적으로 사용한다
* 그럼에도 불구하고, **thread pool size(Default 200)를 계속 초과하게 된다면 thread pool hell이 발생한다. → 평소의 배가 되는 delay 발생**

<br>

## Spring Webflux
![](https://hungryjayy.github.io/assets/img/Spring_Boot/webflux.png)

## 특징
* client req 발생시 event loop을 통해 작업 처리. (서버의 코어 갯수 == thread 갯수). 따라서 적은 수의 thread로 동시성을 다룬다. concurrent user가 1000명 이상일 때 webflux를 사용하면 좋다.
* 이벤트 루프기반 Non blocking을 지원한다. block이 생길 경우 성능이 저하되어 사용 의미가 없어진다.
  * thread를 줄였는데 blocking이 걸리는 API에 요청이 몰린다면 결국 I/O작업이 끝날 때까지 기다려야 하기 때문.
* reactive Programming이 가능해진다. -> event driven

<br>

## 문제

* DB connection을 non-blocking으로 지원하는 라이브러리는 아직 잘 사용되지 않고 있다. R2DBC, jasync sql 등. MongoDB, Redis 등 NoSQL은 지원중.
* 러닝커브 높다.
* 또한, Event 기반이기에 작업이 엉겨붙어(이리갔다 저리갔다) 처리되기 때문에 Tracking이 다소 복잡할 수 있다.

<Br>

#### Reference)

https://velog.io/@dyllis/Spring-MVC-vs-WebFlux