* # Typescript 겪은 이슈들

  ## 200215

  * typeof (ABC) (x) → typeof ABC

  

  ## 200223

  * 나머지 매개변수는 가장 마지막 인자에 들어와야 함.

    ``` javascript
    function foo(...rest) {} // O
    
    function foo2(arg1, ...rest, arg2) {} // X 에러 발생
    ```

  

  ## 200324

  * javascript에서 { 를 선언문과 같은 줄에서 시작해야 하는 이유
    * https://stackoverflow.com/questions/3641519/why-do-results-vary-based-on-curly-brace-placement
