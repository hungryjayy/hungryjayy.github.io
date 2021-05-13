# SRP(단일 책임 원칙)- SOLID 원칙에서 S

* 모든 클래스는 하나의 책임만 갖고, 그 책임을 완전히 캡슐화

  ``` java
  class Animal {
      constructor(name: string){ }
      getAnimalName() { }
      saveAnimal(a: Animal) { }
  }
  ```

  * 위의 예시에서 Animal은 DB에 저장, 프로퍼티 관리 두가지 일을 하게 되어 SRP 원칙 위배



* 응집성 원칙에 근거

* 어떠한 클래스나 모듈은 변경되려는 단 하나의 이유만을 가져야 한다.

  e. g.) 보고서를 편집하고, 출력하는 모듈

  * -> 1. 보고서때문에, 2. 출력때문에 변경될 수 있음 
  * 분리된 두 책임이기 때문에, 클래스나 모듈로 나뉘어야 함.





#### Reference)

#### https://siyoon210.tistory.com/155

#### https://ko.wikipedia.org/wiki/%EB%8B%A8%EC%9D%BC_%EC%B1%85%EC%9E%84_%EC%9B%90%EC%B9%99

#### https://doublem.org/SOLID_SRP_OCP/