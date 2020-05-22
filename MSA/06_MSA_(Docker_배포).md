# Docker 배포

> MS를 jar 형태로 Docker에 직접 배포해본다.



#### Setting

```
<보안 설정>

[JCE - jce_policy-8.zip]
- UnlimitedJCEPolicyJDK8

[apiEncryptionKey.jks]

<Docker Process IP>
RabbitMQ : 172.17.0.2
Config Server : 172.17.0.3
Eureka Server : 172.17.0.4
Zuul Gateway : 172.17.0.5
Albums Microservice : 172.17.0.6
Zipkin Server : 172.17.0.7
MySQL Server : 172.17.0.8
Users Microservice : 172.17.0.9
```



- #### Dockerfile

  > Docker 설정 파일

  ```dockerfile
  FROM openjdk:8-jdk-alpine
  VOLUME /tmp
  COPY apiEncryptionKey.jks apiEncryptionKey.jks
  COPY UnlimitedJCEPolicyJDK8/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
  COPY target/myapp-config-server-0.1.jar ConfigServer.jar
  ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ConfigServer.jar"]
  ```



- `UnlimitedJCEPolicyJDK8` 과 `Dockerfile`을 `myapp-config-server`와 동일 경로에 위치해 둔다.



## 1. Config Server 배포

> Dcoker hub에 Config Server를 배포해본다.
>
> Config Server : 172.17.0.3

**Docker image**

- Terminal에서 **Maven Clean** → **Maven Package** 과정을 마친 후, `docker build --tag=whdvlf94/config-server --force-rm=true .` 명령어를 실행한다.

  ```
  C:\Users\HPE\MSA_Project\myapp-config-server>mvn clean
  C:\Users\HPE\MSA_Project\myapp-config-server>mvn package
  C:\Users\HPE\MSA_Project\myapp-config-server>docker build --tag=whdvlf94/config-server --force-rm=true .
  ```

  

**Docker hub upload**

- Docker hub에 upload 하기

  ```
  C:\Users\HPE\MSA_Project\myapp-config-server>docker push whdvlf94/config-server
  ```



### 1.2 Server start

> rabbitMQ Server를 먼저 실행한 후, Config Server를 시작한다.



```
[rabbitMQ Server]
docker run -d --name myrabbit -p 25672:5672 -p 35672:15672 rabbitmq:management

[Config Server]
docker run -d -p 8012:8012 --name config-server whdvlf94/config-server
```



#### ※ Config Server 의 application.yml 설정 문제

> 기존 Config Server의 application 파일에는  profiles.active=native , rabbitmq.host=localhost로 설정되어 있다.
>
> 하지만, Docker에 업로드를 하게되면 native, localhost는 Windows 환경이 아닌 Docker 환경이 된다.
>
> 따라서, Docker를 실행할 때, - e 설정으로 Config-server 내의 환경을 바꿔준다.



```
docker run -d -p 8012:8012 --name config-server -e "spring.rabbitmq.host=172.17.0.2" -e "spring.profiles.active=default" whdvlf94/config-server
```



![Untitled Diagram (5)](https://user-images.githubusercontent.com/58682321/82511699-ef1fa300-9b48-11ea-9696-59e962843088.png)





## 2. Eureka Server 배포

> DiscoveryServer - 172.17.0.4



#### Dockerfile

```dockerfile
FROM openjdk:8-jdk-alpine
COPY target/myapp-discovery-service-0.1.jar DiscoveryService.jar
ENTRYPOINT ["java","-jar","DiscoveryService.jar"]
```



**Docker hub upload**

- Docker hub에 upload 하기

```
C:\Users\HPE\MSA_Project\myapp-discovery-service>mvn clean mvn package
C:\Users\HPE\MSA_Project\myapp-discovery-service>docker build --tag=whdvlf94/eureka-server --force-rm=true .
C:\Users\HPE\MSA_Project\myapp-discovery-service>docker push whdvlf94/eureka-server
```



### 2.1 Server start



```
docker run -d -p 8010:8010 --name eureka-server -e "spring.cloud.config.url=http://172.17.0.3:8012" whdvlf94/eureka-server
```

- 기존 `bootstrap.yml`에는 `http://localhost:8012`로 되어 있기 때문에, **Docker Process**에서 할당된 IP 주소로 변경해서 실행한다.



- **myapp-discovery-service**

  ```yaml
    security:
      user:
        name: test
        password: test
  ```

  - 기존에는 위의 설정을 **Config Sever yml**에 설정해 놓았다. 주석 처리를 풀어주도록 한다.





## 3. Zuul Gateway 배포

> Zuul Gateway : 172.17.0.5



#### Dockerfile

```dockerfile
FROM openjdk:8-jdk-alpine
COPY target/myapp-zuul-gateway-0.1.jar ZuulApiGateway.jar
ENTRYPOINT ["java","-jar","ZuulApiGateway.jar"]
```



**Docker hub upload**

- Docker hub에 upload 하기

```
C:\Users\HPE\MSA_Project\myapp-zuul-gateway>mvn clean mvn package
C:\Users\HPE\MSA_Project\myapp-zuul-gateway>docker build --tag=whdvlf94/zuul-gateway --force-rm=true .
C:\Users\HPE\MSA_Project\myapp-zuul-gateway>docker push whdvlf94/zuul-gateway
```





### 3.1 Server start



```
docker run -d -p 8011:8011 --name zuul-gateway -e "spring.rabbitmq.host=172.17.0.2" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" -e "spring.cloud.config.uri=http://172.17.0.3:8012" whdvlf94/zuul-gateway
```

- `spring.rabbitmq.host` : `172.17.0.2`
- `eureka.client.serviceUrl.defaultZone` : `http://test:test@172.17.0.4:8010/eureka`
- 기존 `bootstrap.yml`에는 `http://localhost:8012`로 되어 있기 때문에, **Docker Process**에서 할당된 IP 주소로 변경해서 실행한다.



#### myapp-zuul-gateway

> Docker 사용 시 변경 해야하는 내용들

- **application.yml**

  ```yaml
  spring:
    application:
      name: ZuulServer
  
    rabbitmq:
      host: localhost
      port: 25672
      username: guest
      password: guest
  
  
  #Eureka Client
  eureka:
    client:
      serviceUrl:
        defaultZone: http://test:test@localhost:8010/eureka
  ```

- **bootstrap.yml**

  ```yaml
  spring:
    cloud:
      config:
        uri: http://localhost:8012
        name: ConfigServer
  ```





## 4. Albums MicroService 배포

> Albums Microservice : 172.17.0.6



#### Dockerfile

```dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/myapp-api-albums-0.0.1-SNAPSHOT.jar PhotoAppApiAlbums.jar
ENTRYPOINT ["java","-jar","PhotoAppApiAlbums.jar"]
```



**Docker hub upload**

- Docker hub에 upload 하기

```
C:\Users\HPE\MSA_Project\PhotoAlbumService-master>mvn clean mvn package
C:\Users\HPE\MSA_Project\PhotoAlbumService-master>docker build --tag=whdvlf94/albums-microservice --force-rm=true .
C:\Users\HPE\MSA_Project\PhotoAlbumService-master>docker push whdvlf94/albums-microservice
```





### 4.1 Service start



```
docker run -d -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" whdvlf94/albums-microservice
```

- `albums-microservice` 의 경우 몇 개의 서비스가 실행될 지 모르기 때문에 **port , name**을 랜덤으로 설정한다.



#### Albums Microservice

> Docker 사용 시 변경 해야하는 내용들

- **application.yml**

  ```yaml
  server:
    port: ${PORT:0}
  
  spring:
    application:
      name: ZuulServer
  
    rabbitmq:
      host: localhost
      port: 25672
      username: guest
      password: guest
  
  
  #Eureka Client
  eureka:
    client:
      serviceUrl:
        defaultZone: http://test:test@localhost:8010/eureka
  ```





### ※ 윈도우에서 접근할 수 있는 방법

> Albums-MicroService 의 Random port를 Docker 환경 설정에서 port 번호를 지정함으로써 Windows에서도 접근이 가능하도록 해야 한다.



```
docker run -d -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" -e "server.port=30000" -p 30000:30000 whdvlf94/albums-microservice
96ec4a2ced64da88d6fdadb17761e444c86019de99d0af1c64dad45ac34aa68b
```

- **Port Forwording**을 지정함으로써, Windows에서 해당 **port("30000:30000")**에 접근하면 **Albums-Microservice("server.port=30000")**로 포워딩 해준다.



## 5. Users MicroService 배포

> Users Microservice : 172.17.0.7



#### Dockerfile

```dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/myapp-api-users-0.1.jar MyAppApiUsers.jar
ENTRYPOINT ["java","-jar","MyAppApiUsers.jar"]
```



**Docker hub upload**

- Docker hub에 upload 하기

```
C:\Users\HPE\MSA_Project\PhotoAlbumService-master>mvn clean mvn package
C:\Users\HPE\MSA_Project\PhotoAlbumService-master>docker build --tag=whdvlf94/users-microservice --force-rm=true .
C:\Users\HPE\MSA_Project\PhotoAlbumService-master>docker push whdvlf94/users-microservice
```





### 5.1 Service start



```
docker run -d -e "spring.zipkin.base-url=http://172.17.0.7:9411" -e "spring.rabbitmq.host=172.17.0.2" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" -e "spring.cloud.config.uri=http://172.17.0.3:8012" -e "spring.datasource.url=jdbc:mysql://172.17.0.8:3306/users?serverTimezone=Asia/Seoul" -e "spring.datasource.drvier-class-name=com.mysql.cj.jdbc.Driver" -e "spring.datasource.username=user" -e "spring.datasource.password=user" -e "server.port=30002" -p 30002:30002 whdvlf94/users-microservice
```

- `users-microservice` 의 경우 몇 개의 서비스가 실행될 지 모르기 때문에 **port , name**을 랜덤으로 설정한다.





## 6. Code Setting

```
[RabbitMQ : 172.17.0.2]

docker run -d --name myrabbit -p 25672:5672 -p 35672:15672 rabbitmq:management
------------------------------
[Config Server : 172.17.0.3]

docker run -d -p 8012:8012 --name config-server -e "spring.rabbitmq.host=172.17.0.2" -e "spring.profiles.active=default" whdvlf94/config-server
------------------------------
[Eureka Server : 172.17.0.4]

docker run -d -p 8010:8010 --name eureka-server -e "spring.cloud.config.url=http://172.17.0.3:8012" whdvlf94/eureka-server
------------------------------
[Zuul Gateway : 172.17.0.5]

docker run -d -p 8011:8011 --name zuul-gateway -e "spring.rabbitmq.host=172.17.0.2" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" -e "spring.cloud.config.uri=http://172.17.0.3:8012" whdvlf94/zuul-gateway
------------------------------
[Albums Microservice : 172.17.0.6]

docker run -d -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" whdvlf94/albums-microservice
------------------------------
[Zipkin Server : 172.17.0.7]

docker run -d -p 9411:9411 openzipkin/zipkin
------------------------------
[MySQL Server : 172.17.0.8]

docker run -d -p 3306:3306 --name mysql -e "MYSQL_ROOT_PASSWORD=mysql" -e "MYSQL_DATABASE=users" -e "MYSQL_USER=user" -e "MYSQL_PASSWORD=user" mysql:latest
------------------------------
[Users Microservice : 172.17.0.9]

docker run -d -e "spring.zipkin.base-url=http://172.17.0.7:9411" -e "spring.rabbitmq.host=172.17.0.2" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.17.0.4:8010/eureka" -e "spring.cloud.config.uri=http://172.17.0.3:8012" -e "spring.datasource.url=jdbc:mysql://172.17.0.8:3306/users?serverTimezone=Asia/Seoul" -e "spring.datasource.drvier-class-name=com.mysql.cj.jdbc.Driver" -e "spring.datasource.username=user" -e "spring.datasource.password=user" -e "server.port=30002" -p 30002:30002 whdvlf94/users-microservice
```

