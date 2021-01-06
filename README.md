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


## redis logic
```
return Promise.all([
            client.hset('socketToUser', ~~~, ~~~, () => { resolve(); }),
            client.hset('socketToRoom', ~~~, ~~~, () => { resolve(); }),
            client.hset('UserToSocket', ~~~,, ~~~,, () => { 
              client.hget('UserToSocket', ~~~,, (socket: any) => {
              
              });
              resolve(); 
            }),
          ]).then(() => {
```
          
* hset, hget을 이용해 소켓 통신 가능하도록 기존 로직 변경(위 코드는 psudo code)<br>
<br>

```
    return new Promise<void>((resolve) => {
      return socket.getusers (() => {
        return Promise.all(
          people
            .filter( ~~~~ => ~~~~~)
            .map(~~~ => { ~~~
            })
        ).then(() => {
          resolve();
        });
      });
    });
  }
```

* socket 로직을 요청하는 서버에서 위와같이 promise 활용(위 코드는 psudo code)
* filter, map 이용 코드 cleaning<br>
<br>

## docker compose
 <img src = "./images/dockercompose.png"><br>
* docker compose 환경설정 변경<br>
 <br>
 
 <img src = "./images/dependson.png"><br>
*depends_on -> restapi와 frontend가 해당 condition을 먼저 만족해야 해당 환경 실행*<br>
 <br>
 
## Load Balancing
 <img src = "./images/loadbalancing.png"><br>
 * ping restapi의 결과: 0.4와 0.5 ip를 왔다갔다 하는걸 볼 수 있음 - load balancing
    * 해당하는 4,5는 각각 하나의 restapi 서버들임<br>
 <br>

 <img src = "./images/restapi1.png"><br>
 
 <img src = "./images/restapi2.png"><br>
 
 
 <img src = "./images/dockercontainer.png"><br>
 * $ docker-compose ps 결과<br>
<br>
