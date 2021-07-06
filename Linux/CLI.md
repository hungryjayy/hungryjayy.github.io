# CLI



### 기초

* `/` : Root 디렉토리
* `ls -l` : 디렉토리 자세히 보기(읽기 쓰기 권한 확인 가능)
* `pwd` : 현재 디렉토리
* `.... --help` 
* 디렉토리 삭제할 때 `-r`(recursively)
* `touch aaa.txt` : aaa.txt 만들기
* `grep` : 내가 원하는 키워드가 포함되어있는 행을 찾아주는 명령어
* `chmod` : 파일의 권한(readable, writable, executable)을 바꿔줄 수 있음



### `help`, `man`

* help: 간단한 사용법
* man: `man ls` 처럼 man + CLI.
  * 더 상세할 수 있음



### Redirection

* `ls`등과 같은 것들의 결과를 따로 저장 가능
* `ls > example.txt` : ls의 결과를 **터미널에 출력(stdout)**하는 것이 아닌, **파일로 저장**시켜줌
  * **stdout을 redirection해주는 것.**
  * stderror는 redirection해주지 않고 그냥 모니터에 출력될 것 -> error.log에 저장 가능
* http://slideplayer.com/slide/5126304 확인



### 프로세스

* `ps aux`
* 포트 사용하는 IP PID 찾기 : `sudo lsof -i:15672`
* 해당 IP 죽이기: `sudo kill -9 1335``
* ``netstat -ano`
  * -a` : 모든 연결과 대기중인 포트

  * `-n` : 주소, 포트 표시
  * `-o` : PID
  * `-p` : 프로토콜 연결을 표시



### 패키지 매니저

* `apt-get update` : 내가 현재 설치할 수 있는 프로그램 목록을 최신 상태로 반영해주는 CLI
* `apt-cache search ~~~` : ~~~가 들어간, 다운받을 수 있는 것들 조회
* `apt-get install`
* `whereis ~~` : 내가 설치한게 어디있는지 알고 싶을 때



### redis service 계속 켜지는 문제

* HA를 위해 꺼지면 다시 켜지는것?

  `/etc/init.d/redis-server stop` 을 이용하자.



### 서비스 관련 CLI

* `/etc/init.d/redis-server start`
* `/etc/init.d/redis-server restart`
* `redis-cli shutdown` (mac에서)



### 포트 열어주기

* `iptables -I INPUT 1 -p tcp --dport 6379 -j ACCEPT`
* <-> `ufw allow 2337`과 같은 ufw와 구별해서 사용





#### Reference)

#### http://slideplayer.com/slide/5126304

#### https://opentutorials.org/course/2598

#### https://bottlecok.tistory.com/16