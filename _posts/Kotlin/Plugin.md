# Plugin

## all-open 플러그인

* Kotlin은 기본적으로 final class

  * 따라서 상속 불가
  * 안정성 상승
  * effective java "상속에 대한 좋은 설계와 문서화를 할 자신이 없다면 상속을 허용하지 말 것." 
  * 그러나 java에 익숙하다면 이것이 불편할 수 있음

* `all-open` 플러그인

  ```kotlin
  buildscript {
      dependencies {
          classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
      }
  }
  
  apply plugin: "kotlin-allopen"
  ```

  ```kotlin
  plugins {
    id "org.jetbrains.kotlin.plugin.allopen" version "1.4.32"
  }
  ```

  ``` kotlin
  allOpen {
      annotation("com.my.Annotation")
      // annotations("com.another.Annotation", "com.third.Annotation")
  }
  ```

  * 이처럼 annotation을 설정하면 해당 annotation을 갖는 클래스는 모두 all-open이 된다.
    * 상속이 불필요한 곳은 막을 수 있음.
  * 기본적으로 @Configuration, @Componenet, @Async 등 특정 어노테이션을 갖는 클래스들은 이미 open 되어있다(어노테이션에 의해서) - 상세는 공식문서에



#### Reference)

#### https://cchcc.github.io/blog/Kotlin-%EB%94%94%ED%8F%B4%ED%8A%B8%EA%B0%80-final%EC%9D%B8-%EC%9D%B4%EC%9C%A0/

#### https://kotlinlang.org/docs/all-open-plugin.html#gradle

