# 분산 파일 시스템 Tenth2

<br>

### 분산 파일 시스템이 필요한 이유

* **로컬 디스크**: 서비스 서버의 로컬 디스크에 파일 저장할 경우 서비스 서버의 장애에 대비해 백업 서버에 저장을 해야한다.
* **NAS**: 위의 문제를 해결하기 위해 NAS(네트워크 결합 스토리지)를 사용한다. 그러나, NAS 용량의 한계가 있다는 점이 있다. 해결 방안으로 NAS를 더 추가할 수 있는데, 파일 시스템을 어떻게 분산할 것인가에 대한 고민이 남아있다.
* **분산 파일 시스템**: 큰 규모의 데이터를 내부적으로 분산해 저장한다. 장애에 대한 대비가 되어있다.

<br>

##### NAS: 네트워크로 연결된 저장소(하드)

<br>

## Tenth2 특징

### 서비스

: Tenth2의 논리적인 partition.

* 서비스를 신청해 사용해야한다.
* 용도에 따라 구분하면 되고, 공개, 비공개, 보안 타입을 설정할 수 있다.
  * 공개, 비공개: HTTP API 사용에 따라 차이점이 있다. **GET(다운로드)시 인증 필요 여부**. (PUT, POST, DELETE는 항상 인증필요)
  * 보안: 데이터 저장 시 암호화.(개인정보와 관련된 것들)

<br>

### file path 기반

* 리눅스 file system과 유사하다.
* `/${service_id}/a/b/c.png`
  * service_id: tenth2 서비스 id
* full path가 파일조회를 위한 키가 된다.(따라서 디렉토리가 다 필요하다) -> 속도 향상

<br>

### 파일 속성(메타데이터)

* system-defined property
  * 미리 정의된 파일 속성
  * Content-Type, 이미지 width, height 등
* user-defined property
  * 임의의 K-V
  * 일반 파일시스템의 xattr과 유사

<br>

### Replica

* Hot Pool: 최근 업로드 된 3개의 Replica 유지
* Cold Pool: 일정 기간 이상 지난 2개의 Replica 유지

<Br>

### 파일 연산

* LIST, STAT, GET, DELETE, COPY, MOVE, TRUNCATE, PUT, HARDLINK, SYMLINK
* PUT - atomic하게 수행. 기본적으로 immutable.(append-only). 변경을 위해선 옵션 설정이 필요하다.

<br>

--------

## Tenth2 부가 기능

### On-demand Thumbnail

* 이미지파일에 대한 썸네일 생성 기능
* 두가지 방법이 있다.(fname, 쿼리스트링)
* 다양한 종류가 있다.(알볼로, 워터마크 썸네일)

<br>

### Transcoding

* 파일 형식을 변환
* 여러개를 한번에 변경도 가능하고, 미디어 자체(음성 생략 등) 변경도 가능

<br>

### Malware Detection

* 악성코드를 검사해준다.
* 별도 설정 안해도 백그라운드로 수행된다.

<br>

##### 위의 기능 외에도 다양한 부가 기능 존재

<br><br>

#### Reference) 카카오 사내 기술학습 - 분산 파일 시스템 Tenth2