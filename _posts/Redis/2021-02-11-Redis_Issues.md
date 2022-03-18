---
layout: post

title: Redis와 관련해 겪은 이슈들, 간단한 메모

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [redis, 레디스]

featuredImage: 

img: 

categories: [Study, Redis]

date: '2021-02-11'

extensions:

  preset: gfm


---

<br>

## 200216

* pub/sub(redis)에서 subscribe한 객체는 broadcasting을 받기 위해 무한히 blocking된다. 

  → 이것이 sub을 또 다른 목적으로 사용할 수 없는 이유 ?

  * 따라서 일반적으로 client를 정의할 때 pub / sub / client 세개를 정의하거나 sub / pub + client 두개를 사용.

<br>

* Socket server 여러개가 동작하고 broadcasting 하게 하려면 socket.io-redis 라이브러리 사용

  * 각 node에게 event를 배포하기 위해 redis의 pub/sub을 이용
  * 하나의 서버에서 pub/sub을 만들고 연결해놓고 나중에 현재서버에서 접속이 들어오면 다른 서버들에 publish해서 알려주고 다른 서버는 구독중.


<br>

#### reference)

https://github.com/socketio/socket.io-redis/issues/21#issuecomment-60315678