# Was vs Web Server

* Was(Web application server)
  * 사용자 요청에 맞게 동적인 컨텐츠를 전달
  * Web server + Web container
  * DB 서버가 같이 수행
  * 비즈니스 로직 처리
  * 큰 의미에서, Node.js는 여기에 해당할 것
    * 그런데, 정확하게는 서블릿 컨테이너와 비슷..?
    * express가 WAS느낌



* Web server(e.g. nginx)
  * 정적인 컨텐츠를 반환한다.(html, css 등)
  * http 프로토콜 기반으로 클라이언트 요청을 서비스
  * 클라이언트 요청에 대해 가장 앞에서 요청 처리



#### Was는 Web server가 하는 일을 다 할 수 있다.

* 효율적인 분산처리를 위해 Web server도 사용해야 함
* 보통 Web server에 Was가 플러그인같이 붙은 형태



#### Reference)

##### https://gmlwjd9405.github.io/2018/10/27/webserver-vs-was.html

##### https://jeong-pro.tistory.com/84