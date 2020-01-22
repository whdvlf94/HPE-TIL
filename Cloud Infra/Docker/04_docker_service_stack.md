# Swarm 을 이용한 실전 애플리케이션 개발



## 1. Docker Service

> 애플리케이션을 구성하는 일부 컨테이너(단일 또는 복수)를 제어하기 위한 단위



- **service create**

---

> service create는 manager container에서 docker service create 명령으로 생성한다.



**예제)**

- **image : gihyodocker/echo:latest**

```powershell
> docker tag gihyodocker/echo:latest localhost:5000/example/echo:latest
# tag: localhost:5000 로 계정을 바꿔 localhost에서 5000 port로 registry에 접속할 수 있도록 수정한다.

> docker push localhost:5000/example/echo:latest
# http://localhost:5000/v2/_catalog에 접속하여 registry에 제대로 등록되었는지 확인

> docker exec -it manager sh
-------------------------------
$ docker pull registry:5000/example/echo:latest
```

\- **docker service ls**  : docker service 목록 확인 명령어

\- **docker service rm [ID]** : docker service 삭제 명령어





- **docker service start**

---

```powershell
$ docker service create --replicas 1 --publish 8000:8080 --name echo registry:5000/example/echo
--------------------------------

# local에서 8000 port로 접속하면 echo service 8080 주소로 접속할 수 있다.
```

- registry에 있던echo image를 manager container 에 pull 을 이용해 가져온다. 이후 `service create` 명령어를 통해 서비스 배포한다.



- **localhost:8000 접속**

---

> 접속이 되지 않는다. 그 이유는?



```powershell
> docker exec -it manager sh
-------------------------------
$ docker service create --replicas 1 --publish 8000:8080 --name echo registry:5000/example/echo
-------------------------------

> docker ps
-------------------
0.0.0.0:9000->9000/tcp, 0.0.0.0:8000->80/tcp   manager
-------------------

```

- 우리는 8000:8080 을 원했으나 , **manager container** 의 경우 local에서 8000 port 로 접속하게 되면 80 port 로 **manager  container**에 접속하는 것을 확인할 수 있다. 따라서 port의 설정을 바꿔야 한다.
  - **windows(8000) → Manager(80) / Manager(80) → echo(8080)**
  - 2 개의 포트 포워딩



```powershell
> docker exec -it manager sh
-------------------------------
$ service rm echo

$ docker service create --replicas 1 --publish 80:8080 --name echo registry:5000/example/echo

$ docker service ps echo
-------------------------------
ID                  NAME                IMAGE                               NODE         
zpnmq301f939        echo.1              registry:5000/example/echo:latest   83c6350a4cd4
-------------------------------

# localhost:8000 접속 완료
# service 생성 확인
# 일반적으로 service는 manager container에서 생성되지만 위의 경우에는 worker02에서 생성되었다.
```



- **docker service scale - scale out(↔ scale in)**

---

> 서비스의 컨테이너 수를 늘리거나 줄일 수 있는 명령어. 



```powershell
> docker exec -it manager sh
-----------------------------
$ docker service scale echo=3


...

$ docker service ps echo
# service 들이 어떤 container로 구성되어 있는지 확인할 수 있는 명령어
------------------------------
ID                  NAME                IMAGE                               NODE         
b3eac9alnizn        echo.1              registry:5000/example/echo:latest   83c6350a4cd4 
fflcavn8n0ki        echo.2              registry:5000/example/echo:latest   c1730324b70a 
4v229fkf8lgc        echo.3				registry:5000/example/echo:latest   fcc4907d4119

```





## 2. Docker Stack

> 하나 이상의 서비슬르 그룹으로 묶은 단위로, 애플리케이션 전체 구성을 정의한다.



**※ 서비스는 애플리케이션 이미지를 하나 밖에 다루지 못함**





- **Overlay 네트워크 생성**

---

```powershell
> docker exec -it manager sh
-----------------------------
$ docker network create --driver=overlay --attachable ch03
$ docker network ls
-----------------------------
NETWORK ID          NAME                DRIVER              SCOPE
5x1u5qb84i1q        ch03                overlay             swarm
-----------------------------
```





- **dockerfile**: my-webapi.yml

---

```yaml
version: "3"
services:
    api:
        image: registry:5000/example/echo:latest
        deploy:
            replicas: 3
            placement:
                constraints: [node.role != manager]
        networks:
            - ch03
                       
    nginx:
        image: gihyodocker/nginx-proxy:latest
        deploy:
            replicas: 3
            placement:
                constraints: [node.role != manager]
        environment:
            BACKEND_HOST: echo_api:8080
        networks:
            - ch03
        depends_on:
            - api
networks:
    ch03:
            external: true
```



- **docker stack**

---

```powershell
[my-webapi.yml]

manager
	volumes:
		- "./stack:/stack"

#my-webapi.yml 파일을 volume mount가 되어있는 stack 디렉토리로 옮겨준다.
#Windows와 linux 간의 디렉토리 mount

> docker exec -it manager sh
---------------------------
$ cd /stack
/stack $ docker stack deploy -c /stack/my-webapi.yml echo

/stack $ docker service ls
----------------------------
ID                  NAME                MODE                REPLICAS            IMAGE     
wvn5ggh5iifa        echo_api            replicated          3/3                 registry:5000/example/echo:latest
4u7icgntep0l        echo_nginx          replicated          3/3                 gihyodocker/nginx-proxy:latest
-----------------------------


/stack $ docker stack ls
-----------------------------
NAME                SERVICES            ORCHESTRATOR
echo                2                   Swarm
```

- echo_api 1~3 , echo_nginx 1~3 은 worker node 들에 하나씩 추가된다.





- **visualizer**

---



- visualizer.yml

```yaml
version: "3"
services:
    app:
        image: dockersamples/visualizer       
        volumes:
            - /var/run/docker.sock:/var/run/docker.sock
        deploy:
            mode: global
            placement:
                constraints:
                    - node.role == manager
        ports:
            - 9000:8000
             
```



- powershell

```powershell
> docker container exec -it manager docker stacke deploy -c /stack/visualizer.yml visualizer

# http://localhost:9000 접속
```







### 3. 스웜 클러스터 외부에서 서비스 사용하기

---

> 외부 호스트에서 요청되는 트래픽을 목적 서비스로 보내주는 프록시 서버 설정



![diagram](https://user-images.githubusercontent.com/58682321/72128062-f5408e80-33b4-11ea-8bc9-be0c6bda0b55.PNG)



- **ch03-ingress.yml**

---

```yaml
version: "3"
services:
    haproxy:
        image: dockercloud/haproxy
        networks:
            - ch03
        volumes:
            - /var/run/docker.sock:/var/run/docker.sock
        deploy:
            mode: global
            placement:
                constraints:
                    - node.role == manager
        ports:
            - 80:80
            - 1936:1936 
networks:
    ch03:
        external: true
```



- **my-webapi.yml**

---

```yaml
version: "3"
services:
    api:
        image: registry:5000/example/echo:latest
        deploy:
            replicas: 3
            placement:
                constraints: [node.role != manager]
        networks:
            - ch03
                       
    nginx:
        image: gihyodocker/nginx-proxy:latest
        deploy:
            replicas: 3
            placement:
                constraints: [node.role != manager]
        environment:
            SERVICE_PORTS: 80
            BACKEND_HOST: echo_api:8080
        networks:
            - ch03
        depends_on:
            - api
networks:
    ch03:
        external: true
```



- **배포**

---

```powershell
$ docker stack deploy -c /stack/my-webapi.yml echo
$ docker stack deploy -c /stack/ch03-ingress.yml ingress
$ docker service ls
----------------------------
 NAME         ...                               PORTS
ingress_haproxy   ...         *:80->80/tcp, *:1936->1936/tcp
```



- **서비스 이용**

---

```powershell
$ curl http://localhost:8000/
Hello Docker!!%
```

