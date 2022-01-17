# SpringBoot 용어 및 기본 개념

## 용어

* Controller : 비즈니스 로직 처리, 세분화가 필요할 경우 적절한 service에 전달
* Service: DAO로 데이터베이스에 접근, DTO로 전달
* DAO : Data Access Object
* DTO : Data Transfer Object



## boot vs spring

- Boot는 Spring을 잘 사용하기 위해 나온 것
- starter를 통해 내부 디펜던시 관리.
- 빈에 대한 자동설정이 적용되어 생성됨(Auto-configuration)



## DB 연계

#### Spring boot JPA

- 실무에서 많이 사용.
- JPA를 추상화

#### Spring Data JDBC

- DDD를 더 잘 쓰기 위해서 나왔다 하더라
- Data JPA에서 불필요한 부분, 복잡한 부분을 덜어냈다고 하더라
- 조금 Data JPA에서 간소화시킨 버전?
  - 스펙이 작다. 기능이 없다. 이런 관점은 아님
  - 가볍게 만들었다? 정도??



## Bean?

* POJO(plain, old java object)
* IoC 컨테이너에 의해 인스턴스화, 관리, 생성 됨. ++ thread safety 보장
* 기본적으로 싱글톤으로 생성되는 것 + 기존 싱글톤의 문제를 해결
* XML파일로 관리



#### Reference) 

#### https://gmlwjd9405.github.io/2018/11/10/spring-beans.html

#### https://velog.io/@yhh1056/%EC%8A%A4%ED%94%84%EB%A7%81-Bean-Configuration-Singleton
