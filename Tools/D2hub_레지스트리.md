# 사내 레지스트리 - D2hub

: 사내 컨테이너 이미지 저장소

<br>

*c f) GitOps: 빌드 및 배포과정을 git을 통해 관리*

<br>

## 사용법

* **자동 빌드**를 사용하기 위해서 git repository를 연결해야한다.

* docker 사용하던 것 처럼 pull, login, push 할 수 있다.

  ```shell
  docker pull ${registry}/${image}
  
  docker login ${registry} -u ${user} -p ${pass}
  
  docker build -t ${registry}/${image}/${tag}
  docker push ${registry}/${image}/${tag}
  ```

<br>

## 자동 빌드, 자동 배포

* d2hub 내에 존재하는 레포지토리에 대해 **배포 룰**을 정의한다. 쿠버상 **어디에 배포**할 것인지 **namespace**, **클러스터** 설정해야한다.
* 설정한 태그에 대해서는 `git commit`만 하면 **빌드, 배포**과정이 자동으로 수행된다.
  * 이후, 배포 룰에 설정되어있는 dkos 클러스터 내부에서 쿠베를 확인해보면 pod가 종료되고 새로운게 뜨는 과정을 확인할 수 있다.

<br><br>

#### Reference) 카카오 사내 기술학습 - DKOS와 D2HUB 9강~