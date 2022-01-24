---
layout: post

title: Dirty checking

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [jpa, spring boot jpa, dirty checking]

featuredImage: 

img: 

categories: [JPA]

date: '2021-05-13'

extensions:

  preset: gfm
---

: Transaction을 commit하는 시점에 알아서  데이터베이스에 반영해주는 것
* **단건의 수정**에 대한 쿼리를 JPA에서는 지원하지 않는데, 해당 엔티티를 변경하고, 별도로 save하지 않아도, 변경사항이 반영됨



## 더티체킹 과정

* 일단 엔티티의 최초 상태(스냅샷)를 영속성 컨텍스트에 보관해 놓는다.
* 트랜젝션 커밋 시 em 내부에서 flush 호출되고, 이 때 변경된 엔티티와 스냅샷을 비교한다.
* 변경된 엔티티가 있다면 수정 쿼리를 쓰기 지연 sql 저장소에 보내고, 이 sql은 DB에 날라가고, 트랜잭션은 커밋 완료된다.



#### 위의 과정을 통하기 때문에 벌크 연산을 이용한 수정과는 과정이 다름.



#### 만약 @Transactional을 사용하지 않았다면 위의 과정은 발생하지 않고, 원하는 시점에 save 나 saveAndFlush를 사용해야 함.



#### Reference)

https://cheese10yun.github.io/jpa-bulk/

https://devhyogeon.tistory.com/4

https://jojoldu.tistory.com/415

https://interconnection.tistory.com/121