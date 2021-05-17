## 200215

* EOL 처리: 작성 file 끝에 new line 넣어주기(cat 관련 문제) 
  
  * 파일이 끝났다고 판단하지 못하는 문제 때문
  
  #### reference) https://minz.dev/19 



* ID라는 변수명 설정

  * ID vs Id : Id승. 가독성이 좋고 더 많이 쓰임 

    e. g) `userIdWithRoom` 처럼 카멜케이스 중간에 오는 경우



* 디렉토리 설계

  * 컴포넌트 예: 자족적인 컴포넌트 기반으로 설계하라

    [![alt text](https://github.com/goldbergyoni/nodebestpractices/raw/master/assets/images/structurebycomponents.PNG)](https://github.com/goldbergyoni/nodebestpractices/blob/master/assets/images/structurebycomponents.PNG)

  * 역할별 예: 기술적인 역할별로 모아라

    [![alt text](https://github.com/goldbergyoni/nodebestpractices/raw/master/assets/images/structurebyroles.PNG)](https://github.com/goldbergyoni/nodebestpractices/blob/master/assets/images/structurebyroles.PNG)




#### Reference)

#### https://github.com/goldbergyoni/nodebestpractices/blob/master/sections/projectstructre/breakintcomponents.korean.md





## 200407

### lint vs prettier

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





## 200417

* 보고싶은 깃헙 레포지토리에서 URL에 github.com -> github1s.com으로 바꿔주면 

  vscode 웹으로 열림(readonly
  )[Github1s - GitHub코드를 VS Code로 1초만에 둘러보기](https://daddyprogrammer.org/tech-news/?vid=285)