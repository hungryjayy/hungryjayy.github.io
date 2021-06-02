# Transaction 적용이 안되는 몇가지 문제

* Private - 오버라이딩 문제
  * Transaction 처리 과정에서 프록시 객체로 등록하기 때문에 private 불가
  * 마찬가지로 final이 적용된 메서드도 불가능
    * Kotlin에서 allOpen을 통해, 혹은 Open을 통해 가능



* inner Method
  * Transaction 적용되지 않은 메소드에서 @Transactional적용된 내부 메소드 호출 과정은 proxy 객체를 이용해 호출하는 것이 아니라, 내부 호출로 되기 때문에 transaction 적용 불가
  * 이 때 savePost를 호출하는 메소드로 @Transactional 를 옮겨가면 해결
    * 이 때 @Transactional 처리 된 메서드 내부에서 부르는 모든 곳은 atomic하게 처리 됨.(백기선님 블로그 참고)

```java
@Transactional
public void savePost() {
    Post post = new Post();
    post.setTitle("keesun");

    Post newPost = postRepository.save(post);
    System.out.println(postRepository.findById(newPost.getId()));
    System.out.println(entityManager.contains(newPost));
}

// 출처: https://www.whiteship.me/spring-transactional-and-spring-aop/
```



* ReadOnly Method에서 Read-write method 호출
  * read-write 내부에서도 transaction은 readOnly로 동작하게 됨.
  * 반대의 경우도.. 라는데 확인할 필요가 있음



#### Reference)

#### https://www.whiteship.me/spring-transactional-and-spring-aop/

#### https://www.whiteship.me/jpa-entitymanager-contains/

#### https://handr95.tistory.com/3

#### https://kapentaz.github.io/spring/Spring-Transaction-%EC%A0%81%EC%9A%A9-%EB%B2%94%EC%9C%84-%EC%A0%9C%EC%96%B4-%EB%B0%A9%EB%B2%95/#

#### https://netframework.tistory.com/entry/Spring-Transactional%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC
