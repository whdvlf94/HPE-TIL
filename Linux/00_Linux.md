# Linux 



**목차)**

```
1. 가상화 환경 구축 및 이해
2. 리눅스 실행수준 이해 및 제어
3. Vi 편집기 사용하기
4. Network ip 설정
```

---



[Hypervisor 개념 참조](https://nitw.tistory.com/179?category=355566)



## 1. 가상화 환경 구축 및 이해



### VM workstation

---

> Hosted Hypervios : 기존의 OS 호스트 운영체제 환경에서 실행되는 소프트웨어 응용프로그램



![img](https://t1.daumcdn.net/cfile/tistory/277B413952CD546A34)

​																			[출처](https://kyumoonhan.tistory.com/6)



**<네트워크 통신>**

- Host-only : local 통신 (VM 만 가능)

- NAT : host-only + Outbound internet (외부로 나갈 수 있다)
  - 여려 대의 가상머신을 하나의 네트워크로 묶는 효과를 낼 수 있다.

- Bridged : NAT + 물리망 연동



**※ 모든 실습에서 네트워크는 NAT으로 설정하였음**

---







### 커널과 쉘

---

> 두 개의 차이점은?



![Imgur](https://i.imgur.com/nz3UXlY.png)



- **커널(Kernel)**

\- 운영 체제에서 핵심적인 역할

\- 현재 제어하는 하드웨어 장치의 지원 여부 정보, 하드웨어 성능, 하드웨어를 제어하는 코드

\- uname -a : 현재 커널 버전을 확인하는 명령어





- **쉘(Shell)**

\- 사용자와 커널 사이에서 명령을 해석하여 전달하는 해석기(interpreter)와 번역기(translator) 기능

\- 사용자는 **Shell**을 통해 Windows에서 가상화(VMware-centos)에 명령을 내릴 수 있다.

**사용자(명령) -> 쉘(해석) -> 커널(명령 수행 후 결과 전송) -> 쉘(해석) -> 사용자(결과 확인)**



![image-20200119173010953](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200119173010953.png)

- 53185 pts/2 라는 터미널에 **bash**(shell의 한 종류, 명령어 해석기)라는 shell(독립적인)을 부여 받았다.





## 2. 리눅스 실행수준 이해 및 제어



```sh
[root@centos ~]# poweroff ( #는 root 사용자, $는 일반 사용자)
[root@centos ~]# halt -p : 시스템 종료와 poweroff까지
위의 두 개의 명령어는 root@centos ~]# kill -l  → 9) SIGKILL로 시스템 종료

[root@centos ~]# init 0 : 시스템 종료까지
[root@centos ~]# shutdown -P (poweroff보다는 shutdown 명령어 추천)
위의 두 개의 명령어는 root@centos ~]# kill -l  → 15) SIGTERM으로 시스템 종료

Root)
# shutdown -P +10 → 10분 후에 종료(P: poweroff, 대소문자 주의)
```





- **런레벨 바꾸기 실습**

---



**- Run-level 5** **→ 3** **만드는 방법**

```shell
[root@centos ~]# init 3 
# root 사용자로 로그인, 터미널에서 init 3

[root@centos ~]# who -r
# root 계정으로 로그인, who -r로 런레벨 변경 확인

[root@centos ~]# ps -ef:wc -l
# wc(word counting), -l(line) :출력된 기록들을 카운팅하는 명령어
```



**- Run-level 3** **→ 1** **만드는 방법**

```shell
[root@centos ~]# init 1
password 입력
# 단일 사용자 모드 - root 사용자만 실행 가능

[root@centos ~]# who -r
# who -r로 런레벨 변경 확인
```





**- Run-level 5**

- run-level 5는 dashboard 이용이 가능하다.

```shell
[root@centos ~]# cd /lib/systemd/system
[root@centos system]# ls -l runlevel?.target
lrwxrwxrwx. 1 root root 15 12월 19 10:54 runlevel0.target -> poweroff.target
lrwxrwxrwx. 1 root root 13 12월 19 10:54 runlevel1.target -> rescue.target
lrwxrwxrwx. 1 root root 17 12월 19 10:54 runlevel2.target -> multi-user.target
lrwxrwxrwx. 1 root root 17 12월 19 10:54 runlevel3.target -> multi-user.target
lrwxrwxrwx. 1 root root 17 12월 19 10:54 runlevel4.target -> multi-user.target
lrwxrwxrwx. 1 root root 16 12월 19 10:54 runlevel5.target -> graphical.target
lrwxrwxrwx. 1 root root 13 12월 19 10:54 runlevel6.target -> reboot.target

[root@centos system]# systemctl get-default : 현재 런레벨 default 확인
graphical.targetroot

[root@centos system]# systemctl set-default multi-user.target
# 런레벨을 init 3으로 변경(set)

[root@centos system]# systemctl get-default
multi-user.target

```



\- **startx**

: 런레벨의 변화는 없으나, 상위 레벨의 추가적인 서비스를 이용할 수 있다.

ex) 런레벨3에서 startx 명령어를 통해 graphical(런레벨 5) 서비스를 이용할 수 있다. 





- **마운트 활용**

---



```shell
[root@centos ~]# umount /dev/sr0 
# 마운트 해제, /dev/cdrom와 같은 의미
# /dev/cdrom은 /dev/sr0에 링크된 파일
# /dev/cdrom -> sr0

[root@centos ~]# df -h 
# df - 디스크의 남은 공간을 보여준다. -h 는 mount(연결) 상태를 보여줌
```



\- **VM과 host간 file 공유**

```shell
[Windows 환경 작업]
1) data-share 폴더 생성
2) VM -> setting -> options -> Shared Folders -> .iso 파일 선택

[root@centos ~]#vmhgfs-fuse /mnt
 : windows(server)에 있는 .iso 파일을 vmware(client)에 있는 빈 디렉토리(mnt)로 불러온다.
 
[root@centos ~]#cd /mnt
 : 공유한 폴더가 저장되어 있는 위치
 
[root@centos ~]#ls
[root@centos ~]#cd data-share
[root@centos data-share]#mkdir /centos
[root@centos data-share]#mount -o loop CentOS-7-x86_64-DVD-1611.iso /centos
 : loop 이라는 옵션을 통해 파일을 cdrom 형태로 전환시켜 준다. centos라는 디렉토리를 생성해서 그 공간에 자동으로 mount 해준다.
 
[root@centos data-share]#losetup -a
 : loop가 되었는지 확인하는 명령어
(기능 : 루프 장치를 정규 파일 또는 블록 장치와 연결. 즉, .iso와 연결)

[root@centos data-share]#df -h
[root@centos data-share]#ls /centos

```



