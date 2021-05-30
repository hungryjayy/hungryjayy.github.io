# 퀵 정렬(Quick sort)

: **pivot**(보통 가운데 값)을 기준으로 좌우 구간으로 나눠 정복(**분할, 정복**).

* e.g. 오름차순의 경우, pivot 왼쪽엔 pivot보다 작은 값을, 오른쪽엔 큰 값을 위치하도록 partition. 또 이렇게 나뉜 좌 우 배열을 다시 quick sort 수행(**분할**). 이 때 pivot 값은 해당 부분 배열에서 본인의 위치를 찾은 것이기 때문에(**정복**) 재귀에 포함하지 말 것.



## 시간복잡도

* 평균적으로 **O(Nlog(N))** 이지만, 최악의 경우 **O(N^2)**가 된다.
* 최악의 경우: pivot에 해당하는 값이 항상 배열내에서 



## 소팅 과정

<img src="https://gmlwjd9405.github.io/images/algorithm-quick-sort/quick-sort.png" alt="img" style="zoom:30%;" />

* ##### 출처: https://gmlwjd9405.github.io/2018/05/10/algorithm-quick-sort.html

1. 초기 pivot: 5(맨 앞)을 기준으로 수행. 해당 pivot 제외 가장 좌측 low, 가장 우측 high로 접근
2. high에서 왼쪽으로 가면서 pivot보다 작은 것 있으면 stop, low에서 오른쪽으로 가면서 pivot보다 큰 값 있으면 stop
   * 둘다 stop한 상황에서 둘이 교체하고, 다시 진행
   * high < low 가 되었을 때 high와 pivot을 교체(pivot이 왼 쪽에 있는 경우였기 때문에)
3. pivot을 기준으로 좌, 우 서브 배열로 나눠 2번의 과정을 각각 진행한다.



#### Reference)

#### https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/Algorithm

#### https://gmlwjd9405.github.io/2018/05/10/algorithm-quick-sort.html