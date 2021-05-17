# Kotlin 특징(vs java)

## 함수형 프로그래밍(함수가 일급 객체로서의 의미를 가짐)

* 함수를 side effect 없도록 선언하고 사용하는 선언형 프로그래밍.
  * 따라서 forEach vs map : map이 함수형에 가까움

* 가장 큰 두가지 특징
  1. Immutable 
     * 변경 불가능
     * 일급 컬렉션과 비슷한 맥락. list에 하나를 추가하기 위해선 추가된 하나의 list를 새로 만들어 할당.
     * Persistent data structure
  2. 일급 객체
     * 변수나 데이터에 함수를 담을 수 있다.
     * 함수를 literal로 바로 정의할 수 있다.
       * 리턴값으로 전달 가능
       * 함수를 파라미터로 전달 가능



## Java와 동일한 타입안정성 + 타입 추론(Inference) 제공.(Smartcasting)

* `val name = "abced"` <- 이 경우 알아서 String으로 인식



## Null 안정성 제공

* `var foo: String? = "Hello"` 물음표는 nullable을 의미

* 위의 경우 `foo?.bar()`와 같이 쓰면 null이 아닐때 bar() 호출, null일 때 null 반환

* Not-Null Assertion(!!)

* Elvis operator `?:`

  * ```kotlin
    val foo: String? = getString()
    return foo?.length ?: 0
    ```

  * 위의 경우 null 아닐 때 length, null일 때 0 반환



## Delegation pattern(상속 대안)

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



## Extention

```Kotlin
// StringExt.kt
fun String.double(): String() {
	return this + this
}
```

* 이 때 `"Hello".double()` 이처럼 사용 가능.



## Companion 객체

* 어떤 클래스의 모든 인스턴스가 공유하는 객체 만들 때.
* 자바에서 static 변수 / 메서드 사용했을 때와 동일
  * 객체 생성도 전에 런타임시작하는 그 때 즉시 생성된다.



## collection 함수

* (*mutable과 immutable을 구분하여 지원한다는 점*)
* List - remove, add, addAll, removeAt, removeIf 등
* Set - `setOf<type>(items)` 
* Map - Key, value쌍. `Pair(A, B) or A to B`와 같이 세팅



## 표현식 vs 구문

* 표현식은 값을 반환한다.

  * 따라서, 값을 (반환)만든다는 의미
  * 따라서, 대입 연산자 오른쪽에 명시할 수 있다.(값을 저장)

  `val foo = when { }`

* 구문은 무언가가 실행되도록 명령을 지시하는 문장을 의미

  * == 값을 만들지는 않는다.

* when 표현식에서는 항상 else를 필요로 한다.(값을 만들어야 하기 때문에)



## object

* 코틀린에서 싱글톤을 사용하는 법



## Inner class

* 내부(nested) 클래스 장점
  * 내부에서 외부 클래스 멤버 접근 가능
  * 서로 관련된 클래스를 논리적으로 묶어 표현해, 코드의 캡슐화 증가
  * 외부에선 내부에 접근 불가. 따라서 코드 복잡성 감소.
* inner vs nested
  * Kotlin에서는 nested가 default
  * inner를 쓰려면 명시를 해야함.
  * (TODO)차이점 명확하게 구분하기



## enum class

* 열거형(클래스처럼 보이게 하는 상수)
* 관련 있는 상수들을 모아 심볼릭한 명칭의 집합으로
* java에서의 static 변수 효과
* 이러한 상수 타입으로 어떠한 변수를 할당했을 때 그 변수를 수정함으로써 얻는 런타임에서의 에러를 방지할 수 있다.



## Time 시간 관련 API

* `Instant.now()` 한 점을 나타냄. 따라서 비교 가능
* `Duration.ofHours(2)` - 2시간  차이
  * 테스트에 유용하게 쓰일 수도
* `LocalDateTime` : 시간대 포함하지 않음.
  * `LocalDate`, `LocalTime` 도 존재
* `ZonedDateTime` : 시간대 개념 추가
  * Local에서 시간대면 추가되면 Zoned. 반대도 가능



#### Reference)

#### https://yangbox.tistory.com/24
