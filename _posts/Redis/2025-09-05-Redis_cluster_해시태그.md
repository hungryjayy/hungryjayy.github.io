---
layout: post

title: Redis cluster 해시태그

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [redis, 레디스]

featuredImage: 

img: 

categories: [Study, Redis]

date: '2025-09-05'

extensions:

  preset: gfm

---

: Redis cluster를 사용할 때 n개의 키에 multiGet을 하면, 각각의 키가 저장된 슬롯으로 n번의 get 호출이 가게 된다. 이후, redis 클라이언트에서 이를 조합하는데 성능이 많이 떨어지게 된다. hashTag를 사용하면 같은 해시를 갖는 키들은 하나의 슬롯에 저장되어 한번의 호출만으로 불러오는게 가능하다.

<br>

## **Redis Cluster와 해시 태그**

* Redis Cluster는 데이터를 여러 노드에 분산해서 저장함으로써 높은 성능과 가용성을 제공한다. 데이터는 16,384개의 슬롯에 매핑되고, 각 노드는 이 슬롯의 일부를 담당한다. (예: 3 노드면 약 5천 개씩 담당)
* 기본적으로 Redis는 키(key)의 이름을 CRC16 해시 함수에 적용해서 어떤 슬롯에 저장할지 결정한다. 이렇게 하면 키가 클러스터 전체에 균등하게 분산된다.

<br>

### 해시태그 사용의 장점

* 특정 키들을 동일한 노드, 즉 동일한 해시 슬롯에 저장하고 싶을 때가 있다. (특히 불가피하게 mGet을 사용해야 할 때) 이때 **해시 태그(Hash Tag)**를 사용한다. 해시 태그는 키의 일부를 중괄호 {}로 감싸는 방식으로 쓰며, Redis Cluster는 슬롯을 계산할 때 중괄호 {} 안의 문자열만 사용한다.
  * e.g. user:{1000}:name과 user:{1000}:email은 {1000}이라는 동일한 해시 태그를 가지므로 항상 같은 해시 슬롯에 저장

* Cluster 환경에서는 여러 키에 대한 연산(MGET, MSET, DEL 등)을 수행할 때, 해당 키들이 서로 다른 슬롯에 분산되어 있으면 redis 입장에서는 n번의 호출을 받게 된다.

<br>

### **해시 태그 사용의 단점**

**1. 핫스팟(Hotspot) 문제**

* 데이터가 클러스터 전체에 고르게 분산되지 않을 수 있어, 해당 슬롯을 담당하는 노드에만 데이터와 트래픽이 집중되는 **핫스팟(Hotspot)**이 생긴다.

<br>

**2. 클러스터 확장성 저해**

* 핫스팟이 생긴 노드는 클러스터 전체 처리량을 제한하는 병목 지점이 된다. 새로운 노드를 추가해도 핫스팟이 해결되지 않아, 사실상 클러스터의 장점 자체를 잃는다.

<br>

**3. 리샤딩(Resharding)의 어려움**

* 리샤딩하는 시나리오를 생각해보면, 거대해진 특정 해시 슬롯은 옮기는 과정에서 많은 시간과 리소스를 소모하고, 클러스터 운영에도 부담을 준다.

<br>

## 성능 테스트

: 테스트 환경 - spring boot 환경에서 redis mGet을 50개씩 호출하는 환경에서 400 스레드로 부하를 주는 테스트를 했을때, 발생하는 차이

<img src = "https://hungryjayy.github.io/assets/img/Redis/clusterhashtagtest.png">

* mGet이 불가피할때 성능차이를 무시할수는 없을 것 같다. CPU가 올라간 원인은 redis client에서 mget 결과를 조합하는데 발생하는 비용때문인것으로 추정

<br>

## **결론**

: Redis Cluster의 해시 태그는 멀티 키 연산을 가능하게 하는 강력한 기능이지만, 데이터 분산을 하지 못해 핫스팟을 유발할 수 있는 위험성을 가진다.

<br>

**사용하기 좋은 예**

- **카디널리티(Cardinality)가 낮은 데이터에 사용**: 태그 종류가 너무 많지 않고, 각 태그에 속한 데이터 크기가 예측 가능하고 거대해지지 않을 경우에 쓰는 게 안전하다. (속해있는 도메인에 대해 적절히 파악한 후 사용)
  - e.g. rooms:roomId:users:userId 와 같은 key를 가질 때 {rooms:roomId}users:userId 으로 묶으면 적어도 같은 room에 있는 키 끼리는 한 슬롯에 모이게 된다.


