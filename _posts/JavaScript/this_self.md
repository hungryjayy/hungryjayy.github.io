# this vs self



## this

* JS의 모든 함수는 실행될 때마다 함수 내부에 this 객체가 추가됨.
  * 현재 컨텍스트를 참조함

1. ### 기본적으로 전역 객체를 참조.

2. ### 객체의 메서드를 호출했을 때

   * 메서드를 호출한 부모 객체를 참조

     e.g) `A.B` 내부에서 this는 A 객체를 가리킨다.

     ```typescript
     var myObject = {
       name: "foo",
       sayName: function() {
         console.log(this);
       }
     };
     myObject.sayName();
     // console> Object {name: "foo", sayName: sayName()}
     // this는 myObject를 참조
     ```

3. ### new 키워드를 통해 생성자 함수를 호출할 때

   * 생성자를 통해 생성하는 객체를 참조.

     ```typescript
     var Person = function(name) {
       console.log(this);
       this.name = name;
     };
     
     var foo = new Person("foo"); // Person
     console.log(foo.name); // foo
     ```

4. ### `apply()`, `bind()`, `call()`

   * **첫번째 인자**로 넘겨받은 객체를 참조함.

   1. apply: `func.apply(this, [param1, param2])`
   2. bind: `func {}.bind(this, param1, param2)`
   3. call: `func.call(this, param1, param2)`

   * e.g) `bind` 메서드 예시

   ```typescript
   var value = 100;
   var myObj = {
     value: 1,
     func1: function() {
       console.log(`func1's this.value: ${this.value}`);
   
       var func2 = function(val1, val2) {
         console.log(`func2's this.value ${this.value} and ${val1} and ${val2}`);
       }.bind(this, `param1`, `param2`);
       func2();
     }
   };
   
   myObj.func1();
   // console> func1's this.value: 1
   // console> func2's this.value: 1 and param1 and param2
   ```



## self

* 예약어가 아님. 자주 사용되는 variable(사용자가 그냥 `var self` 이렇게 정의)

* 주로 window를 참조할 때 쓰임

  `var self = this`

  * 중첩으로 함수를 리턴하는 곳에서 많이 사용(이전 컨텍스트의 `this` 를 유지하고 싶을 때?)

  ``` typescript
  var a = {
  	b: "c",
      func: function(){
      	var self = this;
          console.log("outer this.b = " + this.b);
          console.log("outer self.b = " + self.b);
        	(function(){
          	console.log("inner this.b = " + this.b);
            	console.log("inner self.b = " + self.b);
          }());
      }
  }
  a.func();
  
  ------ 결과
  
  outer this.b = c // this는 오브젝트 a를 가리킴
  outer self.b = c // self는 로컬 스코프(a 내부)를 가리킴
  inner this.b = undefined // this는 오브젝트를 가리켜야 하나, 이 내부함수를 invoke(call)하는 오브젝트가 없으므로 this는 글로벌 오브젝트 window(func 내부)를 가리킴. 하지만 window에는 b라는 속성이 없으므로 undefined
  inner self.b = c // self는 로컬 스코프 a를 가리킴
  
  출처 : https://velog.io/@woohyun_park/self-vs-this
  ```



#### Reference)

#### https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/JavaScript#this-%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C

#### https://k9e4h.tistory.com/141

#### https://velog.io/@woohyun_park/self-vs-this

#### https://m.blog.naver.com/PostView.nhn?blogId=rjs5730&logNo=221093328140&proxyReferer=https:%2F%2Fwww.google.com%2F