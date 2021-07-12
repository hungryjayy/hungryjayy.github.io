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
* `tail -f aaa.log` : aaa.log를 계속 감시하고 있다가 바뀔때마다 리프레시해서 보여줌
  * 실시간 로그 볼때 편함
* `-u` : --unlock



### 파일 찾기

* `locate` : 디렉토리를 돌면서 찾는 것이 아니고 mlocate라는 DB 를 뒤져서 위치를 찾는 것

  e.g) `locate *.log` : log 파일 모두 찾기

* `find`

  * `find [찾기 시작할 경로] [어떻게 찾을건지] [무엇을]`

    e.g) `find / -name *.log` 

* `whereis`: 실행파일이 어디에 있는지?
  * `whereis ls` : ls 명령어가 어디에있는지?
  * $PATH와 연관 : `echo $PATH` 명령어를 수행하면 bin 밑의 ls를 찾기 위해 PATH에 있는 경로를 전부 찾아다님.
    * 따라서, 어떤 경로에서 ls를 수행하던, 우리가 원하는 ls를 수행 할 수 있는 것



### FG(fore ground)

* `fg` : 백그라운드에 있던 프로그램을 포그라운드로
* `jobs`:  현재 back ground로 실행되고 있는 것 무엇이 있는지
  * nano 작성 중 Ctrl + Z 누르면 back ground로, jobs로 확인 가능



### help와 man(manual)

* help: 간단한 사용법
* man: `man ls` 처럼 man + CLI.
  * 더 상세할 수 있음



### 다중 사용자

* `id` : 내 자신이 누구인지
* `who` : 누가 현재 접속해있는지
*  `sudo useradd -m aaa`
  * `/home`에서 ls를 보면 aaa 사용자가 추가된 것을 보게된다.



### Redirection

* `ls`등과 같은 것들의 결과를 따로 저장 가능
* `ls > example.txt` : ls의 결과를 **터미널에 출력(stdout)**하는 것이 아닌, **파일로 저장**시켜줌
  * **stdout을 redirection해주는 것.**
  * stderror는 redirection해주지 않고 그냥 모니터에 출력될 것 -> error.log에 저장 가능
* http://slideplayer.com/slide/5126304 확인



### 프로세스

* `ps` or `ps aux`(컴퓨터 내 모든 프로세스를 보려면 후자)
  * `ps aux | grep apache` 처럼 사용 가능
* 포트 사용하는 IP PID 찾기 : `sudo lsof -i:15672`
* 해당 IP 죽이기: `sudo kill -9 1335`
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