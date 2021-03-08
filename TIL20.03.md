## 200301

* Typora 적용
* git author 변경
  * git config --list
  * git config --global --user.name=hungryjay
* commit한 모두 author 변경하는 법
  * 바꾸고 싶은 commit으로 git rebase -i -p {commitID}
  * pick을 edit으로 변경
  * 전부  commit --amend --author="hungryjay <aaa@aaa.com>"



## 200302

*cf* ))

* 공인 아이피란? 
  * 인터넷상 서로다른 PC끼리 통신을 위해 필요한 아이피
  * 20.94.163.19

* 사설 아이피

  * 내부망 전용 아이피
  * 196.168.9.127(개발용)

  

### ubuntu pc에서 ssh열고 window에서 putty로 접속

* 업무용에서 개발용 ssh로 접속해 docker container 바라보는 방법
  1. 개발용에 ssh 환경 설정 https://jmoon.co.kr/183
     1. sudo ufw enable
     2. sudo ufw allow 22(ssh default)
     3. sudo ufw reload
  2. window에 putty 설정
     1. 192.168.9.127(ssh 열어놓은 개발용 pc) 접속 및 포트 22
     2. (선택)기타 putty 설정(폰트 등)
  3. 연결 완료

* 연결 완료 후 업무용에서 개발용 docker container로 접속하는 방법

  1. 업무 -> 개발 접근할 fileport, listen(http) port등 개발용에서 열어놓기(위의 ufw command 이용)

  2. 개발용에서 localhost:24000/promanagerobs에서 node 생성해 열어놓기

     1. application 또한 만들어놓기(?)

  3. 업무용 prostudio에서 설정해놓은 port로 접근해 application 생성

     

* 참고

  * localhost:14000/promanager
  * localhost:24000/promanagerops
  * localhost:34000/promanagerrte(아마도..)



## 200303

* DO (Data Object)

  * SO 입출력 전용 Data를 담아서 DTO 역할 수행하는 객체
  * 사용자가 Promanager에서 in/out에 사용되는 프로퍼티(Meta정보)를 정의해 사용, 이에 대한 여러 메서드 제공(e.g. getter setter)
  * excel 파일을 이용해 DO객체 생성 가능

* DOF (Data Object Factory) - DB용 or FILE용

  * (DB전용)
  * DataBase에 접근 DAO 역할

  * Query 작성 후 저장시 자동으로 쿼리에 해당하는 소스 생성
  * (FILE전용)
  * layered file생성, 읽을 때 사용 객체

  ---------

  ### Object flow editer



* BO (biz object)
  * 비즈니스 수행 재사용성 단위.
  * SO나 JO에서 레퍼런스 호출됨.
  * DO 호출 후 데이터 가공 등 수행
  * DOF를 사용하는 부분
* SO (service object)
  * 서비스 수행 관리 object.
  * BO flow 중심 애플리케이션
  * SO의 이름은 특히 더 대문자로 해줘야한다.
  * input output 필요
  * BO를 멤버 var로 설정해줘야한다.
  * 일반 메서드를 만ㄴ들지 말고 오버라이드 메서드를 생성해야한다.
  * generate DD를 하면 servicegroup.xml이 생성되고, 여기서 SO에 대한 정보가 저장된다.
* JO (job object)
  * batch 수행 때 task 정의



* URLconnection & HttpURLConnection

  * HttpURLconnection은 URLConnection을 구현한 클래스

  * 기본적으로 get 메서드

  * setRequestMethod()를 통해 메서드 변경 가능

  * `getResponseCode(), getResponseMessage()` => 응답 코드, 응답 메시지 리턴받을 수 있다.

    * Reference

      https://mytalkhome.tistory.com/855





## 200305~ 면접준비

# Language

## Kotlin

#### Kotlin vs Java

* 함수형 프로그래밍(함수가 일급 객체로서의 의미를 가짐)
  
  * 1급 객체
    * 변수나 데이터에 함수 할당 가능
    * 객체의 인자로 넘길 수 있음
    * 객체의 리턴값으로 리턴 가능
  
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

  * 어떤 클래스의 모든 인스턴스가 공유하는 객체 만들 때. e.g. singleton?
  * 자바에서 static 변수 / 메서드 사용했을 때와 동일
    * 객체 생성도 전에 런타임시작하는 그 때 즉시 생성된다.

* Smart casting

  * 개발자가 따로 type을 캐스팅해줄 필요 없이 컴파일러가 알아서 Type을 캐스팅해줌

* collection 함수

  * (*mutable과 immutable을 구분하여 지원한다는 점*)
  * List - remove, add, addAll, removeAt, removeIf 등
  * Set - `setOf<type>(items)` 
  * Map - Key, value쌍. `Pair(A, B) or A to B`와 같이 세팅

#### Map vs forEach

* forEach는 array 요소를 한번 순회한다.
* map() Array요소가 제공된 함수로 호출될 때 callback에서 적용되는 새 요소들로 새로운 array를 만든다.
* filter는 조건문으로 새로운 함수 반환

#### 표현식 vs 구문

* 표현식은 값을 반환한다.

  * == 값을 만든다
  * == 대입 연산자 오른쪽에 명시할 수 있다.(값을 저장)

  `val foo = when { }`

* 구문은 무언가가 실행되도록 명령을 지시하는 문장을 의미
  
  * == 값을 만들지는 않는다.
  
* when 표현식에서는 항상 else를 필요로 한다.(값을 만들어야 하기 때문에)

### 

## JS TS

### Javascript vs TypeScript

* JS
  * 타입 시스템이 없는(정적) 동적 프로그래밍 언어. 따라서 런타임 에러가 많이 발생할 수 있다.
    * RTE: 프로그램에서 수행할 수 없는 동작을 시도할 때 발생
    * 컴파일 error: 컴파일 타임에 발생. 주로 문법적 오류
  * 비교적 유연하게 개발할 수 있는 환경 제공
* TS는 
  * TS는 JS 기반으로 만들어졌다.
  * 정적 타입을 지원해서 컴파일 단계에서 오류를 포착 가능.
    * 타입을 명시함으로써 가독성 또한 높다.
  * 객체지향 언어로, 데이터 추상화에 중심을 두는 언어.

### event loop, 비동기 처리 등

#### Javascript

- 싱글스레드 기반, Event loop를 기반으로 하는 Node.js
- Javascript engine은 JS를 해석하고 실행하는 엔진

#### Javascript engine(Call stack, heap, task queue)

- Call stack

  - JS는 단 하나의 call stack 사용
  - 따라서 JS가 실행되는 방식은 Run to completion. 하나의 함수가 실행되면 다른 task는 수행될 수 없다.
  - 메서드 실행시 call stack에 새로운 stack frame 생성하고 return시 stack에서 해당 frame을 pop한다.

- Heap

  - 동적으로 생성된 객체(인스턴스)는 힙에 할당.

- Microtask queue

  - task queue보다 여기를 우선적으로 확인
  - mutation observer(?), promise가 여기로 들어감

- Task Queue(event queue)

  - JS 런타임환경에는 처리해야하는 Task를 임시저장하는 대기 큐가 존재. 그것이 Task queue == event queue.
  - Call stack이 비어졌을 때 queue에 먼저 들어온 task가 stack으로 들어간다.
  - 비동기로 호출되는 함수는 Task queue에 enqueue된다.
  - JS에서는 event에 의해 실행되는 함수(핸들러)들이 비동기로 실행된다.
  - 여기서 지워지는 타이밍은 stack에서 수행 후 pop 되고 나서.

- Event loop

  - 위의 작업들을 해주는게 event loop

    ```
    while(queue.waitForMessage()) {
    	queue.processNextMessage();
    }
    ```

    - 이러한 방식으로 이벤트 루프는 현재 실행중인 task가 없는지, task queue에 task가 있는지 반복적으로 확인

- queue 우선순위

  - micrortask queue -> animation frames -> task queue
  - 직접적인 작업들은 Web API에서 처리한다.
  - animation frame : 브라우저 랜더링 관련

- *c.f)* 예시

  - ![img](https://camo.githubusercontent.com/d635b008ba59f57449a924eac64883de31a814d56ed9791db6d5980c6312e1f0/68747470733a2f2f6c68352e676f6f676c6575736572636f6e74656e742e636f6d2f37567a686875684f5767576f62596174704a486c4665714261306b357254357178516e6a7658723545575863346a6f314e446c6168566276616733394b534b38434f56507a4d554833324857774672505762423366536d63334b5152754831336e6c5a626b576f44506c504a707064724b682d6d496a374f524f49646852305a6b6b51735a775977)
    - 실행결과 1 -> 3 -> 2
    - call stack에 test1 in, test 2 in하고 setTimeout메서드의 익명함수는 task queue로 들어간다. 이후 test 3 실행 후 모두 실행되어 각각의 stack frame들이 pop되어 stack이 비게 되면 task queue에 있던 test2의 익명method가 call stack으로 들어가 실행된다.

#### eventloop, 비동기 처리과정 Reference

http://asfirstalways.tistory.com/362

http://sculove.github.io/blog/2018/01/18/javascriptflow/

*두 링크 정독 여러번하기

### Hoisting

- 사전적 의미: 끌어올리기. 선언되는 모든 변수는 호이스트된다.(끌어올려진다)
- 따라서 변수의 정의가 그 범위에 따라 선언과 할당으로 분리 되는것.
- 함수 선언이 함수를 실행하는 부분보다 뒤에 있어도 JS엔진은 함수 선언을 끌어올리기 때문에 가능하다.
- 다만 변수의 값은 끌어올리지 않으므로 선언보다 앞에서 출력하면 `undefined` 된다.

### Closure

* 반환된 내부함수(생명주기가 끝난)가 자신이 선언되었을 때의 환경인 스코프를 기억해 선언되었을 때의 환경 밖에서 접근할 수 있다.

* ```javascript
  var name = `Warning`;
  function outer() {
    var name = `closure`;
    return function inner() {
      console.log(name);
    };
  }
  
  var callFunc = outer();
  callFunc();
  // console> closure
  ```

  * 여기서 outer 내부의 name 변수를 자유변수라고 한다.

### this

* JS의 모든 함수는 실행될 때마다 함수 내부에 this 객체가 추가됨.

1. 객체의 메서드를 호출할 때 `A.B` 에서 this는 A 객체를 가리킨다.
2. 함수를 호출할 때 this는 전역 객체에 바인딩 된다.
3. 생성자 함수를 통해 객체를 생성할 때 -> 일반적인 생성자와 같게 생각하면 됨
   1. 내부에서 객체 자체를 this
4. apply, bind, call
   1. apply: func.apply(this, [param1, param2])
   2. bind: func {}.bind(this, param1, param2)
   3. call: func.call(this, param1, param2)

### arrow function

*  간결하다

  ``` javascript
  materials.map(function(material) { 
    return material.length; 
  }); // [8, 6, 7, 9]
  
  materials.map((material) => {
    return material.length;
  }); // [8, 6, 7, 9]
  
  materials.map(({length}) => length); // [8, 6, 7, 9]
  ```

* 상위 스코프

  ```javascript
  function Person(){
    this.age = 0;
  
    setInterval(() => {
      this.age++; // |this|는 person 객체를 참조
    }, 1000);
  }
  
  var p = new Person();
  ```

# Backend

### 일급 컬렉션?

* ```java
  public class GameRanking {
  
      private Map<String, String> ranks;
  
      public GameRanking(Map<String, String> ranks) {
          this.ranks = ranks;
      }
  }
  ```

* 컬렉션을 wrapping하면서 그 외 다른 멤버변수가 없는 상태

  * 비즈니스에 종속적인 자료구조
  * 불변성이 필요하다면 Collection 내부 각각들의 불변성(필요하다면 불변이 아니어도 됨)
  * 상태, 행위를 이곳에서 관리

* Car라는 클래스 객체 3개를 모두 관리해야할 때 Cars 쓰는것처럼
  
  * cars 하나의 인스턴스로 비즈니스로직 관리 가능



# dev common sense

### Rest api

: API를 설계 할 때 자원을 나타내는 URI가 있고, http method를 통해 자원을 어떻게 처리할 지 설계하는 방식의 아키텍처

* 구성
  * 자원(resource) - URI / 행위 - HTTP method / 표현 representations
* 특징
  * URI로 지정된 리소스에 대한 조작을 한정적인 Interface를 통해 수행하는 아키텍처
  * Stateless: 작업을 위한 상태정보를 따로 저장하지 않는다. 단순히 들어온 요청만을 처리 -> 불필요한 정보 관리 x
  * client-server 구조: 서버는 API를 제공, 클라이언트는 세션이나 로그인 정보를 관리. 각각의 역할이 구분되고 의존성이 줄어들고 확장성은 높아진다.
* 장점
  * 원하는 타입으로 데이터를 주고받을 수 있다.
  * http method 타입과 uri만 읽어도 해당 인터페이스가 어떠한 기능과 연결되는지 파악이 용이하다
  * client-server구조. api로 들어오는 요청을 처리해 요청한대로 보내주기만 하면 된다. 각자의 역할이 명확히 분리 
* 단점
  * 복잡한 비즈니스에서 http method의 한계로 인해 모든 경우를 cover할 수 없을 것 같다.
  * 공식화 된 Rest API 표준이 존재하지 않다는 점도 아쉬운 점 중 하나.

### TDD

test 주도형 개발. 기능 추가 전 테스트 먼저 작성.

*cf) given, when, then*

장점: 

* 리팩토링에 용이. 크기가 커진 함수를 여러 함수로 나누는 과정에서 테스트 코드를 통해 계속 확인을 해가며 리팩토링을 해 중심을 잡을 수 있다.
* 새로운 기능을 추가했을 때 해당 기능에 대한 테스트는 물론, 기존의 테스트들도 다 잘 돌아가는지 확인해 새로운 기능에 대해 신뢰를 할 수있다.

단점:

* 생산성이 줄어든다.
* 모든 기능에 대해 100% 테스트코드를 작성할 수 없는 상황이 발생할 수 있다. 이 때 전략패턴과 같은 기법을 통해 개선할 수 있지만, 테스트를 위해 본 메서드를 변경하는 것 자체가 생산성을 저하시킨다.



## 함수형 프로그래밍

가장 큰 두가지 특징: immutable data, first class citizen

e. g. map, filter 등이 함수형 프로그래밍 함수

* Immutable 
  * 변경 불가능
  * 일급 컬렉션과 비슷한 맥락. list에 하나를 추가하기 위해선 추가된 하나의 list를 새로 만들어 할당.
  * Persistent data structure
* 일급 객체
  * 변수나 데이터에 함수를 담을 수 있다.
  * 함수를 literal로 바로 정의할 수 있다.