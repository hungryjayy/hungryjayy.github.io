---

layout: post

title: Dockerfile

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [docker, 도커, 도커파일, dockerfile]

featuredImage: 

img: 

categories: [Study, Docker]

date: '2021-05-25'

extensions:

  preset: gfm

---

: 도커hub에 등록되어있지 않은 image를 만들고자 할 때 사용. Dockerfile을 통해 만들고자 하는 docker image에 대해 설정.

<br>

* 보통 이러한 형태. (e.g : 스프링 부트 공식문서)

```dockerfile
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

출처: https://spring.io/guides/gs/spring-boot-docker/
```

<br>

## FROM

* 새로운 빌드 스테이지를 초기화함. 추후 추가할 sub 명령어들의 베이스가 됨
* 이 것으로 불러지는 이미지는 docker hub로 부터 가져올 수 있는 base 이미지
* `Dockerfile`은 `FROM` instruction으로 시작되어야 함.

<br>

## RUN

```dockerfile
RUN ["executable", "param1", "param2"]
```

* shell에서 cmd를 치는것과 같음.
* ` RUN ["executable", "param1", "param2"]`
* 이미지 빌드 과정에서 필요한 커맨드를 치기 위해 사용
  * 보통 `npm install ~~ `같이 필요한 것들을 설치하기 위한 명령어가 많이 사용

<br>

## ARG

* `docker build`로 이미지 빌드시, 빌더로 보내줄 수 있는 variable를 보내줌.

  ```dockerfile
  FROM busybox
  ARG user1
  ARG buildno
  ```

* 보안을 위해 credential이나 github key와 같은 정보를 이걸로 보내주는 것은 지양

<br>

## COPY

```dockerfile
COPY [--chown=<user>:<group>] <src>... <dest>
COPY [--chown=<user>:<group>] ["<src>",... "<dest>"]
```

* <src> 디렉터리 파일을 <dest> 디렉터리 파일로 복사해줌.
* 여기서 src는 호스트, dest는 컨테이너의 fs
* 위 예시처럼 다수의 파일도 복사 가능

#### Add

: Copy와 비슷하게 사용. 압축이나 네트워크 상의 파일도 가능하다고 함. 이러한 경우가 아니라면 COPY 사용을 권장

<br>

## ENTRYPOINT

```dockerfile
ENTRYPOINT ["executable", "param1", "param2"]
```

* 이미지를 컨테이너로 띄우는 시점에서 실행할 커맨드.
* 컨테이너로 띄워지는 시점에서의 명령문이기 때문에 docker 이미지를 하나의 실행파일처럼 사용할 때 좋다고 함.

<br><br>

#### Reference)

https://docs.docker.com/engine/reference/builder/#from

https://spring.io/guides/gs/spring-boot-docker/

https://www.daleseo.com/dockerfile/

