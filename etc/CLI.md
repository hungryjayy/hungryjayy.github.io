### CLI

* 해당 포트 사용하는 IP PID 찾기 : `sudo lsof -i:15672`

  

* 해당 IP 죽이기: `sudo kill -9 1335`

* `netstat -ano`
  * `-a` : 모든 연결과 대기중인 포트
  * `-n` : 주소, 포트 표시
  * `-o` : PID
  * `-p` : 프로토콜 연결을 표시



