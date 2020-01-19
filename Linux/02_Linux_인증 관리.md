# Linux



## 원격지 시스템 관리





### Key 기반 인증

---

> Ubuntu(student) ↔ centOS(root)



![image-20200120003059263](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120003059263.png)

\- rsa : 비대칭 key 알고리즘

\- key pair : public, private key 두 개의 키가 /root/.ssh/id_rsa에 저장된다.



![image-20200120003147218](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120003147218.png)

\- id_rsa (**private) ,** id_rsa.pub(**public)**

\- id_rsa.pub을 복사하여 Ubuntu에 보내주어야 한다.



```shell
[centos]

[root@centos .ssh]# ssh-copy-id student@[ubuntu IP]
[root@centos .ssh]# cat id_rsa.pub

[ubuntu-student]

[student@Docker:~/.ssh]$ cat authorized_keys

# [root@centos .ssh]# cat id_rsa.pub 
# [student@Docker:~/.ssh]$ cat authorized_keys
# 두 개의 키 값이 일치하는 지 확인

[root@centos ~]# ssh student@[ubuntu IP] 
# 더 이상 비밀번호를 요구하지 않고, key를 통해 접속 가능
```



- **root login 허용**

---

> Ubuntu(root) ↔ centOS(root)



\- **ubuntu-student**

```shell
root@Docker:/etc/ssh# sudo vi sshd_config
----------------------------
PermitRootLogin yes
#PermitRootLogin prohibit-password
----------------------------

root@Docker:/etc/ssh# sudo systemctl restart sshd
```

**→ 기존에 prohibit-password로 되어 있었기 때문에, Centos에서 root 계정으로 접근이 불가능 했다. 따라서 계정 접근 허용을 위해서는 PermitRootLogin yes 상태로 만들어 주어야 한다. 이후 시스템 재시작**



\- **Centos-root**

```shell
[root@centos ~]# ssh root@[ubuntu IP]
```

**→ centos 서버에서 확인해보면 Ubuntu root 계정에 접속이 가능한 것을 알 수 있다.**





### Passwor 기반 인증

---

> ssh를 key 기반에서 password 기반 인증으로 변경하기



**[Aws-ec2-user]** **계정 생성**

![image-20200120004459274](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120004459274.png)

![image-20200120004508359](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120004508359.png)

```shell
-31-2-150@ip ~]$ sudo -i # root 계정으로 로그인

# su -  로도 root 계정에 접속할 수 있으나, 비밀번호를 반드시 알고 있어야 한다. sudo의 경우 비밀번호를 몰라서 root 계정에 접속할 수 있음.
```

**[Aws-student]** **계정 생성**

![image-20200120004637069](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120004637069.png)

![image-20200120004640071](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120004640071.png)

```shell
[root@ip ~]# adduser student
[root@ip ~]# passwd student
[root@ip ~]# cd /etc/ssh
[root@ip ~ ssh]# vi sshd_config
-----------------------------
PasswordAuthentication yes
-----------------------------
[root@ip ~ ssh]# systemctl restart sshd

# 외부 서버 -> 내부 서버  접속 sshd_config 의 설정에 영향 받음
# 내부 서버 -> 외부 서버  접속 ssh_config 의 설정에 영향 받음
```

**→ student** **계정 password 기반 설정 완료**





**※ vi /etc/hosts**

---

![image-20200120005034699](C:\Users\skybl\AppData\Roaming\Typora\typora-user-images\image-20200120005034699.png)

```shell
student@Docker:~$ ssh Centos
student@Docker:~$ ssh Ubuntu 로 접속 가능
```

