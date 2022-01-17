# Lazy Evaluation

: 불필요한 연산을 피하기 위해 연산을 지연시키기



* **함수형** 프로그래밍 언어에서 주로 사용

  * 수식이 변수에 접근하는 순간이 아니라, 계산 값이 필요 할 때까지 계산을 미룸

* (java의 경우는 Stream에서) **중간 연산을 합쳐서 진행**하고, 합쳐진 연산을 **최종 연산에서 한 번에** 처리

  * 중간 연산: `filter`, `map`, `flatMap`, ...
  * 최종 연산: `allMatch`, `findFirst(first)`, `collect`, `count` ...

* 당장에 해결할 문제를 차례대로 수행하지 않고, 마지막 문제를 제공받을 때 까지 **Lazy하게 기다리기**

* 아래의 예시 중첩된 Stream을 For문으로 바꾸면 이와 같은 로직이 될 수 있을 듯 - 함수형(Stream)이 훨씬 **가독성** 측면에서 좋아보임

  ```c++
  for(it in list) {
              if(it < 5) {
                  if(it % 2 == 0) {
                      it * 10
                  }
              }
          }
  ```



e.g ) 다음과 같은 Stream 로직이 있다고 할 때

```kotlin
// 코드 이해를 위해 Kotlin으로 예시 작성

val list: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9);

val count = list.filter { it <= 5 }
                .filter { it % 2 == 0 }
                .map { it * 10 }
                .count()
```



위의 경우 수행 순서는 다음과 같음

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



e.g) 또다른 예시 : **마지막 문제 받을 때까지 기다리기**, 쓸데없는 연산 피하기

```kotlin
// 코드 이해를 위해 Kotlin으로 예시 작성

val list: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9);

val count = list.filter { it <= 5 }
                .filter { it % 2 == 0 }
                .map { it * 10 }
                .first()
```



바로 위 예시는 아래와같이 first()로 답을 알아내는 순간 쓸데없는 연산을 피해버림

```
1 - it <= 5
	it % 2 == 0
2 - it <= 5
	it % 2 == 0
	it * 10
	2
```



### 그림으로 보는 예시

* Eager Evaluation

![img](https://t1.daumcdn.net/cfile/tistory/9905FD465C4A993D20) 



* Lazy Evaluation

![img](https://t1.daumcdn.net/cfile/tistory/99CC3B505C4A994A1C) 





#### Reference)

#### https://github.com/WeareSoft/tech-interview/blob/master/contents/java.md

#### https://sabarada.tistory.com/154

