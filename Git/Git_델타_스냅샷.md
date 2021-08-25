# Git: 델타와 스냅샷

: git에서 커밋을 저장하는 방식

<br>

* 과거에는 오픈소스 프로젝트를 diff와 patch를 통해 개발했다. 코드를 수정하고 **diff 프로그램**으로 **델타**를 생성해 전달하고 **patch 프로그램**으로 메인 코드에 적용했다고 한다.
* 현재는 git을 사용하면 **스냅샷**과 **델타**를 통해 변화된 사항을 관리한다.
  * 한 브랜치의 히스토리에서 보이는 커밋은 **델타** 혹은 **스냅샷**

<br>

## 델타란?

: 커밋과 커밋 사이에서 변경된 사항을 델타라고 한다.

* 모든 commit을 스냅샷으로 저장하면, 변동사항이 없는 부분까지 중복으로 관리하는 것이 되기 때문에 이러한 방식으로 관리
* `git diff`를 쳐서 봤던 것이 델타
  * `commit 1` -> `commit 2` -> `commit 3` 이라고 할 때 `git diff commit3 commit1` 와 같은 요청을 하면, commit 2 델타(diff)와 commit 1 델타를 적용해서 반환해주는 것





#### Reference)

#### http://dogfeet.github.io/articles/2012/git-delta.html

