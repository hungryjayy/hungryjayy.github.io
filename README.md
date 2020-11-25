# RestAPI design pattern과 HTTP 등
별도의 전송 계층 없이 전송하기 위한 아주 간단한 인터페이스
<br>

## 1. 기본 개념
“사용자라는 Resource(//myweb/users) 이름이 “Terry”인 Representation으로 새로운 사용자를 생성(HTTP POST)한다.”

```
HTTP POST, http://myweb.users
{
	“users”:{
		“name”:”Terry”
	}
}
```

* POST 생성, GET 조회, DELETE 삭제, PUT 업데이트

>- Idempotent: POST만 NO, 나머진 YES. e.g) f(x) = f(f(x))
>- 같은 요청(POST)를 계속한다면 계속해서 생긴다. 다른 method는 (한번 실행 == 같은 거 여러번 실행)
>- GET, HEAD etc.. 은 resource 수정 X → safe하다고 표현

* 모든 개체 resource. //resource그룹명/resource ID명
<br>

## 2. 설계 패턴
* API URL만 보고도 무슨 API인지 알도록 직관적으로.(길지않게 2depth정도만)<br><br>
* REST api는 대상에 대한 행동(CRUD)을 정의. → 대상은 명사, 복수형

>>_나쁜 예)_<br>
>>>HTTP POST : /getDogs<br>
>>>HTTP POST : /setDogsOwner<br>

>>_좋은 예)_<br>
>>>HTTP GET : /dogs<br>
>>>HTTP POST : /dogs/{puppy}/owner/{terry}<br><br>

* resource간 관계 표현
> 1) 서브 url. GET /owner/{terry}/dogs<br>
> 2) 관계를 표현하는 리소스 별도 정의방법(Google it)<br><br>

* HTTP response code(RestAPI 서버 개발 중 만난 http code)<br>
```
200: 성공<br>
400: Bae request – Field validation 실패<br>
401: 인증, 허가 실패<br>
404: 해당 resource 찾을 수 없음<br>
500: 서버 에러<br>
```
<br>
* Error stack은 response 메시지에 포함 시키지 말 것.(기술 스택, 파일 위치 등 노출 우려)<br><br>

* 페이징: 많은 도큐먼트 리턴시 잘라서 리턴하는 페이징 처리 필요
>>> /records?offset=100&limit=25 (100번째 record부터 25개 출력) // e.g) 슬라이드 참조<br>

* 부분 응답: REST api 중 일부만 응답하는 방식. 가독성 높, 부하 낮.<br>

* 검색: GET에 쿼리스트링 사용.
>>> - users?name=cho&region=seoul&offset=20&limit=10<br> <br>

##### Reference
https://www.slideshare.net/Byungwook/rest-api-60505484
