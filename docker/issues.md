## 200215

* Docker image 쓸 때 version을 꼭 명시하기
  * Docker hub 참고. docker image는 모두 hub로부터 온다.



## 200317

* Docker trouble shooting

  * docker-compose를 down 전에 변경한다면 `has active endpoints` 에러



## 200329

* 계속해서 용량이 부족한 문제 발생
  * docker-compose를 새로 실행해 container를 띄울때마다 용량 에러가 발생
  * 매 up 때마다 container에 대한 dummy data가 어마어마하게 쌓여있었다.
  * `sudo docker system prune --volumes` 를 통해 제거



