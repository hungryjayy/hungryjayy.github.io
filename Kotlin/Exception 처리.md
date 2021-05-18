## 예외 처리

* Error랑은 다름.
  * 시스템이 비정상적인 상황(심각한 오류)
  * 개발자가 예측할 수 없고 처리할 수도 없음.(try catch로 잡을 수 없음)



* 개발자가 처리해야 하는 것 == Exception
  * Checked Exception 발생 시 더 특정상황에서의 unchecked exception을 발생시켜 에러메시지를 명확히 전달	



* ### Checked Exception

  * 컴파일 시점
    * 따라서 Try ... catch 이용
  * 반드시 예외처리 해야함.
  * 예외 발생시 롤백 하지 않음
    * 복구가 가능하기 때문(현실적으로는 많지 않음 -> SQL Exception의 경우)
  * IOException, ClassNotFoundException 등등




* ### Unchecked Exception

  * 런타임 시점
  * 명시적으로 하지 않아도 됨
  * 예외 발생시 롤백해야함
  * NPE, IllegalArgumentException, ClassCastException 등



#### Reference)

#### https://madplay.github.io/post/java-checked-unchecked-exceptions

#### https://cheese10yun.github.io/checked-exception/