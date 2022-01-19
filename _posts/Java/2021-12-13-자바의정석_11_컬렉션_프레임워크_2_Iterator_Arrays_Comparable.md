---
layout: post

title: 자바의정석 - 11장 컬렉션 프레임워크 2. Iterator, Arrays, Comparable

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [java, iterator, arrays, comparable, collection]

featuredImage: 

img: 

categories: [Java]

date: '2021-12-13'

extensions:

  preset: gfm
---

<br>

## Iterator

: 컬렉션에 저장된 요소들에 접근하는 기능을 하는 Interface

* Collection 인터페이스는 Iterator 인스턴스를 반환하는 메소드를 갖고있다. 자식 List, Set에도 구현되어있고 각각의 iterator는 자식마다 특성에 맞게 다르게 구현되어있다.

* 원소간 이동 전에 항상 `has~~()` 메소드를 통해 다음 원소가 존재하는지를 확인해야한다.

* **객체지향적**: Collection **공통 인터페이스를 정의**해 컬렉션의 요소를 읽어오는 방법을 표준화하고, **재사용성** 극대화한다. 예를 들어, 아래의 코드에서 `new ArrayList();` 부분만 변경하면 다른 컬렉션의 iterator를 사용 할 수 있다.

  ```java
  public interface Collection<E> extends Iterable<E> {
      Iterator<E> iterator();
  }
  
  // --- 사용 예시
  
  List list = new ArrayList(); // 다른 컬렉션으로 변경할 때는 이 부분만 고치면 된다.
  
  Iterator it = list.iterator();
  while(it.hasNext()) {
    System.out.println(it.next());
  }
  ```
  

<br>

### 사용하지 않는 메소드에 에러 넣기

* **선택적 기능**: 상위 인터페이스의 모든 메소드를 구현하지 않아도 된다. 이 때 호출하는 쪽에서 소스를 까보지 않고도 동작하는 이유를 파악할 수 있도록 **에러라도 throw하도록** 구현하는 것이 좋다. 생성자를 막아놓는 클래스에서도 마찬가지.

  ```java
  public void remove() {
    throw new UnsupportedOperationException();
  }
  ```

* java API 문서에 `remove()` 상세를 보면, 메소드가 지원 되지 않는 하위 Iterator는 `UnsupportedOperationException` 을 throw한다고 적혀있다.

<br>

## Arrays

* `copyOf()`, `copyOfRange()`: 배열의 전체 or 일부를 복사해 새로운 배열을 만들고 반환한다.

  e.g. `Arrays.copyOf(arr, arr.length)` , `Arrays.copyOfRange(arr, 2, 4)`

* `fill()`, `setAll()` : 배열의 모든 요소를 지정된 값으로 채운다. `setAll()`은 함수형 인터페이스를 구현한 객체를 매개변수로 전달받는다.

  e.g. `Arrays.fill(arr, 9)`, `Arrays.setAll(arr, () -> (int) (Math.random() * 5) + 1)` -> 함수형 인터페이스가 들어갈 곳에 람다식을 넣은 것

* `sort()` : 배열을 정렬할 때 사용

* `binarySearch()`: 배열을 검색할 때 사용한다. 단, **정렬이 되어있어야** 이진탐색이 가능하다.

  e.g. `Arrays.binarySearch(arr, 2)`

* `toString()`, `deepToString()`: 다차원 배열에서는 `deepToString()` 사용

* `equals()`, `deepToEquals()`: 다차원 배열에서는 `deepEquals()` 사용. 다차원배열을 `equals()`로 비교하게 되면 결국에 내부의 **배열 주소값**을 비교하는 형태가 되기 때문에 항상 false를 얻는다.

*  `parrallelXXX()`: 여러 쓰레드(프로세스)가 작업을 나누어 처리하도록 한다.

* `spliterator()`: 여러 쓰레드가 작업을 처리할 수 있도록 하나의 작업을 여러 작업으로 나눠주는 `Spliterator`를 반환해준다.

* `stream()`: 컬렉션을 stream으로 변환해준다.

* `asList()`: 리스트로 변환

<br>

## Comparator와 Comparable

: Comparable은 **기본 정렬기준**을 구현하는데 사용, Comparator는 기본 정렬기준 외에 **다른 기준으로 정렬**하고자 할 때 사용

```java
static void sort(Object[] a); // 객체 배열에 저장된 객체에 구현(implement)한 Comparable에 의한 정렬
static void sort(Object[] a, Comparator c); // 지정한 Comparator에 의한 정렬
```

```java
class Descending implements Comparator {
  public int compare(Object o1, Object o2) {
    if (o1 instanceof Comparable && o2 instanceof Comparable) {
      Comparable c1 = (Comparable) o1;
      Comparable c2 = (Comparable) o2;
      return c2.compareTo(c1); // 순서 변경
      // return c1.compareTo(c2) * -1; // 기본 정렬방식의 역으로
    }
  }
}
```

<br><br>

#### Reference)

자바의 정석 3판