# 오픈 스택 수동 설치



## 0. Block Service(Cinder)



<h2> Cinder </h2>

> Nova에서 생성된 인스턴스에 확장하여 사용할 수 있는 저장 공간을 생성 및 삭제하고 인스턴스에 연결할 수 있는 기능을 제공



- **snapshot** : LVM snapshot 공간 사용
  - Cinder는 물리 하드 디스크를 LVM(Logical Volume Manager)으로 설정
- **Cinder Backup** : Object storage(**swift**) 사용
  - swift 는 Default가 two node 이다.





<h3>LVM(Logical Volume Manager)</h3>

---

> 하드 디스크를 논리 볼륨으로 할당하고, 다시 여러 개의 디스크를 좀 더 효율적이고 유연하게 관리할 수 있는 방식

![lvm](https://user-images.githubusercontent.com/58682321/71801104-3ecf6780-309d-11ea-838a-c1eecb2e7482.PNG)

```shell
[root@controller ~(keystone_demo)]# vgs 
'논리 볼륨'

-----------------------
  VG             #PV #LV #SN Attr   VSize   VFree   
  cinder-volumes   1   5   0 wz--n- <20.60g 1012.00m
  cl               1   3   0 wz--n- <77.88g    4.00m
-----------------------

[root@controller ~(keystone_demo)]# pvs
'물리 볼륨'
-----------------------
  PV         VG             Fmt  Attr PSize   PFree   
  /dev/loop1 cinder-volumes lvm2 a--  <20.60g 1012.00m
  /dev/sda2  cl             lvm2 a--  <77.88g    4.00m
  ---------------------

```



- storage node(**Server**)에서 instance(**Client**)에 storage 할당

- **iSCSI Target** : Cinder-volume, LVM
  - 컴퓨팅 환경에서 데이터 스토리지 시설을 이어주는 IP기반의 스토리지 네트워킹 표준이다.



<h3> Storage 유형 </h3>

---

- **block storage** : iSCSI, Cinder, EBS
- **file storage** : NFS, Manila
- **Object 기반 storage** : swift, S3
- **Datebase 기반 storage** : Dynamic db, mysql



### Cinder 생성

---



```shell
# cinder create --name demo-v1 1
demo-v1 이라는 이름의 storage(1G) 생성
```



- **instance에 vloume 할당**

```shell
# cinder list
# nova volume-attach selfservice-instance [cinder ID] auto

instance에서는 /dev/vdb 에 할당 된다.

# lsblk 
compute node 에서는 sdb로 생성(가상 disk)
```











## 1. Object Service(swift)



<h2> Swift </h2>

> swift-proxy, account, container, object로 구성
>
> Default : two node

- swift-proxy : account, container, object를 관리



```shell
# swift upload demo-c1 cirros-0.3.5-x86_64-disk.img

cirros image 파일 업로드

# swift list demo-c1 --lh
# cd /var/tmp
# swift download demo-c1
# ls -l
```



- 인스턴스의 **고 가용성**을 위해서는 새로운 볼륨 생성(Cinder storage)하여, Compute node 들이 shutdown 되었을 때도 사용할 수 있도록 해야한다.

- **즉, 모든 component에 대해서는 이중화(two node)를 해야한다.**

  - 새로운 볼륨 생성을 하지 않을 시에는 compute의 local storage를 사용하기 때문에, 서버가 다운되었을 경우 storage를 이용할 수 없다.

    