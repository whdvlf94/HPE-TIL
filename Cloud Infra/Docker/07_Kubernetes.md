# Kubernetes

#### 컨테이너 운영을 자동화하기 위한 컨테이너 오케스트레이션 도구(도커 스웜과 비슷한 역할)

- 서버 리소스의 여유를 고려한 컨테이너 배포 및 배치 전략
- Scale in/Sclae out
- Service discovery



**-> 다양한 리소스를 조합해 유연한 애플리케이션을 구축할 수 있다.**



### Swarm vs Kubernetes

---

- **Kubernetes** : swarm 보다 충실한 기능을 갖춘 컨테이너 오케스트레이션 시스템
- **swarm** : 여러 대의 호스트를 묶어 기초적인 컨테이너 오케스트레이션 기능 제공, 간단한 멀티 컨테이너 구축



- **service 의 개념 차이**

---

- **swarm service** : 스웜에서 클러스터 안의 서비스(컨테이너 하나 이상의 집합) 를 관리
- **kubernetes servie**: pod의 집합에 접근하기 위한 경로를 정의한다.
  - **kubernetes pod** : 컨테이너의 집합 중 가장 작은 단위로, 컨테이너의 실행 방법을 정의한다.
  -  pod를 외부에 노출시키기 위해 필요한 작업, 파드의 집합에 접근하기 위한 경로를 정의





- **pod** : 컨테이너가 모인 집합체의 단위로, 적어도 하나 이상의 컨테이너로 이루어진다.
  - 컨테이너에서는 서로 연결할 수 있는 중복 되지 않는 Post 제공
  - 고유의 IP 할당
    - Cluster 내부에서 접속(외부에서는 접속 안됨)
  - pod에 문자가 생길 시 pod 삭제 후 자동으로 다시 생성
    - ip 변경됨



![Untitled Diagram.png](https://github.com/whdvlf94/TIL/blob/master/img/Untitled%20Diagram.png?raw=true)



- **kubernetes install**

---

- windows

: docker > setting > kubernetes > install

https://storage.googleapis.com/kubernetes-release/release/v1.17.0/bin/windows/amd64/kubectl.exe

다운로드 후 실행 파일 C:\Program Files\Docker\Docker\resources\bin 로 옮길 것



- linux

```powershell
$ curl -LO https://storage.googleapis.com/kubernetes-release/v1.10.4/bin/linux/amd64/kubectl&&chmod +x kubectl&&mv kubectl /usr/local/bin/
```







### 1. Dashboard를 이용한 kubernetes

---



- **dashboard install**

---

```powershell
>  kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v1.8.3/src/deploy/recommended/kubernetes-dashboard.yaml

> kubectl get pods --namespace=kube-system -l k8s-app=kubernetes-dashboard
> kubectl proxy

# localhost -> 웹 브라우저로 접속
http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/#!/overview?namespace=default

# localhost ip -> 웹 브라우저로 접속
 kubectl proxy --port=8001 --address=[접속할 localhost IP] --accept-hosts='^*$'
```





- **kubernetes Demo**

---

```shell
C:\Users\HPE\Work\git\cloud-computing\04.Docker\day04
# hello.js 가 있는 위치
```



- **hello.js - dockerfile**

```dockerfile
FROM node:slim

EXPOSE 8000

COPY hello.js .

CMD node hello.js
```



- **cmd - image build**

```shell
> docker build -t whdvlf94/hello:latest
> docker run -d -p 8100:8000 whdvlf94/hello:latest

# 웹 브라우저에서 http://locathost:8100 로 접속
```



- **pod.yml**

---

```yaml
apiVersion: v1
kind: Pod
# resource 종류
metadata:
  name: hello-pod
  # pod 이름
  labels:
    app: hello
#app : key 값
#test : value
spec:
#spec -> container 의 정보
  containers:
  - name: hello-container
    image: whdvlf94/hello
    ports:
    - containerPort: 8000
    # service 설정을 안했기 때문에 아직 외부에서 접근하지는 못한다.
```



- **cmd - pod 설정**

---

```shell
> docker push whdvlf94/hello:latest
# build 한 image를 hub에 push
```



- **kubernetes dashboard**

---

1) create에서 pod.yml 복붙

2) Workloads > EXEC 에서 실행 중인 pod를 확인할 수 있다.





- **service.yml**

---

> service 설정을 통해 외부에서 접근 가능하도록 설정



```yaml
apiVersion: v1
kind: Service
# service : 파드의 집합에 접근하기 위한 경로 정의
metadata:
  name: hello-svc
  # service name
spec:
  selector:
    app: hello
  ports:
    - port: 8200
      targetPort: 8000
  type: LoadBalancer
  # 외부에도 공개
  externalIPs:
  - [local host IP]
```



1) create에서 pod.yml 복붙

2) Workloads 에서 실행 중인 service를 확인할 수 있다.



※ **service를 통해 외부에서 접근 가능하도록 설정했다. local host IP 를 통해 pods 접속 가능**





### 2. cmd를 이용한 kubernetes

---

```shell
$ kubectl apply -f pod.yml
# pods 실행

$ kubectl get pod
# pods 실행 상태 확인
$ kubectl get services
# services 실행 상태 확인

$ kubectl get all
# 현재 실행 중인 리소스 확인
```

