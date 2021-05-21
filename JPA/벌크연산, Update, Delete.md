# JPA update 쿼리, 벌크연산



## 벌크연산

* 벌크연산이란? 여러 건의 데이터를 한번에 변경(update or delete)
* JPA에서는 기본적으로 Bulk DELETE를 지원하지 않는다고 함.
* 따라서, @Query어노테이션을 통해 벌크연산을 수행해야 함.



* UPDATE

  ```sql
  String sql = "UPDATE Product p " +
      "SET p.prce = p.price * 1.1 " +
      "WHERE p.stockAmount < :stockAmount";
  
  int resultCount = em.createQuery(sql)
          .setParameter("stockAmount", 10)
          .executeUpdate();
  ```

  출처: https://joont92.github.io/jpa/JPQL/



* DELETE

  ```sql
  String sql = "DELETE FROM Product p " +
      "WHERE p.price < :price";
  
  int resultCount = em.createQuery(sql)
          .setParameter("price", 100)
          .executeUpdate();
  ```

  출처: https://joont92.github.io/jpa/JPQL/



### 벌크연산에 대해서는 영속성 컨텍스트를 건너뛰고, DB에 직접 쿼리

* 발생할 수 있는 문제

  * 한 테스트 내에서 1. entity 생성, 2. 변경, 3. assert로 변경확인과 같은 로직. 이 때 1은 영속성 컨텍스트에 해당 entity 저장, 3에서 find할 때는 DB가 아닌 영속성 컨택스트에서 꺼내와 확인

* 해결 방법

  * `em.refresh()` 사용 : 벌크 연산 직후 entity find 시, 해당 메서드를 통해 영속성 컨택스트 말고 DB에서 find하도록.
* 그런데 이 때도, 영속성 컨텍스트에 해당 엔티티가 존재한다면, DB에서 find한 결과를 버리고 영속성 컨텍스트에 있는 기존 엔티티를 반환.
    
* 이유: 영속성 컨텍스트가 영속상태의 DB 엔티티와의 동일성을 보장해야 하기 때문
    
  1. DB의 엔티티를 새로운 엔티티로 추가하려고하면 기존 엔티티와 Id 중복이 발생하기 때문
    
  2. 영속성 컨텍스트의 엔티티를 modify한 것으로 대체하려고 하면, 영속성 컨텍스트의 수정중인 엔티티가 유실될 위험이 있기 때문
    
* 벌크 연산 수행 후 영속성 컨택스트 초기화
  
  * `Modifying(clearAutomatically = true)`
    * `flushAutomatically` : 영속성 컨텍스트에 쌓여있던 변경사항을 DB에 반영해주는 것



#### 영속성 컨텍스트의 범위

* 영속성 컨텍스트의 생명주기는 트랜잭션과 같음.
  * 따라서, 같은 트랜잭션은 같은 영속성 컨텍스트를, 다른 트랜잭션은 다른 영속성 컨텍스트를 사용
* 따라서, 벌크 연산 수행 시 영속성 컨텍스트를 clear, flush할 지는 해당 트랜잭션 내부에서 조회연산을 또 수행할지를 보고 상황에 맞게 사용하면 될 듯.



#### Reference)

#### https://velog.io/@roro/JPA-JPQL-update-%EC%BF%BC%EB%A6%AC%EB%B2%8C%ED%81%AC%EC%99%80-%EC%98%81%EC%86%8D%EC%84%B1-%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8

#### https://jojoldu.tistory.com/536

#### https://data-make.tistory.com/617

#### https://freedeveloper.tistory.com/154

#### https://joont92.github.io/jpa/JPQL/

#### https://ultrakain.gitbooks.io/jpa/content/chapter3/chapter3.7.html