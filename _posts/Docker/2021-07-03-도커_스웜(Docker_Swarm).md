---

layout: post

title: 도커 스웜(Docker Swarm)

author: hungryjayy

description: null

tags: [docker, 도커, 도커 스웜, swarm]

featuredImage: 

img: 

categories: [Docker]

date: '2021-07-03'

extensions:

  preset: gfm

---

: 호스트간 컨테이너를 배포(컨테이너 오케스트레이션)하기 위한 목적의 도구 중 하나

<br>

![도커 스웜 구조.png](https://itwiki.kr/images/thumb/0/06/%EB%8F%84%EC%BB%A4_%EC%8A%A4%EC%9B%9C_%EA%B5%AC%EC%A1%B0.png/600px-%EB%8F%84%EC%BB%A4_%EC%8A%A4%EC%9B%9C_%EA%B5%AC%EC%A1%B0.png) 

* 도커에서 제공하는데, 일단 도커가 설치되어있다면 별도의 툴을 설치하지 않아도 되고 간편함
* 세부적인 설정이 어렵고, 때문에 대형 노드 클러스터링에는 쿠버와같은 것들을 추천한다고 함

<br>

## 오케스트레이션의 필요성

* 수백 수천개의 컨테이너를 관리해야 하는 상황에서 재설치 / 재설계 없이 동일한 애플리케이션을 배포받을 수 있음
* 클러스터링 용이 : 여러 서버를 하나인 것 처럼 사용 가능

<br>

## 구성

#### 매니저 노드

* 워커 노드에 컨테이너를 배포하고 관리하는 역할. 스웜 명령어는 매니저 노드에서 가능
* HA를 위해 복수(홀수)노드로 띄워놓는다고 함(뗏목 알고리즘)
* 이 노드가 워커 노드의 역할도 수행

#### 워커 노드

* 매니저 노드의 명령을 받고, 상태 체크 등의 수행

#### 레지스트리 노드

* 이미지를 registry에 저장해놓고, 매니저와 워커가 받아올 수 있도록

#### 서비스

* 하나의 배포 단위. 하나의 이미지를 기반으로 생성

#### 스택

* 여러 서비스가 있는 전체 애플리케이션 관리
* compose가 컨테이너들을 정의하는거라면, 스택은 서비스들을 정의

<br>

## CLI

* 매니저노드 만들기 : `docker swarm init --advertise-addr {만들 호스트 IP}`
* 워커 노드 만들기 : `docker swarm join --token {매니저노드가 만들때 발생한 Token} {매니저노드 IP}`
* 스웜 환경 확인 : `docker node ls` 
* 서비스 확인 : `docker service ls`
* 서비스 생성 : `docker service create --name redis --replicas 2 -p 6379:6379 redis`  (2 레플리카)
* 스택 확인 : `docker stack ls`
* 스택 특정 노드 확인 : `docker stack ps {스택}`
* 서비스 테스트 : `curl {IP}`
* 나머지는 그냥 help쳐서..

<br><br>

#### Reference)

https://www.redhat.com/ko/topics/containers/what-is-container-orchestration

https://roseline124.github.io/kuberdocker/2019/07/31/docker-study08.html

https://simpleisit.tistory.com/110

https://subicura.com/2017/02/25/container-orchestration-with-docker-swarm.html

https://team-platform.tistory.com/51?category=829379

