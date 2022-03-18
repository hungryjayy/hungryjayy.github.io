---
layout: post

title: 임계영역(Critical section)

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [os, 운영체제]

featuredImage: 

img: 

categories: [Study, OS]

date: '2021-03-12'

extensions:

  preset: gfm

---

: **멀티 스레드 환경**에서 고려해봐야 할 영역. **동일한 자원**에 접근할 때 해당 영역을 임계영역이라고 함.

<br>

### 임계영역 문제

* 다른 스레드에서 사용할 **공유자원을 변경**해 **의도치 않은 사이드이펙트**를 얻게 되는 경우
* 임계영역을 공유해 **효율을 올리며(동시성)**, **부작용을 막아** 적절히 함께 사용할 수 있도록 프로토콜 설계

<br>

## 임계영역문제를 해결하기 위한 조건

1. Mutual Exclusion(상호 배제)
   * 한 프로세스가 임계영역 사용중이라면 **다른 프로세스는 들어올 수 없음**
   * **Dead lock**을 발생시키는 조건 중 하나
2. Progress(진행)
   * 임계영역에서 **실행중인 프로세스가 없을 때** 별도의 동작 없는 프로세스들이 **진입 후보**로 참여될 수 있음
3. Bounded waiting(한정적 대기)
   * 대기에는 한정이 있어야 함. 프로세스가 해당 **임계영역에 들어갈 수 있는 횟수 제한**(limit)을 두어, Starvation 방지

<br>

## 임계영역문제 해결책 - Spin Lock, Mutex, Semaphore

<br>

#### Locking 메커니즘: 임계영역에 들어가는 프로세스는 Lock을 획득, 나올 때 Lock 방출

<br>

### Spin Lock(스핀 락)

: "조금만 기다리면 바로 쓸 수 있는데 굳이 context switch 해야하나??" 할 때 사용

* Locking 메커니즘 사용

* **Busy waiting** : 진입 전까지 계속 루프를 돌면서 확인. polling 방식과 유사

* context switch 하지않음 : **짧은 시간 안에 진입할 수 있는 경우** context switch 비용이 절감되지만, 반대로 말하면 다른 스레드를 실행 불가(동시성 저하)

* 하드웨어 기반으로 해결. 소프트웨어 알고리즘은 느려서 사용하지 않는다고 함.

  * **피터슨 알고리즘** : **flag**(누가 임계영역 들어갈지), **turn**(누가 임계영역 들어갈 차례인지)이라는 변수를 사용. 

  * 인터럽트를 disable -> enable하는 방법

    ```c++
    void func0() {
      flag[0] = true;
      turn = 1;
      while (flag[1] == true && turn == 1) {} // busy waiting
    
      flag[0] = false;
    }
    
    void func1() {
      flag[1] = true;
      turn = 0;
      while (flag[0] == true && turn == 0) {} // busy waiting
    
      flag[1] = false;
    }
    ```

<br>

### Mutex(상호 배제)

* Locking 메커니즘 사용
* 스핀 락과의 비교
  * 공통점: 임계영역에 하나의 스레드만 접근 가능(Lock을 하나의 스레드만 가질 수 있음)
  * 차이점: Busy waiting이 아니라 **sleep** 상태로 들어가 **context switch**하고 다른 태스크를 수행하다가 **wakeup** 되는 방식
* 0, 1만을 가진 이진(binary) 세마포어와 유사
  * Lock을 가진 스레드가 **반드시 나갈 때 그 락을 해제**해야 함. -> 이진 세마포어와의 차이점.

<br>

### Semaphore

* Counting Semaphore 매커니즘.
  
  * 임계영역(자원)에 접근할 수 있는 스레드 개수를 정해놓음. 방출 시 세마포 증가, 진입 시 감소
  
* 이진 세마포어와 뮤텍스의 **차이점**: 뮤텍스는 나갈 때 나가는 스레드가 lock을 해제해야 하는데, 세마포어에서는 lock을 걸지 않은 스레드가 `signal()`을 이용해 lock 해제 가능.
  
* **단점**이 발생하는 경우
  * busy waiting: 초기의 세마포는 임계영역에 진입해야 하는 프로세스는 진입 코드를 계속 반복 수행해 CPU 시간 낭비했었음
  * 이러한 단점 해결을 위해 진입 실패한 **스레드 block**시킨 후, 임계영역 진입이 가능할 때 깨워주는 형태
  

<br>

#### DeadLock

: 각 스레드들이 서로가 점유하고 있는 자원들을 얻어야 하는 상황일 때 무한정 기다리게 되는 문제

<br>

#### Reference)

https://brownbears.tistory.com/45

https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/OS#%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-%EB%8F%99%EA%B8%B0%ED%99%94

https://github.com/gyoogle/tech-interview-for-developer/blob/master/Computer%20Science/Operating%20System/DeadLock.md

https://gameproyyj.tistory.com/153

https://thinkpro.tistory.com/124

https://www.crocus.co.kr/1371