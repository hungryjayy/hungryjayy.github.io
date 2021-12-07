# Tenth2 인터페이스와 사용예시

: tenth2의 기능을 사용하기 위해서는 **http api** or **tenth2 client library(자체 제공)**를 통해 사용할 수 있다.

<br>

## Http API

* 가장 일반적인 방법
* Tenth2 Web Gateway (TWG)가 tenth2의 api로 변환해준다.
* 인증 필요
* 공인 IP 존재해서 외부망에서 접근 가능하다.
* 기본 80, 443이고 ACL(Access control list. 접근제어목록) 오픈되어있어 방화벽 이슈에서 자유롭다.
* 몇가지 부가기능은 HTTP API를 통해서만 가능하다.

<br>

### 인증

* AWS S3와 유사한 시그니처 기반 방식
  1. Query string 인증: `a/b/c.png?...&Signature=xxxxxxxx`
  2. HTTP 헤더 방식 인증: `Authorization: TWG svd:xxxxxxxx`

<br>

## Client library

* tenth2 api를 사용하기 위한 라이브러리 자체제공.
* 다양한 언어로 구현되어있음
* **내부 통신 전용**. 외부에서 접근 시 방화벽 ACL 이슈가 있음.

<br>

## NFS(network file system)

: 클라이언트 OS에서 Tenth2 서비스 or 서브 디렉토리를 마운트해, 로컬 디렉토리처럼 사용하는 방식

* NAS와 같이 여러 클라이언트에서 파일을 공유하는 목적으로 사용하기 적합하다.
* DKOS에서 연동 가능해 여러 POD간 파일 가능
* 외부에서 접근 시 방화벽 ACL 이슈가 있을 수 있음

<br>

## 사용예시

* Backend Storage: 사용자가 리소스를 업로드하면, client 라이브러리로 저장하고, 읽어들일때는 HTTP API로 사용(카카오 VOD, 다음 메일)
* CDN Storage: 외부로부터 client 라이브러리로 전달받고, 전달받은 것을 사용자에게 HTTP API를 통해 실제 사용자가 볼 수 있는 애플리케이션에 전달.(카카오 미니, 카맵)
* User File Storage: 사용자가 tenth2에 업로드하면 다른 사용자가 이 것을 HTTP API를 통해 받아 사용 (카카오 메일 대용량 첨부)
* Shared Storage: 하나의 서버가 tenth2에 업로드하고, 다른 서버들에서는 tenth2에 저장된 리소스를 read해 사용

<br><br>

#### Reference) 카카오 사내 기술학습 - 분산 파일 시스템 Tenth2