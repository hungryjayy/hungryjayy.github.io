# Spring MVC vs Webflux


## Spring MVC
!<img src = "./images/mvc.png" width = "50%">

* Sync 해당한다.
* 기본적으로 client로 부터 요청이 들어오면 queue를 통한다.
* Thread pool이 수용할 만큼만 처리하고 나머지는 queue에서 대기한다.
* Resource를 줄이기 위해 Thread는 성능에 맞게 최대치를 정해놓고 효율적으로 사용한다
* 그럼에도 불구하고, **thread pool size를 계속 초과하게 된다면 thread pool hell이 발생한다. → 평소의 배가 되는 delay 발생**

<br><br>

## Spring Webflux
![](./images/webflux.png)

## 특징
* client req 발생시 event loop을 통해 작업 처리. (서버의 코어 갯수 == thread 갯수). 따라서 적은 수의 thread로 동시성을 다룬다. concurrent user가 1000명 이상일 때 webflux를 사용하면 좋다.
* Non blocking을 지원하며 blocking이 생길 경우 성능이 저하되어 사용 의미가 없어진다.
- thread를 줄였는데 blocking이 걸리는 API에 요청이 몰린다면 결국 I/O작업이 끝날 때까지 기다려야 하기 때문.
* [reactive Programming](./reactive.md)이 가능해진다. (event driven) 변화에 기민하다.

 _c.f) Event-Driven Programming: 프로그램 실행 흐름이 event(e.g: 마우스 클릭, 키 누르기 등)에 의해 결정되는 프로그래밍 패러다임. 적합한 event handler를 이용해 처리. GUI(graphical user interface) 발전에 따라 event-driven 더욱 많이 쓰임._



## 문제

그러나, DB connection을 non-blocking으로 지원하는 라이브러리는 아직 잘 사용되지 않고 있다. R2DBC, jasync sql 등. MongoDB, Redis 등 NoSQL은 지원중.
또한, Event 기반이기에 작업이 엉겨붙어(이리갔다 저리갔다) 처리되기 때문에 Tracking이 다소 복잡할 수 있다.



#### Reference)

#### https://velog.io/@dyllis/Spring-MVC-vs-WebFlux