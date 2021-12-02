# DB 접근 통제와 작업 요청

<br>

#### DB에 접근하는 일반적인 방법

: Client Tool(SQL developer, mysql workbench) 이용 or DB 콘솔로 직접 접근

<br>

## DB 접근 통제 시스템이란?

: 해당 DB에 접근 권한을 보유하고 있는지, 권한이 어느정도까지인지 제어하는 것.

* Client tool과 DB 사이의 **통제 agent**가 게이트웨이 역할을 수행한다. 따라서, 유저는 DB에 직접 붙는 것이 아니라 에이전트에 붙는다. 권한이 있다면 이 서버가 **중개자**가 되고, 권한이 없다면 요청을 막는다.
  * 따라서 위의 툴들을 이용할 때 목적지 호스트, 포트는 접근통제 서버가 된다.

<br>

### 접근 통제 Agent

: 내부적으로 연결을 허용하는 client를 정해놓는다.(client 프로그램. e.g. mysql workbench). 또한, 유저에 대한 인증을 수행해 접근이 가능한 유저인지를 판단한다.

* Agent 뒷단의 master, slave DB 역할은 failover 과정에서 언제든 바뀔 수 있다. 접근 통제 Agent에서 직접 매번 요청에 맞게 매핑해주는게 아니라면(e.g HAProxy), 클라이언트는 요청 전 DB 리스트에서 DB 권한을 확인할 필요가 있다.
  * 변경사항을 인지하고, port forwarding 정보를 수정해준다.

<br><br>

#### Reference) 카카오 사내 기술학습 - DB 접근통제 시스템을 통한 DB 작업 요청법