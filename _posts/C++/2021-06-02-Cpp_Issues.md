---

layout: post

title: c++과 관련해 겪은 이슈들, 간단한 메모

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [c++, cpp]

featuredImage: 

img: 

categories: [Study, C++]

date: '2021-06-02'

extensions:

  preset: gfm


---

<br>

## 200317

* Vector는 memset 불가
  * cstring 라이브러리

<br>

* C++ string
  * `string.erase(index부터, length만큼);`
  * `string.replace(index부터, length만큼, ""얘로 변경);`
  * `string.find(""얘를 찾아라, index부터);`
  * 이외에도 여러 버전으로 오버로딩되어있으니 라이브러리를 공부하기

<br>

* `isupper()`, `isdigit()`, `toupper()` 등 camel case가 적용이 안되어있으니 주의

<br>

## 200405

* c++ string replace All

```c++
while((pos = str.find(" ")) != string::npos) {
    str.replace(pos, size, str2); // str2로 대체
}
```

<br>

* 백트랙킹 문제에서 굳이 visit 하나하나 바꾸지 말기. check할때 하나하나 접근하는게 이득

<br>

* 이분탐색에서 기본적으로 while 조건문은 left <= right 

<br>

