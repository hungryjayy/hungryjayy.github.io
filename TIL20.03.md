## 200301

* Typora 적용
* git author 변경
  * git config --list
  * git config --global --user.name=hungryjay
* commit한 모두 author 변경하는 법
  * 바꾸고 싶은 commit으로 git rebase -i -p {commitID}
  * pick을 edit으로 변경
  * 전부  commit --amend --author="hungryjay <aaa@aaa.com>"



## 200302

*cf* ))

* 공인 아이피란? 
  * 인터넷상 서로다른 PC끼리 통신을 위해 필요한 아이피
  * 20.94.163.19

* 사설 아이피

  * 내부망 전용 아이피
  * 196.168.9.127(개발용)

  

### ubuntu pc에서 ssh열고 window에서 putty로 접속

* 업무용에서 개발용 ssh로 접속해 docker container 바라보는 방법
  1. 개발용에 ssh 환경 설정 https://jmoon.co.kr/183
     1. sudo ufw enable
     2. sudo ufw allow 22(ssh default)
     3. sudo ufw reload
  2. window에 putty 설정
     1. 192.168.9.127(ssh 열어놓은 개발용 pc) 접속 및 포트 22
     2. (선택)기타 putty 설정(폰트 등)
  3. 연결 완료

* 연결 완료 후 업무용에서 개발용 docker container로 접속하는 방법

  1. 업무 -> 개발 접근할 fileport, listen(http) port등 개발용에서 열어놓기(위의 ufw command 이용)

  2. 개발용에서 localhost:24000/promanagerobs에서 node 생성해 열어놓기

     1. application 또한 만들어놓기(?)

  3. 업무용 prostudio에서 설정해놓은 port로 접근해 application 생성

     

* 참고

  * localhost:14000/promanager
  * localhost:24000/promanagerops
  * localhost:34000/promanagerrte(아마도..)



## 200303

* DO (Data Object)

  * SO 입출력 전용 Data를 담아서 DTO 역할 수행하는 객체
  * 사용자가 Promanager에서 in/out에 사용되는 프로퍼티(Meta정보)를 정의해 사용, 이에 대한 여러 메서드 제공(e.g. getter setter)
  * excel 파일을 이용해 DO객체 생성 가능

* DOF (Data Object Factory) - DB용 or FILE용

  * (DB전용)
  * DataBase에 접근 DAO 역할

  * Query 작성 후 저장시 자동으로 쿼리에 해당하는 소스 생성
  * (FILE전용)
  * layered file생성, 읽을 때 사용 객체