---
layout: post

title: Spring Reactive stream과 Webflux

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [spring, spring boot, 스프링, 스프링부트]

featuredImage: 

img: 

categories: [Study, Spring boot]

date: '2021-02-03'

extensions:

  preset: gfm


---

<br>

## Reactive programming

: 기본적으로 Data == Stream, event 로 보는 관점, 비동기 데이터 스트림을 이용해 데이터를 전달하는 방식의 Programming

<br>

### Reactive Streams

* Backpressure방식의 stream으로 처리함으로써 비동기적인 수행을 가능하게 한다.
* Reactive Stream의 구현체가 바로 Webflux에서의 **Reactor**이다.
  * **리액터 패턴**: 이벤트 핸들링의 일반적인 패턴. Node.js에서 이벤트 디멀티플렉서가 이 패턴으로 되어있다.


<br>

### Backpressure? (== dynamic pull 방식)

<br>

##### push 방식

: publisher가 전달하고자하는 데이터가 쌓이면 바로 subscriber에게 push하는 방식

* subscriber 처리율이 떨어지면 OOM 문제가 발생할 우려 있다.

<br>

##### pull 방식

: subscriber가 처리할 수 있는 만큼 publisher에게 요청한다.
- **Dynamic pull**: subscriber 처리가능한 태스크가 n개가 되면 n개 pull 땡겨오는 방식

<br>

## Reactive Streams의 백프레셔 스트리밍 흐름

<img src = "https://hungryjayy.github.io/assets/img/Spring_Boot/reactiveflow.png">

1. `Subscriber` 가 특정 이벤트에 대해 구독 요청
2. 이벤트 발생 시 `Subscription`은 양 측을 연결하는 매체 역할 수행
3. 작은 단위의 스트림으로 계속해서 스트리밍

  <br>


### Publisher component 두가지
1. `mono`: 0 ~ 1개 데이터 전달
1. `flux`: 0 ~ N개 데이터 전달

<br>

### Cold seq vs Hot seq

: 데이터 생성 시점의 차이
- Cold: 구독 시점에 데이터 생성
    - e.g) API 호출하는 경우. 구독 때마다 새로운 요청을 server에 전송한다.
- Hot: 구독자 고려하지 않고 그냥 데이터 생성. 계속 데이터는 생성 되고 있고 구독 후 부터 구독자가 받게 된다.

<br><br>

#### Reference)

https://javacan.tistory.com/entry/Reactor-Start-1-RS-Flux-Mono-Subscriber <br>

https://engineering.linecorp.com/ko/blog/reactive-streams-with-armeria-1/ <br>

https://www.nurinamu.com/dev/2020/04/09/why-webflux-1/