---

layout: post

title: Container 인스턴스 Locale 설정

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [docker, 도커, 컨테이너 설정, locale]

featuredImage: 

img: 

categories: [DevOps, Docker]

date: '2021-11-23'

extensions:

  preset: gfm

---

: 한글이 마름모 + 물음표로 보인다면 locale 설정이 맞지 않는 것이다.

<br>

* 컨테이너 내부에서 한글 문자를 보면 아래와같이 마름모 + 물음표 + 특수문자 형태로 보이게 된다.

![no_locale](https://hungryjayy.github.io/assets/img/Docker/no_locale.png) 

<br>

* `locale`을 통해 확인해보면 현재 컨테이너의 로케일 설정에서 `LC_ALL=` 이 값이 비어있고 나머지는 POSIX 라는 로케일로 설정되어있다.

![locales](https://hungryjayy.github.io/assets/img/Docker/locales.png) 

<br>

##### POSIX: POSIX 규격을 따르는(우분투) 시스템에서의 디폴트 로케일

<br>

* 위 그림에서 `locale -a` 을 통해 사용가능한 모든 로케일의 이름을 출력하면, `C`, `POSIX`(C와 같음), `C.UTF-8` 세가지가 있음이 확인된다.

  *  `ko_KR.UTF-8` (한국에서 사용하는 한글 UTF-8이라는 뜻)같은걸 사용하고자 한다면 별도 설치가 필요하다.

* 위의 `C`, `C.UTF-8`중 두번째껄 사용하기 위해 **컨테이너 실행 옵션**에 추가 옵션을 주면 된다.

  docker exec -itu 0 **-e LC_ALL=C.UTF-8** ${container} /bin/bash

* 이후 실행환경(Dockerfile이나 컴포즈 설정)에도 env를 반영해 자동적으로 환경변수를 먹이면 된다.

<br><br>

#### Reference)

https://www.44bits.io/ko/post/setup_linux_locale_on_ubuntu_and_debian_container