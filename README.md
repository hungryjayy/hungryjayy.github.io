# Scale-out-with-redis

## Scale out : 접속한 서버의 대수를 늘려 처리 능력 향상 시키는 것

- 서버의 가상화 기능을 사용
- 가상적으로 복수의 서버를 구축해 Scale out
- Sharding, queue, **In memory,** NoSQL, ...
- 분산처리, 병렬처리 등

### ↔ Scale up: 서버 자체를 증강

*c.f) Auto scale*

## Redis

- In memory data store
    - Disk 참조 X → 작업량 향상, 속도 향상
- 유연한 Data structure
    - 다양한 Data 유형(문자열, 목록, 세트, 해시 비트맵 등)
- 단순성(간결함)
- 확장성 및 가용성
- 상기 특성 덕에 **In memory cache 구현**에 적합

#### Reference
Redis 설치 https://redis.io/topics/quickstart


## 1/6 정리
* hset, hget을 이용해 소켓 통신 가능하도록 기존 로직 변경
 <img src = "./images/socketIO.png"><br>

* 이 과정에서 promise 활용 비동기 -> 동기식 처리
* filter, map 이용 코드 cleaning
 <img src = "./images/asyncPromise.png"><br>

* docker compose 환경설정 변경
 <img src = "./images/dockercompose.png"><br>
 
*depends_on -> restapi와 frontend가 해당 condition을 먼저 만족해야 해당 환경 실행*
 <img src = "./images/dependson.png"><br>
 
 
