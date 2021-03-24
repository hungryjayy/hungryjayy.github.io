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



## JS TS

### Javascript vs TypeScript

* JS
  * 타입 시스템이 없는(정적) 동적 프로그래밍 언어. 따라서 런타임 에러가 많이 발생할 수 있다.
    * RTE: 프로그램에서 수행할 수 없는 동작을 시도할 때 발생
    * 컴파일 error: 컴파일 타임에 발생. 주로 문법적 오류
  * 비교적 유연하게 개발할 수 있는 환경 제공
* TS
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
  - mutation observer(?), promise가 여기로 들어감.
  - Promise의 핸들러는 항상 이 queue를 통과해야 한다.
    - 처리되지 못한 에러: microtask queue 빈 후 처리하지 못하는 error에 대해 `unhandledrejection` 발생.

- Task Queue(event queue)

  - JS 런타임환경에는 처리해야하는 Task를 임시저장하는 대기 큐가 존재. 그것이 Task queue == event queue.
  - Call stack이 비어졌을 때 queue에 먼저 들어온 task가 stack으로 들어간다.
  - 비동기로 호출되는 함수는 Task queue에 enqueue된다.
  - JS에서는 event에 의해 실행되는 함수(핸들러)들이 비동기로 실행된다.
  - 여기서 지워지는 타이밍은 stack에서 수행 후 pop 되고 나서.

- web API or 백그라운드

  - Settimeout에서 n초가 다 수행되기 전까지, event handler에서 해당 event가 발생하기 전까지 기다리는 곳
  - 이후 발생하거나 완료 되면 task queue로 보낸다.

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

- *c. f) 예시 2*

  ​	

  ``` javascript
  function aaa() {
    setTimeout(() => {
      console.log('d');
    }, 0); 
    console.log('c');
  }
  
  setTimeout(() => {
    console.log('a');
    aaa();
  }, 0);
  
  Promise.resolve().then(() => {
    aaa();
    console.log('b');
  });
  ```

  * 출력순서 예상해보기.  답: 밑의 closure 밑에

  

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

* 위의 예시 2 답 c b a c d d

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

* 간결하다

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



## JS + Node.js

### var, let, const

* var: function scope

* let: block scope,

* const: block scope, 상수 (재할당 불가)

* var는 함수 내부라면 블록 밖이라도 참조 가능 그러나,

  

#### 예-1) var hoisting

var는 함수 스코프

```javascript
function counter () {
  for(var i=0; i<10; i++) {
    console.log('i', i)
  }
}
counter()
console.log('after loop i is', i) 
// ReferenceError: i is not defined
```

#### 예-2) IIFE (immediately-invoked function expression)

​		*IIFE: 정의하자마자 즉시 실행함수, `()`로 씌워 만들수 있다*

```js
// IIFE를 사용하면
// i is not defined가 뜬다.
(function() {
  // var 변수는 여기까지 hoisting이 된다.
  for(var i=0; i<10; i++) {
    console.log('i', i)
  }
})()
console.log('after loop i is', i) 
// ReferenceError: i is not defined

//////////
// var i 전역변수화 됨 IIFE
(function() {
  for(i=0; i<10; i++) {
    console.log('i', i)
  }
})()
console.log('after loop i is', i) // after loop i is 10
```



### Node.js란? - Javascript가 서버에서 동작될 수 있도록 하는 그러한 환경(플랫폼)

: 크롬 V8 JavaScript 엔진으로 빌드된 JS 런타임기

#### 특징

* Nods.js 라이브러리 내의 api는 모두 비동기
* 단일 스레드.
* 이벤트 메커니즘을 통해 서버가 멈추지 않으며 실시간 데이터 app, SPA, IO 잦은 앱개발에 효율적
  * 여기서 I/O란? 파일 시스템 접근, 네트워크 요청과 같은 작업.
* 하나의 작업 자체가 오래걸리면 전체 성능 저하. (싱글스레드 이기 때문)



#### foreach() vs map()

* foreach
  * 값 반환 x
  * 각 요소에 대한 콜백을 수행. (현재 배열을 변경해서 반환). map보다 빠르다.
* map
  * 값 반환
  * 각 요소에서 함수를 호출하고, 결과로 새로운 배열을 만들어냄.

#### closure: 자신이 생성될 때의 환경을 기억함.



### Node 기능

#### Http 모듈

* 인터넷에서 데이터를 주고 받을 수 있는 프로토콜

#### Express

* package.json : 모듈 버전, 의존 패키지 관리(npm 이용)
* Routing: app.js로 routing 역할 수행
  * URI 및 특정 HTTP 요청메소드인 특정 엔드포인트에 대한 요청에 application이 응답하는 방법을 결정
* 앱 구동

#### Sequelize

* DB와 연동할 때 객체와 테이블을 매핑해주는 ORM
* 이걸 이용하면 객체의 메서드를 통해 쿼리를 조작함. sql 문법을 모르더라도.
  * create(), findOne(), findAll(), update(), destroy() 등

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
  * Stateless: 작업을 위한 (client의)상태정보를 따로 저장하지 않는다. 단순히 들어온 요청만을 처리 -> 불필요한 정보 관리 x
    * 만약 stateful하다면 서버가 클라이언트의 현재 상태를 저장해야한다. 따라서 클라이언트의 상태는 서버에 종속된다.
      * 이 경우의 로드밸런싱을 하는 경우에 서버가 클라이언트의 상태를 공유할수있는 redis같은 시스템이 필요.
    * Stateless하기 때문에 어느 서버가 처리하던 클라이언트의 요청은 동일하게 처리 가능.
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

함수를 side effect 없도록 선언하고 사용하는 선언형 프로그래밍.

가장 큰 두가지 특징: immutable data, first class citizen

e. g. map, filter 등이 함수형 프로그래밍 함수

* Immutable 
  * 변경 불가능
  * 일급 컬렉션과 비슷한 맥락. list에 하나를 추가하기 위해선 추가된 하나의 list를 새로 만들어 할당.
  * Persistent data structure
* 일급 객체
  * 변수나 데이터에 함수를 담을 수 있다.
  * 함수를 literal로 바로 정의할 수 있다.
    * 리턴값으로 전달 가능
    * 함수를 파라미터로 전달 가능

## 200313

### 디미터 법칙

* 객체가 자기 자신을 책임지는 자율적인 존재이다.
* 따라서 객체 내부 구조를 묻지 말고 무언가를 시켜라.

#### 디미터 법칙을 어긴 코드

``` kotlin
object.getChild().getContent().getItem().getTitle()
```

* 기차 충돌(train wreck) : 이와 같이 `getter` 가 줄줄이 이어진 코드
  * 이러한 설계는 객체들이 어떻게 연결되어있는지를 보여준다.
  * 객체 구조(연결)이 변경될 수 있으므로 프로그램은 불안정해진다.

#### 디미터 법칙이 하나의 .을 강제하는 규칙은 아니다.

```kotlin
IntStream.of(1, 15, 2)
  .filter(x -> x > 10)
  .distinct()
  .count();
```

* 이와 같은 코드가 기차 충돌을 초래하기 때문에 디미터 법칙을 위반한다고 생각할 것이다.
  * 하지만 `of`, `filter`, `distinct` 메서드는 모두 `IntStream` 이라는 동일한 클래스 인스턴스를 반환한다. 즉 이들은 `IntStream` 인스턴스를 또다른 `IntStream`인스턴스로 변환한다.
  * 따라서 이 코드는 디미터 법칙을 위반하지 않는다.

#### 디미터 법칙은 오직 결합도와 관련 된 것.

* 객체의 내부구조가 외부로 노출되는 경우에 해당됨.
* e. g) 이 관점에서 보면 racingCar 프로젝트에서 car의 위치들을 비교해 winner를 구하는 로직 또한 Cars가 아니라 Car에서 하는게 맞는 것.





### DTO와 엔티티를 분리하는 이유

* 만약 프론트에서 dto 객체의 구성이나 네이밍등의 변경사항이 있을경우 엔티티를 그대로 쓰면 DB 컬럼명까지 까지 수정해줘야하는 경우가 생길 수 있으니 분리한다.



### Domain, entity, VO

1. Domain

   * 내가 개발하고자하는 영역

   * e. g) 쇼핑몰을 개발한다고 할 때 아래와같은 그림, 네가지 모두가 도메인 객체 

     ![img](https://t1.daumcdn.net/cfile/tistory/2573BF335976884611)

   * Domain은 Entity와 VO로 나뉜다.

2. Entity

   * 식별자를 갖는다. (id)

   * DB 말고도 객체지향적인 개념으로도 쓰인다.

     * 다만 객체지향의 entity는 행위를 갖는다.
     * DB는 그저 값들을 들고있는 구조체에 불과.

   * 로직을 포함할 수 있다.

     

3. VO

   * 값 자체가 의미가 있다..? == 어느 상황에도 **동일하게 취급 되는 것**이 핵심
     * e. g. 1) 서비스에서 사용하는 색상클래스 RED가 `new Color(255,0,0);` 이고, 이 서비스 내부에서 red 색상을 사용하고자 할 때 `Color.RED` 를 사용하면 되는 것과 같은?
     * e. g. 2) 돈으로 따지면 `100원`은 어딜가도 `100원`. 
   * 식별자를 갖지 않는다.
   * 핵심 역할은 `equals()`와 `hashCode()`를 갖는 것.
   * 로직을 가질 수 있다.
   * read only(불변하다)

#### DTO

* Layer간 데이터 전송을 위함.
* Domain model 객체를 그대로 두고, 복사하여 다양한 presentation logic을 추가한 정도
* 가변적.



## 200316

### 원격 Prostudio에서 publish, 개발 nodejs로 consume

* bo 설정

  * 192.168.9.127:5672로 전달.

  * username, password 설정해주어야 함. guest guest 경우 원격으로 전송 불가.

    

1. pub/sub 로직 추가

   * pub: SO -> BO (pub) -> SO -> WAPL

2. docker로 nodejs쪽 container 열어주기(서버열어주기)

3. rabbitmq 실행하기

   * guest/guest가 admin
   * rabbitmqctl start_app

   1. 적당히 username / password와 같이 유저만들어주기
      * rabbitmqctl add_user username password
   2. virtual host로 만들어주기 (queue와 계정을 그룹핑) 
      * rabbitmqctl set_permissions -p / "username" ".*" ".*" ".*"



## 200317

* Docker trouble shooting
  
* docker-compose를 down 전에 변경한다면 `has active endpoints` 에러를 만나게된다..
  
* 시도해볼것 : pub sub을 다른 queue를 사용해 기다리도록 해보자..

  

### Comment 정리

* getter 이름 다르게 붙이는 것 고민해보기
* 서비스 자체를 하나의 싱글톤으로 생각하기

### 코테연습

* Vector는 memset 불가..
* C++ string
  * `string.erase(index부터, length만큼);`
  * `string.replace(index부터, length만큼, ""얘로 변경);`
  * `string.find(""얘를 찾아라, index부터);`
  * 이외에도 여러 버전으로 오버로딩되어있으니 라이브러리를 공부하기
* `isupper()`, `isdigit()`, `toupper()` 등 camel case가 적용이 안되어있으니 주의



## 200318

* TODO: health check 후 restapi log 살펴보기



### docker

* docker container inspect {container ID}
  * 헬스체크 로그?같은걸 볼수있음
* docker logs {container ID}
* docker-compose 실행시 `-d` <-- background로 실행
  * 없이 실행하면 실시간 로그를 확인 할 수 있다.

### npm command (== CLI commands (command line interface))

* init : package.json 생성
* install : 패키지 설치
* start: 패키지 시작
* update: 패키지 업데이트



## 200320

* Kotlin에서 Object를 하나의 싱글톤으로 사용

## 200321



### Kotlin let, also, run, apply, with

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



## 200322

* Docker CLI

  * 컨테이너 모두 삭제

    ​	docker rm `docker ps -a -q`

    

* TODO: DATA 전송 확인 불가..
* 연결은 되었으니 지속적으로 DATA를 주고받는 환경 test?
* 예시 코드살펴보기.

<<<<<<< HEAD
* 연결 성공.(async 코드 구조 문제였던 것으로 추정)
  * 그러나 rest -> PO에서 rest가 보낸 시점에 PO는 받지 않을 수 있음
  * 서버가 양쪽 다 온전히 켜져있는 상태에서 주고받는 것 볼 수 있을 듯.
* ![image-20210322170155058](/home/joowon/.config/Typora/typora-user-images/image-20210322170155058.png)
  * (send)PO -> rest, (recv)rest, (send)rest -> PO, (recv)PO 순으로 통신하면 잘 주고받음.




## 200322

#### 기술 스택

- Kotlin
  - 간결함: 보일러플레이트가 자바에 비해 많이 개선됨.
  - null-safety: `?.` , `!!` , `?:`
  - 회사에서 Node.js → Koltin으로 서버 이관 작업 중..

- Spring boot
  - starter를 통해 내부 디펜던시 관리.
  - 빈에 대한 자동설정이 적용되어 생성됨(Auto-configuration)
- Spring boot JPA
  - 실무에서 많이 사용.
  - JPA를 추상화
- Spring Data JDBC
  - DDD를 더 잘 쓰기 위해서 나왔다 하더라
  - Data JPA에서 불필요한 부분, 복잡한 부분을 덜어냈다고 하더라
  - 조금 Data JPA에서 간소화시킨 버전?
    - 스펙이 작다. 기능이 없다. 이런 관점은 아님
    - 가볍게 만들었다? 정도??
- MariaDB
  - 오픈소스이다.(무료)
  - Mysql보다 많은 기능 제공

#### 해당 기술을 대체 가능한 다른 기술과의 비교

- Java (vs Kotlin)
  - 어떤 점이 더 좋은지, 어떤 점이 더 나쁜지
    - 장점: 레거시로 인한 국내 높은 수요, 많은 레퍼런스
    - 단점: Kotlin에 비해 불편함.
  - 그럼 다른 기술에 비해 해당 기술을 선택해야 하는 이유는 무엇인지
    - 팀원 일부(준영)만 사용 가능, 코틀린의 간결함
- Spring (vs Spring Boot)
  - 어떤 점이 더 좋은지, 어떤 점이 더 나쁜지
    - 장점: 딱히?
    - 단점: Spring Boot의 다운그레이드?
  - 그럼 다른 기술에 비해 해당 기술을 선택해야 하는 이유는 무엇인지
    - Spring boot를 사용하지 않을 이유는 없어보임.
- Spring Boot JDBC (vs Spring Boot JPA)
  - 어떤 점이 더 좋은지, 어떤 점이 더 나쁜지
    - 장점: 간편하다.
    - 단점: 많은 곳에서 Spring boot JPA를 사용. 그런데 기존에서 JDBC로 옮겨가려면 annotation부터 많은 부분을 바꾸어야 하기에, 앞으로도 Spring Boot JPA가 많은 곳에서 사용 될 것으로 보인다.
  - 그럼 다른 기술에 비해 해당 기술을 선택해야 하는 이유는 무엇인지
    - 위의 단점의 이유
- Mysql
  - 어떤 점이 더 좋은지, 어떤 점이 더 나쁜지
    - 장점: 딱히 없어보임
    - 단점: 유료. Mysql에서의 몇몇 기능이 MariaDB에서는 무료 제공
  - 그럼 다른 기술에 비해 해당 기술을 선택해야 하는 이유는 무엇인지
    - 유료

### 협업 툴

- Slack
- Github
  - 사용하기 편하다
- Jira(Confluence)
  - 스펙이 큰 프로젝트에 어울린다고 생각
  - Gitlab을 사용하면 장점이 있다고 생각
  - 오버스펙이다
- Notion



## 200323

### DTO에 관한 고찰

DTO 위치에 대해 정해진건 없다. 다만 service 혹은 controller에서 사용하는 것은 맞는 듯..

- Controller에서 DTO를 Entity로 변환시켜 Service에게 파라미터로 보내기.

- Controller에서 DTO 자체를 Service에게 파라미터로 보내고, Service가 Entity화 시키기.

- Service가 Controller로 응답을 보낼 때, Entity를 반환하고 Controller가 이를 DTO로 변환하여 사용하기.

- Service가 Controller로 응답을 보낼 때, Entity를 DTO로 변환시켜 반환하기.

  ref ) https://xlffm3.github.io/spring%20&%20spring%20boot/DTOLayer/

  https://dbbymoon.tistory.com/4

  https://github.com/HomoEfficio/dev-tips/blob/master/DTO-DomainObject-Converter.md

* 작은 프로젝트일 수록 DTO가 필요없을 수 있다.
* 큰 프로젝트 일 수록 DTO가 힘을 발휘할 것 같다.

#### 나의 정리

* Service에서 Repo에 넘겨줄때
  * DTO to Entity(domain)
    * `fooEntity = FooEntity.of(fooDto);`  or `FooEntity.toEntity(fooDto);`
      * repository에 저장하는 entity는 request에 의존적이지 않고 entity 의존적이어야 한다.
    * service의 private 메서드에서 처리
    * 주로 빌더패턴(Kotlin에서는 딱히 불필요?)
* DB에 저장
  * fooEntity = fooRepository.save(fooEntity);
* 저장 결과 DTO로 반환
  * Entity(domain) to DTO
    * return FooDto.of(fooEntity);
    * converter에서 담당(responseDTO에서 구현한 converter를 서비스에 주입해서 처리)
      * view나 client쪽으로(바깥으로) 노출하는 작업은 해당 responseDTO에 의존적이어야 한다.
* 헷갈릴 때 위의 세 링크를 살펴보기

* Java에서 builder pattern
  * 인자가 많아도 어떤 인자에 맵핑되는지 확인 가능하기에 필요없어진다.

* DTO는 단순한 자료구조로만 사용하는 것이 좋다.
  * 이 관례상 DTO에서 SQL이 실행될 것이라고 생각하는 개발자는 없다.
  * DTO가 Repository를 의존하면 unit test를 작성하기 어려워진다.

### Inner class

* 내부(nested) 클래스 장점
  * 내부에서 외부 클래스 멤버 접근 가능
  * 서로 관련된 클래스를 논리적으로 묶어 표현해, 코드의 캡슐화 증가
  * 외부에선 내부에 접근 불가. 따라서 코드 복잡성 감소.
* inner vs nested
  * Kotlin에서는 nested가 default
  * 



## 200324

* server가 kafka, rabbitmq, PO 뭐든간에, client 쪽에서 어쨌건 이걸 사용할 수 있는 라이브러리를 만들어보자
* 일단은 nodejs 서버를 사용할 것 같다.



### docker

* 환경변수 설정

  * Docker-compose.yml에서 ${VARNAME} 의 표기를 하는 경우 YAML을 처리하는 동안에 compose에서 이 변수를 동적으로 읽는다.

  * 따라서 `image: ghost:${GHOST_VERSION}` 이와 같을 경우

  * CLI에서 `GHOST_VERSION=2 docker-compose up` 이와 같이 버전 조절 가능.

  * 또한, .env파일에 값을 저장하거나 CLI에서 설정하거나, YAML 안에서 `${GHOST_VERSION:-2}` 등의 방식으로 변수 설정 가능

  * ```bsh
    ${parameter:-word}
        파라미터가 세팅이 안되어있거나 null인 경우 word로 대체된다.
    ```

  * docker-compose의 container 부분에서 `${DB_PORT:-3306}:3306` 이와 같은 것은 포트 포워딩의 개념으로 추측. 

    * 외부포트 : 호스트 포트



* RabbitMQ
  * vhost : vhost설정을 통해 각 사용자마다 격리된 환경을 제공 받을 수 있다.
  * durable: exchange, queue의 durable 속성은 disk에 메시지를 저장할지 결정하는 boolean 형태의 option이다.





* Nodejs

  * 설계

    * 좋은 예: 자족적인 컴포넌트 기반으로 설계하라

      [![alt text](https://github.com/goldbergyoni/nodebestpractices/raw/master/assets/images/structurebycomponents.PNG)](https://github.com/goldbergyoni/nodebestpractices/blob/master/assets/images/structurebycomponents.PNG)

    * 나쁜 예: 기술적인 역할별로 모아라

      [![alt text](https://github.com/goldbergyoni/nodebestpractices/raw/master/assets/images/structurebyroles.PNG)](https://github.com/goldbergyoni/nodebestpractices/blob/master/assets/images/structurebyroles.PNG)

  * javascript에서 { 를 선언문과 같은 줄에서 시작해야 하는 이유 }
    * https://stackoverflow.com/questions/3641519/why-do-results-vary-based-on-curly-brace-placement
    * 