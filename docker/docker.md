# 용어

![img](https://blog.kakaocdn.net/dn/cHvenO/btqCwXNywRD/gau1eJ5ShKmlWWEWgZUF2K/img.png)

* Container
  * 애플리케이션을 종속적 객체(라이브러리 등)과 함께 묶어놓은 관리 환경
  * 만들어놓은 Image를 실행한 상태
  * 장점
    * 작은 단위로 개발, 운영 가능하다
    * 가상머신에 비해 가볍다(배포에 소요되는 시간이 상대적으로 적다)
    * 어느 환경에서나 구동 가능



* Image
  * 환경설정, 소스코드, 의존성 등을 설정한 상태의 하나의 버전
    * 만들어진 이미지이기 때문에 불변



* Docker daemon

  * client로 부터 API 입력을 받아 host machine에서 이미지나 컨테이너 등의 도커 객체들을 관리

  * 보통 docker daemon(server)와 client는 같은 machine 내에 존재한다.

    → 설정을 통해 client가 원격(remote)으로 server에 docker command 전달할 수 있다.



* Docker host
  * daemon을 실행하고, image, container를 관리



* Docker client

  * 사용자의 CLI를 요청으로 바꿔 보내주는 역할?

  * docker는 server/client 구조이다.

  * docker command는 HTTP 프로토콜을 이용하는 restapi이다.

    e.g) `docker ps` 라는 CLI는 docker server에 GET api-version/containers 라는 요청을 보내는 것과 같다.



* Docker-compose
  * 다수의 컨테이너를 한번에 실행시키기 위한 도구
  * 각 컨테이너의 설정들을 모두 갖고 있다.



* Docker engine
  * client, server를 통틀어 engine이라고 함.
  * 이미지 생성, 컨테이너 실행



#### reference 

* https://tech.osci.kr/2020/03/03/91690167/
* https://senticoding.tistory.com/94



### docker compose 환경변수 설정

* Docker-compose.yml에서 `${VARNAME}`의 표기를 하는 경우 해당 YAML 파일을 처리하는 동안에 compose에서 이 변수를 동적으로 읽는다.

  * 따라서 `image: rabbitmq:${RABBITMQ_VERSION}` 이와 같을 경우

  * CLI에서 `RABBITMQ_VERSION=2 docker-compose up` 이와 같이 버전 조절 가능.

* 또한, .env파일에 값을 저장하거나 CLI에서 설정하거나, YAML 안에서 `${GHOST_VERSION:-2}` 등의 방식으로 변수 설정 가능

  ```bsh
  ${parameter:-word}
      파라미터가 세팅이 안되어있거나 null인 경우 word로 대체된다.
  ```

* docker-compose의 container 부분에서 `${DB_PORT:-3306}:3306` 이와 같은 것은 포트 포워딩의 개념으로 추측. 

  * 외부포트 : 호스트 포트

  