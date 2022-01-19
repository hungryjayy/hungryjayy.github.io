---

layout: post

title: Docker 볼륨과 바인드 마운트

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [docker, 도커, 도커 볼륨]

featuredImage: 

img: 

categories: [Docker]

date: '2021-08-23'

extensions:

  preset: gfm

---

<br>

* 도커 컨테이너의 데이터를 보존하기 위한 방법
  * 기본적으로 컨테이너가 삭제될 때 해당 컨테이너에 쓰여진 데이터는 함께 삭제

![types of mounts and where they live on the Docker host](https://docs.docker.com/storage/images/types-of-mounts.png) 

* 그림에서 보듯, 약간 차이가 존재

<br>

## 볼륨

* 무언가 데이터가 저장될 때 해당 볼륨에 저장되고, 이를 통해 컨테이너간 공유 가능
  * 어떤 볼륨에 데이터를 저장해 두고, 여러 컨테이너에 마운트 해주면 컨테이너들에서 해당 공유 데이터 접근 가능.
  * 볼륨이란 호스트쪽 경로

``` dockerfile
./mongodb/conf.d/mongo.conf:/etc/mongodb.conf
```

* 이와 같은 경우 `mongo.conf`(왼쪽)를 `/etc/mongodb.conf`에 마운트 (내가 갖고 있는 **데이터 볼륨**을 현재 띄워놓은 **컨테이너**의 해당 위치에 마운트)
  * *마운트: 하드웨어와 특정 디렉토리를 연결하는 것.*
  * 갖고있는 볼륨 확인 : `docker volume ls`

<br>

## 바인드 마운트

* 컨테이너에 데이터를 저장하기 위한 방법

* 호스트 파일 시스템의 경로를 컨테이너로 바로 마운트 가능

* e.g.) 예를 들어, 현재 경로에 `test.txt` 파일을 생성하고, 해당 호스트 경로를 컨테이너의 `/app` 경로에 마운트해보겠습니다. 컨테이너에 터미널을 붙여서 `ls /app` 커맨드를 실행해보면 `test.txt` 파일이 컨테이너의 `/app` 경로에도 존재하는 것을 확인할 수 있습니다.

  ```bash
  $ touch test.txt
  $ docker run -v `pwd`:/app -it --name one busybox /bin/sh
  / # ls /app
  test.txt
  
  출처: https://www.daleseo.com/docker-volumes-bind-mounts/ 
  ```
  * 이 때 반대로 컨테이너에서 생성해도 **호스트의 경로에도 추가됨**

<br>

## 볼륨, 바인드 마운트 차이점

* 해당 마운트 포인트를 도커가 관리해주냐, 개발자가 관리해야하냐의 차이
* 볼륨을 사용할 때는 개발자 스스로 볼륨을 관리해야 하지만, image, container와 비슷한 방식으로 관리되는 이점. 도커에서 볼륨을 권장

<br><br>

#### Reference)

https://docs.docker.com/storage/volumes/

https://boying-blog.tistory.com/31

https://www.daleseo.com/docker-volumes-bind-mounts/