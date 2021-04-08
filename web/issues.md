## 200302

* 공인 아이피란? 

  * 인터넷상 서로다른 PC끼리 통신을 위해 필요한 아이피
  * 20.94.163.19

* 사설 아이피

  * 내부망 전용 아이피

  * 196.168.9.127

    

### ubuntu pc에서 ssh열고 window에서 putty로 접속

* 업무용에서 개발용 ssh로 접속해 docker container 바라보는 방법

  1. 개발용에 ssh 환경 설정 ref) https://jmoon.co.kr/183

     * port 방화벽 열어주기

     1. `sudo ufw enable`
     2. `sudo ufw allow 22(ssh default)`
     3. `sudo ufw reload`

  2. window에 putty 설정

     * 192.168.9.127(ssh 열어놓은 개발용 pc) 접속 및 포트 22

  3. 연결 완료




* 연결 완료 후 업무용에서 개발용 docker container로 접속하는 방법

  * 업무 -> 개발 접근할 fileport, listen(http) port등 개발용에서 열어놓기(위의 ufw command 이용)