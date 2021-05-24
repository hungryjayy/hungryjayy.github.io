# 임계영역(Critical section)

: **멀티 스레딩 환경**(아마 트랜잭션처리되지 않는 DB에서도?)에서 고려해봐야 할 영역. 동일한 자원에 접근할 때 해당 영역을 임계영역이라고 함.



## 임계영역 문제

: 임계영역을 공유해 효율을 올리며, 부작용을 막아 적절히 함께 사용할 수 있도록 프로토콜 설계



## 임계영역문제를 해결하기 위한 조건

1. Mutual Exclusion(상호 배제)
   * 한 프로세스가 임계영역 사용중이라면 다른 프로세스는 들어올 수 없음
2. Progress(진행)
   * 임계영역에서 실행중인 프로세스가 없을 때 별도의 동작 없는 프로세스들이 진입 후보로 참여될 수 있음
3. Bounded waiting(한정 대기)
   * 대기에는 한정이 있어야 함. 만약 한 프로세스가 임계영역에 들어오기로 결정되었다면, 다른 프로세스들은 해당 임계영역에 들어갈 수 있는 횟수 제한(limit)이 있어야 함.



## 임계영역문제 해결책 - Lock, Mutex, Semaphore

### Lock

* 임계영역에 들어가는 프로세스는 Lock을 획득, 나올 때 Lock 방출. -> 동시 접근 방지

* 하드웨어 기반으로 해결. 소프트웨어 알고리즘은 느려서 사용하지 않음.

  * 피터슨 알고리즘 : flag(누가 임계영역 들어갈지), turn(누가 임계영역 들어갈 차례인지)이라는 변수를 사용. 

  * 인터럽트를 disable -> enable하는 방법

    ```c
    int cnt;
    bool flag[2] = { false, false };
    int turn = 0;
     
    void func0() {
        for (int i = 0; i < 10000; i++) {
            flag[0] = true;
            turn = 1;
            while (flag[1] == true && turn == 1) {}
     
            cnt++;
            printf("cnt1 :: %d\n", cnt);
     
            flag[0] = false;
        }
    }
    
    void func1() {
        for (int i = 0; i < 10000; i++) {
            flag[1] = true;
            turn = 0;
            while (flag[0] == true && turn == 0) {}
     
            cnt++;
            printf("cnt2 :: %d\n", cnt);
     
            flag[1] = false;
        }
    }
     
    void main() {
        thread t1(func0);
        thread t2(func1);
        t1.join();
        t2.join();
        cout << "cnt : :" << cnt << endl;
    }
     
    
    출처: https://www.crocus.co.kr/1371 [Crocus]



### Mutex(상호 배제)

* Lock과의 비교
  * 공통점: 임계영역에 하나의 스레드만 접근 가능
  * 차이점: Lock은 잠겨 있는 임계영역은 하나의 프로세스에서만 접근하지만, Mutex는 여러 프로세스에서 접근 가능(공유됨)
* 0, 1만을 가진 이진(binary) 세마포어와 유사
* Lock을 가진 스레드가 **반드시 나갈 때 그 락을 해제**해야 함. -> 이진 세마포어와의 차이점



### Semaphore

* counting semaphore
  * 임계영역(자원)에 접근할 수 있는 스레드 개수를 정해놓음. 방출 시 세마포 증가, 진입 시 감소
* binary semaphore
  * 뮤텍스와 유사. 그러나, **차이점**이 있음.
  * 뮤텍스는 나갈 때 나가는 스레드가 lock을 해제해야 함. lock을 걸지 않은 스레드가 signaling을 이용해 lock 해제 가능.
* **단점**이 발생하는 경우
  * busy waiting: 초기의 세마포는 임계영역에 진입해야 하는 프로세스는 진입 코드를 계속 반복 수행해 CPU 시간 낭비했었음
  * 이러한 단점 해결을 위해 진입 실패한 스레드 block시킨 후, 임계영역 진입이 가능할 때 깨워주는 형태
* **DeadLock**
  * 각 스레드들이 서로가 점유하고 있는 자원들을 얻어야 하는 상황일 때 무한정 기다리게 됨.



#### Reference)

#### https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/OS#%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-%EB%8F%99%EA%B8%B0%ED%99%94

#### https://github.com/gyoogle/tech-interview-for-developer/blob/master/Computer%20Science/Operating%20System/DeadLock.md

#### https://gameproyyj.tistory.com/153

#### https://thinkpro.tistory.com/124

#### https://www.crocus.co.kr/1371