# Callback

### 콜백 기반 비동기 프로그래밍
* 함수 내부의 모든 작업을 수행하고 실행할 함수를 callback으로 전달 <br>

*c.f) script, module 로딩하는 과정 또한 비동기 과정*<br>
→ `loadScript` 내부에서 해당하는 script를 callback한다.<br>

 
### Callback in callback

* 콜백의 중첩. 코드를 복잡하게 만들며 지양해야 한다(콜백 지옥)<br>
* 해결방법<br>
  - 각각 독립적으로 함수화(재사용 불가, 비가시적)<br>
  - `Promise` 사용<br>


### Error handling
* 에러를 고려하는 방법
```
script.onload = () => callback(null, script);
script.onerror = () => callback(new Error(`${src}를 불러오는 도중에 에러가 발생했습니다.`));
```
