# MSA Project



#### Setting



#### Config-Server

- Eureka-Server(Discovery)
    - CoffeeMemberApplication
    - CoffeeOrderApplication
    - CoffeeStatusApplication



#### Service Mesh

- Discovery(Eureka) : MS가 등록된 프로젝트. 일종의 Gateway의 역할
- Configuration : Discovery 서버에서 Configuration 서버를 참조하기 때문에, 먼저 실행되어야 한다.
- Load Balance : 서버에 가해지는 부하를 분산해주는 기술. 클라이언트와 서버풀 사이에 위치하며, 한 대의 서버로 부하가 집중되지 않도록 트래픽을 관리한다.
- Router : Discovery 서버를 기반으로 Routing 작업을 진행한다.



## 1.  yml 파일 설정

> 기존 JAVA 프로젝트 설정 파일은 properties으로 되어 있다.



```yml
server:
  port: 8888

spring:
  application:
    name: msa-architecture-config-server
    
  cloud:
    config:
      server:
        git: 
          uri: https://github.com/joneconsulting/spring-microservice.git

#properties
#server.port=8888 ( key=value )
#spring.application.name=msa-architecture-config-server
#spring.cloud.config.server.git.uri=xxx.git
```

- `properties`  파일은 key, value으로 존재하며, 중복 사용해야하는 불편함이 있다. 따라서, `yml` 파일로 생성하여 관리하는 것이 더 편리하다.
- `application-<active profile>.yml` **profile** 입력을 통해 **active profile**을 선택하여 가동할 수 있다.

- **git uri** 을 통해 **git에 있는 설정 파일을 읽어와 실행**할 수 있다.



#### ※ Profile 생성하는 방법

> 여러개의 yml 파일을 나눠서 설정해도 되고, 하나의 yml 파일 안에 모든 설정을 할 수도 있다.

```yaml
msaconfig:
  greeting: "hello"
  topic-name: "coffee-topic"
  ipaddress: "192.168.10.1"
  dbtype: "oracle"
---

spring:
  profiles: local

msaconfig:
  greeting: "Welcome to local server!!!"
  topic-name: "coffee-topic-local"
  ipaddress: "192.168.10.102"
  dbtype: "mysql"
  
---
```

- **spring.profiles**에 대한 정보가 없는 경우는 **default profile** 이다.
- **spring.profiles=local** 은 `application-local.yml` 와  같은 의미이다.
- 주소창에 http://localhost:8888/msa-architecture-config-server/wrongprofile 와 같이 잘못된 profile 값을 입력하면, **Default**로 입력한 profile 이 호출된다.



## 2. Server



- #### ConfigServerApplication

  ```java
  package com.example.msa.config.server;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.cloud.config.server.EnableConfigServer;
  
  @SpringBootApplication // 최초 실행되는 Application
  @EnableConfigServer	// 어노테이션을 통해 ConfigServer로 등록
  public class MsaConfigServerApplication {
  
  	public static void main(String[] args) {
  		SpringApplication.run(MsaConfigServerApplication.class, args);
  	}
  }
  
  ```

  - 첫 번째로 실행되는 Application
  - 어노테이션을 통해 ConfigServer로 등록

- **build.gradle**



- #### EurekaServerApplication

  ```java
  package com.example.msa.eureka;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
  
  @EnableEurekaServer
  @SpringBootApplication
  public class MsaEurekaServerApplication {
  
  	public static void main(String[] args) {
  		SpringApplication.run(MsaEurekaServerApplication.class, args);
  	}
  }
  
  ```

  - **@EnableEurekaServer** : Discovery Server



- #### MSA-Service

  > coffee-member / coffee-order / coffee-status



**Setting**

- **Port 설정**
- **Application 위치 정보 설정**





## 3. 나만의 Server 생성



```
Config Server(8012) - pom.xml(의존성 관리), 
	Discovery Server(8010) - DashBoard
		myappZuulGatewayApplication(8011) - Load Blance
		
			myapp-api-users(Random port 0) - instance 생성
		
```





- #### myapp-discovery-service(eureka)

  ```java
  @SpringBootApplication
  @EnableEurekaServer // Eureka Server 생성
  public class MyappDiscoveryServiceApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(MyappDiscoveryServiceApplication.class, args);
      }
  
  }
  
  ```

- **application.yml**

  ```yaml
  server:
    port: 8010
  
  spring:
    application:
      name: DiscoveryService
  
  
  eureka:
    client:
      registerWithEureka : false
      fetchRegistry : false
      serviceUrl:
        defaultZone : http://localhost:${server.port}/eureka
  ```

  

- #### myapp-config-server

  ```java
  @SpringBootApplication
  @EnableConfigServer //ConfigServer 생성
  public class MyappConfigServerApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(MyappConfigServerApplication.class, args);
      }
  
  }
  ```

- **application.yml**

  ```yaml
  server:
    port: 8012
  spring:
    application:
      name: ConfigServer
  
    cloud:
      config:
        server:
          git:
            uri: https://github.com/whdvlf94/MyAppConfiguration.git
            username: <github ID>
            password: ****
            clone-on-start: true
  
  ```

  

- #### myapp-api-users

  > MyappApiUsersApplication 에서 @springBootApplication 어노테이션 추가

- **UsersController**

  ```java
  @RestController
  @RequestMapping("/users")
  public class UsersController {
  
      @Autowired
      Environment env;
      //port 번호를 랜덤으로 설정했기 때문에, 할당된 포트 번호를 알기 위해서는
      //Environment 인터페이스를 이용해야 한다.
  
  //   URL : /users/status/check
  //   GetMapping 안에 값을 지정하지 않으면, /users로 접속했을 때 아래의 메서드가 실행된다.
      @GetMapping("/status/check")
      public String status(){
          return String.format("Working on port %s", env.getProperty("local.server.port")) ;
          //
  
      }
  }
  ```

- **application.yml**

  ```yaml
  server:
    port: 0
  #  Random port num = 0
  
  spring:
    application:
      name: users-ws
  
    devtools:
      restart:
        enabled: true
  
  #Eureka Client
  eureka:
    client:
      serviceUrl:
        defaultZone: http://localhost:8010/eureka
  
  gateway:
    ip: 127.0.0.1
  
  token:
    expiration_time: 86400000 #1 days(milliseconds)
    secret: local_secret
  ```

  

  

### Maven apache  - Terminal에서 실행하기



- maven rapository - 환경 변수 편집

```
M2_HOME : C:\Users\HPE\Work\apache-maven-3.6.3
path : %M2_HOME%\bin
```

- Terminal Server start

```
C:\Users\HPE\IdeaProjects\msa-master\LAB\myapp-api-users>mvn spring-boot:run
```



※ intellij에서도 Server를 시작할 수 있다. 이 때, Eureka에서 실행해보면 두 개의 Application이 실행되는 것이 아니고, 마지막으로 실행한 Server만 실행된다.





#### ※ 여러 개의 Application을 실행하는 방법?



> Run/Debug Configurations

![newapp](https://user-images.githubusercontent.com/58682321/81642823-3cbc5180-945f-11ea-8d25-8c15df7028a5.PNG)

- 위와 같이 **server.port**를 직접 입력하는 방법도 있지만, **zuul-gateway**를 이용해 자동으로 Application Server가 실행되도록하는 방법이 있다.



### Zuul - Gateway

```
[Dependency]

Developer Boot DevTools
 spring Boot DevTools
 Lombok

Spring Cloud Config
 Config Client
 
Spring Cloud Discovery
 Eureka Discovery Client

Spring Cloud Routing
 Zuul[maintenance]
```





- #### MyappZuulGateway

  ```java
  @SpringBootApplication
  @EnableZuulProxy
  @EnableEurekaClient
  public class MyappZuulGatewayApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(MyappZuulGatewayApplication.class, args);
      }
  
  }
  ```

- **application.yml**

  ```yaml
  server:
    port: 8011
  
  application:
    name: ZuulServer
  
  
  #Eureka Client
  eureka:
    client:
      serviceUrl:
        defaultZone: http://localhost:8010/eureka
  ```

  - Eureka client 등록을 통해 DashBoard에서 확인할 수 있다.



**※ zuul Gateway 작업을 마치고 myapp-api-users 프로젝트로 돌아가 설정 작업을 진행한다.**

- #### application.yml(myapp-api-users)

```yaml
#Eureka Client
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8010/eureka
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
#    ${spring.application.instance_id:${random.value}} : instance_id 가 없으면 random.value 값을 대입
```

- **Application name**은 **users-ws**로 동일하다. **instance-id** 값을 위와 같이 설정
- 만약, **instance_id** 값이 없는 경우에는 **random.value**를 통해 임의의 값을 대입해준다.(현재, instance_id를 입력하지 않았기 때문에 random value 값이 대입됨)

![randomvalue](https://user-images.githubusercontent.com/58682321/81645288-4ac0a100-9464-11ea-8eb9-6fdb6b806836.PNG)



#### ※ instance id, port 설정 방법



**1) intellij 방법**

> Edit Configuration

![new_instance](https://user-images.githubusercontent.com/58682321/81646606-7f355c80-9466-11ea-92b9-bc87c701e600.PNG)

- 사용자가 원하는 **application.name** , **instance_id** 값을 Override 하여 instance를 생성할 수 있다.

  

**2) Terminal 방법**

```
C:\Users\HPE\IdeaProjects\msa-master\LAB\myapp-api-users>mvn spring-boot:run -Dspring-boot.run.arguments="--spring.application.name=account-ws --spring.application.instance_id=account4 --server.port=9001"
```

- Terminal 방법도 port 번호에 0을 넣을 경우 랜덤으로 포트 번호가 생성된다.





#### 인스턴스 생성 확인 방법

```
localhost:[zuul port]/[spring.application.name]/[@RequestMapping]/[@GetMapping]

----------------------------------------
http://localhost:8011/account-ws/users/status/check
```

- 위의 경로로 접속해서 새로고침을 눌러보면, 요청에 따라 Load Balancing 작업이 진행된다.

  

#### ※ Zuul Gateway를 거침으로써 Load Balance 작업이 진행되고, 이는 트래픽을 여러대의 서버로 분산해주는 역할을 한다.

