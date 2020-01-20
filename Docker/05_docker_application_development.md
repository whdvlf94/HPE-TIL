# Swarm을 이용한 실전 애플리케이션 개발



### 1. MySQL 서비스 구축

---



- **배치 구조**

| MySQL        | Application | Frontend       |
| ------------ | ----------- | -------------- |
| mysql_master | app_nginx   | frontend_nginx |
| mysql_slave  | app_api     | frontend_web   |

![Untitled Diagram.png](https://github.com/whdvlf94/TIL/blob/master/img/Untitled%20Diagram.png?raw=true)







- **MySQL 작업 - dockerfile**

---

```dockerfile
FROM mysql:5.7

# (1) 패키지 업데이트 및 wget 설치
RUN apt-get update
RUN apt-get install -y wget

# (2) entrykit 설치 - 선행 작업이 필요한 경우
# entrykit를 사용하지 않는 경우 server-id를 아래에 입력해 놓았다 하더라도 메인쓰레드 보다 나중에 실행되는 경우가 발생할 수 있다.
# server-id를 입력하는 작업
RUN wget https://github.com/progrium/entrykit/releases/download/v0.4.0/entrykit_0.4.0_linux_x86_64.tgz
RUN tar -xvzf entrykit_0.4.0_linux_x86_64.tgz
RUN rm entrykit_0.4.0_linux_x86_64.tgz
RUN mv entrykit /usr/local/bin/
RUN entrykit --symlink

# (3) 스크립트 및 각종 설정 파일 복사
COPY add-server-id.sh /usr/local/bin/
COPY etc/mysql/mysql.conf.d/mysqld.cnf /etc/mysql/mysql.conf.d/
COPY etc/mysql/conf.d/mysql.cnf /etc/mysql/conf.d/
COPY prepare.sh /docker-entrypoint-initdb.d
COPY init-data.sh /usr/local/bin/
COPY sql /sql

# (4) 스크립트, mysqld 실행
ENTRYPOINT [ \
  "prehook", \
    "add-server-id.sh", \
    "--", \
  "docker-entrypoint.sh" \
]

CMD ["mysqld"]
# mysql : client , mysqld : server

```





- **image 빌드 및 registry 등록 **

---

```powershell
> cd C:\Users\HPE\docker\day04\swarm\todo\tododb
# dockfile 이 있는 곳으로 이동

> docker build -t ch04/tododb:latest .
> docker tag ch04/tododb:latest localhost:5000/ch04/tododb:latest

or

> docker build -t localhost:5000/ch04/tododb:latest .
> docker images 
# 제대로 다운되었는지 확인

> docker push localhost:5000/ch04/tododb
# http://localhost:5000/v2/_catalog  or
# curl http://localhost:5000/v2/_catalog 에 접속하여 registry에 제대로 등록되었는지 확인
```





- **swarm으로 배포하기 - todo_mysql**

---



- **overlay 네트워크 구축**

```powershell
> docker exec -it manager sh
-----------------------------
$ docker network create --driver=overlay --attachable todoapp
$ docker network ls
# NAME todoapp 인 network 가 생성되었는지 확인


> docker stack deploy -c /stack/todo-mysql.yml todo_mysql
# todo-mysql.yml 에 있는 master, slave 서비스를 stack으로 사용
# 이 때, stack의 이름은 todo_mysql

> docker stack ls
# docker stack 생성 확인

```



- **replicas가 shutdown 되는 경우**

- swarm>todo> [파일명].sh , Dockerfile, todo-mysql.yml 파일형식 LF로 설정 

```powershell
> docker build -t localhost:5000/ch04/tododb:latest .
# 설정 후 다시 image 빌드
> docker push localhost:5000/ch04/tododb
> docker exec -it manager sh
------------------------------
$ docker stack rm todo_mysql
# 제대로 작동되지 않았던 stack 삭제 후 다시 생성

$ docker stack deploy -c /stack/todo-mysql.yml todo_mysql
$ docker service ls
# 제대로 작동하는지 확인
```

---





- **worker node 조사**

---

| Worker01            | 172.xx.x.4     |      |
| ------------------- | -------------- | ---- |
| todo_mysql_slave.2  | 10.0.x.xx7     | 3306 |
| **Worker02**        | **172.xx.x.6** |      |
| todo_mysql_master.1 | 10.0.x.xx4     | 3306 |
| **Worker03**        | **172.xx.x.5** |      |
| todo_mysql_slave.1  | 10.0.x.xx6     | 3306 |



- **MySQL 접속하기**

---

**방법 1)**

```powershell
> docker exec -it worker02 sh
# master로 접속
------------------------------
$ docker exec -it [master ID] bash
# MySQL 접속 완료

------------------------------
root@~ $ hostname -i
10.0.x.xx4 
# mysql_master.1의 hostname과 동일한 것 확인
```



**방법 2)**

```powershell
> docker exec -it manager `
>> docker service ps todo_mysql_master `
>> --no-trunc `
>> --filter "desired-state=running" `
>> --format "docker container exec -it {{.Node}} docker exec -it {{.Name}}.{{.ID}} bash"

# Windows 경우 \ 대신 ` 사용
# slave 의 경우 master 대신 slave로 바꿔서 입력하면 된다.
-------------------------------------------------
docker container exec -it 83c6350a4cd4 docker exec -it todo_mysql_master.1.uzsv9nxfgso7vd9t75gix7km7 bash
# 위의 명령어를 실행하면 두 번 접속할 필요없이 windows에서 바로 접속이 가능하다.
```

---



- **데이터베이스 접속**

---

```powershell
[master node]

root@ ~ $ init-data.sh
root@ ~ $ mysql -uroot -p tododb
password : gihyo

mysql> select * from todo;

# init-data.sh 명령어 실행으로 db 내용 변경
# slave node에서도 변경되었는지 확인

[slave node] 
# 방법 2로 mysql 접속

root@ ~ $ mysql -uroot -p tododb
password : gihyo

mysql> select * from todo;

# master에서 변경한 내용을 확인할 수 있다.

```

