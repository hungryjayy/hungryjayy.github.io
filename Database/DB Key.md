# Database Key

## 후보 키

* 튜플을 유일하게 식별할 수 있는(유일성과 최소성을 만족하는) 속성들의 부분집합
* 기본키가 될 수 있는 것들



## 기본 키 (PK)

* 후보키 중 뽑은 것
* Null이면 안됨. (**개체 무결성**의 첫번째 조건)
* 기본키로 정의된 속에는 동일한 값이 중복되어 저장될 수 없음(**개체 무결성**의 두번째 조건)

### 무결성

* 개체 무결성: 릴레이션에서 기본키를 구성하는 속성은 1. 널(Null)값이나 2. 중복값을 가질 수 없다.
* 참조 무결성: 외래 키 값은 Null이거나(해당 튜플이 관계 릴레이션과 관계가 없을 때??) 참조하는 릴레이션의 PK와 같아야 한다.



## 대체 키 (== 보조키)

* 후보키 중 기본키가 아닌 것



## 슈퍼 키

* 유일성만 만족.
* 즉, 구별에 꼭 필요하지 않은 속성도 갖고 있음



## 외래 키 (FK)

* 다른 릴레이션의 기본키를 그대로 참조하는 속성의 집합



#### Reference)

#### https://github.com/gyoogle/tech-interview-for-developer/blob/master/Computer%20Science/Database/%5BDB%5D%20Key.md

#### https://github.com/WooVictory/Ready-For-Tech-Interview/blob/master/Database/Key(%ED%82%A4).md

#### https://halfmoon9.tistory.com/59