# 3 way, 4 way handshake

: UDP와 다르게 TCP 통신에서는 장치 사이에서 연결을 보장하고 명확한 전송을 하기 위해 conn을 맺고 끊을 때 3-way, 4-way handshake와 같은걸 한다.



## 3 way handshake

: TCP 연결을 초기화 할 때 사용 

![img](https://t1.daumcdn.net/cfile/tistory/225A964D52F1BB6917)

1. SYN(n): 클라이언트가 서버에게 SYN 를 보낸다. 이 때 시퀀스넘버라는 난수를 생성해 SYN에 붙여서 보낸다.
   * 난수를 보내는 이유: 다른 SYN 요청과의 구분
   * 보낸 후 client는 **SYN_SENT** 상태가 됨
2. ACK(n+1) + SYN(m): 서버가 클라이언트에게 ACK(요청 수락)와 SYN를 보낸다. 마찬가지로 시퀀스 넘버를 붙여서 보낸다.
   * 서버는 **SYN_RECEIVED** 상태가 됨 
3. ACK(m+1): 클라이언트가 ACK를 보내고 **ESTABLISHED**가 되고, 서버는 받고서 **ESTABLISHED**가 되며 이후로 데이터가 오간다.



## 4 way handshake

: TCP 연결을 종료하기 위해 수행하는 절차

![img](https://t1.daumcdn.net/cfile/tistory/2152353F52F1C02835)

1. FIN: 클라이언트가 연결 종료 메시지 FIN플래그를 보낸다
2. ACK: 서버가 확인메시지를 보낸다. **CLOSE-WAIT** 상태
3. FIN: 서버가 통신 끝났다는 메시지인 클라이언트에게 FIN플래그를 보낸다.
4. ACK: 클라이언트가 ACK를 보낸다.
   * 이 때 둘다 **CLOSED** 상태 



#### 지연으로 패킷이 FIN보다 늦어지는 상황

* 세션 종료 후 전송되는 패킷은 drop, 데이터 유실 됨.
* **TIME-WAIT** 상태: 이러한 상황에 대비해 client는 FIN을 수신하더라도 세션을 남겨놓고 패킷을 기다림.



#### 연결 끊는 상황에서 3 way 아닌 4 way로 구성하는 이유

* Client 데이터 전송이 끝났어도 Server쪽에서 아직 보낼 데이터가 남아있을 수 있기 때문에
* 따라서, 받은 FIN에 대한 ACK만 보내고, **보낼 것이 있으면 다 보내고** 난 후 본인도 FIN을 보낸다.



#### 시퀀스넘버를 난수로 난리는 이유

* Port를 통해 연결하고 데이터를 교환하고 다시 끊고 나서, 시간이 지나고서 같은 포트를 다시 또 사용한다. 따라서, 과거에 사용된 포트번호 쌍을 사용할 가능성이 존재하기 때문에, 과거의 SYN을 현재의 SYN으로 인식할 수 있다.(오해가 생길 수 있다)



#### Reference)

#### https://mindnet.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%89%BD%EA%B2%8C-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-22%ED%8E%B8-TCP-3-WayHandshake-4-WayHandshake

#### https://github.com/WeareSoft/tech-interview/blob/master/contents/network.md#tcp%EC%9D%98-3-way-handshake%EC%99%80-4-way-handshake