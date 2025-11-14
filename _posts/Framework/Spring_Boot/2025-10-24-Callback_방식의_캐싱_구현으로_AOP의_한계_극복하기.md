---
layout: post

title: Callback 방식의 캐싱 구현으로 AOP의 한계 극복하기

author:
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [spring, spring boot, 스프링, 스프링부트, redis, 레디스, cache, 캐시]

featuredImage:

img:

categories: [Framework, Spring_Boot]

date: '2025-10-10'

extensions:

  preset: gfm

---

<br>

: Spring의 `@Cacheable` 어노테이션은 편리하지만, **같은 클래스 내부에서 메서드를 호출할 때 AOP가 동작하지 않는다는 한계**가 있다. 이를 해결하기 위해 **callback 방식의 고차함수**를 활용한 캐싱 로직을 구현했다. 이 방식은 AOP의 한계를 극복하면서도 캐싱 로직을 재사용 가능하게 만들어준다.

<br>

## @Cacheable의 한계

* Spring의 `@Cacheable`은 AOP 기반으로 동작한다.
* AOP는 **프록시 패턴**을 통해 동작하기 때문에, **같은 클래스 내부에서 메서드를 호출할 때는 프록시를 거치지 않아** 캐싱이 동작하지 않는다.
  * e.g.) Service 클래스 내부에서 `this.getCachedData()`를 호출하면 `@Cacheable`이 적용되지 않음

<br>

### 문제 상황

```kotlin
@Service
class UserService {
    @Cacheable("users")
    fun getUserById(id: Long): User {
        // DB 조회
    }

    fun processUser(id: Long) {
        // 같은 클래스에서 호출 - 캐싱이 동작하지 않음!
        val user = this.getUserById(id)
        // ...
    }
}
```

<br>

## Callback 방식의 캐싱 구현

: **고차함수를 callback으로 전달**받아, 캐시 미스 시에만 해당 로직을 실행하는 방식으로 구현한다.

<br>

### 1. CacheDao 인터페이스 설계

```kotlin
interface CacheDao {
    fun <T> cache(
        key: String,
        ttl: Duration?,
        typeRef: TypeReference<T>,
        callBack: () -> T?,
    ): T?

    fun <K, V> cacheBulk(
        keys: List<K>,
        keyMapper: (K) -> String,
        ttl: Duration?,
        typeRef: TypeReference<V>,
        callBack: (List<K>) -> Map<K, V>,
    ): Map<K, V>
}
```

* **cache()**: 단일 키에 대한 캐싱
* **cacheBulk()**: 여러 키에 대한 벌크 캐싱
* **callBack**: 캐시 미스 시 실행될 로직을 고차함수로 전달받음

<br>

### 2. 구현부 - cache() 메서드

```kotlin
override fun <T> cache(
    key: String,
    ttl: Duration?,
    typeRef: TypeReference<T>,
    callBack: () -> T?,
): T? {
    // 1. 캐시 조회
    redisTemplate.opsForValue().get(key)?.let { raw ->
        return convertToValue(raw, typeRef)
    }

    // 2. 캐시 미스 - callback 실행
    val computed = callBack.invoke()

    // 3. 결과를 캐시에 저장
    computed?.let {
        val json = objectMapper.writeValueAsString(it)
        if (ttl != null) {
            redisTemplate.opsForValue().set(key, json, ttl)
        } else {
            redisTemplate.opsForValue().set(key, json)
        }
    }

    return computed
}
```

<br>

### 3. 구현부 - cacheBulk() 메서드

```kotlin
override fun <K, V> cacheBulk(
    keys: List<K>,
    keyMapper: (K) -> String,
    ttl: Duration?,
    typeRef: TypeReference<V>,
    callBack: (List<K>) -> Map<K, V>,
): Map<K, V> {
    if (keys.isEmpty()) return emptyMap()

    val keyToRedisKey: Map<K, String> = keys.associateWith(keyMapper)
    val redisKeys: List<String> = keyToRedisKey.values.toList()

    // 1. 캐시에서 일괄 조회
    val redisRawValues: List<String?> =
        redisTemplate.opsForValue().multiGet(redisKeys) ?: emptyList()

    // 2. 캐시 히트된 데이터 처리
    val redisHitMap: Map<K, V> = keyToRedisKey.keys
        .zip(redisRawValues)
        .filter { (_, rawValue) -> rawValue != null }
        .associate { (key, rawValue) ->
            key to objectMapper.readValue(rawValue!!, typeRef)
        }

    // 3. 캐시 미스된 키만 추출
    val missedKeys: List<K> = keys.filterNot { redisHitMap.containsKey(it) }

    // 모두 캐시 히트
    if (missedKeys.isEmpty()) {
        return redisHitMap
    }

    // 4. 캐시 미스 - callback 실행 (미스된 키만 전달)
    val callBackResults: Map<K, V> = callBack(missedKeys)

    // 5. 결과를 캐시에 일괄 저장
    val redisKeyValueMap: Map<String, String> = callBackResults
        .mapNotNull { (key, value) ->
            val redisKey = keyToRedisKey[key] ?: return@mapNotNull null
            val jsonValue = objectMapper.writeValueAsString(value)
            redisKey to jsonValue
        }.toMap()

    if (ttl == null) {
        redisTemplate.opsForValue().multiSet(redisKeyValueMap)
    } else {
        redisKeyValueMap.forEach { (redisKey, jsonValue) ->
            redisTemplate.opsForValue().set(redisKey, jsonValue, ttl)
        }
    }

    return redisHitMap + callBackResults
}
```

<br>

## 사용 예시

### 단일 캐싱

```kotlin
@Service
class ProductService(
    private val cacheDao: CacheDao,
    private val productRepository: ProductRepository,
) {
    fun getProduct(productId: Long): Product? {
        return cacheDao.cache(
            key = "product:$productId",
            ttl = Duration.ofMinutes(10),
            typeRef = object : TypeReference<Product>() {},
        ) {
            // 캐시 미스 시 실행될 로직 (DB 조회)
            productRepository.findById(productId)
        }
    }
}
```

<br>

### 벌크 캐싱

```kotlin
@Service
class ProductService(
    private val cacheDao: CacheDao,
    private val productRepository: ProductRepository,
) {
    fun getProducts(productIds: List<Long>): Map<Long, Product> {
        return cacheDao.cacheBulk(
            keys = productIds,
            keyMapper = { id -> "product:$id" },
            ttl = Duration.ofMinutes(10),
            typeRef = object : TypeReference<Product>() {},
        ) { missedIds ->
            // 캐시 미스된 ID들만 DB 조회
            productRepository.findAllByIdIn(missedIds)
                .associateBy { it.id }
        }
    }
}
```

<br>

## 장점

### 1. AOP 한계 극복

* 같은 클래스 내부에서 호출해도 캐싱이 정상 동작한다.
* 프록시를 거치지 않아도 되므로, 어디서든 사용 가능하다.

<br>

### 2. 성능 최적화

* **cacheBulk()**: 캐시 미스된 키만 callback으로 전달하여, **불필요한 DB 조회를 방지**한다.
  * e.g.) 100개 중 20개만 캐시 미스 → callback은 20개에 대해서만 실행
* Redis의 `multiGet`, `multiSet`을 활용해 **네트워크 왕복 횟수를 최소화**한다.

<br>

### 3. 유연성

* callback을 통해 캐시 미스 시 실행할 로직을 자유롭게 정의할 수 있다.
* DB 조회뿐만 아니라, 외부 API 호출, 복잡한 계산 등 다양한 로직에 적용 가능하다.

<br>

### 4. 재사용성

* 캐싱 로직이 `CacheDao`에 집중되어 있어, **중복 코드를 제거**할 수 있다.
* 캐시 정책 변경 시 한 곳만 수정하면 된다.

<br>

## 결론

: Callback 방식의 캐싱 구현은 **AOP의 한계를 극복하면서도 재사용 가능한 캐싱 로직**을 제공한다. 특히 벌크 캐싱에서는 캐시 미스된 키만 선별적으로 처리함으로써 성능을 크게 향상시킬 수 있다. 고차함수를 활용한 이러한 패턴은 Spring의 선언적 캐싱이 적합하지 않은 상황에서 강력한 대안이 될 수 있다.

<br><br>

#### Reference)

https://docs.spring.io/spring-framework/reference/integration/cache.html

https://docs.spring.io/spring-data/redis/docs/current/reference/html/
