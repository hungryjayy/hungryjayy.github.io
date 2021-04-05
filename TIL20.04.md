# 200405

* 코딩테스트 준비

  * c++ string replace All

  ```c++
  while((pos = str.find(" ")) != string::npos) {
      str.replace(pos, size, str2); // str2로 대체
  }
  ```
  * 백트랙킹 문제에서 굳이 visit 하나하나 바꾸지 말자. check할때 하나하나 접근하는게 이득