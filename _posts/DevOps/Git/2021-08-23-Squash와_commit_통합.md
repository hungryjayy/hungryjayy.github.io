---
layout: post

title: Squash로 commit 통합하기

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [git, commit]

featuredImage: 

img: 

categories: [DevOps, Git]

date: '2021-08-23'

extensions:

  preset: gfm


---

<br>

* Rebase를 해야하는데, rebase적용해야할 source 브랜치 커밋이 긴 경우, 그리고 충돌이 많을 것 같을때 하나하나 컨플릭 해결하는 것은 너무 많은 일을 해야한다.
  1. Rebase 말고 그냥 merge
  2. 기존 commit을 Squash해, 하나의 커밋으로 통합하고 rebase
* 위 두 가지 방법이 좋아보이는데, 2번의 방법을 사용.
* 기존 팀프로젝트에서 github에서 리뷰 후 merge할때 squash는 사용해봤지만, CLI로 해보려니 방법을 몰라 헤맸다..

<br>

## 과정

1. 커밋이 합쳐지기 전인 이전 버전을 추후에 사용할 수 있으니, `temp/~~~` 와 같이 새로운 branch를 만들어서 하는 것이 좋을 것 같다.

2. 일단 커밋 로그를 찍어보면 (`git log --oneline`) 이렇게 보이긴 하는데,내가 작업한 commit 중 아래의 보이는 32개의 commit을 합치려 한다.

   ![image-20210823162357944](https://hungryjayy.github.io/assets/img/Git/commit_oneline.png) 

   - 세어보면 32개

3. `git rebase -i HEAD~32` (`-i` == `--interactive`) 하면 아래와 같이 나온다.

   * pick(사용할 커밋) : 사용할 커밋을 pick으로 표시

   * squash(사용하지만 통합) : 사용하지만 통합할 커밋을 squash로 표시

   * 나의 경우 하나만 빼고 다 통합이니까 가장 위에꺼(가장 오래된 것) 하나를 reword로 적절히 네이밍해주고, 나머지는 전부 squash

     ![image-20210823163046093](https://hungryjayy.github.io/assets/img/Git/edit_message.png) 

4. 완료

   ![image-20210823163213830](https://hungryjayy.github.io/assets/img/Git/squash_done.png) 

<br><br>
