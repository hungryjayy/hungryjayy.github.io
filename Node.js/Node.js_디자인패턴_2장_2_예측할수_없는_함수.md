# Node.js 디자인 패턴 2장 - 2. 예측할 수 없는 함수

<br>

## 예측할 수 없는 함수 

```javascript
const fs = require('fs');
const cache = {};
function inconsistentRead(filename, callback) {
    if(cache[filename]) { // 동기적으로 호출
        callback(cache[filename]);
    } else { // 비동기적 호출
        fs.readFile(filename, 'utf8', (err, data) => {
            cache[filename] = data;
            callback(data);
        })
    }
}
```

* 위의 `inconsistentRead()`는 캐싱된 파일이 있냐 없냐에 따라 동기적 / 비동기적으로 동작한다.

```javascript
function createFileReader(filename) {
    const listeners = [];
    inconsistentRead(filename, value => {
        listeners.forEach(listener => listener(value));
    });
    
    return {
        onDataReady: listener => listeners.push(listener)
    };
}
// -----------------

const reader1 = createFileReader('data.txt');
reader1.onDataReady(data => {
    console.log(data);
    
    const reader2 = createFileReader('data.txt');
    reader2.onDataReady(data => {
        console.log(data);
    })
})
```

* 위의 시나리오에서 `reader2`의 listener는 동작하지 않는다. `reader1`의 경우 `inconsistentRead`가 비동기로 동작해 **비동기식 연속 전달 방식** 으로 동작하지만, `reader2`의 경우 동기적으로 호출되기 때문에 **동기 연속 전달 방식(CPS)** 으로 동작하기 때문
* 따라서, 결과를 예측하기 어렵고 실제 어플리케이션의 상황이라면 더더욱 어려울 수 있다.

<br>

### 동기 API 사용 

* **완전히 동기식**으로 만들기 : **동기 I/O 사용**으로 **블로킹 되는 것**은 많은 경우 권장되지 않지만, **가장 쉽고 효율적인 해답**이 될 수 있다.

<br>

### 지연 실행

* **완전히 비동기**로 만들기 : 동기처리 부분에 `process.nextTick()`사용 -> 콜백을 대기중인 I/O 이벤트 큐 대기열의 맨 앞으로 밀어넣고, 즉시 반환해 이벤트 루프의 다음 사이클까지 함수의 실행을 지연시킴
  * 가장 앞으로 밀어넣기 때문에 `nextTick()` 는 starvation을 발생시킬 수 있다. -> `setImmediate()`로 해결

<br><br>

#### Reference) Node.js 디자인패턴