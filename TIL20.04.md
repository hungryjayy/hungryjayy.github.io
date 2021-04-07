# 200405

* 코딩테스트 준비

  * c++ string replace All

  ```c++
  while((pos = str.find(" ")) != string::npos) {
      str.replace(pos, size, str2); // str2로 대체
  }
  ```
  * 백트랙킹 문제에서 굳이 visit 하나하나 바꾸지 말자. check할때 하나하나 접근하는게 이득



# 200407

* 코딩테스트 준비
  * 이분탐색에서 기본적으로 while 조건문은 left <= right 



* lint vs prettier

  * lint: 코드 정적 분석
    * 코딩 컨벤션에 어긋나는지
    * 안티패턴 검출
    * 간단한 code formatting

  *여태 사용하던건 lint에 prettier의 포메팅 기능을 포함했던 것.* 

  * prettier: 코드가 예쁘게 보이는지
    * 코드 최대 길이
    * 작은 따옴표 or 큰 따옴표

> use prettier for formatting and linters for catching bugs!

* #### eslint-plugin-prettie

  * Prettier를 ESLint 플러그인으로 추가한다. 즉, Prettier에서 인식하는 코드상의 포맷 오류를 ESLint 오류로 출력해준다.