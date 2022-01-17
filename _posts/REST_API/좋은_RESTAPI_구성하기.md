# 좋은 restapi 구성하기

### Resource는 복수형, 명사 - 의미상 이게 명확

* `/rooms/{roomId}` : 모든 rooms 중 roomId에 해당하는 room
* `/rooms/` : 모든 rooms



### get, post, put, delete 각각의 http method 각각의 성격을 위반 x

* e.g) `GET` 메서드는 어떠한 상태를 변경하지 않음.
* `POST`는 값을 읽기만 하지는 않음.(적어도 생성하는 메서드이기 때문에 `GET`과는 명확히 구별)
  * 보안상 취약하다고 이걸 사용하지 말고 HTTPS 통신을 하도록 해서 해결하는 것이 바람직.



### API version을 갖게 하는 것도 관리 측면에서 좋음

* URI에 v1을 명시해, 각각의 버전으로 맵핑될 수 있도록?



### camel case보다는 dash나 snake case가 좋아보임

* 그러나 팀원과의 상의 필요
* 개발자 뿐 아니라 일반 사용자들도 html을 통해 접근 할수 있음을 염두
* restapi는 애초에 원활한 커뮤니케이션을 목적으로 하기 때문



### HATEOAS - restful하게 만드는 특징

* client는 link를 통해서 api에 접근 가능.
* server에서 URI를 변경해도 문제가 생기지 않음
* client와 server의 분리



### filtering, sorting, field 선택, paging 등을 제공

* ### Error 처리



* 보안에 대해서
  * Session 사용 금지. -> Stateless라는 특징을 위반
    * 브라우저에게 token정보등을 저장할 수 있도록 할 수 있음
  * HTTP auth / OAuth

* 문서화
  * Swagger UI
  * 설계용, 배포용 두가지로 나눠도 좋음.



#### Reference)

#### https://www.slideshare.net/brotherjinho/restful-api-64494716

