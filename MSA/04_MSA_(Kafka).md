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
  





## 3. Kafka 적용 실습

> Coffee 주문 프로세스에 포함되어 있는 각각의 마이크로 서비스에 Kafka를 적용해보는 실습



### Micro Services

> [msa-book]
>
> build.gradle : springBootVersion = '1.5.22.RELEASE'
>
> [Zuul Server]
>
> build.gradle : springBootVersion = '1.5.22.RELEASE'



#### 3.1) Coffee Member

![coffee_rest](https://user-images.githubusercontent.com/58682321/82107353-ea7e7780-9761-11ea-8341-6449908edc53.PNG)



- **CreateMemberTable(PUT)**

  ```java
  @RequestMapping(value = "/createMemberTable", method = RequestMethod.PUT)
      public void createMemberTable() {
          iCoffeeMemberMapper.createMemberTable();
      }
  ```

  - Postman :`http://localhost:9099/member/createMemberTable` 

    

- **InsertMemberData(PUT)**

  ```java
      @RequestMapping(value = "/insertMemberData", method = RequestMethod.PUT)
      public void insertMemberData() {
          iCoffeeMemberMapper.insertMemberData();
      }
  ```

  - Postman : `http://localhost:9099/member/insertMemberData`

    

- Postman에 접속하여 create, insert 작업을 실행해본다.
  - **※ 오류가 뜨는 경우, Zull Server의 버전 변경해 줄 것.**

- **Create , Insert** 가 성공적으로 이루어지면, `localhost:8081/console`(Member)에 접속해서 테이블과 객체가 제대로 생성되었는지 확인한다.



#### 3.2) Coffee Order

![order_rest](https://user-images.githubusercontent.com/58682321/82108496-d2125b00-9769-11ea-87f3-fe17a09d5516.PNG)



- **CoffeeOrder(POST)**

  ```java
      @HystrixCommand
      @RequestMapping(value = "/coffeeOrder", method = RequestMethod.POST)
      public ResponseEntity<CoffeeOrderCVO> coffeeOrder(@RequestBody CoffeeOrderCVO coffeeOrderCVO) {
  
          //is member
          if (iMsaServiceCoffeeMember.coffeeMember(coffeeOrderCVO.getCustomerName())) {
              System.out.println(coffeeOrderCVO.getCustomerName() + " is a member!");
          }
  ```

  - Postman : `http://localhost:9099/order/coffeeOrder`

- `localhost:8080/console`에 접속해서 주문 내역 확인 해볼 것





#### fallbackTest()

> 예외 발생 처리
>
> Circuit Breaker : 원격 자원의 소비자와 리소스 사이에 중간자(middle man)를 두어 개발자에게 서비스 실패를 가로채고 다른 대안을 선택할 기회를 준다. 평소에는 Close 되어있으며, 예외가 발생할 경우 작동한다.

```java
    @HystrixCommand(fallbackMethod = "fallbackFunction")
    @RequestMapping(value = "/fallbackTest", method = RequestMethod.GET)
    public String fallbackTest() throws Throwable {
        throw new Throwable("fallbackTest");
    }

    public String fallbackFunction() {
        return "fallbackFunction()";
    }
```

- Postman : `localhost:9099/member/fallbackTest`(**GET**) 을 3번 요청하는 경우 아래와 같은 결과나 나온다.

  

![fallback](https://user-images.githubusercontent.com/58682321/82108732-8d87bf00-976b-11ea-87c3-6963e52dcb3a.PNG)