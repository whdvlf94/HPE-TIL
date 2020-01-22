# 네트워크

- **docker-compose.yml**

```yaml
networks: 
    mongo-networks:
        driver: bridge        
```

```powershell
> docker network create --driver=bridge --attachable mongo-networks
```



- **Docker swarm** -> 오케스트레이션
  - 여러 docker host를 클러스터로 묶어주는 컨테이너 오케스트레이션



- **compose** : 여러 컨테이너로 구성된 도커 애플리케이션을 관리(주로 단일 호스트)
- **swarm** : 클러스터 구축 및 관리 (주로 멀티 호스트)
- **service** : 스웜에서 클러스트 안의 서비스(컨테이너 하나 이상의 집합)을 관리
-  **stack** : 스웜에서 여러 개의 서비스를 합한 전체 애플리케이션을 관리



### 1.Docker Swarm

---

> Docker in Docker, dind
>
> 도커 컨테이너 안에서 도커 호스트를 실행



- **Dokcer in Docker, dind** : virtual Box 같은 가상화 소프트웨어를 이용하지 않고, dind 기능을 통해 물리 호스트 1대에 여러 대의 도커 호스트를 실행할 수 있다.

- **registry** : 도커 레지스트리 역할을 할 컨테이너로, manager 및 worker 컨테이너가 사용하는 컨테이너





- **registry image download**

---

```
> docker pull docker:19.03.5-dind
```





- **docker-compose.yml**

---

```yaml
PS C:\Users\HPE\docker\day03> mkdir swarm
PS C:\Users\HPE\docker\day03> cd .\swarm\
PS C:\Users\HPE\docker\day03\swarm> code .
-----------------------------------------------
version: "3"
services: 
  registry:
    container_name: registry
    image: registry:latest
    ports: 
      - 5000:5000
      
    volumes: 
      - "./registry-data:/var/lib/registry"

  manager:
    container_name: manager
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    ports:
      - 8000:80
      - 9000:9000
    depends_on: 
      - registry
    expose: 
      - 3375
    command: "--insecure-registry registry:5000"
    volumes: 
      - "./stack:/stack"

  worker01:
    container_name: worker01
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    depends_on: 
      - manager
      - registry
    expose: 
      - 7946
      - 7946/udp
      - 4789/udp
    command: "--insecure-registry registry:5000"

  worker02:
    container_name: worker02
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    depends_on: 
      - manager
      - registry
    expose: 
      - 7946
      - 7946/udp
      - 4789/udp
    command: "--insecure-registry registry:5000"

  worker03:
    container_name: worker03
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    depends_on: 
      - manager
      - registry
    expose: 
      - 7946
      - 7946/udp
      - 4789/udp
    command: "--insecure-registry registry:5000"
    
---------------------------------

> docker-compose up
```

- **volumes [host]:[container]** :  현재 host 내에 존재하지 않는 volume인 경우에는 이 작업을 통해 새로운 디렉토리를 생성한다.

- **container_name : **으로 container 이름을 설정할 경우 name 앞뒤로 수식어가 붙지 않는다.

- **command: "--insecure-registry registry:5000" ** 
  - 모든 manager 및 worker 컨테이너는 registy 컨테이너에 의존,
  - docker registry에는 일반적으로 HTTPS를 통해 접근하지만, HTTP를 사용하기 때문에 command 요소에 위와 같은 값을 줘 HTTP로도 이미지를 내려받을 수 있게 한다.

- **docker-compose up** : registry, manager, worker 1~3 총 5개의 컨테이너를 한번에 실행



- **powershell**

---

```powershell
> docker ps 

# registry , manager, worker 01~03 컨테이너 생성 확인 

> docker exec -it worker03 docker ps
# dind는 docker 안에 docker를 생성하는 것이기 때문에
# 위와 같이 입력을 하게 되면 worker03 docker 의 현재 container 실행 상태를 보여준다.

> docker exec -it worker03 docker images
# worker03 docker가 보유하고 있는 image 또한 검색할 수 있다.

```

#### 하지만, 아직까지는 모든 컨테이너가 서로 협조해 클러스터로 동작하는 상태는 아니다.



- **leader, worker 지정**

---

> manager(leader)는 swarm 클러스터 전체를 제어한다.

- 클러스터링 과정(**서버의 군집화**)을 통해 manager, worker 01 ~ 03 의 노드들을 하나로 군집화 할 수 있다.



```powershell
> docker exec -it manager docker swarm init
--------------------------
docker swarm join ~
--------------------------
# swarm init : leader로 지정하는 명령어, 위에서는 manager node로 진행했기 때문에
# manager node 가 leader가 된다.
# docker swarm join : worker 를 추가할 수 있는 명령어

> docker exec -it worker01 sh
/ # docker swarm join ~

# worker01 이 worker node로 추가된다. 
# worker02~03도 진행

> docker exec -it manager docker node ls
# leader, worker node 들을 확인할 수 있다.
# 확인하는 것은 manager(leader) node만 가능!
```





- **registry 이용하기**

---

```powershell
> docker tag busybox:latest localhost:5000/busybox:latest
# tag: localhost:5000 로 계정을 바꿔 localhost에서 5000 port로 registry에 접속할 수 있도록 수정한다.
# tag: localhost:5000 로 계정을 바꿔 registry에 image를 push 할 수 있도록 수정한다.


> docker push localhost:5000/busybox:latest
# http://localhost:5000/v2/_catalog 접속하여 image 등록 확인

> docker exec -it manager sh
-----------------------
/ # docker pull registry:5000/busybox:latest
/ # docker images
------------------------
# busybox image 가 pull 된 것을 확인할 수 있다.

```

- **localhost:5000/busybox:latest** 가 아닌 **registry/busybox:latest** 을 사용한 이유는?
  - **manager node**에서 **localhost**는 **windows**이다. 하지만 image 파일은 port 5000을 이용하여 registry container에 push 했다. 따라서 localhost:5000 이 아닌 registry로 입력해야  pull 할 수 있다.

