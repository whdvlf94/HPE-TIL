# Linux



## 1. 파일의 접근권한 관리

- 리눅스에서는 파일이나 디렉터리를 생성할 때 기본 접근 권한이 자동적으로 설정
- 일반 파일 : 소유자는 읽기와 쓰기 권한 설정, 그룹과 기타 사용자는 읽기 권한만 설정
- 디렉터리 : 소유자는 읽기,쓰기,실행 권한 설정, 그룹과 기타 사용자는 읽기, 실행 권한만 설정





- **파일의 속성**

---

**- 디렉토리 : d / 일반적인 파일 : - / 장치 파일 : b(블록 디바이스), c(문자 디바이스), ㅣ(링크)**



![image-20200120000539389](https://user-images.githubusercontent.com/58682321/72683944-9b139c00-3b1f-11ea-9ef1-1f06cf690980.png)





- **기호를 이용한 파일의 접근 권한**

---



![image-20200119225157601](https://user-images.githubusercontent.com/58682321/72683947-9fd85000-3b1f-11ea-84f0-c577cf06786a.png)



- **r(read), w(write), x(execute)**

\- 소유자(user) : **rw-** :읽거나 쓸 수 있다. (※ **rwx 는 읽고,쓰고, 실행까지 가능한 파일**)

\- 그룹(group) : **r-- :** 읽을 수만 있다.

\- 사용자(other) : **r-- :** 읽을 수만 있다.

※ root는 모든 권한이 다 부여가 된다. 소유자와 다른 개념



```shell
[root@centos /]# clear
[root@centos /]# mkdir ch5
[root@centos /]# cd ch5/
[root@centos ch5]# cp /etc/hosts test.txt
[root@centos ch5]# ls -l
합계 4
-r--r--r--. 1 root root 202 12월 23 15:30 test.txt

[root@centos ch5]# chmod g+wx test.txt
[root@centos ch5]# ls -l
합계 4
-r--rwxr--. 1 root root 202 12월 23 15:30 test.txt

```



- **숫자를 이용한 파일의 접근 권한 **

---

```shell
[root@centos ch5]# ls -l
합계 4
-rw-r--r--. 1 root root 202 12월 23 15:30 test.txt


[root@centos ch5]# chmod 444 test.txt
[root@centos ch5]# ls -l
합계 4
-r--r--r--. 1 root root 202 12월 23 15:30 test.txt
```





- **기본 접근 권한 설정 - umask**

---

> 파일이나 디렉터리 생성 시 부여하지 않을 권한을 지정해 놓는 것



```shell
[root@centos ch5]# umask 077
[root@centos ch5]# touch linux.txt
# 리눅스에 일반 파일 생성 시,
# 소유자 읽기,쓰기 / 그룹,기타 사용자 읽기 권한 설정이 default 다.

[root@centos ch5]# ls -l
합계 4
-rw-------. 1 root root 202 12월 23 15:30 test.txt
# umask 값을 077 로 주었기 때문에
# 위와 같은 결과가 나왔다.
```





- **특수 접근 권한**

---

- SetUID : 맨 앞자리가 4
- SetGID : 맨 앞자리가 2
- 스티키 비트(sticky bit) : 맨 앞자리가 1





\- **SetUID**

: 해당 파일이 실행되는 동안에는 파일을 실행한 사용자의 권한이 아니라 파일 소유자의 권한으로 실행

```shell
[user1@centos ch5]$ touch set.exe
[user1@centos ch5]$ chmod 755 set.exe # 실행 권한 부여
[user1@centos ch5]$ ls -l set.exe
-rwxr-xr-x 1 user1 user1 202 12월 23 15:30 set.exe

[user1@centos ch5]$ chmod 4755 set.exe
[user1@centos ch5]$ ls -l set.exe
-rwsr-xr-x 1 user1 user1 202 12월 23 15:30 set.exe
# 소유자의 실행 권한 문자가 x -> s 로 변경된 것을 확인
# set.exe를 실행하면 항상 user1의 권한을 가지고 실행된다는 의미
```

- **SetUID는 접근 권한에서 맨 앞자리에 4를 설정**





\- **SetGID**

:  SetGID가 설정된 파일을 실행하면 해당 파일이 실행되는 동안에는 파일 소유 그룹의 권한으로 실행

- **SetGID는 접근 권한에서 맨 앞자리에 2를 설정**



\- **스티키 비트(sticky bit)**

: 스티키 비트는 디렉터리에 설정, 디렉터리에 스티키 비트가 설정되어 있으면 이 디렉터리에는 누구나 파일 생성 가능

```shell
[user1@centos ch5]$ ls -dl /tmp
drwxrwxrwt 12 root root 4096 12월 23 15:30 /tmp

```

- 파일은 파일을 생성한 계정으로 소유자가 설정되며, 다른 사용자가 생성한 파일은 삭제 불가
- **스티키 비트는 접근 권한에서 맨 앞자리에 1을 설정**