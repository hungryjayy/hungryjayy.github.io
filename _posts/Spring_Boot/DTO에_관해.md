# DTO(data transfer object)에 관해

* Layer간 데이터 전송을 위함.
* Domain model 객체를 그대로 두고, 복사하여 다양한 presentation logic을 추가한 정도
* 가변적.(추후 요구사항에 따라 값 자체가 변할 수는 있음)
* 컨테이너 역할(따라서 비즈니스 로직 등 behavior 갖지 않음)



### DTO와 엔티티를 분리하는 이유

* 만약 프론트에서 dto 객체의 구성이나 네이밍등의 변경사항이 있을경우 엔티티를 그대로 쓰면 DB 컬럼명까지 까지 수정해줘야하는 경우가 생길 수 있으니 분리한다.



### DTO에 관한 고찰

* DTO 위치에 대해 정해진건 없다. 다만 service 혹은 controller에서 사용하는 것은 맞는 듯..
  * Controller에서 DTO를 Entity로 변환시켜 Service에게 파라미터로 보내기.
  * Controller에서 DTO 자체를 Service에게 파라미터로 보내고, Service가 Entity화 시키기.
  * Service가 Controller로 응답을 보낼 때, Entity를 반환하고 Controller가 이를 DTO로 변환하여 사용하기.
  * Service가 Controller로 응답을 보낼 때, Entity를 DTO로 변환시켜 반환하기.
  * 작은 프로젝트일 수록 DTO가 필요없을 수 있다.
  * 큰 프로젝트 일 수록 DTO가 힘을 발휘할 것 같다.



#### Reference )

#### https://xlffm3.github.io/spring%20&%20spring%20boot/DTOLayer/

#### https://dbbymoon.tistory.com/4

#### https://github.com/HomoEfficio/dev-tips/blob/master/DTO-DomainObject-Converter.md



### 나의 정리

* Service에서 Repo에 넘겨줄때
  * DTO to Entity(domain)
    * `fooEntity = FooEntity.of(fooDto);`  or `FooEntity.toEntity(fooDto);`
      * repository에 저장하는 entity는 request에 의존적이지 않고 entity 의존적이어야 한다.
    * service의 private 메서드에서 처리
    * 주로 빌더패턴(Kotlin에서는 딱히 불필요?)
      * Java에서 builder pattern
        * 코틀린에서는 인자에 넣을 때 인자가 많아도 어떤 인자에 맵핑되는지 확인 가능하기에 필요없어진다.



* DB에 저장
  * fooEntity = fooRepository.save(fooEntity);



* 저장 결과 DTO로 반환
  * Entity(domain) to DTO
    * `return FooDto.of(fooEntity);`
    * converter에서 담당(responseDTO에서 구현한 converter를 서비스에 주입해서 처리)
      * view나 client쪽으로(바깥으로) 노출하는 작업은 해당 responseDTO에 의존적이어야 한다.



* DTO는 단순한 자료구조로만 사용하는 것이 좋다.(**원시 타입**)
  * 이러한 점에서 DTO에서 SQL이 실행되지 않을 것이다 라고 생각하기.
  * DTO가 Repository를 의존하면 unit test를 작성하기 어려워진다.