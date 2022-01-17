# 자바의정석 - 09장 java.lang 패키지와 유용한 클래스

: 자바 프로그래밍에 **가장 기본이 되는 클래스들**이 모여있는 **패키지**. 별도로 import하지 않아도 사용 가능하다. (e.g. String, System, ..)

<br>

## Object

: 모든 클래스의 최고 조상 클래스

<br>

* `equals`: 주소값으로 객체 비교. Object는 최고 조상 클래스이기 때문에 다른 class를 정의할 때 `equals`를 오버라이딩해 사용 가능

  * e.g. `String` class에서 문자열 내용만 같다면 같도록 **오버라이딩**

* `hashCode()`: 해싱 기법에 사용되는 해시함수를 구현한 것. `equals()` 와 같은 결과를 가져가야 하기 때문에 이것 또한 **오버라이딩**

  * 객체를 출력해보면 **"클래스@해시코드"**의 형태로 출력

* `finalize()`: GC에 의해 소멸될 때 자동적으로 호출된다.

* `notify()`, `notifyAll()` : 객체 자신을 사용하려고 기다리는 쓰레드를 하나만 or 모두 깨운다.

* `wait()`: 다른 쓰레드가 noti하기까지 현재 쓰레드를 무한히 or 지정된시간(timeout)동안 기다리게 한다.

* `clone()`: 인스턴스 값 복제이기 때문에 **참조타입**의 인스턴스 변수가 있다면 복제한 인스턴스 변경이 **원래 인스턴스에 영향**을 줄 수 있다. 해당 클래스가 `Clonable()` 인터페이스를 구현해야 사용가능.

  * 공변 반환타입을 사용하면 자식 타입으로 형변환 가능

* **얕은 복사 깊은 복사**: 얕은 복사는 **값만 복제**해 객체는 공유하는 것, 깊은 복사는 **객체까지 복제**.

  ```java
  public Circle deepCopy() {
    Object obj = null;
    
    try {
      obj = super.clone();
    } catch () {}
    
    Circle c = (Circle)obj; // 값만 복제, Point 객체는 공유
    c.p = new Point(this.p.x, this.p.y); // Point 인스턴스 새롭게 할당
  }
  ```

* `getClass()`: 클래스 객체 getter. -> `class com.company.Foo` 객체 얻어온다. 

  * 클래스당 하나만 존재

  * 클래스로더에 의해 메모리에 올라갈 때 객체가 자동으로 생성

    ```java
    // 세가지 방법
    Class cObj = new Card().getClass();
    Class cObj = Card.class;
    Class cObj = Class.forName("Card");

<br>

## String

* Immutable class. +와 같은 연산을 수행하면 기존걸 변경하는 것이 아니라 새로운 String 인스턴스를 생성한다.

* 문자열 리터럴: 컴파일 시 클래스 파일에 저장된다.

  ```java
  String s = "abc"; // 문자열 리터럴 "abc"의 주소를 s 변수에 할당
  String s2 = new String("abc"); // 새로운 String 인스턴스 생성
  ```

* `equals()`: Object의 해당 함수를 오버라이딩. (==와는 다르다)

* `join()`, `concat()`, `compareTo()`, `endsWith()`, `equalsIgnoreCase()`, `indexOf()`, `trim()` 등

* `getBytes()`: 인코딩

<br>

## StringBuffer, StringBuilder

* immutable한 String과 다르게 미리 buffer 크기를 설정해놓고 그 크기 내에서는 변경이 가능하다.
* 버퍼 크기를 초과하면 새롭게 할당하는 방식
* `equals()` 오버라이딩 X, `toString()` 오버라이딩 O(String으로 변환)

<br>

#### StringBuilder

: StringBuffer는 Thread safe하도록 동기화되어 있는데, 성능을 떨어트릴 수 있다. StringBuilder는 동기화를 제거한 버전이다. 멀티 스레드 환경에서는 상황에 맞게 개발자가 동기화를 해줘야한다.

<br>

### Math

* `round()` : 첫째자리에서 반올림해서 Long으로 돌려준다.
* `~~Exact()` : 오버플로우 발생 시 `ArithmeticException` 발생

<br>

### Wrapper 클래스

* 기본형을 객체로 변환해주는 클래스. 
* Integer, Boolean, Character, Byte, Short, Long, Float, Double 등
  * 숫자 관련 wrapper들은 조상으로 Number를 갖는다.
* `Integer.parseInt()`, `Boolean.parseBoolean()` 등을 통해 문자열 -> 기본형으로 변환
* `Integer.valueOf()`, `Byte.valueOf()` 등을 통해 문자열 -> 래퍼 클래스로 변환

<br>

## 그 외 유용한 클래스

### Objects

* 기본적으로 모두 static 메소드

* `isNull` vs `requireNonNull` 아래의 두 문장은 같은 일을 한다.

  ```java
  if(Objects.isNull(obj)) throw NullPointException("Must not be null");
  
  // requireNonNull로 간단히 처리 가능 
  Objects.requireNonNull(obj, "Must not be null");
  ```

* `Objects.equals()` -> 널체크까지 처리.

  * 기존: `if(a != null && a.equals(b))`

* `deepEquals()`: 깊은 비교(다차원)

* 이외의 다양한 오버라이딩

<br>

### 기타

* Random

  * `Math.Random()` == `new Random().nextDouble()`

  * Random으로 사용하면 seed값 설정 가능. 같은 seed로 난수생성시 항상 같다.

* Regex: 정규표현식 관련

* Scanner: 화면, 파일, 문자열과 같은 입력 소스로부터 문자 데이터를 읽어오는 것

  ```java
  Scanner s = new Scanner(System.in);
  s.nextBoolean();
  s.nextLine();
  // 등등 여러가지 next~메소드 존재
  ```

* StringTokenizer: 구분자로 파싱해 토큰들을 얻어냄

* BigInteger: Int 배열을 사용해 long보다 큰 값을 다룰 수 있도록

<br>

<br><br>

#### Reference) 자바의 정석 3판