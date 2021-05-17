# let, also, run, apply, with

#### let

* ```kotlin
  val resultIt = person.let {
      it.name = "James"
      it.age = 56
      it // (T)->R 부분에서의 R에 해당하는 반환값.
  }
  ```

  * 타입 T의 확장함수
  * Block의 마지막 return값에 따라 let의 return도 변한다.

  * `T?.let { }` 형태를 통해 non-null로 만들수 있다.

#### with

* ``` kotlin
  val person = Person("James", 56)
  with(person) {
      println(name)
      println(age)
      //자기자신을 반환해야 하는 경우 it이 아닌 this를 사용한다
  }
  ```

  * 확장 함수가 아니라 일반 함수. 따라서 객체 receive를 직접 입력받는다.
  * 블럭 안에서 곧바로 person의 프로퍼티에 접근 가능.
  * 주로 객체의 함수를 여러개 호출할 때 그룹화하는 용도

#### run

* 첫번째 형태

  ```kotlin
  val person = Person("James", 56)
  val ageNextYear = person.run {
      ++age
  }
  
  println("$ageNextYear")
  
  // 57
  
  ```

  * with처럼 인자로 람다 리시버를 받는다.
  * 차이점은 T의 확장함수라는 점.
  * 따라서 non-null 일때만 실행할 수 있다.

  

* 두번째 형태

* ``` kotlin
  val person = run {
      val name = "James"
      val age = 56
      Person(name, age)
  }
  ```

  * 확장함수가 아니다.
  * 어떠한 객체를 생성하기 위한 명령문들을 하나의 블럭으로 묶어 가독성을 높이는 용도

#### apply

* ```kotlin
  val person = Person("", 0)
  val result = person.apply {
      name = "James"
      age = 56
  }
  
  println("$person")
  
  //Person(name=James, age=56)
  ```

  * T의 확장함수.
  * it, this를 사용할 필요 없다.
  * 블럭에서 return값을 받지 않는다.
  * 앞의 let, with, run은 모두 T -> R의 R을 반환하지만 apply는 T를 반환

#### also

* ```kotlin
  val person = Person("", 0)
  val result = person.also {
      it.name = "James"
      it.age = 56
  }
  
  println("$person")
  
  //Person(name=James, age=56)
  ```

  * T의 확장함수., T를 반환.
  * 객체의 속성을 전혀 사용하지 않거나 변경하지 않을 때 also 사용

* apply, also는 자기 자신을 리턴한다는 점에서 `builder` 패턴과 동일한 용도로 사용.