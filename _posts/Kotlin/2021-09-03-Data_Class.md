---
layout: post

title: Data class

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [kotlin]

featuredImage: 

img: 

categories: [Study, Kotlin]

date: '2021-09-03'

extensions:

  preset: gfm


---

: 보통 DTO 등 데이터를 보관하기 위한 목적으로 사용할 때 Kotlin에서는 Data class로 선언해 사용한다.

<br>

* JPA 사용 시 Kotlin에서는 entity를 data class로 선언하지 않는다.
  * Entity는 그냥 class로, 어노테이션을 사용해서.
* 주로 DTO나 VO를 목적으로 data class를 사용하는 것으로 보인다.

<br>

## 특징

* 코틀린은 기본적으로 상속을 막아놓고 `open` keyword를 추가해 상속 가능한 class로 변경할 수 있다. 그런데, data class에서는 open keyword 조차 불가능하다.
  * 애초에 상속을 받는 것을 목적으로 만든 클래스가 아니기 때문
* 위와 마찬가지로 abstract, inner 키워드도 불가능하다.
* primary constructor가 있어야한다. -> `data clarr Foo( //here) `
* 컴파일러가 primary constructor에 선언된 모든 프로퍼티들에 대해 여러 메소드들을 제공한다.

<br>

## 자동 생성 메소드

: 이 메서드들은 data class 생성 시점에 자동으로 만들어진다.

* `hashCode()`: 객체의 주소값을 해싱해서 생성한 객체마다 갖는 고유의 값. 따라서, 같은 객체인지 판별할 때 쓰인다.

* `equals()`: 객체가 담고있는 내용이 같은지를 검사한다. `hashCode` 구분.

* `toString()`

* `componentN()`: JavaScript의 구조 분해 할당처럼 쓰이는 것 같다.

  ```kotlin
  // 구조 분해 할당
  val (name, age) = person
  
  // componentN() 이용. 선언한 순서대로 숫자가 들어가야 한다.
  val name = person.component1()
  val age = person.component2()
  ```

* `copy()`: 어떠한 객체의 내용을 그대로 복사해 사용할 수 있게 해준다.

  * 사이드 이펙트를 없애고 새로 만들어버림 (함수형의 패러다임에 어울리는 메소드인 것 같다.)

    ```kotlin
    fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
    
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    ```

<br><br>

#### Reference)

https://kotlinlang.org/docs/data-classes.html