# 자바의정석 - 11장 컬렉션 프레임워크 5. Properties, Collections

## Properties

: **Key-Value** (String, String) 형태로 무언가를 저장하고자 할 때 사용한다.

* 데이터를 txt, xml 등의 **외부 파일로부터** 읽고 쓰는 편리한 기능 제공한다. 그래서 간단한 입출력은 Properties를 사용한다.

  * 외부 파일에서 K-V는 '='로 연결된 형태여야 한다.

* HashTable을 상속받아 구현해, Map의 특성(저장순서 유지 X)을 갖는다.

* 컬렉션 프레임워크 이전의 구버전이라, Iterator가 아닌 Enumeration을 사용한다.

  ```java
  Properties prop = new Properties();
  
  prop.setProperty("timeout", "30");
  prop.setProperty("language", "kr");
  prop.setProperty("size", "10");
  
  println(prop.getProperty("timeout"));
  println(prop.getProperty("language"));
  println(prop.getProperty("size"));
  

<br>

## Collections

: `Arrays`가 배열 관련 메서드를 제공하는 것처럼, `Collections`는 컬렉션과 관련된 메서드를 제공한다.

<br>

* 동기화

  * `Vector`, `HashTable` 같은 JDK1.2 이전의 구버전 클래스들은 기본적으로 **동기화**가 되어있다. -> **performance 저하**

  * `ArrayList`, `HashMap`과 같은 컬렉션들은 **필요한 경우에만 동기화**를 한다.

    ```java
    List<Integer> list = Collections.synchronizedList(new ArrayList<>());
    Set<Integer> set = Collections.synchronizedSet(new HashSet<>())
    ```

* 불변 컬렉션: `List<Integer> list = Collections.unmodifiableList(new ArrayList<>());`

* 싱글턴: `Collections.singletonList(new ArrayList<>());`

* **한 종류의 객체**만 저장하는 컬렉션

  * 아래와 같이 컬렉션에 모든 종류의 객체를 저장할 수 있다.

    ```java
    List<Object> list = new ArrayList<>();
    list.add("string");
    list.add(1);
    ```

  * 대부분의 경우 보통 한 종류의 객체만 저장하기도 하고, **해당 종류의 객체만 저장되도록 strict하게** 제한할 때 사용한다.

    ```java
    List<String> list = Collections.checkedList(new ArrayList<>(), String.class);
    ```

  * **generics**를 사용하면 아래와 같이 한 종류의 객체만 저장하도록 구현 가능하나, **하위호환성**을 위해 이 메소드가 지원된다.

    ```java
    class Foo<T> {
      List<T> list = new ArrayList<T>();
      
      void add(T item) { list.add(item); }
      // ...
    }