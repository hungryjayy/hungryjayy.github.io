## JPA repository 사용시 Optional<T> 이슈

* 기본적으로 자바에 맞춰져 있으므로 Optinal로 묶여있음

* Kotlin의 장점을 잘 살리려면 Nullable을 바로 체크할 수 있어야 함.

  * Extension 사용

    1. 함수를 재정의하기

    2. 이미 잘 구현되어있는 built-in extension 사용

       e.g) `findById()` -> `findByIdOrNull()`

       성능 이슈가 있다고 하지만, 무시할 수 있을정도의 미미한 성능차



* @JoinColumn 어노테이션의 옵션 name은 해당 엔티티 테이블에 어떤 컬럼명으로 저장할지를 지정
  * 실제로 FK가 맵핑되는 컬럼은 해당 타입(엔티티)의 PK이니까 자동으로 알아서 맵핑해줌.



## SQL in절로 서브쿼리 시 성능 이슈

* In을 이용하면 서브쿼리를 먼저 수행하고 그 결과로 메인 쿼리를 수행

  * row의 데이터들을 모두 확인함

  * -> 따라서 EXISTS나 JOIN으로 대체가능한 것은 대체하는 것이 좋음

    

#### Reference)

#### https://wakestand.tistory.com/511

#### https://eastglow.github.io/data-base/2018/09/07/Oracle-%EC%84%9C%EB%B8%8C%EC%BF%BC%EB%A6%AC%EC%99%80-IN-%EB%AC%B8%EC%9C%BC%EB%A1%9C-%EC%9D%B8%ED%95%9C-%EC%84%B1%EB%8A%A5-%EC%A0%80%ED%95%98.html



## findAllBy~~ vs findBy~~~

* 둘은 이름만 다르지, 같은 쿼리를 만들어 냄.
*  복수형을 만들어내는 곳에선 All, 아닌 곳에선 findBy를 사용해 코드의 가독성을 높이면 됨