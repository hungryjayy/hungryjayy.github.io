# Sequelize 연관관계

<br>

### 테이블 연관관계와 객체 연관관계

: JPA를 공부할 때에도 보았듯 두 연관관계의 차이점은 **테이블**은 외래키로 **양방향 연관관계**를 갖지만, **객체**는 참조를 통해 **단방향 연관관계를 두 개** 갖는 것과 같다.

<br>

## Sequelize에서 연관관계 맵핑 방법

### belongsTo

* 의미 그대로 A가 B에 속해있을 때 `A.belongsTo(B)`와 같이 맵핑한다.
* 이 때 FK는 **A의 테이블**에 형성된다.

<br>

### hasOne

* 1 : 1-0 맵핑에서, A가 B를 갖고 있을 때 `A.hasOne(B)` 이러한 방식으로 맵핑한다.
* `belongsTo`와 반대로, target인 **B의 테이블**에 FK가 생성된다.

<br>

### hasMany

* 1 : N 맵핑에서 사용된다. 마찬가지로 target의 테이블에 FK가 생성

<br>

### belongsToMany

* N : M 관계에서 사용되는 듯 하다.
* 주로 N : M 관계에서는 JPA에서와 마찬가지로 연결 테이블(중간 테이블)로 관리하는게 좋아보이고, 이 때 연결되는 두 테이블에서는 belongsToMany를 갖게 될 것 같다.

<br>

## FK의 주체(연관관계의 주인)

: JPA에서 **연관관계의 주인**이라는 개념을 사용하는데, Sequelize에서는 딱히 그러한 용어를 사용하진 않는 것 같다.

<br>

#### 객체 연관관계에서 어디에 FK를 유지할 것인가?

*A, B 테이블 중 B 테이블의 컬럼에 A에 대한 FK가 있는 상황에서,*

* A 객체가 갖는 B의 필드값을 변경하고 UPDATE와 같은 쿼리를쳐도 B에 업데이트 되지 않고 B를 통해 업데이트해야한다.

  ![image-20210818122333311](./images/query_error.png) 

* join 쿼리로 조회는 되는 것 같다.

<br>

#### 생각해봐야 할 것

* 필요에 따라 양방향으로 객체를 갖을 수 있다. 그런데 A, B 객체 둘다 서로 의존하도록 하면 circular dependency 이고, 이 것에 대한 문제가 발생할 수 있다는 것을 인지하고, 어떻게 처리할 것인지를 생각해야 할 것 같다.
* **양방향 객체참조를 유지하고 join Query를 가능하게 하기** vs **단발성 조회 Query 두번 날리기**
  * 무엇이 더 이득일지 benchmark 테스트 해보기

<br>

#### npm test 시 쿼리 디버깅하기

* CLI : `DEBUG=${서버 컨테이너이름} npm test`
* e.g) `DEBUG=meeting:* npm test`

<br><br>

#### Reference) https://sequelize.org/master/manual/associations.html

