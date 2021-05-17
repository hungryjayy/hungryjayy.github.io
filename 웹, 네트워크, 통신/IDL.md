# IDL

: Interface Definition Language

* 다른 언어로 작성된 여러 서비스 들을 연결하기 위해 중간 인터페이스를 정의하는 언어

  * e.g) XML, JSON, Protocol Buffer

  e.g) c++을 사용한 컴포넌트와 자바를 사용한 컴포넌트 사이에서 국한되지 않고 인터페이스를 묘사

* 통신 환경에서 이종간 플랫폼에도 보내지고, 그 환경에서 잘 실행되도록 함

* 다른 플랫폼에 데이터를 보낼 때 구조화된 데이터를 IDL로 직렬화(Serialization)



## XML

* 사람이 읽고 쓸수 있음
* 스키마 정보 없이 사용 가능
* SOAP와 같은 표준이 있음
* JSON에 비해 태그 등 때문에 같은 데이터라도 내용이(코드가) 더 많음
* 인코딩, 디코딩에 상대적으로 많은 리소스 소비

## JSON

* 사람이 읽기에 가독성이 좋음 -> 개발, 디버깅에 편리
* 스키마 정보 없이 사용 가능
  * 간결함 좋음
* 웹 브라우저에 용이
* XML에 비해 가벼움
* 표준이 없음

## PB(Protocol Buffer)

* Byte Stream으로 직렬화해 보냄 -> 이진 데이터라 사람이 읽을 수 없음
* 데이터 압축률 좋음
* 처리속도 빠름
* 스키마에 대한 정확한 지식 필요
* 구글에서 개발한 직렬화 프로토콜



#### Reference) 

#### https://www.joinc.co.kr/w/man/12/ProtocolBuffer

#### http://gdthink.blogspot.com/2006/07/idlinterface-definition-language.html

#### https://soulduo.tistory.com/91