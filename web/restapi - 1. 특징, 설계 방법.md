# Rest api

: API를 설계 할 때 자원을 나타내는 URI가 있고, http method를 통해 자원을 어떻게 처리할 지 설계하는 방식의 아키텍처

* ### 구성

  * 자원(resource) - URI / 행위 - HTTP method / 표현 representations




* ### 특징

  * URI로 지정된 리소스에 대한 조작을 한정적인 Interface를 통해 수행하는 아키텍처
  * Stateless
    * 작업을 위한 (client의)상태정보를 따로 저장 안함
    * 단순히 들어온 요청만을 처리 -> 불필요한 정보 관리 x
    * 만약 stateful하다면 서버가 클라이언트의 현재 상태를 저장해야한다. 따라서 클라이언트의 상태는 서버에 종속된다.
      * 이 경우의 로드밸런싱을 하는 경우에 서버가 클라이언트의 상태를 공유할수있는 redis같은 시스템이 필요.
    * Stateless하기 때문에 어느 서버가 처리하던 클라이언트의 요청은 동일하게 처리 가능.
  * client-server 구조: 서버는 API를 제공, 클라이언트는 세션이나 로그인 정보를 관리. 각각의 역할이 구분되고 의존성이 줄어들고 확장성은 높아진다.




* ### 장점

  * 원하는 타입으로 데이터를 주고받을 수 있다.
  * http method 타입과 uri만 읽어도 해당 인터페이스가 어떠한 기능과 연결되는지 파악이 용이하다
  * client-server구조. api로 들어오는 요청을 처리해 요청한대로 보내주기만 하면 된다. 각자의 역할이 명확히 분리 




* ### 단점

  * 복잡한 비즈니스에서 http method의 한계로 인해 모든 경우를 cover할 수 없을 것 같다.
  * 공식화 된 Rest API 표준이 존재하지 않다는 점도 아쉬운 점 중 하나.



## 좋은 restapi를 구성하기

* ### resource는 복수형, 명사 - 의미상 이게 명확

  * `/rooms/{roomId}` : 모든 rooms 중 roomId에 해당하는 room
  * `/rooms/` : 모든 rooms

* ### get, post, put, delete 각각의 http method 각각의 성격을 위반 x

  * e.g. get 메서드는 어떠한 상태를 변경하지 않음.
  * post는 값을 읽기만 하지는 않음.
    * 보안상 취약하다고 이걸 사용하지 말고 https 통신을 하도록 해서 해결하는 것이 바람직.

* ### API version을 갖게 하는 것도 관리 측면에서 좋음

* ### camel case보다는 dash나 snake case가 좋아보임

  * 그러나 팀원과의 상의 필요
  * 개발자 뿐 아니라 일반 사용자들도 html을 통해 접근 할수 있음을 염두
  * restapi는 애초에 원활한 커뮤니케이션을 목적으로 하기 때문

* ### HATEOAS - restful하게 만드는 특징

  * client는 link를 통해서 api에 접근 가능.
  * server에서 URI를 변경해도 문제가 생기지 않음
    * client와 server의 분리

* ### filtering, sorting, field 선택, paging 등을 제공

* ### Error 처리

  * 200, 300, 400, 500대 http 상태 코드 숙지



* 보안에 대해서
  * Session 사용 금지.
    * Stateless라는 특징을 위반
  * HTTP auth / OAuth

* 문서화
  * Swagger UI
  * 설계용, 배포용 두가지로 나눠도 좋음.



##### Reference)

##### https://www.slideshare.net/brotherjinho/restful-api-64494716

##### https://www.rocketpunch.com/cards/post/436146