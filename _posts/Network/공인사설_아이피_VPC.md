# 공인 아이피, 사설 아이피, VPC

## 공인 아이피

* 인터넷상 서로다른 PC끼리 통신을 위해 필요한 아이피

* ISP가 할당(Internet Service Provider)

  e.g ) 20.94.163.19



## 사설 아이피

* 내부망 전용 아이피

  * 외부에서 직접 접근할 수 없고 내부끼리 통신할 경우
  * 접근하려면 해당 망이 있는 **VPC**에 접근 먼저 한다음 해당 망(VPC) 내부의 사설 아이피로 접근해야함
  * 이걸 돕는 것(내부 어디에 연결해있는지)이 Routing table

  e.g ) 196.168.9.127

* 세가지 사설 아이피 대역

  Class A : 10.0.0.0 ~ 10.255.255.255(10/8 prefix)

  Class B : 172.16.0.0 ~ 172.31.255.255(182.16/12 prefix)

  Class C : 192.168.0.0 ~ 192.168.255.255(192.168/16 prefix)



## VPC

<img src="https://miro.medium.com/max/700/1*Ehn4uEQMtbmdPsU6MxVc3Q.png" alt="img" style="zoom:80%;" /> 

* 위 그림처럼 VPC별로 네트워크 구성. 각각은 독립적으로 네크워크
* VPC의 아이피범위를 `RFC1918`라는 사설 아이피 대역(위의 class A~C)에 맞춤
* 이 VPC를 더 잘개 쪼개놓은 것이 서브넷
  * **EC2에서 통신하려는 인스턴스끼리 같은 서브넷에 구성해두었어야하는데, 이걸 생각 못해 swarm 접속을 못했던 것**



## 200302

### ubuntu pc에서 ssh열고 window에서 putty로 접속

* 업무용에서 개발용 ssh에 접속

  1. 개발용에 ssh 환경 설정

     * port 방화벽 열어주기(ufw나 iptables 명령어)

     1. `sudo ufw enable`
     2. `sudo ufw allow 22(ssh default)`
     3. `sudo ufw reload`

  2. window에 putty 설정

     * 192.168.9.127(ssh 열어놓은 개발용 pc) 접속 및 포트 22

  3. 연결 완료




  * 연결 완료 후 업무용에서 개발용 docker container로 접근

    * 업무 -> 개발 접근할 fileport, listen(http) port등 개발용에서 열어놓기(위의 ufw command 이용)



#### Reference)

#### https://medium.com/harrythegreat/aws-%EA%B0%80%EC%9E%A5%EC%89%BD%EA%B2%8C-vpc-%EA%B0%9C%EB%85%90%EC%9E%A1%EA%B8%B0-71eef95a7098

#### https://velog.io/@hidaehyunlee/%EA%B3%B5%EC%9D%B8Public-%EC%82%AC%EC%84%A4Private-IP%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%A0%90

#### https://jmoon.co.kr/183