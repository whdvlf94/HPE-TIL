# Docker MS

> 모든 Server 와 Service를 다 띄웠으나, Zuul Server를 통한 통신이 이루어지지 않는다.
>
> Docker 내에서 네트워크 통신이 이루어지고 있지 않기 때문





## 1. Docker Network Setting 

> Docker Network setting을 통해 각 서버 간 통신이 이루어지도록 한다.



#### Network Setting

```
docker network create pthoto-app-network
```



#### Server, Service 다시 실행

```
[rabbitMQ 172.18.0.2]

docker run -d --name myrabbit --network photo-app-network -p 15672:15672 -p 25672:25672 -p 15671:15671 -p 5671:5671 -p 4369:4369 -e "RABBITMQ_DEFAULT_USER=guest" -e "RABBITMQ_DEFAULT_PASSWORD=guest" rabbitmq:management
-------------------------------
[config 172.18.0.3]

docker run -d -p 8012:8012 --name config-server --network photo-app-network -e "spring.rabbitmq.host=172.17.0.2" -e "spring.profiles.active=default" whdvlf94/config-server
-------------------------------
[eureka 172.18.0.4]

docker run -d -p 8010:8010 --name eureka-server --network photo-app-network -e "spring.cloud.config.url=http://172.18.0.3:8012" whdvlf94/eureka-server
-------------------------------
[Zuul 172.18.0.5]

docker run -d -p 8011:8011 --name zuul-gateway --network photo-app-network -e "spring.rabbitmq.host=172.18.0.2" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.18.0.4:8010/eureka" -e "spring.cloud.config.uri=http://172.18.0.3:8012" whdvlf94/zuul-gateway
-------------------------------
[Zipkin Server : 172.18.0.6]

docker run -d -p 9411:9411 openzipkin/zipkin
-------------------------------
[MySQL Server : 172.18.0.7]

docker run -d -p 3306:3306 --name mysql --network photo-app-network -e "MYSQL_ROOT_PASSWORD=mysql" -e "MYSQL_DATABASE=photo_app" -e "MYSQL_USER=user" -e "MYSQL_PASSWORD=user" mysql:latest
-------------------------------
[Albums Microservice : 172.18.0.8]

docker run -d --network photo-app-network -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.18.0.4:8010/eureka" whdvlf94/albums-microservice
-------------------------------
[Users Microservice : 172.18.0.9]

docker run -d --network photo-app-network -e "spring.zipkin.base-url=http://172.18.0.6:9411" -e "spring.rabbitmq.host=172.18.0.2" -e "spring.cloud.config.uri=http://172.18.0.3:8012" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.18.0.4:8010/eureka"  -e "spring.datasource.url=jdbc:mysql://172.18.0.7:3306/photo_app?serverTimezone=Asia/Seoul" -e "spring.datasource.drvier-class-name=com.mysql.cj.jdbc.Driver" -e "spring.datasource.username=user" -e "spring.datasource.password=user" whdvlf94/users-microservice
```
