# Spring MVC vs Webflux

### 전제 개념
[Sync, Async, blocking, non-blocking 대한 개념](./syncAsyncblock.md)


## Spring MVC
![](./images/MVC.png)

* Sync 해당한다.
* 기본적으로 client로 부터 요청이 들어오면 queue를 통한다.
* Thread pool이 수용할 만큼만 처리하고 나머지는 queue에서 대기한다.
* Resource를 줄이기 위해 Thread는 성능에 맞게 최대치를 정해놓고 효율적으로 사용한다
* 그럼에도 불구하고, **thread pool size를 계속 초과하게 된다면 thread pool hell이 발생한다. → 평소의 배가 되는 delay 발생**
