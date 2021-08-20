# In Clause

* `findBy~~In` 의 인자로 List를 받으면 자동으로 in clause로 변경 됨

  * Query로 치면 `where ~~ in list` 와 같은 형태라고 보면 됨

* (210820) 사실 `where id in(1, 2, 3)` 은 `where id = 1 or id = 2 or id = 3` 과 같다고 한다.

* `select ... from Table where id in (id1, id2, id3)` 이와 같은 형태로

  * 그런데 여기서 ids는 고정된 크기의 객체가 아님

  * e.g) 5개씩 잘라서 받으며, 12개의 id를 받는다고 가정한다면 아래와 같은 형태로 받게 될 것

    ``` mysql
    select .... from Sample where id in (? ,? ,?, ?, ?);
    select .... from Sample where id in (? ,? ,? ,? ,?);
    select .... from Sample where id in (? ,? );
    ```

  * 이러한 경우 **11, 12, 13, 14, 15**개의 id의 리스트를 받을 때를 고려하면 총 **5개의 서로 다른 쿼리**가 생겨버림

  * 문제점

    * DB 관점에서 preparedStatement 효과를 누리지 못하게 됨
    * 힙 메모리 점유됨
    * 1000개 단위로 fetch해 온다면, 1000개의 다른 execution plan cache를 발생하게 될 것

  * 해결

    * in_clause_parameter_padding 옵션 지정 해주기 -> 2의 제곱 단위로 padding
    * 직접 padding
    * execution plan cache 사이즈 조정 이 경우에 2048부터해서 줄여가며 메모리 상황을 보아야 함



#### Reference)

#### https://meetup.toast.com/posts/211



## SQL in절로 서브쿼리 시 성능 이슈

* In을 이용하면 서브쿼리를 먼저 수행하고 그 결과로 메인 쿼리를 수행

  * row의 데이터들을 모두 확인함

  * -> 따라서 EXISTS나 JOIN으로 대체가능한 것은 대체하는 것이 좋음

    

#### Reference)

#### https://wakestand.tistory.com/511

#### https://eastglow.github.io/data-base/2018/09/07/Oracle-%EC%84%9C%EB%B8%8C%EC%BF%BC%EB%A6%AC%EC%99%80-IN-%EB%AC%B8%EC%9C%BC%EB%A1%9C-%EC%9D%B8%ED%95%9C-%EC%84%B1%EB%8A%A5-%EC%A0%80%ED%95%98.html





## In clause 동작방식

* 일단 `findByIdIn`과 같은 JPA 메서드는 쿼리로 변경되는데 `.... WHERE IN` 의 형태로 변경되긴 할텐데, 이 WHERE IN은 어떻게 동작하는가?
  * 만약 IN절이 또다른 쿼리(서브쿼리)라면 일단 서브쿼리 먼저 수행하는 것이 당연한 것
* 제일 먼저 테이블에 접근해 한 레코드를 가져오며, 그 레코드의 해당 값이 IN절에서 뽑아낸 여러 요소들에 포함되는지 체 하나라도 일치한다면 레코드에 대한 쿼리를 수행하는 것



#### Reference)

#### https://doorbw.tistory.com/222

