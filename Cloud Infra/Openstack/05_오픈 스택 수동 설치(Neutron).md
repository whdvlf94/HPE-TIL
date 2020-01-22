# 오픈 스택 수동 설치



## 0. Network Service(Neutron)



1. 사용자는 Neutron API를 이용하여 Neutron 서버로 IP를 할당 요청
2. Neutron Server는 Queue로 다시 요청
   - 컴퓨터의 기본적인 자료 구조의 한가지로, 먼저 집어 넣은 데이터가 먼저 나오는 FIFO(First In First Out)구조로 저장하는 형식
3. Queue는 Neutron agents ,  Neutron plugin 으로 IP 할당 지시
4. agents, plugin은 지시 받은 작업을 데이터 베이스(Neutron database)에 저장
5. agents는 네트워크 porvider에게 작업 지시
6. 수시로 작업상태를 데이터베이스에 업데이트
7. 할당된 IP 인스턴스 사용 가능

---



<h4> ML2 Plug in </h4>
1. **Type Driver(LOCAL , FLAT, VLAN, VXLAN/GRE)**

- **LOCAL** : Host only Bridge 유형, 동일 Compute Host안에 같은 네트워크 통신만 가능
- **FLAT** : 별도의 flat networ 구성시 별도의 물리 인터페이스에 연결. 서로 다른 bridge에 연결된 인스턴스간 라우터를 설정
- **VLAN** : 802.1q Vlan tagging을 사용하여 네트워크 트래픽 분리. 서로 다른 VlanID안의 인스턴스간 통신은 반드시 라우터를 통해야한다.
- **GRE/VxLAN** : Overlay network 구성. 클라우드 내의 모든 호스트에 대한 P2P Turnnel을 만든다.
  - Tunnel을 통해 모든 호스트는 하나의 Mesh topology를 형성
    - Mesh topology : 모든 컴포넌트가 링크가 1:1로 연결되어 있다.



2. **MechanismDriver**

- 메커니즘 드라이버의 Agent는 기존에 사용하던 OpenvSwitch, Linuxbridge 등을 그대로 사용할 수 있으며, Arista 매커니즘 드라이버 등을 사용할 수 있다.

---



<h4> Configure networking options

> neutron에서 인스턴스에 대한 연결을 제공하는 네트워크의 종류

- Networking Option 1 : Provider networks(**ext1**)
  - openstack 관리자가 생성
- Networking Option 2 : Self-service networks(**int1**)
  - 사용자 생성 network
  - tenant Network(내부 네트워크)



**dvr(Distributed Virtual Router)**

- 각 compute(br-int) node의 br-ex를 분산 시켜 병목 현상을 제거



**(※ 강의에서는 linuxbridge-agent 방식을 사용)**



```shell
controller]# ovs-vsctl show

network, compute 노드 간의 링크 연결 상태
```

![bridge](https://user-images.githubusercontent.com/58682321/71794788-6cf57d00-3086-11ea-91de-a0ef7c5b46c1.PNG)

- **qg** -> **Port qg** -> **patch int-br-ex** -> **Port int-4** -> **interface4(ens 33)** -> **Internet**





## Networking service (Neutron) 설치 - compute1 

[Install the components](https://docs.openstack.org/neutron/rocky/install/compute-install-rdo.html)

- compute1에 neutron service 추가

```shell
# yum install openstack-neutron-linuxbridge ebtables ipset -y
# cp /etc/neutron/neutron.conf /etc/neutron/neutron.conf.old

# scp controller:/etc/neutron/neutron.conf /etc/neutron/neutron.conf

# chgrp neutron /etc/neutron/neutron.conf
```



[Configure networking options](https://docs.openstack.org/neutron/rocky/install/compute-install-option2-rdo.html)

```shell
# vi /etc/neutron/plugins/ml2/linuxbridge_agent.ini
 
 -------------------------------------------
158 physical_interface_mappings = provider:ens33
[vxlan]
214: enable_vxlan = true
241: local_ip = 10.0.0.101
265: l2_population = true

182 [securitygroup]
183 enable_security_group = true
184 firewall_driver = neutron.agent.linux.iptables_firewall.IptablesFirewallDriver
----------------------------------------------------
```



```shell
# modprobe br_netfilter
# lsmod|grep br_netfilter
활성화 확인

# sysctl -a|grep bridge-nf-call-iptables
# sysctl -a|grep bridge-nf-call-ip6tables
활성화 확인(1)

# systemctl enable neutron-linuxbridge-agent.service
# systemctl start neutron-linuxbridge-agent.service
```



- **controller 에서 제대로 설치 되었는지 확인**

```shell
[root@controller ~(keystone_admin)]# openstack network agent list

------------------------------------------------
| 89693586-fd0b-4389-be8e-9515dffefaf4 | Linux bridge agent | compute1   | None              | :-)   | UP    | neutron-linuxbridge-agent |

------------------------------------------------

```



---



## CLI 로 관리

[Neutron](https://docs.openstack.org/install-guide/launch-instance-networks-selfservice.html)

- **Create the self-servicie network - demo** 

```shell
# . keystonerc_demo
# openstack network create selfservice

# openstack subnet create --network selfservice \
  --dns-nameserver 8.8.4.4 --gateway 172.16.1.1 \
  --subnet-range 172.16.1.0/24 selfservice

dns, gateway, subnet range 설정
```



- **Create a router  - admin**

```shell
# . keystonerc_admin
# openstack port-list
# openstack router create router


# openstack router add subnet router selfservice
# openstack router set router --external-gateway ext1
```



![cli network](https://user-images.githubusercontent.com/58682321/71797659-7e448680-3092-11ea-92f6-b471d191e003.PNG)

- CLI 작업을 통해 위와 같은 네트워크를 생성했다.





- **Create m1.nano flavor - admin**

```shell
# openstack flavor create --id 0 --vcpus 1 --ram 64 --disk 1 m1.nano
```



- **Generate a key pair - demo**

```shell
# . keystonerc_demo
# openstack keypair create --public-key ~/.ssh/id_rsa.pub mykey

# openstack keypair list
```





- **Add security group rules - demo**

```shell
# openstack security group rule create --proto icmp default
# openstack security group rule create --proto tcp --dst-port 22 default

icmp, port 설정
```



<h3> Launch an instance </h3>
[Launch an instance on the self-services network](https://docs.openstack.org/install-guide/launch-instance-selfservice.html)

```shell
# openstack image create "cirros-0.3.5" --container-format bare --disk-format qcow2 --file ./cir4-disk.img

# openstack network list
selfservice ID 확인 : bbb87fd1-6db2-49bbadf

# openstack server create --flavor m1.nano --image cirros-0.3.5   --nic net-id=bbb87fd1-6db2-49bbadf --security-group default   --key-name mykey selfservice-instance

# openstack server list
selfservice-instance : ACITVE 상태이면 완료

```



- **Access the instance using a virtual console** 

```shell
# openstack console url show selfservice-instance
url 주소 확인할 수 있는 명령어. 접속하면 cirros console 창이 뜬다.

# virsh list --all
# virsh console 1
disconnect는 ^]
```



- **Access the instance remotely**

```shell
# openstack floating ip create ext1
# openstack server add floating ip selfservice-instance 10.0.0.215
공인 IP를 사설 IP에 할당

# openstack server list
공인 IP가 연결된 것을 확인할 수 있다.
```



<h4> CLI 로 instance 관리하기 </h4>
```shell
# ip netns exec qrouter-e01a6585-8ece-4e0d-96a6-57e008117d44 ssh cirros@10.0.0.215
instance 실행
```

