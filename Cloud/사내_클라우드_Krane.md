# 사내 클라우드 시스템 Krane

: 카카오에서 제공하는 클라우드 서비스. Kakao + Crane(기중기).

* 국내 세 곳의 region에서 Prod, Dev로 나눠서 제공
  * Prod: 서비스를 위한 인스턴스, Dev: 개발을 위한 인스턴스
* 80, 443 port 외의 port에 대해선 따로 **ACL(접근 제어 목록)** 따로 신청해야 한다.

<br>

## 사용법

### Instance

* Prod: 직접 생성 불가. 직접 생성을 요청해야한다.
* Dev: 직접 Krane 관리화면에 접속해 instance 생성 가능하다. 적절히 OS, 스펙을 선택해서 생성한다. 나와있지 않은 것은 직접 요청 할 수 있다.
* **인스턴스 접근**: kerberos 인증 `kinit ${name}` -> fqdn으로 ssh 접근. `${host_name}.${region}.krane.9rum.cc`

<br>

### Volume

: 볼륨을 생성하고 존재하는 인스턴스와 연결 할 수 있다.

* 파티션 생성 및 포맷: `fdisk /dev/vdb; mkfs.ext4 /dev/vdb1; mkdir /ext; mount /dev/vdb1 /ext`

<br>

### Quota

: 최대 생성량을 넘을 때 증설 가능.

<br>

### Member 관리

: 추가, 삭제하려는 멤버 관리 가능

<br>

### 소유권 이전

: 부서이동이나 퇴사시 vm 소유권 양도 가능

<Br><br>

#### Reference) 카카오 사내 기술학습 - 카카오 사내 클라우드 시스템 Krane