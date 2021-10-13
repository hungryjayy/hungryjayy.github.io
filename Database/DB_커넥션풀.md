# DBCP(DB 커넥션 풀)

: Thread pool과 같은 맥락. 디비 커넥션을 관리하기 위한 풀

<br>

* 커넥션을 관리하는 캐시 or 기법
* 매 요청마다 쓰레드를 생성 삭제하면 비용이므로 WAS에서 thread pool을 둠으로써 재사용하는 방법과 같음
  * 마찬가지로 DB connection이 있을때마다 커넥션 객체를 생성 삭제하면 비용이 될 수있음
* 일반적으로 Thread 수 > DB connection 수 가 좋다고 함. 모든 요청에 항상 DB를 호출할 필요는 없기 때문
  * 이 두 곳에서 유지하는 커넥션 / 쓰레드 수는 메모리와 직관됨. 많을수록 메모리를 사용하고, 적을 수록 클라이언트 대기요청이 증가하게 됨
  * WAS에서 설정할 값이고, 실 서버 테스트 후 수를 정하는게 바람직해보임

<br>

#### 커넥션이 부족한 경우(모두 사용중일 때)

: 클라이언트가 대기상태가 되고, 커넥션이 풀 반환되면 순차 제공

* OOP(out of memory) 발생. 이 경우 보통 Thread 개수나 DB connection 조정

<br>

## 과정

1. 웹 컨테이너가 실행되면서 **적절한 수의 DB 커넥션 객체**들을 생성해 pool에 저장해놓음
2. DB(HTTP) 요청시 풀에서 커넥션을 가져 DB 접근에 사용 후, 다시 pool에 반환

<br>

#### *DB와의 통신은 무슨 프로토콜??*

* 기본적으로 신뢰성 바탕의 TCP/IP 통신 위에서 감싸진 형태로 DB마다 존재
  * MariaDB(mysql과 같음) - mysql client/server protocol
  * MongoDB - mongoDB wire protocol
  * Redis - RESP(Redis Serialization Protocol)

<br><br>

#### Reference)

#### https://www.holaxprogramming.com/2013/01/10/devops-how-to-manage-dbcp/

#### https://delf-lee.github.io/post/connection-pool/