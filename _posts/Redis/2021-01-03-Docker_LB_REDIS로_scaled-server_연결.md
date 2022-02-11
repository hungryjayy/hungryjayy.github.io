---
layout: post

title: Docker를 통해 LB / REDIS로 scaled-server 리소스 공유

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [redis, 레디스]

featuredImage: 

img: 

categories: [Redis]

date: '2021-01-03'

extensions:

  preset: gfm

---

<br>

## Redis docker-compose 설정

 <img src = "https://hungryjayy.github.io/assets/img/Redis/dockercompose.png"><br>

* default로 6379 포트를 갖는다.
* redis-cli에 ping날리는 정도로 간단하게 헬스체킹이 가능하다.

<br>

 <img src = "https://hungryjayy.github.io/assets/img/Redis/dependson.png">

* 애플리케이션 서버 디펜던시 설정에 redis 헬스체크도 추가해주면 더 좋을 것 같다.

<br>

## Server Load Balancing
<img src = "https://hungryjayy.github.io/assets/img/Redis/loadbalancing.png"><br>

 * `$ ping` 결과: `172.28.0.4`와 `172.28.0.5` 두 애플리케이션 서버에 적절히 LB해주는 것을 볼 수 있고, Redis로 scalablity를 제공한다.
 * 도커를 통한 스케일링은 컴포즈 설정에 `scale=2`을 명시하거나 실행 커맨드에 옵션값을 부여하면 가능하다.

 <img src = "https://hungryjayy.github.io/assets/img/Redis/restapi1.png"><br>

 <img src = "https://hungryjayy.github.io/assets/img/Redis/restapi2.png"><br>

<br>

<img src = "https://hungryjayy.github.io/assets/img/Redis/dockercontainer.png"><br>

 * `$ docker-compose ps` 결과<br>

<br>

### 레디스 리소스 공유

: 별도로 레디스를 통한 소켓서버 스케일링을 확인하려면 A서버에 소켓 연결된 유저에 대해 B 서버를 통해 소켓메시지 emit이 되는 것을 확인하면 된다.

