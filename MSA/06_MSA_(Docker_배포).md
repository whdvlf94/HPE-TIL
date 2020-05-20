# Docker 배포

> MS를 jar 형태로 Docker에 직접 배포해본다.



#### Setting

```
<보안 설정>

[JCE - jce_policy-8.zip]
- UnlimitedJCEPolicyJDK8

[apiEncryptionKey.jks]

<Dockerfile>
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

