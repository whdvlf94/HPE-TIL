# Config Server - yml 통합 관리

> Config Server에서 마이크로서비스의 yml을 통합적으로 관리하는 방법



#### Setting

```
C:\Users\HPE\Work\dev 아래에 yml 파일 생성

- application.yml : ConfigServer 설정 파일
- users-ws.yml : users-ws 설정 파일

```

- **application.yml**

  ```yaml
  gateway:
      ip: 127.0.0.1
  
  token:
      expiration_time: 86400000 #1 days(milliseconds)
      secret: test
  ```

- **users-ws.yml**

  ```yaml
  login:
      url:
        path: /users/login
  
  spring:
      datasource:
          url: jdbc:h2:mem:testdb
  ```

  

## 1. yml 통합 관리

> Config Server 에서 profile 통합 관리하기



### 1.1) Config Server



- #### application.yml

  ```yaml
  spring:
    application:
      name: ConfigServer
  
    cloud:
      config:
        server:
          native:
            search-locations: file:///${user.home}/Work/dev
  
    profiles:
      active: native
      
    rabbitmq:
      host: localhost
      port: 5672
      username: admin
      password: admin
  
  management:
    endpoints:
      web:
        exposure:
          include: bus-refresh
  ```

  ※ **search-locations** 설정 유의할 것

  

![config](https://user-images.githubusercontent.com/58682321/82001377-087ea600-9696-11ea-92a7-ee67cfd7f899.PNG)



### 1.2) api-users Server



- #### bootstrap.yml

  ```yaml
  spring:
    cloud:
      config:
        uri: http://localhost:8012
        name: users-ws
  #      name: ConfigServer
  ```

  ※ `bootstrap.yml`은 `application.yml`보다 더 높은 우선 순위를 지닌다.

  - 위의 경우 **Config Server 설정 파일 뿐만 아니라 users-ws Server의 설정 파일 까지 읽을 수 있다.**



![users_ws](https://user-images.githubusercontent.com/58682321/82001380-09afd300-9696-11ea-9c36-405662276815.PNG)







## 2. ConfigServer - 암호화 처리

> [JCE](https://www.oracle.com/java/technologies/javase-jce8-downloads.html) 에 접속하여 JCE Download



#### Setting

```
다운로드 받은 파일에서 local_policy.jar , US_export_policy.jar 를 복사하여
C:\Program Files\Java\jdk-13.0.2\lib\security 에 넣는다.
```





### 2.1) JCE(대칭 키)

> Config Server에서 진행



- #### bootstrap.yml

  ```yaml
  encrypt:
    key: test1234
  ```

  **※ 주의)** 대칭 키를 분실하면 안 된다. 암호화 키로 한 번 암호화한 것은 그 키없이는 복호화할 수 없다.

  

- Postman을 이용한 암호화 과정

  ![encrypt](https://user-images.githubusercontent.com/58682321/82003030-509fc780-969a-11ea-8563-6a35b7a207a0.PNG)



- Postman을 이용한 복호화 과정

  ![decrypt](https://user-images.githubusercontent.com/58682321/82003034-51d0f480-969a-11ea-9ddb-6b5fe5cc6d5f.PNG)







#### **※** 위의 과정을 토대로 **username , password** 를 암호화 할 수 있다.



- #### users-ws.yml

  ```yaml
  login:
      url:
        path: /users/login
  
  spring:
      datasource:
          url: jdbc:h2:mem:testdb
          username: 7899ab783c996063251df7237ce6c44622d146b8b6103d3cc64960de74feff66
          password: '{cipher}eb538a47858fc549fe7986d9a065400398f67ebace7590ec886293f89fb9c9e7'
  ```

  - `'{cipher}encrypt password'` : **cipher** 를 넣으면, **GET** 으로 요청했을 때, 복호화되어서 보여진다.



![cipher](https://user-images.githubusercontent.com/58682321/82003838-5ac2c580-969c-11ea-8d4d-d09b3b00f037.PNG)





### 2.2) RSA(비대칭 키)



#### Setting

```
keytool -genkeypair -alias apiEncryptionKey -keyalg RSA -keypass "1q2w3e4r" -keystore apiEncryptionKey.jks -storepass "1q2w3e4r"
```

![jks](https://user-images.githubusercontent.com/58682321/82004495-fd2f7880-969d-11ea-821f-ed0fd6c38866.PNG)

- **jks 파일 생성**



- #### bootstrap.yml

  ```yaml
  encrypt:
  #  key: test1234 #JCE 대칭키
  
    # jks : 비대칭 키
    key-store:
      location: file:///${user.home}/Work/dev/apiEncryptionKey.jks
      password: 1q2w3e4r
      alias: apiEncryptionKey
  ```

  

![RSA](https://user-images.githubusercontent.com/58682321/82004956-1684f480-969f-11ea-89c9-36f1551fa90d.PNG)



#### 암호화 값이 <n/a>로 출력되는 이유?

- 기존에 JCE 였던 Key 값이 바뀌었기 때문에, Postman을 통해 확인해 보면, 암호화 되어 있는 값 들이 **<n/a>**  형태로 출력된다. 따라서 Postman에서 **encrypt** 과정을 다시 거쳐 나온 비대칭 키(**RSA**) 값을 기존 키 값 대신 넣으면 정상적으로 호출되는 것을 확인할 수 있다.

  

#### 