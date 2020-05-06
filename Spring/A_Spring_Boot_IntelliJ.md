Spring Boot - IntelliJ



## 1. Project 생성 및 설정

> File / Project / Spring initializer



### 1.1) IntelliJ 이용



#### src/main/resources/application.yml

- **application.yml**

  ```yaml
  # yml 파일을 이용한 application properties 관리
  
  #spring Web port
  server:
    port: 8888
  ```
  - **No active profile set, falling back to default profiles: default**
  - resources 설정 파일이 하나 밖에 없기 때문에, 위와 같이 default profile이 실행된다.



#### src/main/resources/application-local.yml

- **application-local.yml**

  ```yaml
  #spring Web port
  server:
    port: 9999
  ```

  

![application-local](https://user-images.githubusercontent.com/58682321/81131886-a7651d00-8f87-11ea-8451-9e4283f1e3a8.PNG)

- **The following profiles are active: local**
  - **Active profile을 지정**했기 때문에 **default profile**이 실행되지 않는다.





### 1.2) jar 파일 생성(package)



- **우측 bar에 있는 Maven 클릭 → Lifecycle packages 더블클릭**



#### Terminal 창에서 jar 파일 실행해보기

```
C:\Users\HPE\IdeaProjects\demo1\target>
java -jar -Dspring.profiles.active=local demo-0.0.1-SNAPSHOT.jar
```

- **The following profiles are active: local**
  - **Active profile을 지정**했기 때문에 **default profile**이 실행되지 않는다.





## 2. intelliJ - Git 연동

> VCS / Enable Version Control Integration



- **Project 우측 버튼** → **Git Add** → **Define remote(github 주소 붙여넣기)** → **업로드 할 파일 설정(.gitignore)** → **commit message 작성** → **Commit & Push**

![git](https://user-images.githubusercontent.com/58682321/81133218-ed23e480-8f8b-11ea-96c4-fa8263d28e99.PNG)

​				**(※ Log 창을 통해 git hub에 push 된 것을 알 수 있다.)**





## 3. Spring 다국어 적용하기



- **DemoApplication**

  ```java
  @SpringBootApplication
  public class DemoApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(DemoApplication.class, args);
      }
  
      @Bean
      public LocaleResolver localeResolver() {
          SessionLocaleResolver localeResolver = new SessionLocaleResolver();
          localeResolver.setDefaultLocale(Locale.US);
          return localeResolver;
      }
  
  }
  
  ```



- **controller/HelloWorldController**

  ```java
  @RestController
  public class HelloWorldController {
  
      @Autowired
      private MessageSource messageSource;
  
  
      @GetMapping(path = "/hello-world-internationalized")
      public String helloWorldInternationalized() {
          return "Good Morning";
      }
  
      @GetMapping(path = "/hello-world")
      public String helloworld(
          @RequestHeader(name = "Accept-Language", required = false)
                  Locale locale) {
  
          return messageSource.getMessage("greeting.message", null, locale);
      }
  
  }
  
  ```

  - **Postman**에서 **Header** { **KEY** : Accept-Languae , **name** : ko/ja/fr } 을 **GET** 방식으로 요청하면, 요청한 언어로 호출된다.

    

- **properties setting**

![messages](https://user-images.githubusercontent.com/58682321/81141518-db503a80-8fa7-11ea-9cd8-57e383c385ad.PNG)







## 4. TDD



- **TestUserDaoService**

  ```java
  public class TestUserDaoService {
  
      UserDaoService service = new UserDaoService();
  
      @Test
      public void  testUserList() {
          List<User> list = service.getUserList();
          System.out.println(list);
  
      }
  }
  ```

  - `Alt` + `insert` 를 이용하여 **User class(entity 패키지 아래) 생성** , **getUserList() 메서드 생성** 작업을 할수 있다.



- **User**

  ```java
  import lombok.AllArgsConstructor;
  import lombok.Data;
  import lombok.NoArgsConstructor;
  
  //lombok 기능
  @Data   
  // setter and getter, toString, hashCode, equals 를 선언하지 않고도 사용할 수 있도록하는 어노테이션
  @AllArgsConstructor // Override Constructor 기능을 제공하는 어노테이션
  @NoArgsConstructor  // Default Constructor
  public class User {
  
      private int id;
      private String name;
      private Date joinDate;
      private String password;
      private String ssn;
  }
  ```

  - **Lombok**
    - **@Data** : setter and getter , toString(), hashCode(), equals() 를 선언하지 않고도 사용할 수 있도록 지원
    - **@AllArgsConstructor** : Override Constructor 기능 제공
    - **@NoArgsConstructor** : Default Constructor 기능 제공



- **UserDaoService**

  ```java
  @Component
  public class UserDaoService {
      private static List<User> users = new ArrayList<>();
  
      private static int userCount =3;
  
      static {
          users.add(new User(1, "Kenneth", new Date(), "test1", "701010-1111111"));
          users.add(new User(2, "Alice", new Date(), "test2", "455110-2222222"));
          users.add(new User(3, "Elena", new Date(), "test3", "722312-3333333"));
      }
      public List<User> getUserList() {
          return users;
      }
  }
  ```

  



### 4.1 Test 실습



- **TestUserDaoService**

  ```java
  package com.example.demo.dao;
  
  import com.example.demo.entity.User;
  import org.junit.jupiter.api.Assertions;
  import org.junit.jupiter.api.Test;
  
  import java.util.List;
  
  public class TestUserDaoService {
  
      UserDaoService service = new UserDaoService();
  
      @Test
      public void  사용자목록() {
          List<User> list = service.getUserList();
  
          Assertions.assertTrue(list.size() == 3, "초기 사용자는 3명이어야 합니다.");
  //      message는 condition이 false인 경우에만 출력된다.
  
          Assertions.assertEquals(3, list.size(),"초기 사용자는 3명이어야 합니다.");
  //      assertEquals(expected, actual(실제 값), message(false인 경우에만 출력))
  
  
      }
  
      @Test
      public void 사용자정보확인() {
          User user = service.getUserList().get(0);
          Assertions.assertTrue(user.getId() == 1);
      }
  }
  
  
  ```



### 4.2 RestController 실습



- **UserDaoService**

  ```java
  @Component
  public class UserDaoService {
      private static List<User> users = new ArrayList<>();
  
      private static int userCount = 3;
  
      static {
          users.add(new User(1, "Kenneth", new Date(), "test1", "701010-1111111"));
          users.add(new User(2, "Alice", new Date(), "test2", "455110-2222222"));
          users.add(new User(3, "Elena", new Date(), "test3", "722312-3333333"));
  
      }
  
      public List<User> getUserList() {
          return users;
      }
  
      public User getUser(Integer id) {
          for (User user : users) {
              if (id.equals(user.getId())) {
                  return user;
              }
          }
          return null;
      }
  }
  ```

  

- **UserController**

  ```java
  @RestController
  public class UserController {
  
      @Autowired
      private UserDaoService service;
  
      @GetMapping("/users")
      public List<User> getUserList() {
          List<User> list = service.getUserList();
  		//list의 내용 출력
          for (User user:list) {
              System.out.println(user);
                      }
          return list;
      }
  }
  -----------------------------------------------------------
  User(id=1, name=Kenneth, joinDate=Wed May 06 16:35:20 KST 2020, password=test1, ssn=701010-1111111)
  User(id=2, name=Alice, joinDate=Wed May 06 16:35:20 KST 2020, password=test2, ssn=455110-2222222)
  User(id=3, name=Elena, joinDate=Wed May 06 16:35:20 KST 2020, password=test3, ssn=722312-3333333)
  
  ```

  - **DB에 데이터가 저장되어 있지 않기 때문에, GET 방식으로 요청해야 Console 창에 데이터 값이 출력된다.**



### 4.3 예외 처리

- **UserNotFoundException**

  ```java
  package com.example.demo.exception;
  
  import org.springframework.http.HttpStatus;
  import org.springframework.web.bind.annotation.ResponseStatus;
  
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public class UserNotFoundException extends RuntimeException {
   public UserNotFoundException(String msg) {
       super(msg);
   }
  }
  ```

  - **Null** 값을 처리하기 위해 예외 처리를 해주어야 한다.



### 4.4 원하는 정보만 추출하기



#### 1) 어노테이션 활용



- **User**

  ```java
  @JsonIgnoreProperties(value = {"password", "ssn"})
  public class User {
  
  //    @JsonIgnore
      private String password;
  //    @JsonIgnore
      private String ssn;
  }
  ```

  - **password** , **ssn** 와 같이 보안에 민감한 데이터의 경우, **@JsonIgnore** 또는 **@JsonIgnoreProperties** 어노테이션을 이용해 호출의 여부를 설정할 수 있다.



#### 2) Filter 활용



- **User**

```java
  @JsonFilter("UserInfo")
  public class User {
  
      private int id;
      private String name;
      private Date joinDate;
      private String password;
      private String ssn;
  
  }
```

  

- **UserController**

  ```java
      @GetMapping("/users/{id}")
      public MappingJacksonValue getUser(@PathVariable(value = "id") Integer id)
  										//데이터 형이 다른 경우 value를 통해 작성 가능
          User user = service.getUser(id);
  
         if (user == null) {
              throw new UserNotFoundException("id:" + id + " is not exist");
          }
  
          SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                  .filterOutAllExcept("id", "name", "joinDate");
          FilterProvider provider = new SimpleFilterProvider()
                  .addFilter("UserInfo", filter);
  
          MappingJacksonValue mapping = new MappingJacksonValue(user);
          mapping.setFilters(provider);
  
          return mapping;
      }
  ```

  - **Null** 값이 발생 한 경우(id 값이 없을 때), **exception package**에 생성했던 class를 통해 예외 처리가 가능하도록 한다.

  - `.filterOutAllExcept`를 통해 원하는 Key 값만 호출되도록 설정한다.

  - `.addFilter` 의 이름과 **User class**에서 **@JsonFilter** 이름을 동일하게 설정해야 `.filterOutAllExcept`이 제대로 동작할 수 있다.
  
    
  
  ![filter](https://user-images.githubusercontent.com/58682321/81156913-67229080-8fc1-11ea-8d74-739f879fcf85.PNG)
  
  - **id , name, joinDate**만 출력된 것을 알 수 있다.
  
    

