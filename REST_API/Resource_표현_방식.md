# REST API resource 표현방식

* 기본적으로 명사형태

## 1. document

* 1개의 인스턴스 나타냄

  ``` java
  /users/{id}
  ```

* 객체 인스턴스, DB의 record와 유사한 개념
* 일반적으로 id를 통해(유일한 것)
* GET, PUT, DELETE에 사용



## 2. collection

* resource의 묶음

* 일반적으로 POST, GET(collection)에 사용

  * flush 해야하는 경우 DELETE도 사용될 것 같음

  ````
  /users
  ````



## 3. store

* client 입장에서의 resource 저장소. 참조 블로그에선 "장바구니" 라고 표현

* API로 client가 resouce를 생성, 삭제를 자유롭게

* 약간 hash(key - value)처럼 생각

  ``` 
  /users/{id}/my-users
  ```



## 4. controller

* Client 입장에서 Server의 메서드를 실행하는 느낌

* CRUD 로 구분이 안될만한 것들에 수행하면 될 듯

  * RPC call 대신에 이걸 사용하면 rest의 단점을 보완가능할 것 같고, RPC를 사용하지 않아도 될 것 같음

* REST 특성상 명사를 사용하도록 권장하지만, 여기서는 function을 실행하는 느낌이기 때문에 동사를 사용해도 될 것 같다고 함

  ``` 
  /users/{id}/doSomething
  ```





#### Reference)

#### https://sabarada.tistory.com/28

#### http://storyg.co/rest-api-explain