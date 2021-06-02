# Issues

## 200301

* `git config --list` 
  * author등 현재의 상태 볼 수 있음.



* `git config --global --user.name="hungryjay"`
  * author 변경
  * user.name 대신 다른 것(email 등)도 변경 가능
  * global option 없을 경우 현재의 디렉토리 한정



* commit 기록들에서 모두 author 변경하기
  * github과 다른 author일 경우 contribution 안찍힘.
  * 바꾸고 싶은 commit으로 `git rebase -i -p {commitID}`
  * pick을 edit으로 변경
  * 전부  `commit --amend --author="hungryjay <aaa@aaa.com>"`



### Git 레포지토리 합치는 방법

##### 상황: B -> A로 병합

1. `git remote add sub(remote) B`
2. `git fetch sub`
3. `git merge --allow-unrelated-histories sub/main(branch이름)`
4. A에서 remote까지 지워주기



#### reference) https://mansoo-sw.blogspot.com/2017/08/git-repository-merge.html