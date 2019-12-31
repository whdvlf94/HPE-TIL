# 오픈스택 사용하기

## 1. 오픈스택 network 설정

#### 1.1 network 설정

```shell
# cd /etc/sysconfig/network-scripts/

[root@controller network-scripts]# cat ifcfg-ens33 
DEVICE=ens33
NAME=ens33
DEVICETYPE=ovs
TYPE=OVSPort
OVS_BRIDGE=br-ex
ONBOOT=yes
BOOTPROTO=none

[root@controller network-scripts]# cat ifcfg-br-ex 

DEFROUTE="yes"
UUID="1af500c1-fde3-4c96-a5c0-19e632e5fc2b"
ONBOOT="yes"
IPADDR="10.0.0.100"
PREFIX="24"
GATEWAY="10.0.0.2"
DNS1="10.0.0.2"
DEVICE=br-ex
NAME=br-ex
DEVICETYPE=ovs
OVSBOOTPROTO="none"
TYPE=OVSBridge
OVS_EXTRA="set bridge br-ex fail_mode=standalone"

```

- device 는 물리적인 인터페이스의 이름과 매칭이 되어야 한다.

- ONBOOT=yes : 부팅 시 자동으로 활성화

- PREFIX='24' : 24바이트를 네트워크 주소로 설정(=넷마스크)



-> openstack 설치 완료 후 Openvswitch



```shell
network-scripts]# ovs-vsctl show

## ip a 보다 계층 구조가 잘 나와있다.
```



<h4> 1.2 Horizon 접속 정보 </h4>
```shell
홈 디렉토리

# cat keystonerc_admin    

	export OS_USERNAME=admin #Horizon 유저 계정    
	~
export OS_PROJECT_NAME=admin #Horizon 프로젝트 계정
~
```



- export : local 변수를 global 변수로 사용하기 위한 명령어











## 2. Horizon(DashBoard) 사용



### 2.1 Horizon 메뉴

**※ 사용 목적 : 프로젝트 Menu , 관리 목적 : 관리 Menu** 

---

- <h3>2.1.1 인증 menu</h3> : Keystone 서비스가 담당
- 프로젝트 : resource Quotas가 설정된 사용자 그룹
  
  \- Quotas 수정

![캡처](https://user-images.githubusercontent.com/58682321/71610008-a59dde00-2bd0-11ea-9cdb-15281e7e18b5.PNG)

​		

​		**< Openstack>**

![캡처1](https://user-images.githubusercontent.com/58682321/71610009-a59dde00-2bd0-11ea-9203-0f44cecdb265.PNG)

>  **Linux** : 사용자의 account가 저장된 디렉토리 : /etc/passwd, group, shadow 



---

- <h3>2.1.2 관리 menu</h3> : admin에게만 권한이 있다. (리눅스의 경우는 root 계정)

- **Compute** : Nova 서비스
  
  - 하이퍼바이저 : Qemu, KVM
  
  - 호스트 집합 :  Compute host를 관리하기 위한 목적
  
  - 인스턴스 : Nova 서비스, 인스턴스를 생성할 수 있는 메뉴x, 관리 목적
  
  - 이미지 : Glance 서비스, 설치된 이미지 등록
  
  - Flavor : aws에서의 인스턴스 타입과 같다.
  
    : 디스크는 2개까지만 할당이 가능하다. 관리자 계정으로 Flavor 생성 및 삭제가 가능하다.
  
- **볼륨**
  
- **네트워크** : Neutron 서비스
  
- **시스템**
  
  - 시스템 정보
  
    ![캡처3](https://user-images.githubusercontent.com/58682321/71610010-a6367480-2bd0-11ea-823f-5f6f53a23f1e.PNG)
  
    >  Nova에서 중요한 프로세스
  
    

---

- <h3> 2.1.3 프로젝트 menu</h3>
- 네트워크
    - 보안 그룹 : aws 보안 설정과 같음
  - 오브젝트 스토리지
    - 컨테이너 : aws S3와 같음









---

## 3. Horizon으로 사용 및 관리하기

![캡처4](https://user-images.githubusercontent.com/58682321/71610007-a59dde00-2bd0-11ea-80fa-d73304b6f720.PNG)

> Openstack 서비스 사용 순서



- 8.Floating IP(**AWS의 EIP와 같다**) : 공인 IP ,  	Fixed IP : 사설 IP

  - Floating IP를 Provider에게 요청.

  - AWS 의 경우 서비스 reboot할 경우 공인 IP가 계속 변경된다. DHCP가 새로운 IP 주소를 할당한다.(**Dynamic IP**)

  - 이 때, **EIP(Static IP)**을 요청하게 되면 서비스 reboot 시에도 변경되지 않는 공인 IP가 할당된다.

    

- 10.Instance 생성 : **Compute Service -> IaaS **
- 1~11 : AWS의 EC2(**IaaS**)와 같다
- 11.Vloume/snapshot : AWS의 EBS와 같다.
- 12.Object storage : AWS의 S3와 같다.



- 4~9 : Self-service
- 1~3 : 관리자(**Provider**)의 작업, 2.사용자생성 -> IT 관계자





### < 관리자 Menu >

---

<h5> 3.1 프로젝트 생성 </h5>
![캡처5](https://user-images.githubusercontent.com/58682321/71610249-ab94be80-2bd2-11ea-9bd4-27a2d60551cd.PNG)

- 프로젝트 맴버에 원하는 사용자를 추가할 수 있으며, 사용자의 role를 설정할 수 있다.

- Quotas 수정 : VCPUs 4 , 인스턴스 4
- pro1 프로젝트 생성

<h5> 3.2 사용자 생성 </h5>
- stack1 : member
- mgr1 : admin,

<h5> 3.3 Flavor 생성 </h5>
- a.tiny :  Flavor 접근 권한 -> pro1 선택 (pro1 프로젝트에서만 flavor를 사용할 수 있다.)
- a.nano

---




stack1 계정에서는 인스턴스가 보이지 않는다. 이는 Openstack 설치 과정에서 install 하지 않았기 때문. 따라서 일반 사용자(stack1) 계정에서 인스턴스를 사용하기 위해서는 설치 과정에 install을 해야한다.

