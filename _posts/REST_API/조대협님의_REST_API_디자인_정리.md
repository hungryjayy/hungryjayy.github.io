# 조대협님의 RestAPI design (slide share 정리)
: 별도의 전송 계층 없이 전송하기 위한 아주 간단한 인터페이스



## 1. 기본 개념
사용자라는 Resource(`/myweb/users`) 이름이 “Terry”인 **Representation**으로 새로운 사용자를 생성(HTTP POST)해라

```http
HTTP POST, http://myweb/users
{
	“users”:{
		“name”:”Terry”
	}
}
```

### HTTP method

* POST 생성, GET 조회, DELETE 삭제, PUT 업데이트

>- Idempotent: POST만 NO, 나머진 YES.
>  - `f(x) = f(f(x))`
>- 같은 요청(POST)를 계속한다면 계속해서 생긴다. 다른 method는 (한번 실행 == 같은 거 여러번 실행)
>- GET, HEAD etc.. 은 resource 수정 X → safe하다고 표현

* 모든 개체를 `행위 /resource그룹명/resource ID명`


## 2. 설계 패턴
* API URL만 보고도 무슨 API인지 알도록 직관적으로.(**길지않게 2depth정도만**)
* REST api는 대상에 대한 행동(CRUD)을 정의. → 대상은 **명사**, **복수형**

```http
나쁜 예)
HTTP POST : /getDogs
HTTP POST : /setDogsOwner
```

```http
좋은 예)
HTTP GET : /dogs
HTTP POST : /dogs/{puppy}/owner/{terry}
```

* Resource간 관계 표현

  1. 서브 url. GET /owner/{terry}/dogs

  2. 관계를 표현하는 리소스 별도 정의방법

     `GET /people/terry/ownsdogs/puppy`



* HTTP response code

```HTTP
200: 성공
400: Bae request – Field validation 실패
401: Auth 에러. 인증, 허가 실패
404: 해당 resource 찾을 수 없음
500: 서버 에러
```


* Error stack은 response 메시지에 포함 시키지 말 것.(기술 스택, 파일 위치 등 노출 우려)



* 페이징: 많은 도큐먼트 리턴시 잘라서 리턴하는 페이징 처리 필요
  `/records?offset=100&limit=25 (100번째 record부터 25개 출력)`

  `/recores?page=5&rpp=25`

  `/records?start=50&count=25`



* 부분 응답

   * REST api 중 일부만 응답하는 방식. 가독성은 높고, 부하를 낮춤.

      `/people:(id,first-name,last-name,industry)` - 파싱에 어려움

      `/terry/friends?fields=id,name` - 직관적

      `?fields=title,media.address.city`

   * 코드 내에 JOIN이 있을 때, 패킷을 줄이고자 할 때.



* 검색: 보통 GET에 쿼리스트링 사용.

   `users?name=cho&region=seoul&offset=20&limit=10`



#### Reference
#### https://www.slideshare.net/Byungwook/rest-api-60505484