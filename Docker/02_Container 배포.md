# 0. Container 배포



### 1. Mongodb를 이용한 실습

---



- **mongodb 접속 하기**

```
1. mongodb 를 docker container로 실행
2. dockerfile 작성 (mongodb 설치를 위한 이미지 생성)
3. dockerfile의 image build
    - whdvlf94/mymongdb:latest
4. mongodb container 생성 -> 실행
5. client에서 mongodb 테스트
    $ mongo -h(ip) -p(port)
    mongo> show dbs;
    mongo> use bookstore;
    mongo> db.books.save('{"title":"Docker compose smapel"}');
    mongo> db.books.find();
```

```powershell
[powershell]
PS C:\Users\HPE\docker\day02> mkdir mongo
PS C:\Users\HPE\docker\day02> cd mongo
PS C:\Users\HPE\docker\day02\mongo> code dockerfile

[dockerfile]
---------
FROM mongo

CMD ["mongod"]
---------


[powershell]
---------
> docker build -t whdvlf94/mymongodb:latest .
> docker run -p 27017 whdvlf94/mymongodb:latest

> docker exec -it [container ID] bash
root@11e3442ae5bd:/# mongo

or

> docker exec -it [containeer ID or Name] mongo


#mongodb 접속 완료


```



- **mongod node 생성하기**

```
4.  rs.initiate()
5.  rs.add("10.0.0.12:40002")
    rs.add("10.0.0.13:40003", {arbiterOnly: true}) --> Primary 선정에만 관여, 복제는 하지 않음
6.  db.isMaster() 
#현재 node가 master인지 확인하는 명령어

7.  rs.status()
8.  (NODE01)
	mongo 10.0.0.11:40001
        > use bookstore
        > db.books.insert({title: "Oliver Twist"})
        > show dbs
9.  mongo 10.0.0.12:40002
        > rs.slaveOk()
        > show dbs
        > db.books.find()
10. (Primary) > db.shutdownServer()
11. (Secondary) -> (Primary) 로 승격
    - db.books.insert() 사용 가능
    - 나머지 노드들은 지속적으로 master에게 heartbeat 전달
12. (기존 Master 다시 기동) -> Secondary로 작동 됨 

** MongoDB 3대 설치 (Primary x 1, Secondary x 2)
13. Replica Set
    ex) mongod --replSet myapp --dbpath /폴더지정 --port 27017 --bind_ip_all
```



- **dockerfile**

```dockerfile
FROM mongo

CMD ["mongod","--replSet","myapp"]
```



- **powershell**

```powershell
[server]
> docker build -t whdvlf94/mymongodb:latest .
> docker run -p 27017:27017 whdvlf94/mymongodb:latest

[client]
# 새로운 powershell 열기
> docker exec -it [container ID] bash
# mongo

or

> docker exec -it [container ID] mongo

> rs.initiate();


> docker exec -it [container ID] hostname -i

-----------
# bash에서 ip 주소 확인하기
- ifconfig
- ip addr show
- hostname -i
-----------
```



- **container 여러 개 생성**

```powershell
> docker run -d -p [host port]:[container port] whdvlf94/mymongodb:latest

# container를 여러 개 생성할 경우 host port는 다르게 설정해야 충돌이 나지 않는다.
# 단, container port는 동일하게 설정하더라도 충돌이 나지 않는다.

[ip 주소 확인하는 방법]
> docker inspect [container ID]
> docker exec -it [container ID] hostname -i

[mongo 접속 방법]
> docker exec -it [container ID] sh

/# mongo [host ip]:[port]

```



**※ 포트 포워딩**

| windows IP:PORT | container IP:PORT | 포트 포워딩   |
| :-------------: | :---------------: | ------------- |
| 127.0.0.1:27017 |  172 ~ .1:27017   | 27017 → 27017 |
| 127.0.0.1:27018 |  172 ~ .3:27017   | 27018 → 27017 |
| 127.0.0.1:27019 |  172.~ .4:27017   | 27019 → 27017 |



- **container node 간 연결 - ping test**

```powershell
# 현재 3개의 container 노드 생성
# 각 노드 끼리 연결이 되는지 테스트 하기 위해 ping test 진행

> docker exec -it [container ID] bash
/# apt-get update
/# apt-get install -y inutils-ping
# 3개 노드 모두 진행


[27017 -> 27018]

27017 /# mongo [172. ~ .3]:27017
```



- **작업 단순화 하기**

---



- **setup.sh , replicaSet.js 수정**

```powershell
[setup.sh]

mongo mongodb://172.17.0.2:27017 replicaSet.js



[replicaSet.js]

config = {
    _id: "replication",
    members: [
        {id:0, host: "mongo1:27017"},
        {id:1, host: "mongo2:27017"},
        {id:2, host: "mongo3:27017"},

    ]
}

rs.initiate();
rs.conf();

# node 마다 id를 지정할 수 있는 장점이 있다.
```



- **dockerfile**

```dockerfile
FROM mongo

RUN mkdir /usr/src/configs
WORKDIR /usr/src/configs
COPY replicaSet.js .
COPY setup.sh .

CMD ["./setup.sh"]
```





## 중요!!



- **powershell**

**※ 사전 작업 container , image 삭제**

```powershell
> docker build -t whdvlf94/mymongodb

> docker run -p [172. ~ .2]:27017 whdvlf94/mymongodb

# connect error
# ip 주소가 [172. ~ .2] 가 아니거나 
# setup.sh 의 mongod 명령어는 서버가 실행되야 가능한 명령어 이나
# 서버가 실행되기 이전이므로 container가 실행되지 않았을 수 도 있다.
```

---



- **해결 방법 -> docker-compose**

```yaml
[docker-compose.yml]

version: "3"
services:
    mongo1:
        image: "mongo"
        ports:
            - "27017:27017"
        volumes:
            - $HOME/mongoRepl/mongo1:/data/db #mount 작업
        networks:
            - mongo-networks
        command: mongod --replSet myapp
    mongo2: #mongo1 처럼
    mongo3: #mongo1 처럼

    mongo_setup:
        image: "mongo_repl_setup:latest"
        depends_on: #우선 순위 지정 
            - mongo1
        networks:
            - mongo-networks

networks:
    mongo-networks:
        driver: bridge



[replicaSet.js]

config = {
    _id: "replication",
    members: [
        {_id:0, host: "mongo1:27017"},
        {_id:1, host: "mongo2:27017"},
        {_id:2, host: "mongo3:27017"},

    ]
}

rs.initiate(config);
rs.conf();

# 172 ~ ip 주소는 container를 생성할 때 할당되는 유동 ip 이기 때문에 
# ip 주소 대신 hostname(mongo 1~3)로 할당하는 것이 바람직하다.
```



**이어서 진행 **

```
7.  rs.status()
8.  (NODE01)
	mongo 10.0.0.11:40001
        > use bookstore
        > db.books.insert({title: "Oliver Twist"})
        > show dbs
9.  mongo 10.0.0.12:40002
        > rs.slaveOk()
        > show dbs
        > db.books.find()
10. (Primary) > db.shutdownServer()
11. (Secondary) -> (Primary) 로 승격
    - db.books.insert() 사용 가능
    - 나머지 노드들은 지속적으로 master에게 heartbeat 전달
12. (기존 Master 다시 기동) -> Secondary로 작동 됨 

** MongoDB 3대 설치 (Primary x 1, Secondary x 2)
13. Replica Set
    ex) mongod --replSet myapp --dbpath /폴더지정 --port 27017 --bind_ip_all
```



