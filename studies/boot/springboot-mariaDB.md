# SpringBootwithMariaDB
Spring boot와 Maria DB를 connect하는 방법

![](./images/Untitled.png)

- Spring data JPA, JPA, Hibernate를 통해 MariaDB 연결.
    1. 환경설정(build.gradle.kts)에 plugin 및 allopen 환경설정.
    2. Entity class 정의
        - 기본 생성자 필요, optional parameter 붙는 변수 마지막에 정의
        - top-level class

    3.  Repository interface 정의
    4. JUnit Test 파일 만들었던 방식과 같이 test파일 만들고 @DataJpaTest annotation
    5. Repository에서 선언한 interface들, entityManager 선언
    6. 테스트
        - DB에 ORM 방식 접근 가능하게 해주는 entityManager (JPA의 하나의 Interface) 튜토리얼 [https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.htm](https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html)

- 위의 Repository, Entity, Test 연결 원활히 만드는 Kotlin의 주요 개념 "**Extension**"
    - Third party library 클래스에 새로운 함수 추가(확장) 가능(상속이나 decorator을 통해)
        - Generic type: 타입 확장
        - 확장 함수는 runtime에 결정 x 실제 함수가 정의된 때에 결정.(modify의 경우 original을 따름)

        [https://kotlinlang.org/docs/reference/extensions.html](https://kotlinlang.org/docs/reference/extensions.html)


#### 추후 과제: Webflux + DBconnection
