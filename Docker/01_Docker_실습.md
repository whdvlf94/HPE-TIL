

# 0. Container 구축 및 배포



### 1. Dockerfile 수정 방법

---



- **Build 사용**

---



```powershell
> code .
# visual studio 에서 dockerfile 코드 수정
# do

> docker build -t whdvlf94/simpleweb:modified .
# 변경된 image build

> docker stop [수정 전 container ID]
> docker rm [수정 전 container ID]
> docker rmi [수정 전 image ID]
> docker run -d -p 8080:8080 whdvlf94/simpleweb:modified
# image, container 수정 후 재시작
```

**-> 너무 비효율 적이다.**



- **Container 에서 수정**

---



```powershell
> docker exec -it [container ID or Name]  sh

/home/node # vi index.js

> docker restart [container ID]
```

- **windows에 있는 index.js 파일은 내용이 바뀌지 않는다.**





### 2. 퍼시스턴스 데이터를 다루는 방법

---

> -v 옵션, --volume-from 옵션 이용하기



#### Date Volume

---

>  windows 에서 수정 후, 바로 반영



**vloume을 마운트 하여 바로바로 업데이트 되도록 해준다**



- 컨테이너 업데이트
  - 새 버전의 이미지 다운 -> **pull**
  - 기존 컨테이너 삭제 -> **stop, rm**
  - 새 이미지를 이용하여 새 컨테이너 실행 -> **run**



- 컨테이너 유지 정보
  - AWS S3
  - 데이터 볼륨



```powershell
> docker run -d -p ~ \
  -v [host 위치]:[container 위치]
```





**※ dockerfile 을 이용하여 성능 개선하는 방법 ?**

---

> Dockerfile - Visual Studio Code 이용



```powershell
PS C:\Users\HPE\docker\day01\simpleweb> docker build -t whdvlf94/simpleweb:modified .
----------------------------------------------------
Sending build context to Docker daemon   2.01MB
Step 1/6 : FROM node:alpine
 ---> e1495e4ac50d
Step 2/6 : WORKDIR /home/node
 ---> 'Using cache
 ---> e897974810e6
Step 3/6 : COPY ./package.json ./package.json
 ---> 'Using cache
 ---> 455646685b36
Step 4/6 : COPY ./index.js ./index.js
 ---> 'Using cache
 ---> f30a84726027
Step 5/6 : RUN npm install
 ---> 'Using cache
 ---> 92c8acb2658a
Step 6/6 : CMD ["npm", "start"]
 ---> 'Using cache
 ---> 2a732c94f688
 ----------------------------------------------------
 
 # 'Using cache' 사용하여 속도를 줄일 수 있다.
 # 시간이 소요되는 것은 image 다운과 dependency 부분이다.
 
```



- **dockerfile 수정**

```dockerfile
# 성능 개선?

FROM node:alpine

WORKDIR /home/node
COPY ./package.json ./package.json
RUN npm install

COPY ./index.js ./index.js 


CMD ["npm", "start"]
```



- **-v 옵션 : mount 작업**

---

**※ 사전 작업**

1. image, container 모두 삭제
2. dockerfile 수정

```powershell
> docker build -t whdvlf94/simpleweb:modified .

-mount 작업-
> docker run -v [windows 위치]:[linux 위치] -d -p 8080:8080 whdvlf94/simpleweb:modified
# index.js , package.json 이 포함되어 있는 디렉토리 위치



> docker exec -it [container ID] sh

# dockfile 에서 문구 수정

/home/node # cat index.js

# cat 명령어로 확인해보면 수정사항이 바로 반영되는 것을 알 수 있다.
```





<h4> Data volume container </h4>
---

> 데이터 볼륨에 MySQL 데이터 저장하기



- **dockerfile**

```dockerfile
\Users\HPE\docker\day02\vd> code .

FROM busybox
VOLUME  /var/lib/mysql 
CMD [ "bin/true" ]

# 볼륨 컨테이너 역할을 하는 이미지
```



- **powershell**

```powershell
> docker build -t example/mysql-data:latest .
> docker run -d --name mysql-data example/mysql-data:latest
```



- **--volume-from 옵션** 

```powershell
> docker run -d --rm --name mysql `
>> -e "MYSQL_ALLOW_EMPTY_PASSWORD=yes" `
>> -e "MYSQL_DATABASE=volume_test" `
>> -e "MYSQL_USER=example" `
>> -e "MYSQL_PASSWORD=example" `
>> --volumes-from mysql-data `
>> mysql:5.7

# volume_test databases 생성


> docker exec -it mysql mysql -uroot -p volume_test
# docker exec -it mysql sh
# mysql -uroot -p volume_test

# docker exec -it mysql : mysql(container)에 추가적으로 cmd 실행 하겠다
# container에 접속하여 mysql -uroot -p volume_test를 실행하겠다.
# volume_test 를 뒤에 붙인 이유는 접속하면서 vloume_test를 사용하겠다는 의미

[mysql]

mysql> create table user(id int primary key auto_increment, name varchar(20));
mysql> insert into user(name) values('User1');
mysql> insert into user(name) values('User2');
mysql> insert into user(name) values('User3');


mysql> select * from user;
+----+-------+
| id | name  |
+----+-------+
|  1 | User1 |
|  2 | User2 |
|  3 | User3 |
+----+-------+

```



- **docker **

```powershell
> docker container stop mysql
# --rm 설정을 했기 때문에, stop 명령어를 실행할 경우 삭제까지 된다.


> docker run -d --rm --name mysql `
>> -e "MYSQL_ALLOW_EMPTY_PASSWORD=yes" `
>> -e "MYSQL_DATABASE=volume_test" `
>> -e "MYSQL_USER=example" `
>> -e "MYSQL_PASSWORD=example" `
>> --volumes-from mysql-data `
>> mysql:5.7

# volume_test databases 다시 생성

> docker exec -it mysql mysql -uroot -p volume_test

...
[mysql]

mysql> select * from user;
+----+-------+
| id | name  |
+----+-------+
|  1 | User1 |
|  2 | User2 |
|  3 | User3 |
+----+-------+

```



#### container를 삭제했음에도 불구하고 지워지지 않았다. 그 이유는 ?

**->  애플리케이션 컨테이너와 데이터 볼륨 컨테이너를 분리하여 저장했기 때문**

![container](https://subicura.com/assets/article_images/2017-01-19-docker-guide-for-beginners-2/container-update.png)

[출처]([https://cultivo-hy.github.io/docker/image/usage/2019/03/14/Docker%EC%A0%95%EB%A6%AC/](https://cultivo-hy.github.io/docker/image/usage/2019/03/14/Docker정리/))

---







### 3. docker-compose

---

> yaml 포맷으로 기술된 설정 파일로, 여러 컨테이너의 실행을 한 번에 관리할 수 있게 해줌



- **docker-compose 사용 예시**

```powershell
> docker-compose version
# 컨테이너 실행


[dockerfile]
version:
services:
   echo:
    image: example/echo:latest
    ports:
        - 9000:8080
        
[powershell]
$ docker build -t example/echo:latest ./Dockerfile
$ docker run -p 9000:8080 example/echo:latest

# docker-compose(dockerfile 로 작성한 명령어)가 더 복잡해 보이지만
# 여러 개의 컨테이너를 실행할 수 있다.

# docker-compose의 service의 단위는 container이다.
```

**※ 확장자는 .yaml or .yml 로 지정**





- **docker-compose 실습**

---

\- dockerfile > **docker-compose.yml** 생성

```powershell
[dockerfile]
version: '3'
services: 
    my-mysql:
        image: mysql:5.7
        ports:
            - '3306:3306'


[powershell]
> docker run -d -p 3306:3306 --name my-mysql mysql:5.7
```



- **powershell**

```powershell
PS C:\Users\HPE\docker\day02\vd> docker-compose up

> docker run -d -p --name my-mysql mysql:5.7

# MYSQL_ALLOW_EMPTY_PASSWORD=true 가 없어 container가 실행되지 않는다.
# 아래와 같이 명령어를 작성해야 함.

docker run  -p 3306:3306 --name -e MYSQL_ALLOW_EMPTY_PASSWORD=true  my-mysql mysql:5.7
```



- **docker-compose.yml**

```dockerfile
version: '3'
services: 
    mysql1:
        image: mysql:5.7
        ports:
            - '3306:3306'
        environment: 
            - MYSQL_ALLOW_EMPTY_PASSWORD=true
```



- **powershell**

```powershell
> docker-compose up
# container 실행
# 서버가 실행 중 이므로 새로운 cmd 창을 열어 container가 제대로 작동 중인지 확인

> docker-compose up -d
# 서버를 백그라운드에 실행 시켜 현재 cmd 창을 이용할 수 있다.

> docker-compose down
# container 종료
# stop , rm 같이 진행된다
# 위의 명령어는 docker-compose.yml 이 저장되어 있는 디렉토리에서 가능하다.
```
