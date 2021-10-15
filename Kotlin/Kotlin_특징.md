

# Kotlin 특징

## 함수형 프로그래밍(함수가 일급 객체로서의 의미를 가짐)

: 함수를 side effect 없도록 선언하고 사용하는 선언형 프로그래밍.

e.g ) `forEach` vs `map` : `map`이 함수형에 가깝다. `forEach`는 원 배열에 side effect가 발생할 가능성이 있지만, `map`의 경우 원래의 배열을 바꾸지 않고 **새로운 배열을 만들어**버린다.

<br>

## 가장 큰 두가지 특징

1. **Immutable**
   
   * 변경 불가능. 만약 변경이 필요하면 새로운 Immutable 변수 or 객체를 하나 만듦으로 해결.
   * Persistent data structure
2. **일급 객체**
   
   * 변수나 데이터에 함수를 담을 수 있다.
   * 함수를 literal로(표현식) 바로 정의할 수 있다.
     * 리턴값으로 전달 가능
     * 함수를 파라미터로 전달 가능
     
     ```kotlin
     val sum = fun Int.(other: Int): Int = this + other
     ```

<br>

*연속 전달방식(Continuation-Passing Style): 결과를 계속 전달하는 방식*

* stream: `filter().map()`과 같이 결과를 계속 전달
* 자바스크립트에서 결과를 계속 콜백으로 전달하는 것

<br>

## Java와 동일한 타입안정성 + 타입 추론(Inference) 제공.(Smartcasting)

* `val name = "abced"` <- 이 경우 알아서 String으로 인식

<br>

## Null 체크 용이

* Nullable

  ```kotlin
  var foo: String? = "Hello"
  
  foo?.bar()
  // foo != null ? foo.bat() : null
  ```

* Not-Null Assertion(!!)

* Elvis operator `?:`

  ```kotlin
  val foo: String? = getString()
  
  return foo?.length ?: 0
  // return foo != null ? foo.length : 0
  ```

<br>

## Extention

```Kotlin
fun String.double(): String {
	return this + this
}

println("Hello".double()) // HelloHello
```

<br>

## Companion object

* 어떤 클래스의 모든 인스턴스가 공유하는 객체 만들 때.
* 자바에서 **static 변수** / 메서드 사용했을 때와 동일
  * 객체가 생성되기 전에 런타임시작하는 그 때 즉시 생성된다(Data영역에 할당).

<br>

## collection 함수

* *mutable과 immutable을 구분하여 지원한다*
* List: 기본적으로 immutable. <-> MutableList
* 이외에도 Set, Map, Array 등 존재

<br>

#### 코틀린에서 Array vs List

* Array에서는 Iterator를 내부적으로 지원하는데, List는 element가 불연속적인 메모리 공간으로 할당되어있다. Iterator는 구현에 의해 만들어진다.
* 배열은 크기가 정해져있고, List가 동적으로 할당 가능.(c++의 백터처럼 add, remove 가능)

<br>

## 표현식 vs 구문

* 표현식은 값을 반환한다.

  * 값을 (반환)만든다는 의미
  * 따라서, 대입 연산자 오른쪽에 명시할 수 있다.(값을 저장)

  `val foo = when { }`

* 구문은 무언가가 실행되도록 명령을 지시하는 문장을 의미

  * 값을 만들지는 않는다.

* when "표현식"에서는 항상 else를 필요로 한다.(값을 만들어야 하기 때문에)

<br>

## object

* 코틀린에서 싱글톤 객체를 사용하는 방법

<br>

## Inner class

* 내부(nested) 클래스 장점
  * 내부에서 외부 클래스 멤버 접근 가능
  * 서로 관련된 클래스를 논리적으로 묶어 표현해, 코드의 캡슐화 증가
  * 외부에선 내부에 접근 불가. 따라서 코드 복잡성 감소.
* inner vs nested
  * Kotlin에서는 nested가 default
  * inner를 쓰려면 명시를 해야함.
  * (TODO)차이점 명확하게 구분하기

<br>

## enum class

* 열거형(클래스처럼 보이게 하는 상수)
* 관련 있는 상수들을 모아 심볼릭한 명칭의 집합으로
* java에서의 static 변수 효과
* 이러한 상수 타입으로 어떠한 변수를 할당했을 때 그 변수를 수정함으로써 얻는 런타임에서의 에러를 방지할 수 있다.

<br>

## Time 시간 관련 API

* `Instant.now()` 한 점을 나타냄. 따라서 비교 가능
* `Duration.ofHours(2)` - 2시간  차이
  * 테스트에 유용하게 쓰일 수도
* `LocalDateTime` : 시간대 포함하지 않음.
  * `LocalDate`, `LocalTime` 도 존재
* `ZonedDateTime` : 시간대 개념 추가
  * Local에서 시간대면 추가되면 Zoned. 반대도 가능

<br><br>

#### Reference)

#### https://yangbox.tistory.com/24
