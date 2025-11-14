---
layout: post

title: Lazy Evaluation

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [java, 지연실행, Lazy Evaluation]

featuredImage: 

img: 

categories: [Language, Java]

date: '2021-06-16'

extensions:

  preset: gfm

---

: 변수가 function에 접근하는 순간이 아니라 실제로 그 값이 필요할 때까지 연산을 미뤄 불필요한 연산을 피하는 방법

<br>

* **함수형** 프로그래밍 언어의 스트림에서 주로 사용한다. **중간 연산**에는 결과값이 아직 없고, **최종 연산**을 접하는 순간 연산이 수행된다.
  * 중간 연산: `filter`, `map`, `flatMap`, ...
  * 최종 연산: `allMatch`, `findFirst(first)`, `collect`, `count` ... 등 특히 계산 관련

* 당장에 해결할 문제를 차례대로 수행하지 않고, 마지막 문제를 제공받을 때 까지 **Lazy하게 기다리기**

<br>

## 코드 예시

```kotlin
// 코드 이해를 위해 Kotlin으로 예시 작성

val list: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9);

val count = list.filter { it <= 5 }
                .filter { it % 2 == 0 }
                .map { it * 10 }
                .count()

// -- 만약 스트림을 사용 안했다면 아래와 같은 로직이 될 것 같다.

for(it in list) {
            if(it < 5) {
                if(it % 2 == 0) {
                    it * 10
                }
            }
        }
```

<br>

* 위의 경우 결국 연산이 실제로 수행되는 순서는 다음과 같을 것 같다.

```
1 - it <= 5
	  it % 2 == 0
2 - it <= 5
	  it % 2 == 0
	  it * 10
3 - it <= 5
	  it % 2 == 0
4 - it <= 5
	  it % 2 == 0
	  it * 10
5 - it <= 5
	  it % 2 == 0
6 - it <= 5
7 - it <= 5
8 - it <= 5
9 - it <= 5
	  count
```



* 또다른 예시: 만족하는 값을 얻으면 그냥 연산 끝.

```kotlin
// 코드 이해를 위해 Kotlin으로 예시 작성

val list: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9);

val count = list.filter { it <= 5 }
                .filter { it % 2 == 0 }
                .map { it * 10 }
                .first()

// --
1 - it <= 5
	  it % 2 == 0
2 - it <= 5
	  it % 2 == 0
	  it * 10
	  2
```

* 바로 위 예시는 아래와같이 `first()` 에 만족하는 답을 알아내는 순간 쓸데없는 연산을 피해버린다.

<br>

## 그림으로 보는 예시

* Eager Evaluation

![img](https://t1.daumcdn.net/cfile/tistory/9905FD465C4A993D20) 



* Lazy Evaluation

![img](https://t1.daumcdn.net/cfile/tistory/99CC3B505C4A994A1C) 





#### Reference)

https://github.com/WeareSoft/tech-interview/blob/master/contents/java.md

https://sabarada.tistory.com/154

