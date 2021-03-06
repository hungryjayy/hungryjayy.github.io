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
* SO (service object)
  * 서비스 수행 관리 object.
  * BO flow 중심 애플리케이션
* JO (job object)
  * batch 수행 때 task 정의





## 200305

### Language

#### Javascript vs TypeScript

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

#### Kotlin vs Java

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

* Map vs forEach
  * forEach는 array 요소를 한번 순회한다.
  * map() Array요소가 제공된 함수로 호출될 때 callback에서 적용되는 새 요소들로 새로운 array를 만든다.
  * filter는 조건문으로 새로운 함수 반환



### Backend

* 일급 컬렉션?

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



