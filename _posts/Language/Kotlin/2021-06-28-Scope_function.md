---
layout: post

title: Scope function(let, also, run, apply, with)

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [kotlin]

featuredImage: 

img: 

categories: [Language, Kotlin]

date: '2021-06-28'

extensions:

  preset: gfm
---

<br>

### let

```kotlin
val numbers = mutableListOf("one", "two", "three", "four", "five")
numbers.map { it.length }.filter { it > 3 }.let { 
    println(it)
    // and more function calls if needed
} 
```

* 타입 T의 확장함수
* lambda result: Block의 마지막 return값에 따라 let의 return도 변한다.
* `T?.let { }` 형태를 통해 null 관리 가능

<br>

### with

``` kotlin
val numbers = mutableListOf("one", "two", "three")
val firstAndLast = with(numbers) {
    "The first element is ${first()}," +
    " the last element is ${last()}"
}
println(firstAndLast)
```

* 확장 함수가 아니라 **일반 함수**. 블록 내부에서 lambda result를 반환한다.
* `it`을 통해서가 아니라 블럭 안에서 곧바로 person의 프로퍼티에 접근 가능.
* 사용 용도: 람다 result를 반환하지 않으면서 이 객체로 무언가 수행하길 원할 때 “ *with this object, do the following.*”

<br>

### run

```kotlin
val result = service.run {
    port = 8080
    query(prepareRequest() + " to port $port")
}
```

* with처럼 인자로 람다 리시버를 받는다.
* **T의 확장함수**
* 어떠한 **객체를 초기화**하기 위한 명령문들을 하나의 블럭으로 묶어 가독성을 높이는 용도

<br>

### apply

```kotlin
val adam = Person("Adam").apply {
    age = 32
    city = "London"        
}
println(adam)
```

* T의 확장함수, T를 그대로 반환
* it, this를 사용할 필요 없다.
* 사용 상황: *"apply the following assignments to the object.*”
* 블럭에서 return값을 받지 않는다.

<br>

### also

```kotlin
val numbers = mutableListOf("one", "two", "three")
numbers
    .also { println("The list elements before adding new one: $it") }
    .add("four")
```

* T의 확장함수, T를 그대로 반환.
* 객체의 속성을 전혀 사용하지 않거나 변경하지 않을 때 also 사용
* 주로 프로퍼티가 아니라 객체 자체에 대한 참조가 필요할 때 사용하라고 권장 *“and also do the following with the object.”*

<br><br>

#### Reference)

https://kotlinlang.org/docs/scope-functions.html

https://0391kjy.tistory.com/25