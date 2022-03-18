---
layout: post

title: Coroutine

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [kotlin, coroutine]

featuredImage: 

img: 

categories: [Study, Kotlin]

date: '2021-02-01'

extensions:

  preset: gfm


---

<br>

## 특징

- Task 단위가 Object이다. (= 경량 thread이다.)
- 메모리 heap에 적재(multi thread의 경우 stack에 적재)하기 때문에 교체시 object만 교체하고, 그 object는 heap을 공유한다.
- 이러한 과정이 단일 Thread위에서 발생. 따라서, OS 레벨의 context switch 발생하지 않음
- No context switch 장점을 살리기 위해 단일 thread 위에서 여러 object 실행하는 것이 좋다.
- 루틴 실행과 종료 시기를 개발자가 정한다.
- Java의 Future / JS의 Promise와 Generator / Kotlin의 Defered



## Cancellation and Timeouts

- `job.cancel()`을 통해 진행되던 coroutine이 취소될 수 있다.
    - coroutine의 모든 suspend function은 취소 가능. `cancellationException`을 throw
    - 부모 scope에서 취소 → 자식 scope에서도 취소.
    - GlobalScope로 생성된 coroutine은 자신이 실행된 스코프가 취소되어도 영향을 받지 않음.

![](https://hungryjayy.github.io/assets/img/Kotlin/1.png)

- withTimeout() { ... }` → timeout거는 용도
- 위에서 delay(500L) : suspend 기능. 취소가 가능한 상태.



## Composing Suspending functions

- 순차적 처리

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bf2eaf83-aaed-4858-a2c5-47e79a125e3b/Untitled.png](https://hungryjayy.github.io/assets/img/Kotlin/2.png)

- async 처리(위 방식보다 두배 빠름)
    - async를 통해 Defferd를 반환받음.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7675e7de-a663-43de-b081-5fec8bee6e2a/Untitled.png](https://hungryjayy.github.io/assets/img/Kotlin/3.png)

- 두 xxxxAsync()는 suspend가 아님에도 Defered<int>를 반환받는다. 즉, 외부에서 사용될 때 Async로 사용

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/07e87060-20ae-4a88-a807-1b4afb1aef73/Untitled.png](https://hungryjayy.github.io/assets/img/Kotlin/4.png)

- 바로 위의 async를 사용한 예제
    - 아래와 같이 동시성을 부여하여 사용한다면 외부에서 사용하다가 예외 발생시 범위 내의 모든 코루틴 취소 가능해 보다 안정적.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/479acd71-1ca6-4a63-9a87-ba545b3e1a26/Untitled.png](https://hungryjayy.github.io/assets/img/Kotlin/5.png)

<br>

## Coroutine Context and Dispatchers

- launch { ... } 에 매개변수 없는 경우: 실행중인 CoroutineScope에서 context(+Dispatcher) 상속받음.
    - Dispatchers.Default: 코루틴이 Globalscope에서 실행될 경우에 사용.
    - Dispatchers.Unconfined: 호출한 thread에서 코루틴 시작. 중간에 suspend function을 호출한다면 해당 func을 마치고 코루틴이 결된 후 다시 시작. 결국 공유 데이터를 update하지 않는 경우에 사용
        - → 따라서 Unconfined는 일반적인 경우에 사용하지 않음.
    - Dispatchers.NewSingleThreadContext: 새로운 스레드 생성. 더이상 사용 안할시 close().
        - .use()와 함께 쓰면 필요 없을 때 알아서 지워줌.
- 디버깅을 위해 naming.
    - var v1 = async(CoroutineName("v1coroutine")){ ... } → 로깅시 유용
- job: 수명주기를 직접 관리 가능
    - job을 print찍으면 현재의 job 상태 및 id?를 알 수 있다.
        - isActive는 coroutineContext[Job]?.isActive == true 를 간략화한 것.
    - join을 통해 해당 job 완료를 기다릴 수 있다.
        - var job1 : Job =  launch { ... }
        - job1.join()
    - start - 동작상태 반환(Boolean)
- Job별로 컨트롤, 통합 컨트롤 가능
    - Job별로 별도 control하는 것이 필요한 경우 val job = CoroutineScope() 형태
    - 여러 코드 반영하여 컨트롤
        - var job = Job()
        - Coroutine(Scope(Dispatchers.Default + job).launch{ ... }
- withContext: coroutine의 context 전환



## Asynchronous Flow

자세한 예제 ref) [https://charko.tistory.com/17?category=887416](https://charko.tistory.com/17?category=887416) 

- 비동기로 처리 된 값이나 리스트 리턴하는 경우.
    - List<int> 처럼 list로 묶어서
    - Sequence(비동기 X)
        - 이 경우 return되는 것은 seq의 주소값. 해당 메소드에서 yield()로 발생시켜 return
    - suspending function.
    - flows: sequence의 비동기형 (emit()을 통해 return)

- Flows are cold: sequence와 비슷한 stream. flow 내부의 builder가 flow가 쌓이기 전에 실행하지 않음.
- Flow cancellation
    - flow는 별도의 취소지점 없음.
    - 여기서는 delay(suspend기능)이 있을 때 취소가 됨.
    

### 자세한 문법적 부분은 필요할때 링크 참조

<br><br>

#### Reference)

[https://kotlinlang.org/docs/reference/coroutines/basics.html](https://kotlinlang.org/docs/reference/coroutines/basics.html)

[https://aaronryu.github.io/2019/05/27/coroutine-and-thread/](https://aaronryu.github.io/2019/05/27/coroutine-and-thread/)

[https://www.letmecompile.com/kotlin-coroutine-vs-javascript-async-comparison/](https://www.letmecompile.com/kotlin-coroutine-vs-javascript-async-comparison/)