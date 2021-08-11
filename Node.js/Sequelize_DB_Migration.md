# Sequelize에서 DB Migration하기

## Query Interface

* Sequlize: ORM

* Query interface
  * DB와의 communication을 위함.(low level)
  * 대부분의 Sequlize method들은 query interface의 도움을 받아 구현된다.

<br>

## Sequelize migration
* **마이그레이션**: 이미 운영중인 서비스의 DB를 변경해야 할 때

```typescript
Module.exports = {
	up:function(queryInterface, Sequelize){
		//Add altering commands here
		return promise.
	}
	down:function(queryInterface, Sequelize){
		//Add reverting commands here.
	}
}
```

<br>

### up & down
* **up**: DB 변경사항 적용
  * `$ sequelize db:migrate` : up에 정의된 코드 실행
* **down**: 'up' 실행되기 전의 상태로 DB 복원.
  * `$ sequelize db:migrate:undo` : down에 정의된 코드 실행

.<br><br>

## Trouble Shooting

*Docker환경에서 마이그레이션 잘 적용되지 않는 문제 -> Mount 디렉토리 확인*

* **문제 상황**: Model을 수정하고 마이그레이션 파일 생성해서 테스트까지 완료했는데, 다른 PC에서 **500 code와 함께 컬럼이 존재하지 않는다고 에러**

* **해결**: docker 설정부의 MariaDB를 마운트하는 디렉토리 내부에 해당 PC(문제있던)에서 이미 이전의 모델들이 존재했기 때문. 컬럼값이 달라서 마이그레이션 제대로 되지 않던 것.

  * **아래의 마운트 경로`/data/mariadb` 디렉토리를 지워버리고 다시 새롭게 만들기**

  ```yaml
  volumes:
    - ./data/mariadb:/var/lib/mysql:Z
  ```

<br><br>

#### Reference

#### https://sequelize.org/master/manual/query-interface.html