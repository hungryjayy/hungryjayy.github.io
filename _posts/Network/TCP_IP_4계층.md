# TCP / IP 프로토콜의 4계층

<img src="https://blog.kakaocdn.net/dn/bTFbPP/btqObZlPFkB/rtTQk14mYJGkjx3T9cGfd1/img.png" alt="img" style="zoom: 67%;" /> 

* OSI와의 비교



## 계층 별 역할



### 1계층: 네트워크 인터페이스 계층(Network Interface, Network Access)

: TCP/IP 패킷을 받고, 보내는 역할

* OSI의 1, 2계층
* 에러 검출, 패킷의 프레임화를 통해 물리계층으로



### 2계층: 인터넷 계층(Internet Layer)

: 노드 간 패킷을 전송, 라우팅 기능 담당

* OSI의 3(네트워크)계층
* 전송 계층에서 받은 데이터에 패킷을 붙여 **최종 목적지까지 정확한 전송**(라우팅)을 처리
* **IP** 프로토콜
* **Port 정보**가 있는 전송 계층에 **IP 헤더**를 붙여서 아랫층으로 전달



### 3계층: 전송 계층(Transport Layer)

: 노드 간 연결을 제어

* OSI의 4(전송)계층
* 종단 간 신뢰성있는 전송을 제공
* 논리 주소, **포트**를 가지고 있음
  * 포트를 갖고 여러 개의 **TCP 커넥션**(한 IP에서 각 Port를 통해 각 커넥션 구별)을 유지
* TCP, UDP



### 4계층: 응용 계층(Application Layer)

: 서비스에 접근할 수 있는 어플리케이션 제공

* OSI의 5~7 계층
* 어플리케이션들이 데이터를 교환하기 위해 사용하는 프로토콜 정의
* 브라우저같은 것이 이 계층
  * 따라서 포트번호 사용. (HTTP의 경우 80)
* HTTP, Telnet, SSH, FTP ..



#### Reference)

#### https://devowen.com/344#recentComments

#### https://ryusae.tistory.com/4