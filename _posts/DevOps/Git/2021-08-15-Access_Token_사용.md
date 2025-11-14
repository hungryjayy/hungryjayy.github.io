---
layout: post

title: 비밀번호 대신 PAT(Personal Access Token) 받아 사용하기

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [git]

featuredImage: 

img: 

categories: [DevOps, Git]

date: '2021-08-15'

extensions:

  preset: gfm


---

<br>

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/error.png)

: 깃헙 레포지토리에 push했더니 위와같은 에러가 있었는데, 알고 보니 21년 8/13 이후로 깃헙이 더이상 비밀번호 인증방식을 사용하지 않는 것 같다.

<br>

## 1. 액세스 토큰 받기

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/settings.png)

1) 깃헙 로그인 후 Settings - developer settings

<br>

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/personal.png)

2. Personal access tokens

<br>

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/generate.png)

3. 토큰 생성하기

<br>

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/token.png)

4. Note에 description 적어주고, expiration 기간 설정, scope는 귀찮으니 다 체크 해주면 된다.

<br>

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/key.png)

* 예시용 토큰

5. 키값 나중에 필요할수있으니 갖고있기

<br>

![image-20210815175709797](https://hungryjayy.github.io/assets/img/Git/error.png)

!?!?!?????

<br><br>

## 2. (Mac) 키 체인 접근

![image-20210815181358130](https://hungryjayy.github.io/assets/img/Git/keychain.png)

<br><br>

#### Reference)

https://stackoverflow.com/questions/68775869/support-for-password-authentication-was-removed-please-use-a-personal-access-to