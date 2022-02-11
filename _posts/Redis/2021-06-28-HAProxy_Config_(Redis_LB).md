---
layout: post

title: HAProxy Config (Redis LB)

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [redis, 레디스]

featuredImage: 

img: 

categories: [Redis]

date: '2021-06-28'

extensions:

  preset: gfm

---

<br>

### HAProxy에서의 LB

* 기본적으로 Reverse Proxy 방식으로 동작한다.
* 별도 conf 파일을 구성해 사용해야 한다.

<br>

## Config 특징

* 로컬에 설치하면 `/etc/haproxy/haproxy.cfg`에 존재

* 도커로 설치하면 따로 config 구성해서 proxy 컨테이너 올릴 때 볼륨 마운트 해주어야 함.

* 가장 중요한 네가지 `global`, `defaults`, `frontend`, `backend`

* swarm과 같이 **별도의 DNS 맵핑**이 필요한 작업에서는 `resolvers`라는 것을 통해 따로 맵핑작업을 수행해야 한다.

  *c f) Swarm 환경에서 DNS 네임서버는* `127.0.0.11:53`

<br>

## 1. global

* HAProxy에게 low한 level로부터 영향을 미치는 전체적인 보안이나 퍼포먼스 관련된 config

  e.g) 

  ```
  global
      maxconn 50000						# HAProxy와 붙을 수 있는 최대 커넥션. Out of mem 방지
      log /dev/log local0					# 로그 어디에 저장할지(IP 혹은 디렉토리)
      user haproxy						# Root 권한 누구에게 줄지
      group haproxy						# Root 권한 누구에게 줄지
      stats socket /run/haproxy/admin.sock user haproxy group haproxy mode 660 level admin
      									# Runtime API 사용가능하게 함(런타임에 config 변경 등)
      nbproc 2							# Process, Thread 수
      nbthread 4							# Process, Thread 수
      ssl-default-bind-ciphers ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE- ...		# 보안 관련
      ssl-default-bind-options ssl-min-ver TLSv1.2 no-tls-tickets				# 보안 관련
  ```

<br>

## 2. defaluts 

* front, back의 디폴트를 설정. 즉, 중복을 줄이기 위한 목적

  * 물론 오버라이드 가능.

* HTTP, TCP 모드를 고를 수 있는데, 둘다 설정하면 TCP, HTTP 따로도 가능

  e.g)

  ```
  defaults
      timeout connect 10s						# 서버로의 TCP 연결 지연시간 설정
      timeout client 30s						# Client와의 연결 지연시간. TCP 에선 timeout이 server와 같아야한다
      timeout server 30s						# Server와의 연결 지연시간
      log global								# Global section의 로그 설정을 따른다.
      mode http								# HTTP 모드(더 높은 레벨의 트래픽 감지 필요 시.) or TCP 모드
      option httplog							# 잘 사용되지 않는 TCP로그보다 더 자세하게
      maxconn 3000							# 디폴트 2천. 얼마나 front 연결의 최대 커넥션
  ```

<br>

## 3. frontend

* client가 연결할 수 있는 IP와 port를 정의한다.

  * HAProxy는 백엔드 서버 앞에서 Reverse Proxy 방식을 사용

* 많은 웹사이트에 노출시키기 위해서는 그만큼 많은 frontend서버를 사용할 듯

  e.g)

  ```
  frontend www.mysite.com										# 각 웹사이트마다 www.mysite.com 처럼 네이밍가능
      bind 10.0.0.3:80										# IP, Port 리스너
      bind 10.0.0.3:443 ssl crt /etc/ssl/certs/mysite.pem    # SSL 보안
      http-request redirect scheme https unless { ssl_fc }   # client가 redirect를 할 수 있도록 다른 URL제공
      use_backend api_servers if { path_beg /api/ }		   # 어떤 백엔드 풀과 연결할지
      default_backend web_servers								
  ```

<br>

## 4. backend

* LB 되기 위한 서버의 **그룹**에 대한 사항을 정의

  e.g)

  ```
  backend web_servers
      balance roundrobin							# 서버 선택 알고리즘. roundrobin, leastconn
      cookie SERVERUSED insert indirect nocache  # Sticky Session 유지를 위해 쿠키 전달
      option httpchk HEAD /						# 헬스체크. TCP는 connection 관련 체크만, HTTP는 
      											# 성공적 HTTP 응답까지 오는지 체크. 디폴트로는 OPTTIONS 메서드를
      											# 통해 헬스체크 (2~300대 응답이 오는지)
      default-server check maxconn 20				# 헬스체크, maxconn 등 default config 설정
      server server1 10.0.1.3:80 cookie server1  # 핵심 부분. IP 대신 도메인을 사용하면 시작시에 resolve된다.
      server server2 10.0.1.4:80 cookie server2  # 혹은 직접 resolvers를 통해 runtime에 업데이트되도록 구현
      											# 포트가 명시되지 않으면 클라이언트가 접속한 포트 사용
      											# maxxconn을 설정해야한다.(여기선 default-server에 설정)
  ```

<br><br>

#### Reference)

https://www.haproxy.com/blog/the-four-essential-sections-of-an-haproxy-configuration/