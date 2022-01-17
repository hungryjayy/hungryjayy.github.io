# Issues

## 200317

* Vector는 memset 불가
  * cstring 라이브러리



* C++ string
  * `string.erase(index부터, length만큼);`
  * `string.replace(index부터, length만큼, ""얘로 변경);`
  * `string.find(""얘를 찾아라, index부터);`
  * 이외에도 여러 버전으로 오버로딩되어있으니 라이브러리를 공부하기



* `isupper()`, `isdigit()`, `toupper()` 등 camel case가 적용이 안되어있으니 주의



## 200405

* c++ string replace All

```c++
while((pos = str.find(" ")) != string::npos) {
    str.replace(pos, size, str2); // str2로 대체
}
```



* 백트랙킹 문제에서 굳이 visit 하나하나 바꾸지 말자. check할때 하나하나 접근하는게 이득



* 이분탐색에서 기본적으로 while 조건문은 left <= right 



