### Kotlin 특징(vs java)

* 함수형 프로그래밍(함수가 일급 객체로서의 의미를 가짐)

  * 1급 객체
    * 변수나 데이터에 함수 할당 가능
    * 객체의 인자로 넘길 수 있음
    * 객체의 리턴값으로 리턴 가능

  

  

  * 함수형 프로그래밍

    * 함수를 side effect 없도록 선언하고 사용하는 선언형 프로그래밍.
    * 가장 큰 두가지 특징: immutable data, first class citizen
      * e. g. map, filter 등이 함수형 프로그래밍스러운 함수

    

    * Immutable 
      * 변경 불가능
      * 일급 컬렉션과 비슷한 맥락. list에 하나를 추가하기 위해선 추가된 하나의 list를 새로 만들어 할당.
      * Persistent data structure
    * 일급 객체
      * 변수나 데이터에 함수를 담을 수 있다.
      * 함수를 literal로 바로 정의할 수 있다.
        * 리턴값으로 전달 가능
        * 함수를 파라미터로 전달 가능



* Java와 동일한 타입안정성 + 타입 추론(Inference) 제공.



* `val name = "abced"` <- 이 경우 알아서 String으로 인식



* Null 안정성 제공

  * `var foo: String? = "Hello"` 물음표는 nullable을 의미

  * 위의 경우 `foo?.bar()`와 같이 쓰면 null이 아닐때 bar() 호출, null일 때 null 반환

  * Not-Null Assertion(!!)

  * Elvis operator `?:`

    * ```kotlin
      val foo: String? = getString()
      return foo?.length ?: 0
      ```

    * 위의 경우 null 아닐 때 length, null일 때 0 반환



* Delegation pattern(상속 대안)

  ``` kotlin
  class CopyPrinter(copier: Copy, printer: Print)
   : Copy by copier, Print by printer
  interface Copy {
   fun copy(page: Page): Page
  }
  interface Print {
   fun print(page: Page)
  }
  ```

  * 위 예제에서 copy로 copy를, print로 print를 그 자리에서 바로 선언하고 있다.



* Extention

  ```Kotlin
  // StringExt.kt
  fun String.double(): String() {
  	return this + this
  }
  ```

  * 이 때 `"Hello".double()` 이처럼 사용 가능.



* Companion 객체

  * 어떤 클래스의 모든 인스턴스가 공유하는 객체 만들 때.
  * 자바에서 static 변수 / 메서드 사용했을 때와 동일
    * 객체 생성도 전에 런타임시작하는 그 때 즉시 생성된다.



* Smart casting

  * 개발자가 따로 type을 캐스팅해줄 필요 없이 컴파일러가 알아서 Type을 캐스팅해줌



* collection 함수

  * (*mutable과 immutable을 구분하여 지원한다는 점*)
  * List - remove, add, addAll, removeAt, removeIf 등
  * Set - `setOf<type>(items)` 
  * Map - Key, value쌍. `Pair(A, B) or A to B`와 같이 세팅



### Map vs forEach

* forEach는 array 요소를 한번 순회한다.
* map() Array요소가 제공된 함수로 호출될 때 callback에서 적용되는 새 요소들로 새로운 array를 만든다.
* filter는 조건문으로 새로운 함수 반환



### 표현식 vs 구문

* 표현식은 값을 반환한다.

  * == 값을 만든다
  * == 대입 연산자 오른쪽에 명시할 수 있다.(값을 저장)

  `val foo = when { }`

* 구문은 무언가가 실행되도록 명령을 지시하는 문장을 의미

  * == 값을 만들지는 않는다.

* when 표현식에서는 항상 else를 필요로 한다.(값을 만들어야 하기 때문에)



### object

* 싱글톤



### let, also, run, apply, with

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



### Inner class

* 내부(nested) 클래스 장점
  * 내부에서 외부 클래스 멤버 접근 가능
  * 서로 관련된 클래스를 논리적으로 묶어 표현해, 코드의 캡슐화 증가
  * 외부에선 내부에 접근 불가. 따라서 코드 복잡성 감소.
* inner vs nested
  * Kotlin에서는 nested가 default
  * inner를 쓰려면 명시를 해야함.
  * (TODO)차이점 명확하게 구분하기



### enum class

* 열거형(클래스처럼 보이게 하는 상수)
* 관련 있는 상수들을 모아 심볼릭한 명칭의 집합으로
* java에서의 static 변수 효과
* 이러한 상수 타입으로 어떠한 변수를 할당했을 때 그 변수를 수정함으로써 얻는 런타임에서의 에러를 방지할 수 있다.





### Time 시간 관련 API

* `Instant.now()` 한 점을 나타냄. 따라서 비교 가능
* `Duration.ofHours(2)` - 2시간  차이
  * 테스트에 유용하게 쓰일 수도
* `LocalDateTime` : 시간대 포함하지 않음.
  * `LocalDate`, `LocalTime` 도 존재
* `ZonedDateTime` : 시간대 개념 추가
  * Local에서 시간대면 추가되면 Zoned. 반대도 가능





#### Reference)

#### https://yangbox.tistory.com/24





## Plugin

### all-open 플러그인

* Kotlin은 기본적으로 final class

  * 따라서 상속 불가
  * 안정성 상승
  * effective java "상속에 대한 좋은 설계와 문서화를 할 자신이 없다면 상속을 허용하지 말 것." 
  * 그러나 java에 익숙하다면 이것이 불편할 수 있음

* `all-open` 플러그인

  ```kotlin
  buildscript {
      dependencies {
          classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
      }
  }
  
  apply plugin: "kotlin-allopen"
  ```

  ```kotlin
  plugins {
    id "org.jetbrains.kotlin.plugin.allopen" version "1.4.32"
  }
  ```

  ``` kotlin
  allOpen {
      annotation("com.my.Annotation")
      // annotations("com.another.Annotation", "com.third.Annotation")
  }
  ```

  * 이처럼 annotation을 설정하면 해당 annotation을 갖는 클래스는 모두 all-open이 된다.
    * 상속이 불필요한 곳은 막을 수 있음.
  * 기본적으로 @Configuration, @Componenet, @Async 등 특정 어노테이션을 갖는 클래스들은 이미 open 되어있다(어노테이션에 의해서) - 상세는 공식문서에



#### Reference)

#### https://cchcc.github.io/blog/Kotlin-%EB%94%94%ED%8F%B4%ED%8A%B8%EA%B0%80-final%EC%9D%B8-%EC%9D%B4%EC%9C%A0/

#### https://kotlinlang.org/docs/all-open-plugin.html#gradle





## 예외 처리

* Error랑은 다름.
  * 시스템이 비정상적인 상황(심각한 오류)
  * 개발자가 예측할 수 없고 처리할 수도 없음.(try catch로 잡을 수 없음)



* 개발자가 처리해야 하는 것 == Exception
  * Checked Exception 발생 시 더 특정상황에서의 unchecked exception을 발생시켜 에러메시지를 명확히 전달	



* ### Checked Exception

  * 컴파일 시점
  * 반드시 예외처리 해야함.
  * 예외 발생시 롤백 하지 않음
    * 복구가 가능하기 때문(현실적으로는 많지 않음)
  * IOException, ClassNotFoundException 등등

* ### Unchecked Exception

  * 런타임 시점
  * 명시적으로 하지 않아도 됨
  * 예외 발생시 롤백해야함
  * NPE, IllegalArgumentException, ClassCastException 등



#### Reference)

#### https://madplay.github.io/post/java-checked-unchecked-exceptions

#### https://cheese10yun.github.io/checked-exception/