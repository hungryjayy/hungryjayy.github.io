# Redis를 통해 서버 Scale out에서 자원 공유

### Scale out : 접속한 서버의 대수를 늘려 처리 능력 향상 시키는 것

: 서버의 수를 늘림. 복수의 서버를 구축해 Scale out

* 공유 자원에 대해 고려해야 한다.

<br>

### Scale up: 서버 자체를 증강

: 인스턴스를 더 좋은 인스턴스로 변경. e.g) EC2를 사용하다가 더 성능좋은것으로 이관하는 것

*c.f) 클라우드환경에서 Auto scaling 한다는 것은 트래픽에 따라 scale out / in 한다는 것*

<br>

## Redis

- In memory data store
    - Disk 참조 X → 작업량 향상, 속도 향상
    - **RDB**: failover를 위해 특정 시점의 스냅샷을 디스크에 저장하긴 한다. 실제로 이 작업이 오래걸려서, redis 장애가 발생하는 경우가 많다고 한다.
- Key value쌍의 다양한 Data structure: 문자열, 목록, 세트, 해시 비트맵 등
- Socket.io 지원 -> Redis가 브로커 역할 수행
- 확장성 및 가용성 - 클러스터링 가능. HA에 용이
    - 싱글 스레드 기반이다. 따라서, I/O가 많아 의미있는 block이 발생할만한 작업을 조심해야한다. Redis 장애는 `getKeys()` , `flush()` 처럼 다건의 레코드 처리 커멘드때문에 발생하는 경우일 때가 꽤 있다고 한다.
    - 다건의 문제를 해결하기 위해 cursor 매커니즘을 활용한다. scan을 이용해 일정 개수만큼만 조회하는 방식.

<Br>

#### Memcached와의 비교

* 둘다 key-value이다
* Memcached는 멀티스레드이다. global cache lock을 통해 thread safety를 가능하게 한다. 

<br>

### Adapter: Redis

: Redis가 어댑터로써 **서버간 브로커 역할**을 수행한다.

* 소켓서버의 특정 Room에 메시지를 보내면, Redis는 브로커가 되고 각 서버들에게 전달된다.
* 여러 서버가 redis로 연결될 때 채널 개념이 발생하고 이 채널 정보는 socket server에서 들고 있는다. 각 socket server는 메시지를 받으면 서버에서 구독하고 있는 채널에 메시지가 해당하는지를 찾고 맞으면 메시지를 해당 Room에 준다.
* Pub/Sub 매커니즘이 있다. 각 socket server는 n개의 채널을 subscribe할 수 있다.
* **Adapter 역할**
  * 일종의 cluster처럼 연결된 각 서버에 broadcast 하는 방식
  * routing message의 역할을 수행하는 Interface

<img src = "./images/redisadapter.png">

```javascript
const redisAdapter = require('socket.io-redis');

const io = require('socket.io');
```

```javascript
socketServer = io(baseServer)
  .adapter(redisAdapter({
    host: 'redis',
    port: 6379
}));
```

<br>


## Code level example

```javascript
const redis = require('redis');

// default port 6379
const client = redis.createClient({ host: 'redis', port: 6379 });

// Unhandled Error를 방지하기 위해 아래 error 이벤트에 대한 핸들링을 꼭 해야한다.
client.on('error', (error: any) => {
  debug(error);
});
```
<br>

```javascript
client.hset(hash, key, val, (err, res) => {
    console.log(res); // 1 or 0
}

client.hget(hash, key, (err, res) => {
    console.log(res); // value
}
```

<br>

* Promisify: 위 코드를 깔끔하게 Promise로 이용하기 위해 아래와 같이 구현(이와 같이 여러 method 구현해 놓고 필요에 따라 사용)
```javascript
public set(hash: string, key: string, value: string) {
    return new Promise((resolve) => {
      client.hset(hash, key, value, (err, result) => {
        resolve(reply);
      })
    })
  }
```

<br><br>

#### Reference)

#### reference) <br>

#### https://socket.io/docs/v3 <br>

#### https://socket.io/docs/v3/using-multiple-nodes/

#### Redis 로컬 설치: https://redis.io/topics/quickstart
