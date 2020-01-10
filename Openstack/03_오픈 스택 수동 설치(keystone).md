# Openstack CLI로 관리하기

| identity 서비스(Keystone)   |
| --------------------------- |
| Image Service (Glance)      |
| Block Storage (Cinder)      |
| Network Service (Neutron)   |
| Compute Service (Nova)      |
| Object Storage (Swift)      |
| Dashboard service (Horizon) |





### 0. Identity 서비스(Keystone)



## 1. CLI 관리

> Horizon(dashboard)를 이용하지 않고, CLI을 통해 sql 문을 이용하지 않고도 DB에 접근할 수 있도록 하는 것



```shell
# source keystonerc_admin
# . keystonerc_admin
위의 둘중 하나의 명령어 입력

root@controller ~ (keystone_admin)#

# openstack user list

DB로 부터 user list를 불러온다. 즉, mysql문을 사용하지 않아도 된다.
```



```shell
# vi keystonerc_admin
```

![Inkedkeystone_admin_LI](https://user-images.githubusercontent.com/58682321/71674358-54534180-2dbe-11ea-8b9e-e816eb9f4188.jpg)



- Horizon(**Dashboard**)에서 생성한  user,project 확인

```shell
# openstack role list --user stack1 --project pro1
# openstack role list --user mgr1 --project pro1
```



- **CLI로 관리(demo 사용자 생성)**

```shell
# openstack create --description "Demo Project" demo
# openstack project create --description "Demo Project" demo
# openstack user create --password abc123 --project demo demo
# openstack role add --project demo --user demo _member_
# openstack role list --project demo --user demo
```

![demo](https://user-images.githubusercontent.com/58682321/71674236-03dbe400-2dbe-11ea-9c02-42dee5ae5a4a.PNG)

- Horizon(**Dashboard**) 을 이용하지 않고 User,Project를 생성할 수 있다.

- Horizon에서 **demo**계정 로그인 가능





## 2. 오픈스택 설정 선행 조건(CentOS) - Environment

[docs.openstack](https://docs.openstack.org/install-guide/environment.html)



## Manual 로 Openstack 설치하는 방법(manual-controller)

> Controller 는 All-in-one 으로 설치함



**설치 과정**

| Environment          |
| -------------------- |
| 1. security          |
| 2. Host networking   |
| 3. NTP               |
| 4. Openstack package |
| 5. SQL database      |
| 6. Message queue     |
| 7. Memcached         |
| 8. Etcd (skip 함)    |



### 2.1 Security

---



```shell
# openssl rand -hex 10
```

- passwd를 수동으로 생성하는 방법



```shell
# grep PW openstack.txt|more
```

- openstack Glance, Cinder 등 계정 정보가 나타난다.





### 2.2 Host networking

---



1. **Openstack > controller2 생성 > controller.vmdk 붙여넣기**

2. **VM workstation 실행**

![m-cont](https://user-images.githubusercontent.com/58682321/71674239-04747a80-2dbe-11ea-97f8-ab1362163ffd.PNG)

```shell
# vi /etc/sysconfig/network-scripts/ifcfg-ens33
```

- UUID 주석 처리, IP 주소 변경



3. **Xshell 로 접속**

   

### 2.3 NTP

---



```shell
# vi /etc/chrony.conf

sever 주소 변경. 기존에 했기 때문에 그냥 넘어간다.

# chronyc sources

server 활성화 확인
```



### 2.4 Openstack packages 

---



```shell
# rpm -qa|grep openstack
```

- 설치 목록 확인

```shell
# yum repolist
# yum upgrade -y
# yum install python-openstackclient -y
# yum install openstack-selinux -y
# yum install mariadb mariadb-server python2-PyMySQL -y
```



### 2.5 SQL databases

---



```shell
# cd /etc/my.cnf.d
# vi openstack.cnf

[mysqld]
bind-address = 10.0.0.11

default-storage-engine = innodb
innodb_file_per_table = on
max_connections = 4096
collation-server = utf8_general_ci
character-set-server = utf8

# systemctl start mriadb
# systemctl enable mriadb

enable 명령어는 reboot 할 때 자동으로 활성화 되도록 하는 명령어
satrt는 즉시 시작하라는 명령어

# systemctl status mriadb
상태 확인

# mysql_secure_installation

모두 Y , 비밀번호는 root 계정 비밀번호로 설정

```



- **MariaDB 접속**

  ```shell
  # mysql -uroot -p
  
  root 계정으로 로그인
  ```

  - mysql 에 접속 완료





### 2.6 Message queue

---



```shell
# yum install rabbitmq-server

# systemctl enable rabbitmq-server
# systemctl start rabbitmq-server

---
오류가 뜨는 경우
# vi /etc/hosts
controller, compute 주소 변경
---

# rabbitmqctl add_user openstack RABBIT_PASS
# rabbitmqctl set_permissions openstack ".*" ".*" ".*"

```





### 2.7 Memcached

---



```shell
# yum install memcached python-memcached
# vi /etc/sysconfig/memcached

edit)
OPTIONS="-l 127.0.0.1,::1,controller"

# systemctl enable memcached.service
# systemctl start memcached.service
# systemctl status memcached.service
```



```shell
# netstat -an|grep 11211
# ss -nlp|grep 11211
```

- 호스트에 장착된 네트워크 인터페이스의 통신 상태를 보여주는 명령어









## 3. Keystone 서비스 설정

[Keystone Installation - CentOS](https://docs.openstack.org/keystone/rocky/install/keystone-install-rdo.html)



- mysql 접속

```shell
# mysql -uroot -p
```

**(중간 생략)**

```shell
# ln -s /usr/share/keystone/wsgi-keystone.conf /etc/httpd/conf.d/

```

- **심볼릭 링크 : 이전 노트 필기 추가할 것!**



**(중간 생략)**



- admin pwd : ADMIN_PASS

- demo-openrc

  ```mysql
  export OS_PROJECT_DOMAIN_NAME=Default
  export OS_USER_DOMAIN_NAME=Default
  export OS_PROJECT_NAME=myproject
  #export OS_USERNAME=myuser
  #export OS_PASSWORD=abc123
  export OS_AUTH_URL=http://controller:5000/v3
  export OS_IDENTITY_API_VERSION=3
  export OS_IMAGE_API_VERSION=2
  ```



**※ admin-openrc , demo-openrc 는 root에 생성할 것**

```shell
# mv *openrc /root

잘못 생성한 경우 root로 옮겨준다.
```



