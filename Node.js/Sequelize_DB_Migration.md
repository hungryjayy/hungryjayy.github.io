# Sequelize에서 DB Migration하기

## Query Interface<br>

* Sequlize: ORM

* Query interface
  * DB와의 communication을 위함.(low level)<br>
    대부분의 Sequlize method들은 query interface의 도움을 받아 구현된다.<br><br>

## Sequelize migration<br>
이미 운영중인 서비스의 DB를 변경해야 할 때 편리.<br>

```
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
up: DB 변경사항 적용<br>
* `$ sequelize db:migrate` : up에 정의된 코드 실행<br>
down: 'up' 실행되기 전의 상태로 DB 복원.
* `$ sequelize db:migrate:undo` : down에 정의된 코드 실행<br>

. . .<br>
<br>

#### Reference
https://sequelize.org/master/manual/query-interface.html
