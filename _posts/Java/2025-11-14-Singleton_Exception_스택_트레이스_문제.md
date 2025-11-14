---

layout: post

title: Singleton Exception 재사용으로 인한 스택 트레이스 문제

author:
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [java, kotlin, exception, debugging, jvm]

featuredImage:

img:

categories: [Study, Java]

date: '2025-11-14'

extensions:

  preset: gfm

---

<br>

## 문제 발견

: 운영 중인 서비스의 APM(Application Performance Monitoring) 시스템을 모니터링하던 중, 이상한 현상을 발견했다. 특정 API에서 발생한 Exception의 스택 트레이스가 실제 발생 위치와 전혀 다른 경로를 가리키고 있었다. 더욱 이상한 점은, **모든 Exception들이 동일한 호출 스택을 가지고 있다**는 것이었다.

<br>

### 실제 케이스 (예시 코드)

* **발생 API**: `/api/users/{id}/activate`
* **실제 발생 위치**: `UserActivationService.kt:110`
* **APM에 기록된 스택**:
  
  ```
  com.example.service.exception.BusinessException
      at BusinessException.<clinit>(BusinessException.kt:53)
      at UserValidationService.validateUser(UserValidationService.kt:146)
      at NotificationService.updateUserInfo(NotificationService.kt:109)
      at VirtualThread.run(...)
  ```

에러는 `/api/users/{id}/activate` API에서 발생했는데, APM에는 `/api/notifications/poll` API의 Virtual Thread 경로로 기록되어 있었다. 이건 **발생할 수 없는 동선** 이었고, 디버깅을 어렵게 만들었다.

<br>

## 원인 분석

### Singleton Exception 패턴

```kotlin
// BusinessException.kt
class BusinessException(val type: ErrorType) : RuntimeException() {
    companion object {
        val USER_NOT_FOUND = BusinessException(...)  // Line 50
        val PERMISSION_DENIED = BusinessException(...)  // Line 53
        val INVALID_PARAMETER = BusinessException(...)
        // ... 20여 개의 Exception 싱글톤
    }
}

// 사용
throw BusinessException.USER_NOT_FOUND  // 싱글톤 재사용
```

: 문제가 된 코드는 위와 같은 패턴으로, 각 Exception을 싱글톤 인스턴스로 만들어 재사용하는 패턴이었다. 메모리 효율적이고 편리해 보이지만 이것이 문제의 원인이었다.

<br>

### Exception의 스택 트레이스 캡처 메커니즘

Exception은 **생성 시점**에 스택 트레이스를 캡처한다.

```java
// java.lang.Throwable
public class Throwable {
    private StackTraceElement[] stackTrace;  // 인스턴스 필드

    public Throwable() {
        fillInStackTrace();  // 생성자에서 현재 스택을 캡처
    }

    public synchronized Throwable fillInStackTrace() {
        // 한 번 캡처된 스택은 변경되지 않음
        if (stackTrace != null || backtrace != null) {
            this.stackTrace = getOurStackTrace();
            return this;
        }
    }
}
```

핵심은 다음과 같다:
* Exception은 **생성자에서** 현재 스택 트레이스를 캡처한다
* 한 번 캡처된 스택은 **변경되지 않는다**
* throw할 때마다 갱신되지 않고 **생성 시점의 스택을 유지**한다

<br>

### `<clinit>`과 Companion Object 초기화

Kotlin의 companion object는 Java의 static 멤버로 컴파일되며, `<clinit>` (Class Initialization) 메서드에서 한 번에 초기화된다.

```kotlin
companion object {
    val ERROR_A = MyException("A")  // Line 3
    val ERROR_B = MyException("B")  // Line 4
    val ERROR_C = MyException("C")  // Line 5
}
```

위 코드는 다음과 같이 컴파일된다:

```java
static {};  // <clinit> 메서드
  Code:
     0: new Companion
     // ...
    11: new MyException("A")
    20: putstatic ERROR_A
    23: new MyException("B")
    32: putstatic ERROR_B
    35: new MyException("C")
    44: putstatic ERROR_C
    57: return
```

**중요한 점은 `<clinit>`는 하나의 메서드이며, atomic하게 동작해 한 번 실행되면 중간에 멈출 수 없다는 것** 따라서 `ERROR_B`만 접근해도 `ERROR_A`, `ERROR_B`, `ERROR_C` 모두가 생성된다.

<br>

### 문제 시나리오

#### T1: 서버 시작 후 첫 요청 - `/api/notifications/poll` API

```kotlin
// NotificationService.kt:72
updateUserInfo(...)
// → UserValidationService.validateUser:146
// → throw BusinessException.PERMISSION_DENIED
//   ↑ companion object 첫 접근 → <clinit> 실행
```

이 시점에 companion object의 **모든 Exception 싱글톤이 한 번에 생성**된다:

```kotlin
// <clinit> 실행 중
val USER_NOT_FOUND = BusinessException(...)  // Line 50
// → fillInStackTrace() 호출
// → 스택 캡처: [<clinit>:50, validateUser:146, poll, VirtualThread]

val PERMISSION_DENIED = BusinessException(...)  // Line 53
// → fillInStackTrace() 호출
// → 스택 캡처: [<clinit>:53, validateUser:146, poll, VirtualThread]
```

#### T2: 이후 요청 - `/api/users/{id}/activate` API (일반 Thread)

```kotlin
// UserActivationService.kt:110
require(...) {
    throw BusinessException.USER_NOT_FOUND  // 싱글톤 재사용
}
```

**APM에 기록되는 스택:**
* Exception 객체는 이미 T1에서 생성된 싱글톤 객체를 재사용
* T2 스택 트레이스는 T1에서 캡처된 poll 경로를 그대로 유지
* 실제 throw 위치(Line 110)는 **기록되지 않는다**

<br>

## 로컬 재현 실험

: 문제를 명확히 이해하기 위해 로컬 환경에서 재현 실험을 진행했다.

<br>

### 실험 방법

1. `ErrorController.kt:55`에 `e.printStackTrace()` 추가
2. 서버 실행 후 순차적으로 API 호출

<br>

### 실험 결과

#### 첫 번째 요청: `/api/notifications/poll`

```
BusinessException(exceptionType=PERMISSION_DENIED, errorMessage=null)
    at BusinessException.<clinit>(BusinessException.kt:64)
    at AuthenticationInterceptor.preHandle(AuthenticationInterceptor.kt:41) // < 여기
    at HandlerExecutionChain.applyPreHandle(...)
    at DispatcherServlet.doDispatch(...)
    ... (Spring Filter Chain)
```

#### 두 번째 요청: `/api/users/{id}/activate`

```
BusinessException(exceptionType=USER_NOT_FOUND, errorMessage=null)
    at BusinessException.<clinit>(BusinessException.kt:50)
    at AuthenticationInterceptor.preHandle(AuthenticationInterceptor.kt:41) // < 여기
    at HandlerExecutionChain.applyPreHandle(...)
    at DispatcherServlet.doDispatch(...)
    ... (동일한 Spring Filter Chain)
```

### 핵심 발견

* Exception type과 `<clinit>` line number는 다르다 (64 vs 50). **하지만 나머지 호출 스택은 완전히 동일**하다
* 실제 throw 위치(`UserActivationService:110`)는 스택에 없고 모두 companion object가 초기화된 시점(`AuthenticationInterceptor:41`)의 스택을 가진다

<br>

## 정리

### 모든 Exception이 같은 스택을 갖는 이유

### `<clinit>` 실행 중 모두 생성

모든 Exception 객체가 **같은 `<clinit>` 메서드 실행 중**에 순차적으로 생성되므로

1. **호출 스택의 root는 동일**
   
   * 모두 `<clinit>` 내부에서 생성됨
   * `<clinit>`를 호출한 경로도 동일 (예: `AuthenticationInterceptor:41` → `<clinit>`)
   
2. **차이점은 `<clinit>` 내부 line number만**
   
   * `ERROR_A.stackTrace[0]`: `<clinit>:50`
   * `ERROR_B.stackTrace[0]`: `<clinit>:53`
   * line number를 제외한 나머지 스택은 100% 동일
   
3. **나머지 스택은 완전히 동일**
   ```
   ERROR_A.stackTrace = [<clinit>:50, AuthenticationInterceptor:41, ...]
   ERROR_B.stackTrace = [<clinit>:53, AuthenticationInterceptor:41, ...]
                         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 동일!
   ```

<br>

## 해결 방안

### Singleton 패턴 제거 → 매번 새 인스턴스 생성

#### 현재 방식 (문제)

```kotlin
companion object {
    val USER_NOT_FOUND = BusinessException(ErrorType.USER_NOT_FOUND)
}

// 사용
throw BusinessException.USER_NOT_FOUND  // 싱글톤 재사용
```

#### 개선안 1: 직접 생성

```kotlin
// companion object 제거

// 사용
throw BusinessException(ErrorType.USER_NOT_FOUND)
```

#### 개선안 2: 팩토리 함수

```kotlin
companion object {
    fun userNotFound() = BusinessException(ErrorType.USER_NOT_FOUND)
    fun invalidParameter() = BusinessException(ErrorType.INVALID_PARAMETER)
}

// 사용
throw BusinessException.userNotFound()  // 매번 새 인스턴스
```

<br>

## 결론

: Exception은 **생성 시점**에 스택 트레이스를 캡처하며, 이는 변경되지 않는다. 따라서 Exception은 **throw하는 시점마다 새로 생성**해야 정확한 스택 트레이스를 얻을 수 있다.

<br>

<br>

#### Reference)

* Java Virtual Machine Specification §5.5 - Initialization
* https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-5.html#jvms-5.5
