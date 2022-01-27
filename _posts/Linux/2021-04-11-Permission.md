---
layout: post

title: Permission

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [linux, 리눅스]

featuredImage: 

img: 

categories: [Linux]

date: '2021-04-11'

extensions:

  preset: gfm
---

: 어떠한 사용자가 **파일**, **디렉토리**에 대해 Read, Write, Execute를 할 수 있게 / 없게 하는 것

<br>

## Access mode

```shell
$ ls -al

-rw-rw-r-- 1 joowon joowon 0 Jul 12 12:34 example.txt
```

### `rw-rw-r--` 

* `rw-` : owner의 권한
* `rw-` : group의 권한
* `r--` : other의 권한(운영체제의 다른 모든 사용자들)

<br>

### r(read), w(write), x(exec) 세 가지 권한

#### Change Mode: `chmod `

1. `777` 과 같은 CLI로 권한 변경 가능

   * 4: read

   * 2: write

   * 1: execute

   * 0: 아무것도 없음

     e.g ) `chmod 777 example.txt`

<br>

2. 혹은 u-r / o+w 와 같은 옵션을 주어서

   e.g) `chmod o+r example.txt`

   e.g 2) `chmod u-w example.txt`

   e.g 3) `chmod -R o+w example-dir` (디렉토리에 대해 recursive하게)

<br>

#### 디렉토리

* 실행권한: cd 명령어로 들어갈 수 있는가?
* 쓰기권한: 해당 디렉토리 내부 내용을 바꿀 수 있는가?

<br><br>

#### Reference)

https://opentutorials.org/course/2598/14190