# REST 와 HTTP / HATEOAS

* ## http api

  * http를 사용해서 서로 정해둔 스펙으로 통신
  * 넓은 의미의 restapi




* ## rest api(restful api)

  : http api와 같으나 추가적으로 **restful한 네가지 특징**을 갖는다.

  ### 1. 자원 식별: 각각의 자원은 URI를 통해 식별 가능해야한다.

  * 따라서 URI를 작성할 때 사람이 읽고도 자원의 위치를 알 수 있도록

  ​	`GET /room/1`

  

  ### 2. 표현을 통한 리소스 조작: 자원을 묘사한 표현을 전송한다.

  * GET, POST, PUT, DELETE, PATCH(일부 수정)

  * 따라서 서버 코드에 얽매이지 않고 client 구현 가능.

  * 서버의 수정에도 영향을 받지 않는다.

    e.g) `GET http://www.example.com/v2/apple`

    

  ### 3. 자기 서술형 메시지: 수신자가 이해하기 위한 모든 정보를 가지고 있어야 한다.

  * REST API 메시지만 보고도 쉽게 이해할 수 있도록 자기서술적이다.
  
    ```Http
    GET /HTTP /1.1
    ```
  ```
    
  ```
```http
    HTTP/1.1 200 OK
  \[{ “op” : “remove”, “path” : “a/b/c"}\]
```

    이러한 메시지에서
      
    ```Http
  GET /HTTP /1.1
    Host: www.test.co.kr
  ```
    
  
    
  ```http
    HTTP/1.1 200 OK
  Content-Type: application/json-patch+json
    \[{ “op” : “remove”, “path” : “a/b/c"}\]
  ```





### 4. **hateoas : 클라이언트가 리소스에 접근하기를 원한다면, server가 응답을 줄 때 hyperlink를 추가해서 보내준다. 다음에 클라이언트가 어떤 API를 호출해야하는지는 해당 링크를 통해 받을 수 있다. **

* **이 원칙을 통해 클라이언트와 서버는 완전하게 분리됨.**
  
* **URI 등이 바뀌어도 클라이언트에서는 자동으로 연결됨**
  
    * **클라이언트에게 자원을 보내면서 다음에 연결할 URL을 링크로 같이 보내기 때문에**
    
      * e.g) 주문에 대한 정보를 보낼 때 주문 고객에게 사용 가능한 작업을 식별하는 링크를 주문 presentation에 포함
    
    * 요청에 대한 응답을 보낼 때 그 스크린에서 할 수 있는 것들을 요청으로 보내면 됨
    
      #### ref) https://docs.microsoft.com/ko-kr/azure/architecture/best-practices/api-design#use-hateoas-to-enable-navigation-to-related-resources
    
    > 이 원칙이 중요한 것은 이렇게 함으로서 클라이언트와 서버간의 완전한 분리가 이루어지게 됩니다. 만약 서버의 자원을 나타내는 URI 가 변경되었을 경우 클라이언트는 서버의 변화에 종속적으로 그 정보를 클라이언트 정보에 추가하게 됩니다. (SPA 상에서 href 데이터를 바꾸어 줘야함) 하지만 HATEOAS를 제대로 적용했을 시 아래와 같이 _links.profile 에 대한 href정보만 조회해주면 되므로 서버에서 URI정보가 바뀌어도 클라이언트 측에서 소스 변경없이 그대로 사용할 수 있게 됩니다.
  
  >출처: https://engkimbs.tistory.com/855 [새로비]
  
  **e.g )**

```
"_links" : {
  "self" : {
    "href" : "http://localhost:8080/api/events/46"
  },
  "profile" : {
    "href" : "/docs/index.html#resources-events-get"
  }
}
```



#### reference) 

#### https://doitnow-man.tistory.com/96

#### https://www.inflearn.com/questions/126743

#### http://amazingguni.github.io/blog/2016/03/REST%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4-1

#### https://sabarada.tistory.com/9
