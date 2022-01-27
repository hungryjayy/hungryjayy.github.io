---
layout: post

title: Daemon, Service

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [linux, 리눅스]

featuredImage: 

img: 

categories: [Linux]

date: '2021-04-10'

extensions:

  preset: gfm
---

: 항상 켜져있어야 하는 것

<br>

* **웹 브라우저**에 무언가를 쳤을 때 응답을 **웹 서버**에서 줄 수 있어야 한다.
  * 언제 브라우저 요청이 올 줄 모른다. 따라서 웹 서버는 항상 켜져있어야 하는 **데몬** or **서비스**
* `/etc/init.d` 디렉토리에 **데몬 프로그램** 위치한다.
  * 일반적으로 프로그램을 켜는 것과는 다르다.
  * `sudo service apache2(예) start / stop` 
* 위의 CLI로 실행한 서비스는 `ps aux`를 통해 확인 가능.

<br>

#### *이전에 redis와 rabbitMQ가 계속 커져있던 이유는 당시의 그것들이 데몬으로 켜져있었어서*

* CLI 환경 기준 `/etc/rc3.d`로 가서 `ls -l` 을 해보면, 최근에 지웠던 redis와 rabbitmq 확인 가능
  * GUI기준 `rc5.d`라고 함
* 각 서비스에 대해 링크들이 걸려있음을 확인 가능 (->)

<br><br>

#### Reference)

https://opentutorials.org/course/2598