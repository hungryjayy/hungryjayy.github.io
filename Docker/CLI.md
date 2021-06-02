# CLI

* `docker container inspect {container ID}`
  * 헬스체크 로그?같은걸 볼수있음



* `docker logs {container ID}`



* docker-compose 실행시 `-d` <-- background로 실행
  * 없이 실행하면 실시간 로그를 확인 할 수 있다.



* 컨테이너 모두 삭제

  ​	`docker rm $(docker ps -a -q)`
  
  * 위와 같은 CLI 내부 $(CLI) 와 같은 형태는 내부 ps를 통해 전체에 대해 CLI를 주고자 할 때 좋음.



* `sudo docker system prune --volumes`
  * 저장해놓은 container들 완전 제거



* `--build` : 도커 컨테이너 이미지 생성
  * 이미지는 불변이기 때문에 설정 변경 있을 때 이걸 해줘야 한다.