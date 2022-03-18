---
layout: post

title: Shell과 Kernel

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [linux, 리눅스]

featuredImage: 

img: 

categories: [Study, Linux]

date: '2021-04-11'

extensions:

  preset: gfm
---

: 터미널(Shell)에 CLi를 입력하면 쉘은 Kernel에게, Kernel은 하드웨어에게 전달

<br>

![img](https://blog.kakaocdn.net/dn/buMPbF/btqZlQMBSNx/1p80FhTQppyLGtUuKzOb60/img.png) 

* 사람이 받는(보는) 것은 위의 과정과 역순으로 전달

<br>

## 커널

: 하드웨어와 가장 가까이 있는 프로그램

* "커널을 통해 하드웨어 제어"
  * HW : CPU, 메모리(RAM), Disk
* 따라서, 함부로 건드리면 안됨

<br>

## 쉘

: 사용자가 선호하는 쉘을 선택해 사용하고 커널을 제어할 수 있게 됨

* `echo $0` 을 통해 해당 터미널이 어떤 쉘을 사용하는 지 알 수 있음.
* bash, zsh 등의 명령어 해석기
  * 둘은 부모가 같고 비슷함.
  * `/bin` (root디렉토리 하위의 bin 디렉토리)에 존재

<br>

### 쉘 스크립트란?

: 순차적으로 실행되어야 할 명령의 순서(각본)

* 해당 명령어 셋을 재사용 할 수 있음
* `.sh` 파일
* 우리가 쉘에서 사용하는 많은 명령어들이 **실제로는 쉘 스크립트**처럼 동작함
* 스크립트 파일은 `#!/bin/bash` 로 시작
  * 이 밑의 line들은 bash에 의해 해석되어야 한다 라는 의미.

<br>

### 쉘 스타트업 스크립트

* `alias l = 'ls -al'` : ls -al의 별명을 l로 만들어 준 것
* bash에 무엇을 실행할지 코드가 있음.

<br><br>

#### Reference)

https://opentutorials.org/course/2598/14203

https://reakwon.tistory.com/135