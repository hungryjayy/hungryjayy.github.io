---

layout: post

title: SQL Injection

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [database, db, sql, sql injection, 보안]

featuredImage: 

img: 

categories: [Study, Database]

date: '2021-07-25'

extensions:

  preset: gfm


---

<br>

## 공격 방법

* ### 인증 우회

  * 아이디를 입력하고 비밀번호 창에 "1234 or 1=1" 과 같은 비밀번호를 전달해주면, 이게 쿼리로 변환되면서 WHERE 절에서 항상 true 결과를 불러오는 쿼리가 되고, admin 페이지로 접근하게 됨.

<br>

* ### 에러 유발 

  * 기본적으로 웹앱은 쿼리 오류 발생시 DB오류를 브라우저에 노출함
  * `UNION SELECT`을 통해 에러를 일으킴
    * 이 쿼리에서 union하는 두 테이블은 컬럼 수가 일치해야함
    * 따라서, 에러가 안날때까지 수행해보고, 컬럼의 갯수를 파악할 수 있음

<br>

## 방어 방법

* SQL 구문에 주석을 삽입해 Where 구문 무력화

  ```java
  String query = "Select * From Users"
         + " Where id = '"+ id +"' and Password = '" + Password + "'"
  ```
  
* SQL 서버 오류 시, 에러 메시지 감추기

  * 일반 사용자는 view를 통해서만 원본 데이터에 접근 할 수 있도록 막아두는 것

<br>

* ### Prepared statement 사용

  * 일반적인 Statement
    *  "1. 쿼리 문장 분석, 2. 컴파일, 3. 실행"
    * 이 순서를 쿼리 수행마다 매번 반복
    * **동적 쿼리**
  * Prepared Statement
    * 처음 한번만 위의 세 단계 거치고, 캐시에 담아놓는다. 이후 **캐시를 통해 재사용**
    * **정적 쿼리**

<br><br>

#### Reference)

https://github.com/WooVictory/Ready-For-Tech-Interview/blob/master/Database/SQL%20-%20Injection.md

https://m.mkexdev.net/427

https://medium.com/pocs/sql-injection%EC%9D%B4%EB%9E%80-3b57f2415ef4

https://devbox.tistory.com/entry/Comporison