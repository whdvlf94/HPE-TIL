# Git

- 코드 관리 도구 -> 버전

- 변경 사항을 versioning 을 통해 관리

- 폴더(디렉토리)를 통해 관리 
  - 모든 history가 하나의 디렉토리에서 관리 된다.



## 1. git add, commit

```powershell
$ git init

Initialized empty Git repository in C:/Users/HPE/TIL/.git/

HPE@DESKTOP-B2STUDENT14 MINGW64 ~/TIL (master)
$ ls -a
./ ../ .git/  

$ git status
```

(※ git을 사용하고자 하는 폴더(디렉토리)에서 git init을 통해 git을 활성화 한다.)

\- $ ls -a 를 통해 .git 폴더가 생성된 것을 확인

\- 현재 git 의 상태를 확인



```powershell
$ git add 00_markdown_basic.md
```

\- git에 등록하고자 하는 파일을 stage 위에 올려 두었다.



```bash
$ git commit
```

\- 업로드한 파일의 현재 상태를 저장( 사진 찍은 것과 같은 의미)

\- vi editor 이 뜨게 되는데, 이는 정보를 입력하기 위해 뜨게 된다.

```bash
$ git config --global user.email
$ git config --global user.name
```

\- 유저 email, name 등록



---

```
--vi editor--
first commit 입력

:wq!
```

---



```bash
$ git log
```

\- 제대로 commit 이 되었는지 확인

\- 이로써 현재 local에서 업로드 할 준비가 모두 되었다.

---

```bash
HPE@DESKTOP-B2STUDENT14 MINGW64 ~/TIL (master)
$ git log
commit d7cf5655e0ecc8313562e66b8b8c67e8131d8599 (HEAD -> master)
Author: JongPil Yoon <whdvlf94@gmail.com>
Date:   Fri Dec 27 14:41:47 2019 +0900
```

---





## 2.  README.md 파일 추가 

```bash
$ git add README.ad
$ git diff --staged
# 변경 사항을 확인 할 수 있다.
```

\- readme.ad 파일을 새롭게 추가하고, status로 확인해 보면 stage에 올라온 것을 확인할 수 있다.



```bash
$ git commit -m "add README.md"
# git log 로 코맨트 추가된 것 확인
```

\- vi editor 창으로 이동하지 않고 commit 할 수 있다.





## 3. git checkout

```bash
HPE@DESKTOP-B2STUDENT14 MINGW64 ~/TIL (master)
$ git log --oneline
970f7d8 (HEAD -> master) add README.md
d7cf565 first commit
#d7cf565 복사

HPE@DESKTOP-B2STUDENT14 MINGW64 ~/TIL (master)
$ git checkout d7cf565
# checkout (README.md ID)
# 이를 통해 일시적으로 되돌아 갈 수 있다.
HPE@DESKTOP-B2STUDENT14 MINGW64 ~/TIL ((d7cf565...))
$ ls
00_markdown_basic.md
# README.md 파일이 없는 것을 확인

HPE@DESKTOP-B2STUDENT14 MINGW64 ~/TIL ((d7cf565...))
$ git log
commit d7cf5655e0ecc8313562e66b8b8c67e8131d8599 (HEAD)
Author: JongPil Yoon <whdvlf94@gmail.com>
Date:   Fri Dec 27 14:41:47 2019 +0900

    first commit
```



```bash
$ git checkout master
# 원래 상태로 다시 되돌아 오는 명령어
```



## 4. git remote

- **git commit 이후 Local에 있는 정보를 Github 에 remote 하는 작업**



```bash
$ git remote add origin(원격저장소 이름) https ~ (github 주소)

$ git remote -v
origin  https://github.com/whdvlf94/TIL.git (fetch)
origin  https://github.com/whdvlf94/TIL.git (push)

$ git push orgin master
# github에 업로드 완료
```





