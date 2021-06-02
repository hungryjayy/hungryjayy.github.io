# 함수의 메서드 (call, apply, bind)

: 함수에 존재하는 기본 **메소드**. 

* 첫번째 인자로 this를 대체할 수 있어, 실행 컨텍스트(window)를 다른 것으로 바꿀 때 사용가능.



## call

* 보통 함수와같이 인자를 넣어서 전달
* `func.call(this, a, b)`

## apply

* this와 배열로 전달
* `func.apply(this, [1, 2, 3])`

* zerocho.com 예시 - call, apply

```typescript
var example = function (a, b, c) {
  return a + b + c;
};
example(1, 2, 3);
example.call(null, 1, 2, 3);
example.apply(null, [1, 2, 3]);
```

```typescript
var obj = {
  string: 'zero',
  yell: function() {
    alert(this.string);
  }
};
var obj2 = {
  string: 'what?'
};
obj.yell(); // 'zero';
obj.yell.call(obj2); // 'what?'
```



## bind

* this만 바꿔 반환하고, 호출하지는 않음.
*  `call(this, 1, 2, 3)`은 `bind(this)(1, 2, 3)` 와 같음.

* zerocho.com 예시 - bind

```typescript
var obj = {
  string: 'zero',
  yell: function() {
    alert(this.string);
  }
};
var obj2 = {
  string: 'what?'
};
var yell2 = obj.yell.bind(obj2);
yell2(); // 'what?'

// obj.yell에서 this는 obj2가 된다. this가 obj2가 된 함수를 반환하기 위한 목적
```





#### Reference)

#### https://www.zerocho.com/category/JavaScript/post/57433645a48729787807c3fd