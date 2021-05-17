# This vs self



## this

* JS의 모든 함수는 실행될 때마다 함수 내부에 this 객체가 추가됨.
  * 현재 컨텍스트를 참조함

1. 객체의 메서드를 호출할 때 `A.B` 에서 this는 A 객체를 가리킨다.
2. 함수를 호출할 때 this는 전역 객체에 바인딩 된다.
3. 생성자 함수를 통해 객체를 생성할 때 -> 일반적인 생성자와 같게 생각하면 됨
   1. 내부에서 객체 자체를 this
4. apply, bind, call
   1. apply: func.apply(this, [param1, param2])
   2. bind: func {}.bind(this, param1, param2)
   3. call: func.call(this, param1, param2)



## self

* 예약어가 아님. 자주 사용되는 variable(사용자가 그냥 `var self` 이렇게 정의)

* 주로 window를 참조할 때 쓰임

  `var self = this`

  * 중첩으로 함수를 리턴하는 곳에서 많이 사용

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
  
  outer this.b = c
  // this는 오브젝트 a를 가리킴
  outer self.b = c
  // self는 로컬 스코프 a를 가리킴
  inner this.b = undefined
  // this는 오브젝트를 가리켜야 하나, invoking 오브젝트가 없으므로 this는 글로벌 오브젝트 window를 가리킴. 하지만 window에는 b라는 속성이 없으므로 undefined
  inner self.b = c
  // self는 로컬 스코프 a를 가리킴
  
  ```



#### Reference)

#### https://velog.io/@woohyun_park/self-vs-this

#### https://m.blog.naver.com/PostView.nhn?blogId=rjs5730&logNo=221093328140&proxyReferer=https:%2F%2Fwww.google.com%2F