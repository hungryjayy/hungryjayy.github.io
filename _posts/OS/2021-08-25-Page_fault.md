---
layout: post

title: 페이지 부재와 교체

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [os, 운영체제]

featuredImage: 

img: 

categories: [OS]

date: '2021-08-25'

extensions:

  preset: gfm

---

<br>

## 페이지 부재 과정

: MMU가 OS에게 제어권을 넘겨 Disk(보조기억장치)에서 가져오는 과정

<img src="https://hungryjayy.github.io/assets/img/OS/page_fault.jpeg" alt="img" style="zoom:60%;" /> 

1. 실행되는 Process에서 필요한 프레임(페이지)를 가져오기 위해 Page Table을 봤는데, `i(invalid)`로 되어있으면, **OS**에게 페이지를 찾아올 것을 요청
2. OS가 디스크에서 필요한 페이지의 위치 찾음
3. 페이지를 가져온다.
4. 물리메모리에 빈 프레임이 있으면 저장. 그런데, 프레임이 존재하지 않으면 **페이지 교체** 발생
   1. **페이지 교체 알고리즘**을 통해 내려갈(swap out) victim 페이지를 고른다.
   2. 해당 페이지를 디스크에 기록하고, 관련 페이지 테이블을 수정한다.
      * 이 때 victim 프레임 swap out, 페이지 swap in 하는 두번의 디스크 접근이라 오버헤드일 수 있어, 줄여야함.
        1. 모든 페이지마다 변경 bit를 주고, 확인해서 변경된건 어차피 disk에 적용해야하기 때문에 선택
        2. **다른 페이지 교체 알고리즘**
5. 페이지 테이블에 `valid` 값 설정해주고 명령어 재시작(시작 위치 프로그램 카운터 참고)

<Br>

#### 페이지 테이블

<img src="https://hungryjayy.github.io/assets/img/OS/page_table.jpeg" alt="img" style="zoom: 50%;" /> 

<br>

### 페이지 교체 알고리즘

1. **FIFO** : 가장 오래된것 내리기

   * 이해하기 쉬우나, **오래된 페이지가 항상 필요하지 않다는 것을 보장할 수 없으며** 효율이 좋지 않음
   * **Belady의 모순**: 물리메모리에 페이지를 저장할 수 있는 프레임 갯수를 늘리면 page fault가 줄을 것이라 생각했지만, 오히려 page fault가 많아지는 것
     * 오래되어서 디스크로 내렸는데 사실은 그 페이지가 앞으로 많이 사용될 페이지인 경우. 따라서 항상 그런것은 아니다.

   

2. **OPT**(최적 페이지 교체): **앞으로 가장 오랫동안 사용되지 않을** 페이지 내리기

   * 가장 효율이 좋으나, 실제에서는 불가능함. 보통 연구 목적으로 사용한다고 함.
   * Belady의 모순을 해결

   

3. **LRU**: 가장 오랫동안 참조(사용)되지 않은 페이지 내리기

   * FIFO보단 좋고, OPT보단 좋지 않다고 함.
   * **앞으로 사용될 것을** 예측할 순 없겠지만, 실제로는 **지역성이라는 개념**이 있어, 실제로는 이게 적합하다고 함
   * Belady 모순 없음

   

4. LFU: 가장 적게 사용된 페이지 내리기

   * 특정 페이지가 초반에만 집중적으로 사용되었으면, 이제 더 이상 사용되지 않더라도 계속 메모리에 머물게 되는 문제.
   * 잘 쓰이지 않음

<br><br>

#### Reference)

https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/OS

https://velog.io/@gimtommang11/%EA%B0%80%EC%83%81%EB%A9%94%EB%AA%A8%EB%A6%AC

https://frontalnh.github.io/2018/04/04/%EC%9A%B4%EC%98%81%EC%B2%B4%EC%A0%9C-%EA%B0%80%EC%83%81-%EB%A9%94%EB%AA%A8%EB%A6%AC%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80/