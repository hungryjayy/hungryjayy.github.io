### Map vs forEach

* forEach
  * 값 반환 X
  * 각 요소에 대한 콜백을 수행. (현재 배열을 변경해서 반환). map보다 빠르다.
  * JS에서는 for-loop가 더 빠르고, kotlin에서는 forEach(Collection의 경우)가 더 빠르다.
  * 원래의 배열을 바꿀 염려 있음. 주로 DB 저장과 같은 일에 사용됨
* map
  * 값 반환 O
  * 각 요소에서 함수를 호출하고, 결과로 새로운 배열을 만들어냄.
  * 원래의 배열에 영향을 주지 않기 때문에 함수형 프로그래밍에 더 적합.



### for vs forEach

* `break`, `continue`등을 사용하며 중간에 빠져나올 일이 있을때는 `for`, 한바퀴 전체를 돌기 위한 목적이라면 `forEach` 가 적절
* 성능
  * 일반적 반복문: for문이 더 좋음
  * Collection 반복문: forEach가 더 좋음
    * list, map, set과 같은 클래스들은 Collection을 상속받는데, 해당 클래스 몇몇 함수들은 inline을 제공받음 따라서 더 빠름.
  * asSequence: lazy collection의 한 종류임.
    * 체인 형식(filter, map 등)으로 컬렉션 호출 시 list는 값을 저장하기 위해 tmp collection을 따로 만든 후 값을 저장하는 형를 띄우는데, 이는 오버헤드가 되며 퍼포먼스 저하.(java 8에서는 stream에 대응되는 lazy collection이 제공되면서 이를 해결했다고 함)



#### Reference)

#### https://hwan-shell.tistory.com/245

#### https://medium.com/mobile-app-development-publication/kotlin-for-loop-vs-foreach-7eb594960333

