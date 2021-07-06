# CLI

* `/` : Root 디렉토리
* `ls -l` : 디렉토리 자세히 보기(읽기 쓰기 권한 확인 가능)
* `pwd` : 현재 디렉토리
* `.... --help` 
* 디렉토리 삭제할 때 `-r`(recursively)
* `touch aaa.txt` : aaa.txt 만들기



* `help`, `man`
  * help: 간단한 사용법
  * man: `man ls` 처럼 man + CLI.
    * 더 상세할 수 있음



* 포트 사용하는 IP PID 찾기 : `sudo lsof -i:15672`
* 해당 IP 죽이기: `sudo kill -9 1335``
* ``netstat -ano`
  * -a` : 모든 연결과 대기중인 포트

  * `-n` : 주소, 포트 표시
  * `-o` : PID
  * `-p` : 프로토콜 연결을 표시



* `apt-get update` : 내가 현재 설치할 수 있는 프로그램 목록을 최신 상태로 반영해주는 CLI
* `apt-cache search ~~~` : ~~~가 들어간, 다운받을 수 있는 것들 조회
* `apt-get install`



* redis service 계속 켜지는 문제

  * HA를 위해 꺼지면 다시 켜지는것?

    `/etc/init.d/redis-server stop` 을 이용하자.



* 서비스 관련 CLI
  * `/etc/init.d/redis-server start`
  * `/etc/init.d/redis-server restart`
  * `redis-cli shutdown` (mac에서)



* 포트 열어주기
  * `iptables -I INPUT 1 -p tcp --dport 6379 -j ACCEPT`





#### Reference)

#### https://bottlecok.tistory.com/16