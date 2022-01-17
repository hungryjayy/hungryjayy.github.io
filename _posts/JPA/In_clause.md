# In Clause

* `findBy~~In` 의 인자로 List를 받으면 자동으로 in clause로 변경 된다.  `where ~~ in (1, 2, 3)`  && `where in id=1 or id=2 or =id=3` Query와 같은 형태

  * 그런데 여기서 ids는 고정된 크기의 객체가 아니다.

    e.g) 5개를 batch size로 받게 되면 아래와 같은 쿼리가 완성될 것이고, 결국 5개짜리 prepared, 2개짜리 prepared statement가 생긴다. 사용하면 사용할 수록 더 많은 prepared statement가 생길 것이다.

    ``` mysql
    select .... from Sample where id in (? ,? ,?, ?, ?);
    select .... from Sample where id in (? ,? ,? ,? ,?);
    select .... from Sample where id in (? ,? );
    ```


<br>

#### 문제점

* 임의의 n개의 리스트에 대한 in query가 생성되고, 이 때마다 preparedStatement 효과를 누리지 못하게 된다.
* 쿼리가 동적으로 계속 생성되므로 모든 것에 대해 **힙 메모리**가 점유된다. 따라서, 이 문제가 더 클 것으로 보인다.
* 1000개를 batch로 쿼리를 치면, 1000개의 다른 execution plan cache를 발생하게 될 것

#### 해결

* in_clause_parameter_padding 옵션 지정 해주기 -> 2의 제곱 단위로 padding
* execution plan cache 사이즈 조정 이 경우에 2048부터해서 줄여가며 메모리 상황을 보아야 함

<br>

### SQL in절로 서브쿼리 시 성능 이슈

* In절에 서브쿼리를 넣으면, 당연하게 **서브쿼리를 먼저 수행**하고 그 결과로 메인 쿼리를 수행한다.

  * 이 때 row의 데이터들을 모두 확인하게 된다. 성능 향상을 위해 EXISTS나 JOIN으로 대체가능한 것은 대체하는 것이 좋음


<br>

## In clause 동작방식

* 일단 `findByIdIn`과 같은 JPA 메서드는 쿼리로 변경되는데 `.... WHERE IN` 의 형태로 변경되긴 할텐데, 이 WHERE IN은 어떻게 동작하는가?
  * 만약 IN절이 또다른 쿼리(서브쿼리)라면 일단 서브쿼리 먼저 수행하는 것이 당연한 것
* 제일 먼저 테이블에 접근해 한 레코드를 가져오며, 그 레코드의 해당 값이 IN절에서 뽑아낸 여러 요소들에 포함되는지하나라도 일치한다면 레코드에 대한 쿼리를 수행하는 것. 
  * **Look up의 순서의 차이**: 서비스 로직으로 for문을 돌면서 쿼리를 치는 것과 **I/O의 관점** 말고도 이런 차이가 있다.

<br><br>

#### Reference)

#### https://doorbw.tistory.com/222

#### https://meetup.toast.com/posts/211

#### https://wakestand.tistory.com/511

#### https://eastglow.github.io/data-base/2018/09/07/Oracle-%EC%84%9C%EB%B8%8C%EC%BF%BC%EB%A6%AC%EC%99%80-IN-%EB%AC%B8%EC%9C%BC%EB%A1%9C-%EC%9D%B8%ED%95%9C-%EC%84%B1%EB%8A%A5-%EC%A0%80%ED%95%98.html

