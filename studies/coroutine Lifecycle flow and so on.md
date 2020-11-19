- e.g) Global scope의 코루틴 (코루틴의 생명주기 == Application의 생명주기)

    ![](./Coroutine Image/6.png)

- e.g)코루틴 생명주기 == 수행하는 작업 범위

    ![](./Coroutine Image/7.png)
    
- coroutineScope builder 이용, 자신만의 스코프 선언.

    ![](./Coroutine Image/8.png)
    
- runBlocking(regular function) vs coroutineScope(suspending function)
    - coroutineScope는 자식 thread("Task from nested scope") 의 종료를 기다리는 동안 현재 thread("Task from coroutine launch")를 block하지 않음.
    - runBlocking은 대기중인 현재 thread를 block.

- coroutine 코드 분리(suspending function)

    ![](./Coroutine Image/9.png)

- suspending function에서 또다른 suspend를 실행한다면 복잡해지므로 주의해야한다.(call back을 여러번 수행하지 않아야 하는 것과 같은 맥락?)
