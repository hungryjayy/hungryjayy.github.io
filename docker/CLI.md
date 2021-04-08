

* `docker container inspect {container ID}`
  * 헬스체크 로그?같은걸 볼수있음



* `docker logs {container ID}`



* docker-compose 실행시 `-d` <-- background로 실행
  * 없이 실행하면 실시간 로그를 확인 할 수 있다.



* 컨테이너 모두 삭제

  ​	docker rm `docker ps -a -q`



* `sudo docker system prune --volumes`
  * 저장해놓은 container들 완전 제거