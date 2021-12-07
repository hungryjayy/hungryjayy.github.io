# 쿠버네티스 기반 DKOS

<br>

#### VM 가상화 vs 컨테이너 가상화

* VM 가상화: 리눅스 커널 위에 guest OS가 올라간다.
* 컨테이너 가상화: 리눅스 커널의 네임스페이스나 cgroup 등의 기술을 활용해 호스트의 커널을 공유하고, 때문에 훨씬 **가벼운 가상화 환경**을 제공한다.

<br>

#### Docker

* 가상화 기술을 활용해 컨테이너를 생성하고 서비스할 수 있는 환경 제공
* 하나의 머신에 유효. 스웜을 사용하면 다중 머신에서도 가능하긴하다. -> **쿠버네티스 등장**

<br>

## Kubernetes란?

* 네트워크, 스토리지 등을 개발자가 제어하고 자동화 가능.
* 선언형: 리소스의 형상을 yml에 정의할 수 있다.

<br>

## DKOS

: 카카오의 **Kubernetes** 기반 **클러스터 오케스트레이션** 서비스. **dkos 내부에서는 쿠베에서 사용하던 것처럼 사용 가능**

* **dkos 화면**에서는 **클러스터를 생성**하고, 생성 후에는 클러스터 사용자 추가 삭제 가능
  * dkos상 owner or member 권한에 따라서 가능.
* 쿠버상에서는 모두 admin 권한.
* dkos 생성하면 그 내부에는 노드들이 있다.
* **모니터링**: 사우론
* **대시보드**: dkos 토큰값으로 쿠베 대쉬보드 사용 가능하고 cron job이나 deployment 상태들 확인 가능
  * 유용하다고 함

<br>

### dkosctl

* `dkosctl sc`: dkos constol select cluster로 원하는 클러스터 조회 가능
* select 후 dkos 내부에 들어가면 이후로는 `kubectl get nodes` 처럼 쿠베 사용하듯 사용하면 됨.

<br>

## DKOS 아키텍처 - 세가지 타입의 node

#### Master: 쿠버네티스 운영 컴포넌트 동작

<br>

#### Ingress node

: ingress nginx controller라는 nginx 애플리케이션이 뜬다.

* DSR-manager가 있어, 응답에 대해선 DSR이 적용되어 응답이 간다.

<br>

#### worker node

: 사용자 application이 올라간다.

<br><br>

#### Reference) 카카오 사내 기술학습 - DKOS와 D2HUB 1~8강



