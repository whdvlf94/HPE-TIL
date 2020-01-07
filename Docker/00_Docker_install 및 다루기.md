

# Docker

> 컨테이너 기반의 오픈소스 가상화 플랫폼

- 개발 환경에서 공통된 부분은 공유하고, 다른 부분만 추가하는 프로덕트





## 0. Docker 실습

- **helloworld shell script file**

```shell
#!/bin/sh

echo "Hello, Wolrd!"

# shell script file의 이름은 helloworld
```



- **Dockerfile**

```dockerfile
FROM ubuntu:16.04
# 컨테이너의 원형(틀) 역할을 할 도커 이미지(운영 체제)를 정의
# 위의 경우는 ubuntu image 이고, version 16.05 이다.

COPY helloworld /usr/local/bin
# shell script file 을 local(docker) /usr/local/bin 디렉토리에 복사하겠다는 의미
# COPY [source] [target]

RUN chmod +x /usr/local/bin/helloworld
# 도커 컨테이너 안에서 어떤 명령을 수행하기 위한 것
# shell script file 의 실행 권한 설정


CMD ["helloworld"]
# 완성된 이미지를 도커 컨테이너로 실행하기 전에 먼저 실행할 명령을 정의
```



- **Docker image build**

```shell
$ docker image build -t helloworld:latest .
# dockerfile을 토대로 새로운 이미지 생성.
# : 뒤에는 버전을 입력
# . 는 현재 디렉토리를 의미함
```

**\- 현재까지는 실행할 수 없다. 이미지 파일로 하나의 템플릿을 만든 것**



- **Docker container run**

```shell
$ docker container run hellowworld:latest
Hello, World!
# run = create + start
# container를 재시작 할 경우 start 명령어만 입력해주면 된다.
```

**\- 도커 이미지를 실체화 시키는 과정**



**-> 도커 이미지에 애플리케이션에 필요한 파일을 운영 체제와 함께 담아서 컨테이너 형태로 실행**



## 1. 로컬 도커 환경 구축하기

[hub.docker](https://hub.docker.com/) : image file download



#### Settings

---

- shared Drives : 비밀번호를 설정해야 시작할 수 있다.
  - 설정 > 계정 > 로그인 옵션 > 비밀번호 설정



```powershell
> docker run --rm -v c:/Users:/data alpine ls /data

# local c:/User 에 저장한 파일을 docker의 /data 디렉토리에 저장하겠다는 의미
```



#### sign in

---

1. **windows > sign in**



2. **powershell 사용**

```powershell
> docker login

> docker version
# server, client 두 가지 community가 있다.
```





#### Docker의 구성요소

---

- **Docker Image** : 도커 컨테이너를 구성하는 파일 시스템과 실행할 애플리케이션 설정을 하나로 합친 것으로, 컨테이너를 생성하는 템플릿 역할
- **Docker Container** : 도커 이미지를 기반으로 생성되며, 파일 시스템과 애플리케이션이 구체화돼 실행되는 상태





#### 도커 이미지로 도커 컨테이너 만드는 과정

---

- **Docker image**

```powershell
> docker image pull gihyodocker/echo:latest

# hub.docker 에 있는 이미지 파일을 pull 로 다운로드 할 수 있다.
# 단순히 템플릿 상태, 아직 실행할 수 없다.

> docker image ls

# 이미지 실행 상태 확인

```

- **Docker container**

```powershell
> docker run gihyodocker/echo:latest

# 도커 이미지 실행

> curl http://localhost:9000/
# 접속되지 않는다
# port 설정이 되어 있지 않기 때문

> docker container run -p 9000:8080 gihyodocker/echo:latest 
> curl http://localhost:9000/

```

- **포트 포워딩 : docker host 포트(localhost) 9000으로 호출하면 8080 주소(docker container)로 연결 해주겠다는 의미**

-  **Windows 포트 번호를 입력해주지 않으면 임의의 번호로 생성된다.**





```powershell
> docker ps
> docker container ls
# 현재 작동중인 container를 나타내는 명령어

> docker ps -a
> docker container ls -a
# 종료된 container까지 나타내는 명령어

> docker stop [container ID]
# container를 멈추는 명령어

> docker stop $(docker ps -q)
# 현재 작동중인 모든 container 멈추는 명령어
# docker rm $(docker ps -q) : 현재 작동중인 모든 container 삭제

> docker container rm [container ID]
> docker rm [container ID]
# container를 삭제하는 명령어

> docker container prune
# container를 한 번에 삭제하는 명령어

> docker image rm [image ID]
> docker rmi [image ID]
# image를 삭제하는 명령어 


> docker run --name myweb1 -d -p 8080 gihyodocker/echo:latest
# myweb1 이라는 이름으로 컨테이너 생성

```

**※  container ID 가 앞 두자리 만으로도 식별이 가능하다면 container ID 는 앞 두자리만 입력해도 stop(or rm)이 가능하다.**







### 2. 컨테이너 배포

---

1. 개발 :

   - package.json 

   - index.json

2. npm install : 의존성 (dependencies) 해결

   - install을 위해서는 node가 설치 되어 있어야 한다.

3. npm start -> node index.js

---



```powershell
PS C:\Users\HPE\docker\day01\simpleweb> code .
```



![package json](https://user-images.githubusercontent.com/58682321/71869854-f8d6da00-3156-11ea-9e46-d1f6de52c596.PNG)

![index js](https://user-images.githubusercontent.com/58682321/71869853-f8d6da00-3156-11ea-9306-bfedb8ca96c7.PNG)



```powershell

PS C:\Users\HPE\docker\day01\simpleweb> ls

    디렉터리: C:\Users\HPE\docker\day01\simpleweb


Mode                LastWriteTime         Length Name
----                -------------         ------ ----
-a----     2020-01-07   오후 1:55              0 index.js
-a----     2020-01-07   오후 1:54            120 package.json


PS C:\Users\HPE\docker\day01\simpleweb> npm install
# node_modules 이 설치된 것을 확인할 수 있다.
# jode.js 실행에 필요한 플랫폼

PS C:\Users\HPE\docker\day01\simpleweb> npm start

> @ start C:\Users\HPE\docker\day01\simpleweb
> node index.js

Listening on port 8080

# http://localhost:8080 로 입력하면 Hi, there! 의 문구가 나온다.
```





### 3. Docker를 이용한 컨테이너 배포

---

**< Node.js 이용한 컨테이너 배포>**

1. Node  설치

   : Node.js is a JavaScript-based platform for server-side and networking applications.

2. 개발 :

   - package.json 

   - index.json

3. npm install : 의존성 (dependencies) 해결

4. npm start -> node index.js

---

**< Docker >**

1. **FROM -> Node 사용가능 한 이미지**
2. **RUN -> NPM INSTALL 실행**
3. **CMD -> NPM START**

---



![docker](https://user-images.githubusercontent.com/58682321/71870732-66383a00-315a-11ea-9ddf-3bb7c850ba4d.PNG)

- alpine image 사용  : 
  - **A minimal Docker image based on Alpine Linux** with a complete package index and only 5 MB in size!
- cmd 사용법 : **CMD ["npm", "start"]**



```powershell
PS C:\Users\HPE\docker\day01\simpleweb> docker build -t whdvlf94/simpleweb:latest .
# -t(tag)

Step 2/3 : RUN npm install
 ---> Running in 54c22ae63d39
/bin/sh: npm: not found

# But , Node를 설치하지 않았기 때문에 install이 진행되지 않는다.
```



- **npm : not found 해결 방법**

  - Node 설치 : RUN curl -o- http:// ~

  - BASE IMAGE 교체

    

![node alpine](https://user-images.githubusercontent.com/58682321/71871264-fcb92b00-315b-11ea-8b6a-ec8c60d1e831.PNG)

​																				**<Base Image 교체>**



```powershell
PS C:\Users\HPE\docker\day01\simpleweb> docker build -t whdvlf94/simpleweb:latest .

Step 2/3 : RUN npm install
 ---> Running in 47741100a7ff
Step 3/3 : CMD ["npm", "start"]
 ---> Running in 18d3d966164c
 
# 설치 된 것을 확인할 수 있다.

> docker image ls
--------------------
REPOSITORY           TAG                 IMAGE ID            CREATED             SIZE
whdvlf94/simpleweb   latest              03c7bb9b6f52        8 minutes ago       111MB
---------------------
# docker images 로 입력해도 된다.

> docker ps -a
# container가 실행되지 않는 것을 확인

> docker logs [container ID]
# 문제가 발생한 container id로 log data 를 조회

```



- **container 가 실행되지 않을 때, 해결 방법**



![COPy](https://user-images.githubusercontent.com/58682321/71871866-f0ce6880-315d-11ea-9151-08b5f03623c4.PNG)





**※ 문제 원인)** : package.json 파일이 windows에 존재하기 때문에 docker와 호환이 되지 않아 오류가 생겼다. 따라서 

Windows에 있는 package.json 파일을 docker 디렉토리 내(**현재 디렉토리**)로 복사한다.

**(※ 이후 index.js 파일도 COPY 해준다.)**



```powershell
> docker build -t whdvlf94/simpleweb:latest .
# dockerfile을 수정했기 때문에 다시 build 해주어야 한다.

--------
> docker images
<none> ..
# none 은 기존에 있었던 image 파일 이므로 제거해주어야 한다.

> docker container rm [IMAGE ID] or docker container prune(container 모두 삭제)
> docker image rm [IMAGE ID]
--------

> docker run -d whdvlf94/simpleweb:latest
# 포트 포워딩이 되어있지 않기 때문에 웹 브라우저에서 연결이 되지 않는다.

> docker stop [container ID]
> docker run -d -p 8080:8080 whdvlf94/simpleweb:latest	컨테이너 생성
# 웹 브라우저에 접속 완료
```



- **image 파일 계정에 업로드 하기**

**※ docker에서는 image 파일을 repository 와 혼용해서 쓴다.**

```powershell
> docker push whdvlf94/simpleweb:latest
```



- **업로드 되어 있는 image 파일 가져오기**

```powershell
# 기존에 존재하는 container, image를 먼저 삭제

> docker stop [container ID]
> docker rm [container ID]

> docker ps -a
# container 종료된 것 확인

> docker rmi [image ID]

> docker images
# image 삭제된 것 확인

> docker pull whdvlf94/simpleweb:latest
> docker run -d -p 8080:8080 whdvlf94/simpleweb:latest
# 내 계정에 업로드 되어 있는 image file을 가져와 실행할 수 있다.
```



- **exec - 실행 중인 컨테이너에서 명령 실행하기**

```powershell
> docker exec -it [container names] [command 내용]
# 작동 중인 command에 추가 명령 실행
# -i : input(입력모드) , -t : tty(console or 터미널)

> docker exec -it [container names] sh
# container 접속, 리눅스가 실행된다.

```





-  **home 디렉토리 설정**

```dockerfile
FROM node:alpine

COPY ./package.json /home/node/package.json
COPY ./index.js /home/node/index.js 
# package, index 를 /home/node 디렉토리에 설치

RUN npm install

CMD ["npm", "start"]
```



```powershell
> docker build -t whdvlf94/simpleweb:latest .
> docker run -d -p 8080:8080 whdvlf94/simpleweb:latest
> docker ps

# container는 생성되지 않는다.
# package, index 파일은 /home/node/ 에 생성되지만
# npm install은 root directory에서 진행되기 때문에 설치가 진행되지 않는다.
```



- **해결 방법 - WORKDIR**

  

![workdir](https://user-images.githubusercontent.com/58682321/71875326-61c64e00-3167-11ea-89ea-c2a1ba9716e9.PNG)

```powershell
PS C:\Users\HPE\docker\day01\simpleweb> docker exec -it 4d8772041458 sh
# 리눅스 접속
# sh => shellscript
/home/node #
# root 디렉토리가 /home/node 로 변경된 것을 확인할 수 있다.
```



- **index.js 수정 및 새로운 container 생성**

```powershell
PS C:\Users\HPE\docker\day01\simpleweb> docker exec -it 4d8772041458 sh

/home/node # vi index.js
-----------
res.send('Hellow, there!')
-----------

PS C:\Users\HPE\docker\day01\simpleweb> docker restart 4d8772041458


PS C:\Users\HPE\docker\day01\simpleweb> docker run -d -p 8081:8080 whdvlf94/simpleweb:latest
# 호출하는 port 번호는 바꿔야 한다.(바꾸지 않으면 충돌남)
# 새로운 container 생성
```



```powershell
> docker ps
# 두 개의 container가 생성된 것을 확인할 수 있다.

> docker exec -it [새로운 container ID or NAME] sh

/home/node # vi index.js
-----------
res.send('Hi, there!')
-----------

# 앞서 'Hellow, there!'로 바꿨던 내용이 반영되지 않은 것을 확인할 수 있다.

```



**※ 생성된 두 개의 container의 네트워크 주소가 같은 것을 확인할 수 있다. 즉, 두 개의 container는 서로 통신이 가능함**



---



- **pull - 이미지 내려받기**

```powershell
PS C:\Users\HPE\docker\day01\simpleweb> docker run -d -p 8081:8080 whdvlf94/simpleweb:latest

# image를 지운 상태에서 실행하게되면, image까지 다운 받아 container를 생성한다.
# 단, 해당 계정에 image file이 존재하는 경우에만 해당

> docker image build -t whdvlf94/simpleweb:latest .

> docker build --pull=true -t whdvlf94/simpleweb:latest .
# 이전에 받아왔던 도커 이미지는 일부러 삭제하지 않는 한 호스트 운영 체제에 저장된다.
# --pull 옵션을 사용하면 매번 베이스 이미지를 강제로 새로 받아온다.
```

 **--pull** 옵션을 사용하지 않으면, 도커는 변경된 부분만을 반영해 빌드를 시도한다

​	- Using cash ...



- **tag - 이미지에 태그 붙이기**

```powershell
> docker image tage whdvlf94/simpleweb:latest whdvlf94/simpleweb:example
> docker images
------------
REPOSITORY           TAG                 IMAGE ID            CREATED             SIZE
whdvlf94/simpleweb   latest              ab1aaa364c9b        10 minutes ago      114MB
whdvlf94/simpleweb   example             ab1aaa364c9b        10 minutes ago      114MB
------------
```



- **rm - 컨테이너 파기하기**

```powershell
> docker run --name myweb -d -p 8080:8080 whdvlf94/simpleweb:latest
> docker ps
> docker stop myweb
# container 일시 중단. 삭제된 것이 아니다
> docker start myweb
# container 다시 시작 가능

> docker run --name myweb2 --rm -d -p 8080:8080 whdvlf94/simpleweb:latest
> docker stop myweb2
# container 중단과 동시에 파기한다.
```



- **MySQL 5.7 container 생성**

```powershell
> docker run -d -p 3306:3306 --name mysql mysql:5.7
# image : mysql , version : 5.7

# image는 다운로드 됐으나, container는 생성되지 않은 상태

> docker logs [container ID]
# error 원인 파악

You need to specify one of MYSQL_ROOT_PASSWORD, MYSQL_ALLOW_EMPTY_PASSWORD and MYSQL_RANDOM_ROOT_PASSWORD
# root, allow, random_root 중 password를 설정해야 한다.

> docker run -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d -p 3306:3306 --name mysql mysql:5.7
# MYSQL_ALLOW_EMPTY_PASSWORD=true 설정
# container 생성 완료

> docker exec -it mysql bash
root@ : /# mysql -uroot -p
---------
mysql>
#mysql 접속


> docker run -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d -p 3307:3306 --name mysql_slave mysql:5.7
# 여러 개의 mysql을 생성할 수 있다.
```



- mysql 접속 - windows 

```powershell
PS C:\Users\HPE> mysql -uroot -p -h 127.0.0.1 --port 3306
```



