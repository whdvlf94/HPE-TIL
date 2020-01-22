# Swarm을 이용한 실전 애플리케이션 개발





## 1. API 서비스 구축



- **API** : **TODO 앱에서 각 엔트리에 대한 기본적인 조작을 제공하는 RESTful API**

  - TODO 앱의 도메인에 특화된 마이크로서비스.
  -  API 서비스에 전달된 INSERT,UPDATE, DELETE 쿼리는 마스터 DB로 전달되며 SELECT 쿼리는 슬레이브 DB에서 수행된다.

  

![Untitled Diagram.png](https://github.com/whdvlf94/TIL/blob/master/img/Untitled%20Diagram.png?raw=true)



- **iamge build - go 언어로 구현된 애플리케이션 빌드**

---

```powershell
cd  C:\Users\HPE\docker\day04\swarm\todo\todoapi


> docker build -t localhost:5000/ch04/todoapi .
> docker push localhost:5000/ch04/todoapi:latest

# http://localhost:5000/v2/_catalog 에 접속하여 이미지 push 확인

```





- **스웜에서 todoapi 서비스 실행하기**

---



- todo-app.yml

```yaml
version: "3"
services:
  api:
    image: registry:5000/ch04/todoapi:latest 
    deploy:
      replicas: 2
      placement:
        constraints: [node.role != manager]
    environment:
      TODO_BIND: ":8080"
      TODO_MASTER_URL: "gihyo:gihyo@tcp(todo_mysql_master:3306)/tododb?parseTime=true"
      TODO_SLAVE_URL: "gihyo:gihyo@tcp(todo_mysql_slave:3306)/tododb?parseTime=true"
    networks:
      - todoapp

networks:
  todoapp:
    external: true

```

※ nginx 는 주석 처리 할 것.



- **powershell**

```powershell
> docker container exec -it manager sh
$ docker stack deploy -c /stack/todo-app.yml todo_app
$ docker stack ls
---------------------
NAME                SERVICES            ORCHESTRATOR
todo_app            1                   Swarm
todo_mysql          2                   Swarm
visualizer          1                   Swarm

# stack 설정은 되었지만, 제대로 운영 중인지 알 수 없다.
----------------------


$ docker stack services todo_app
----------------------
ID                  NAME                MODE                REPLICAS            IMAGE                               PORTS
p0luwrbhuttq        todo_app_api        replicated          2/2                 registry:5000/ch04/todoapi:latest
# 잘 작동되고 있는 것을 확인
----------------------


$ docker service ps todo_app_api
# 2개의 todo_app_api 가 어떤 노드에서 생성되었는지 확인할 수 있는 명령어



$ docker service logs -f todo_app_api
todo_app_api.2.jyh4r3eg30z0@b222c76f5b9d    | 2020/01/14 01:30:46 Listen HTTP Server
todo_app_api.1.8k519mv05kf4@ba9f1c65212c    | 2020/01/14 01:31:39 Listen HTTP Server
# api service 작동 여부 확인하기
# replicas :2 로 설정했기 때문에 2개의 api service가 생성되었다.
```



- **netstat 설치**

```powershell
> docker exec -it worker02 sh
/$ docker exec -it [app_api_1] bash

$ apt-get update
$ apt-get install -y net-tools
$ netstat -ntpl
# 8080 port 생성 확인
# image: registry:5000/ch04/todoapi:latest 
# TODO_BIND: 8080
# 즉, todo_app_api 1,2 가 포함되어 있는 node(port8080)에서 실행하면 된다.
```



- **server POST**

```powershell
[app_api_1] $ curl -XGET http://localhost:8080/todo?status=TODO
----------------------
[{"id":8,"title":"인그레스 구축하기","content":"외부에서 스웜 클러스터에 접근하게 해주는 인그레스 구축","status":"TODO","created":"2020-01-14T00:36:29Z","updated":"2020-01-14T00:36:29Z"}]
----------------------

> curl -XPOST -d '{"title":"Test1","content":"Test Content1"}' http://localhost:8080/todo
> curl -XGET http://localhost:8080/todo?status=TODO
----------------------
[{"id":9,"title":"Test1","content":"Test Content1","status":"TODO","created":"2020-01-14T02:47:50Z","updated":"2020-01-14T02:47:50Z"},{"id":8,"title":"인그레스 구축하기","content":"외부에서 스웜 클러스터에 접근하게 해주는 인그레스 구축"
```

**※ API image가 설정되어 있는 node에서만 수행가능**



- **server DONE**

```powershell
> curl -XPUT -d '{"id":9, "title":"Test modified","content":"Test Content modified", "status":"DONE"}' http://localhost:8080/todo
```







## 2. Nginx 구축



- 클라이언트 요청 -> **8000 port**: Nginx reverse proxy(todo_app_api:8080) ->**8080 port**:backend web application(**API 서비스**)



- 클라이언트로부터 받은 HTTP 요청을 Nginx의 `리버스 프록시` 기능을 사용해 백엔드 웹 애플리케이션으로 전송하는 작업



---



- **image build**

```powershell
> PS C:\Users\HPE\docker\day04\swarm\todo\todonginx> docker build -t localhost:5000/ch04/nginx:latest .
```



- todo-app.yml -주석 처리 해제

```yaml
version: "3"
services:
  nginx:
    image: registry:5000/ch04/nginx:latest
    deploy:
      replicas: 2
      placement:
        constraints: [node.role != manager]
    depends_on:
      - api
    environment:
      WORKER_PROCESSES: 2
      WORKER_CONNECTIONS: 1024
      KEEPALIVE_TIMEOUT: 65
      GZIP: "on"
      BACKEND_HOST: todo_app_api:8080
      BACKEND_MAX_FAILES: 3
      BACKEND_FAIL_TIMEOUT: 10s
      SERVER_PORT: 8000
      SERVER_NAME: todo_app_nginx
      LOG_STDOUT: "true"
    networks:
      - todoapp
```



```powershell
> docker exec -it manager sh
/ $ docker stack deploy -c /stack/todo-app.yml todo_app

# image update


/ $ docker stack services todo_app
---------------------------------
ID                  NAME                MODE                REPLICAS            IMAGE                               PORTS
kmjf4rqf3dd2        todo_app_api        replicated          2/2                 registry:5000/ch04/todoapi:latest
nvybp742hn8s        todo_app_nginx      replicated          2/2                 registry:5000/ch04/nginx:latest
---------------------------------
# 작동 여부 확인

/ $ docker service ps todo_app_api
---------------------------------
ID                  NAME                IMAGE                               NODE                DESIRED STATE       CURRENT STATE            ERROR               PORTS
nkxi6j1onx4h        todo_app_api.1      registry:5000/ch04/todoapi:latest   1dc389455110        Running             Running 24 minutes ago                
xuu7141y0yai        todo_app_api.2      registry:5000/ch04/todoapi:latest   ba9f1c65212c        Running             Running 24 minutes ago
---------------------------------
# 서비스가 어떤 node에서 생성되었는지 확인
```



- **todo_app_nginx 접속**

---

```powershell
> docker exec -it worker01 sh
/$ docker exec -it [todo_app_nginx.1 ID] bash

$ apt-get update
$ apt-get install -y curl
# curl 명령어 설치

$ curl http://localhost:8000
# 접속 가능 확인

$ curl -XGET http://localhost:8000/todo?status=TODO
```





| Worker01(b222c76f5b9d)  | Worker02(**ba9f1c65212c**) | Worker03(**1dc389455110**) | PORT     |
| ----------------------- | -------------------------- | -------------------------- | -------- |
| 172.21                  | 172.21                     | 172.21                     |          |
| **todo_mysql_master.1** | **todo_mysql_slave.2**     | **todo_mysql_slave.1 **    | **3306** |
| 10.0.2.                 | 10.0.2.                    | 10.0.2.                    |          |
|                         | **todo_app_api.2**         | **todo_app_api.1**         | **8080** |
|                         | 10.0.2.                    | 10.0.2.                    |          |
| **todo_app_nginx.1**    | **todo_app_nginx.2**       |                            |          |
| 10.0.2.                 | 10.0.2.                    |                            |          |