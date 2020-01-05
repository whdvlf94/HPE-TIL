# Openstack CLI로 관리하기



## 0. Image Service(Glance)

> Nova를 통해 생성되는 가상 머신은 Glance를 통해 가상 머신 이미지를 제공받고 관리한다.



## Controller(All-in-One) - Image Service (Glance)

---

> 다양한 하이퍼바이저에서 사용할 수 있는 가상머신 이미지를 관리하고, 가상머신에 설치된 운영체제를 보관 및 관리



[가상화 에뮬레이션 개념 참조](https://m.blog.naver.com/PostView.nhn?blogId=likeme96&logNo=220541223082&proxyReferer=https%3A%2F%2Fwww.google.com%2F)



#### 1. 이미지 생성 및 확인하기

---



```shell
# yum install -y wget
wget : 웹 서버로부터 콘텐츠를 가져오는 명령어

# wget https://download.cirros-cloud.net/0.3.5/cirros-0.3.5-x86_64-disk.img
# ls -l
# file cirros-0.3.5-x86_64-disk.img 
# qemu-img info cirros-0.3.5-x86_64-disk.img 

# qemu-img --help
# qemu-img convert -O vmdk cirros-0.3.5-x86_64-disk.img cirros-0.3.5-x86_64-disk.vmdk
# file cirros-0.3.5-x86_64-disk.vmdk 
# qemu-img info cirros-0.3.5-x86_64-disk.vmdk
```

- qemu-img 명령어 설명 추가



**※ Mount on 연결이 되지 않았을 경우 대처 방법**

```shell
# mkdir /share
root에 share 라는 디렉토리 생성

# vmhgfs-fuse /share
share 디렉토리를 data share로 지정
이 과정을 거치면 share 하위 디렉토리에 data-share 가 연결된 것을 확인 할 수 있다.

# df -h
마운트 확인

# cp cirros-0.3.5-x86_64-disk.vmdk /share/data-share/
root에 있는 vmdk 파일을 공유 디렉토리로 복사한다.
Windows에서 파일 확인 가능

```

#### -> VM workstation 에서 가상 환경 활성화 완료





#### 2. Openstack 에 cirros 이미지 등록 하기

---



```shell
# . keystonerc_stack1 
# glance image-list
```

![stack1-class](https://user-images.githubusercontent.com/58682321/71703530-6366dd80-2e18-11ea-9631-c87cdbdcb688.PNG)





```shell
# openstack image create "cirros-vmdk" --file /root/cirros-0.3.5-x86_64-disk.vmdk --disk-format vmdk --container-format bare 
# glance image-list
```

![20200103_2](https://user-images.githubusercontent.com/58682321/71703553-8db89b00-2e18-11ea-9879-457378a4d0ad.PNG)



```shell
# glance image-show bd6a706b-18bc-4c71-9277-a3c0268d0c2c
```

![20200103_3](https://user-images.githubusercontent.com/58682321/71703607-d8d2ae00-2e18-11ea-972f-8547f181c873.PNG)



- Vm workstation 에서 cirros 이미지를 만들어서 Openstack 이미지에 등록하는 과정









## Manual-Controller - image 수동 설치

[glance/rocky/install](https://docs.openstack.org/glance/rocky/install/install-rdo.html)









---

# Openstack CLI로 관리하기



## 1. Compute Service(NOVA)



- **Nova compute**
  - Nova-api는 Queue를 통해 nova compute에 인스턴스를 생성하라는 명령어를 전달
  - 인스턴스 생성 명령어를 전달받은 **nova compute**는 **하이퍼바이저 (KVM, QEMU)**라이브러리를 통해 하이퍼바이저에게 인스턴스를 생성하라는 명령어를 전달
  - Nova-conductor를 통해 DB에 접근한다.



- Nova가 지원하는 가상화 유형
  - A 군 : KVM, QEMU
  - B 군 : Hyper-V, Vmware, XenServer 6.2 (유료)
  - C 군 : docker, LXC, Baremetal



- **Container**

![container](https://user-images.githubusercontent.com/58682321/71709580-658e6380-2e3b-11ea-8a9f-a6d774ddaf31.PNG)

- Host의 kernel, Hareware emulation을 빌려 쓴다.

  - OS level isolation : 일부 커널의 기능을 개별적으로 isolation하게 지원

  

- Qemu/KVM 와 같은 하이퍼바이저 보다 경량화 되어 있기 때문에 요즘 많이 사용된다.









### 1. manual controller

---





[docs.openstack-nova 설치](https://docs.openstack.org/nova/rocky/install/controller-install-rdo.html)

※ 비밀번호 설정 주의할 것.



```shell
[root@controller ~]# openstack compute service list
```

- 수동 설치가 성공한 경우 

  ![nova manual](https://user-images.githubusercontent.com/58682321/71711257-7a6ef500-2e43-11ea-9bad-89029a36cc41.PNG)





### 2. Compute

---

> 이 과정은 Controller(All-in-one) Node에 Compute 노드를 추가하는 작업이다. 
>
> 필요에 의해 여러 개의 compute node를 추가할 수 있다.



- **VM workstation** > New VM > compute1.vmdk

  ```shell
  Compute node 추가
  ----------------------------------------------
  hostnamectl set-hostname compute1
  exit
  vi /etc/sysconfig/network-scripts/ifcfg-ens33
  #UUID
  IPADDR=10.0.0.101
  ```



- **Xshell > compute1**

[docs.openstack-compute 설치](https://docs.openstack.org/nova/rocky/install/compute-install-rdo.html#install-and-configure-components)

```shell
# cd /etc/nova/
# cp nova.conf nova.conf.old
# scp controller:/etc/nova/nova.conf /etc/nova/nova.conf


vi /etc/nova/nova.conf
-------
my_ip=10.0.0.101
vncserver_proxyclient_address = 10.0.0.101
-------

위의 2개만 수정할 것. 나머지는 controller 에서 다 수정함
(※ 수정한 내용을 scp를 통해 가져왔다)
```



- 설치 완료가 진행되지 않는 경우

  ```shell
  # systemctl start libvirtd.service openstack-nova-compute.service
  ```

- **controller (All-in-one)**

  ```shell
  # vi /etc/sysconfig/iptables
  
  ------
  13번 아래에 추가
  -A INPUT -s 10.0.0.101/32 -p tcp -m multiport --dports 5671,5672 -m comment --comment "001 amqp incoming amqp_10.0.0.101" -j ACCEPT
  -A INPUT -s 10.0.0.101/32 -p tcp -m multiport --dports 5671,5672 -j ACCEPT
  -A INPUT -s 10.0.0.100/32 -p tcp -m multiport --dports 5671,5672 -j ACCEPT
  ------
  
  systemctl reload iptables
  ```

  - 방화벽으로 인해 설치가 진행되지 않을 수도 있다. 이때는 위와 같이 설정해 준다.



- **compute1**

```shell
[root@controller ~]# 
[root@controller ~]# . keystonerc_admin 
[root@controller ~(keystone_admin)]# openstack compute service list --service nova-compute

controller, compute1 state 상태가 up으로 되어 있으면 완료
```



