# 오픈스택(클라우드 운영체제)

: 'VMware Workstation', 'Virtual Box'와 같이 가상머신을 제공하여 호스트 컴퓨터에서 다수의 운영체제를 동시에 실행 관리 가능하도록 지원하는 IaaS 형태의 플랫폼.

-> **오픈소스의 하이퍼바이저(Hypervisor)를 지원, 관리하는 서비스**

[오픈스택 개념 참고](https://blog.naver.com/love_tolty/220231856481)



## VMware에 오픈스택 설치하기

## 1. Packstack으로 All-in-one 설치



## 컨트롤러 노드 VM 생성 절차



![image-20191230142543831](https://user-images.githubusercontent.com/58682321/71606024-00274200-2bb1-11ea-90ff-ee2da370d215.png)

![image-20191230172802744](https://user-images.githubusercontent.com/58682321/71606032-0b7a6d80-2bb1-11ea-9757-8cc129ffb1c9.png)

- 호스트 이름 : controller

  ![image-20191230172848542](https://user-images.githubusercontent.com/58682321/71606044-1c2ae380-2bb1-11ea-98a1-cfcfd136dcaf.png)

- Number of cores per processor : 2

  

- Disk File :  browse -> HPE>openstack 



- VM settings : cpu mode 변경

  ※ Virtual Intel VT-x/EPT or AMD-V/R VI : CPU, 메모리 가상 모듈 활성화

  ![image-20191230143554164](https://user-images.githubusercontent.com/58682321/71781885-1e5cca00-3017-11ea-83ea-95878350913a.png)

  

  #####  -> openstack에서 기본 Hypervisor로 KVM을 사용하며, KVM기반 VM을 시작하기 위해서 CPU 가상 모드를 반드시 활성화하여야 한다.

  

- CD/DVD : ISO file을 mapping 시켜 설치 준비 완료



- 홈 파티션의 경우 용량이 거의 필요 없기 때문에 2GiB로 설정
- / 파티션을 70 GiB로 늘려준다.

- 호스트 이름 : controller(이더넷 켬)

- Type : NAT

  

...

**(중간 생략)**

...

```shell
# ip a	
ip 주소 확인
# yum repolist	
인터넷 연결 확인
Controller]# yum update -y	
최신 버전으로 업데이트
```

---

### 사전 준비

- 네트워크 및 방화벽 조건(CentOS 7 서비스 최적화)

  ```shell
  #systemctl stop firewalld
  #systemctl disable firewalld
  #systemctl disable NetworkManager
  #systemctl stop NetworkManager
  ```
  
  - **오픈 스택은 firewalld 대신에 iptables를 사용하므로 CentOS 7의 Default 방화벽인 firewalld를 정지하고, 비활성화 시킨다.**
  - **오픈스택 네트워크는 NetworkManager가 활성화 되어 있으면 작동을 하지 않기 때문에 CentOS 7에 Default로 활성화 되어 있는 NetworkManager 서비스를 정지하고 비활성화 시킨다.**
  
  
  
  
  
  ---
  
  SE Linux : port, 레이블? 개념정리
  
  ```shell
  # getenforce
  Enforcing	## 방화벽이 활성화되어 있다
  
  # setenforce 0
  # getenforce
  Permissive	## 방화벽을 제거
  ```
  
  \- 명령어로 바꾸는 것은 메모리에 저장되기 때문에 재부팅을 하게되면 방화벽이 다시 활성화 되어 있다. **이는 아래와 같이 vim 편집기로 해결 가능**
  
  ```shell
  # vi /etc/selinux/config
  
  SELINUX=disabled
  
  :wq!
  
  # 방화벽 제거를 유지하는 방법
  ```
  
  
  
- 하드웨어 정보 확인

  ```shell
  # egrep '(vmx|svm)' /proc/cpuinfo
  vmx는 인텔 기반, svm은 AMD 기반의 CPU를 의미
  
  # lscpu
  Virtualization: VT-x
  ```



## 

- 호스트 정보 확인

  ```shell
  # cat /etc/centos-release
  ```

---



## 2. NTP 서버 설정

```shell
# yum install chrony -y
# vi /etc/chrony.conf

--------수정---------
# server0 ~
# server1 ~
# server2 ~
server3 ~
server 2.kr.pool.ntp.org iburs

server 127.127.1.0 	: local clock source(로컬 clock system 사용) --- 'Server'

allow 10.0.0.0/24  :  'Client'
--------------------
:wq!

# yum install -y ntpdate
# date
# grep server /etc/chrony.conf
변경 사항 확인

# ntpdate 2.kr.pool.ntp.org

# systemctl start chronyd
# systemctl enable chronyd
# chronyc sources

127.127.1.0 이 안보일 경우
# systemctl restart chronyd
```

* 컨트롤러 노드가 NTP 서버의 역할(정확한 시간을 제공하는 서버)을 수행하고 나머지 노드는 NTP 클라이언트로서 그 시간 정보를 서버로부터 가져와서 동기화를 각각 수행

- 외부로 부터 시간을 받아 서버와 클라이언트의 시간의 동기화를 수행한다. 이 후, 서버와 클라이언트 간의 시간 차이가 나는 것을 수정하는 작업을 진행한다.



```shell
# vi /etc/hosts

-------------
10.0.0.100 controller
10.0.0.101 compute1
-------------
:wq!

```



![nova호스트준비](https://user-images.githubusercontent.com/58682321/71782742-eb1f3880-3020-11ea-9ab0-fea00cce9bec.PNG)



## 3. 오픈 스택 설치

- Openstack repository 등록
- Packstack 설치
- Openstack 설치
- Openstack network 설정
- Horizon 접속 정보



---

**실행 노드 : controller**



```shell
# yum install -y centos-release-openstack-rocky
# yum repolist
# yum upgrade -y
```

- Rocky 버전 다운로드



```shell
# yum install -y openstack-packstack*

# packstack --gen-answer-file /root/openstack.txt
# vi /root/openstack.txt
Openstack 설치 옵션이 들어가는 answer 파일을 생성 및 수정
```

- "COMFIG_COMPUTE_HOSTS"에는 자신의 compute 노드의 IP를 입력합니다.
- CONFIG_KETSTONE_ADMIN_PW : abc123
- CONFIG_PROVISIOIN_DEMO : n

```shell
# time packstack --answer-file=/root/openstack.txt
```

- 설치 시간을 확인하기 위해 time 명령 사용



**※ 오류 뜨는 경우**

```shell
# openstack-status

neutron-openvswitch-agent: inactive 일 경우 시스템을 재시작 해줘야한다.

# systemctl restart network
```



```shell
# openstack-status
오픈스택 서비스 확인

# yum install -y openstack-utils
오픈스택 커맨드가 설치되어 있지 않을 경우 위의 명령어 실행
```



```shell
# cat keystonerc_admin
```

- Horizon 접속 URL 정보 확인

