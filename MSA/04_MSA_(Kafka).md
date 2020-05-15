# Kafka



## 1. Kafka - Command

> kafka_2.12-2.3.1.zip download



#### Setting

```
kafka_2.12-2.3.1 파일을 C:\Users\HPE\Work\ 아래에 옮긴다.
```



### 1.1) 실행 순서

> cmd 창 4개를 띄운다.
>
> cmd1 : Kafka Server
>
> cmd2 : Zookeeper Server
>
> cmd3 : 구독자(consumer)
>
> cmd4: 발행자



#### 1. Kafka Server(cmd1)

```
C:\Users\HPE\Work\kafka_2.12-2.3.1>.\bin\windows\kafka-server-start.bat .\config\server.properties
```



#### 2. Zookeeper Server(cmd2)

```
C:\Users\HPE\Work\kafka_2.12-2.3.1>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```



#### 3. Kafka-topic



#### 구독자(cmd3)

```
C:\Users\HPE\Work\kafka_2.12-2.3.1>.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic msa_20200515
```

```
C:\Users\HPE\Work\kafka_2.12-2.3.1>.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
msa_20200515
```



- **구독자 등록**

```
C:\Users\HPE\Work\kafka_2.12-2.3.1>.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic msa_20200515
```





#### 발행자(cmd4)

```
C:\Users\HPE\Work\kafka_2.12-2.3.1>.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic msa_20200515
```

​	**※ 발행자 커맨드에서 아무 값을 입력하면, 구독자(cmd3)에서 해당 내용이 출력된다.**



## 2. Kafka - intellij



#### 실행 순서

**ZooKeeper, Kafka Server** → **Config Server(8888)** → **Eureka Server(9091)** → **Zuul Server(9099)** → **Turbin Server(9999)** → **Hystrix Server(7070) → ** **Coffee API(8080,8081,8082)**



- 위의 과정을 마친 후, 주소 창에 `localhost:9091`**(Eureka)** 에 접속하여 **Zuul(9099), Turbin(9999), Hystrix(7070)** 에 서버가 올라온 것을 확인한다.

- `localhost:7070/hystrix` 접속 후, `http://localhost:9999/turbine.stream` 을 입력하면, **hystrix DashoBoard** 를 통해 모니터링이 가능하다.

  - **Turbin** : **hystrix**를 이용하여 **Circuit** 정보를 모아주는 역할
  - **hystrix** : **Circuit Breaker**의 상태를 모니터링 할 수 있는 **DashBoard**를 제공해주는 라이브러리

  

