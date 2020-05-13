# 암호화 및 접근 권한 설정



#### Dependency

```xml
        <!--  jackson XML   -->
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
            <version>2.11.0</version>
        </dependency>


        <!-- spring security-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Model Mapper -->
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.3.7</version>
        </dependency>
```



## 1. Model Mapper

> Model Mapper를 통해
>
>  CreateRequestModel , CreateResponseModel 을 UserDto와 Mapping
>
>  UserDto 를 UserEntity와 Mapping 할 수 있다.



#### ※ 흐름

**CreateUserRequestModel** → **UsersController** → **UserDto(Mapping)** → **UserService** → **UserServiceImpl** → **UserEntity(Mapping)** → **CreateUserResponseModel(Endpoint)**



- #### CreateUserRequestModel

  ```java
  @Data
  public class CreateUserRequestModel {
  
      @NotNull
      @Size(min = 2)
      private String firstName;
  
      @NotNull
      @Size(min = 2)
      private String lastName;
  
      @NotNull
      @Email
      private String email;
  
      @NotNull
      @Size(min = 2, max = 16)
      private String password;
  }
  
  ```

  - Postman을 통해 값을 입력(POST)할 경우, 사용자의 입력 조건

  

- #### UsersController

  ```java
  @RestController
  @RequestMapping("/users")
  public class UsersController {
  
      @Autowired
      Environment env;
      //port 번호를 랜덤으로 설정했기 때문에, 할당된 포트 번호를 알기 위해서는
      //Environment 인터페이스
      
      
      //사용자 추가 API
      @PostMapping(
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
      )
  
      public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails, HttpServletRequest request) {
              
          System.out.println(request.getRemoteAddr());
          //Debug test : application.yml에 설정한 ip 값이 제대로 보내졌는 지 확인
  
          // ModelMapper - [Mapping]
          ModelMapper modelMapper = new ModelMapper();
          modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  
  		// CreateUserRequestModel -> UserDto (using ModelMapper)
          // CreateUserResponseModel -> UserDto (using ModelMapper)
          
          //입력한 값과 UserDto 클래스와의 매핑작업
          UserDto userDto  = modelMapper.map(userDetails,UserDto.class);
          
          //매핑이 이루어진 userDto 객체를 UserEntity로 넘겨준다
          UserDto createdDto = userService.createUser(userDto);
  
          //CreateUserRequestModel
          //return new ResponseEntity(HttpStatus.CREATED);
  
          //CreateUserResponseModel
          CreateUserResponseModel returnValue = modelMapper.map(createdDto, CreateUserResponseModel.class);
          return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
  
      }
  ```

  - **Environment** : 랜덤으로 설정한 **Port** 번호를 알기 위해 사용한 인터페이스
  - `createUser()`의 타입은 `ResponseEntity<CreateResponseModel>`로 지정하며, 메서드 내에는 **Request 값이** 삽입된다.
  - 사용자가 Postman 등을 이용하여, 값을 입력하면 **modelMapper** 를 통해 `UserDto`와 매핑된다.
  - 이 후, 매핑된 값을 `userServiceImpl`으로 넘긴 후, `UserEntity`와 매핑 작업을 진행한다.
  - 마지막으로 `UserEntity` 즉, DB에 저장된 값을 토대로 `CreateUserResponseModel` 객체에 넘겨 사용자의 화면단으로 정보를 전달한다.

  

- #### UserDto

  ```java
  package com.example.myappapiusers.shared;
  
  import lombok.Data;
  
  @Data
  public class UserDto {
  
      private String firstName;
      private String lastName;
      private String email;
      private String password;
  
      private String userId;
      private String encryptedPassword;
  
  }
  ```

  - 각 객체 간 데이터 통신이 이루어 질 수 있도록 설정한 클래스

  

- #### UserServiceImpl

  ```java
  @Service
  public class UserSerivceImpl implements UserService {
      
      UsersRepository repository;
      // repository는 JPA를 이용하여 repository를 사용한 곳 마다 @Bean이 생성된다.
  
      BCryptPasswordEncoder bCryptPasswordEncoder;
      //BCryptPasswordEncoder는 Bean을 생성할 수 있는 설정이 되어있지 않다.
      
      @Autowired
      public UserSerivceImpl(UsersRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
          this.repository=repository;
          this.bCryptPasswordEncoder=bCryptPasswordEncoder;
      }
  
      @Override
      public UserDto createUser(UserDto userDeatils) {
  
          //UserDto -> UserEntity
          userDeatils.setUserId(UUID.randomUUID().toString()); //UUID : ID 자동 생성
  
          //password encryptedpassword
          userDeatils.setEncryptedPassword(bCryptPasswordEncoder.encode(userDeatils.getPassword()));;
  
          ModelMapper modelMapper = new ModelMapper();
          modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  
          UserEntity userEntity = modelMapper.map(userDeatils,UserEntity.class);
  //      userEntity.setEncryptedPassword("test encrypted password");
  //		비밀번호 암호화 대신 위와 같은 문장을 넣을 수도 있다.	        
          repository.save(userEntity);
          UserDto returnValue = modelMapper.map(userEntity,UserDto.class);
  
          return returnValue;
      }
  
  }
  ```

  - **BCryptPasswordEncoder** 는 **UsersRepository** 처럼 JPA 설정이 되어있지 않기 때문에, **Bean** 객체를 생성할 수 없다. 따라서 **Bean**을 생성할 수 있도록 따로 지정해야 한다.
  - **BCryptPasswordEncoder** : 사용자가 입력한 **password** 값을 입력받아 encoding 과정을 거치며, 암호화된 값을 **bcryptPasswordEncoder** 객체에 넘겨준다. 이후, **modelMapper**를 이용해 `UserEntity`에 저장하고, 이를 `UserDto` 타입으로 변환하여 반환한다.

  

  - **MyappApiUsersApplication - Bean 생성**

    ```java
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
            }
    ```

    - Application 실행 시 가장 먼저 실행되는 클래스이다. 이 때, **BCryptPasswordEncoder** 의 **Bean**을 해당 클래스에 선언하여, 프로젝트 실행과 동시에 **Bean** 객체가 생성되도록 한다.

    

- #### UserEntity

  ```java
  @Data
  @Entity
  @Table(name = "users")
  public class UserEntity implements Serializable {
  
      @Id
      @GeneratedValue
      private Long id;
  
      @Column(nullable = false, length = 50)
      private String firstName;
  
      @Column(nullable = false, length = 50)
      private String lastName;
  
      @Column(nullable = false, length = 120, unique = true)
      private String email;
  
      @Column(nullable = false, unique = true)
      private String userId;
  
      @Column(nullable = false, unique = true)
      private String encryptedPassword;
  
  }
  ```

  - `repository/AccountRepository`를 이용해 `UserEntity` 객체와 **H2 DB** 를 연동한다.



- #### CreateResponseModel

  ```java
  @Data
  public class CreateUserResponseModel {
  
      private String firstName;
      private String lastName;
      private String email;
      private String userId;
  }
  ```

  - 사용자가 값을 입력한 후, 결과를 확인할 수 있는 화면단(**End Point**).
  - **UUID** 를 이용해  userId 값을 자동으로 수정한다.



![encode](https://user-images.githubusercontent.com/58682321/81781799-b9206480-9533-11ea-9bc2-dd0d79d53f0c.PNG)



![h2](https://user-images.githubusercontent.com/58682321/81785103-d3a90c80-9538-11ea-9dcd-219b6663e144.PNG)

**※** Postman에서 값 입력 후, 테이블을 조회하면 위와 같이 나타난다. 이 때, password 값을 동일하게 설정하여도, 암호화가 다르게 설정되는 것을 확인할 수 있다.