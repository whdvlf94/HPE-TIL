# HATEOAS

> 현재 리소스와 연관된(호출 가능한) 자원 상태 정보를 제공

[참조 사이트](https://blog.woniper.net/219)

- **RESTful API를 사용하는 클라이언트가 전적으로 서버와 동적인 상호작용이 가능하도록 하는 것**

  

**setting**

```
[Maven dependency 추가]
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>

[spring 2.2.6 RELEASE 로 변경]
```





## 1. HATEOAS 특징



- 클라이언트가 서버로부터 어떠한 요청을 할 때, 요청에 필요한(의존되는) URI를 응답에 포함시켜 반환.
- **즉, 모든 동작을 URI를 이용하여 동적으로 알려준다.**



#### 장점

- 요청 URI가 변경되더라도 클라이언트에서 동적으로 생성된 URI를 사용함으로써, 클라이언트가 URI 수정에 따른 코드를 변경하지 않아도 되는 편리함 제공한다.
- URI 정보를 통해 들어오는 요청 예측할 수 있다.
- Resource가 포함된 URI를 보여주기 때문에, Resource에 대한 확신을 얻을 수 있다.



## 2. HATEOAS 실습



- **User → Users**

  ```java
      @GetMapping("/hateoas/users/{id}")
      public MappingJacksonValue retrieveUser(@PathVariable(value = "id") Integer id) {
          User user = service.getUser(id);
  		
          //null 값 처리를 위한 기능
          if (user == null) {
              throw new UserNotFoundException("id:" + id + " is not exist");
          }
          
          //HATEOAS 설정
          EntityModel<User> model = new EntityModel<>(user);
          WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserList());
          model.add(linkTo.withRel("all-users"));
  
          //user INFO filter
          SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                  .filterOutAllExcept("id", "name", "joinDate", "ssn");
          FilterProvider provider = new SimpleFilterProvider()
                  .addFilter("UserInfo", filter);
  
          MappingJacksonValue mapping = new MappingJacksonValue(model);
          mapping.setFilters(provider);
  
          return mapping;
      }
  ```

![user](https://user-images.githubusercontent.com/58682321/81246649-1ce9ef80-9053-11ea-99a5-b7f5eb271d59.PNG)



- **User**

  ```java
      @GetMapping("/users")
      public MappingJacksonValue getUserList() {
  
  
          List<User> list = service.getUserList();
  
          List<EntityModel<User>> result = new ArrayList<>();
          //users 에서 각 user 마다 link를 설정하기 위해서는
          //List<EntityModel<User>> 타입의 빈 List 를 추가해야 한다.
  
  
          //HATEOAS link 설정 작업
          for(User user: list) {
              EntityModel<User> model = new EntityModel<>(user);
              //User entity에 대해 각각의 EntityModel 객체를 생성한다.
              WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
              //HATEOAS 메서드를 이용해 각 user 마다의 link를 생성
              model.add(linkTo.withRel("user-detail"));
              //link name
              result.add(model);
              //HATEOAS 가 적용된 model을 result List에 추가한다.
          }
  
          //user 정보 filter 작업
          SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                  .filterOutAllExcept("id", "name", "joinDate", "ssn");
          FilterProvider provider = new SimpleFilterProvider()
                  .addFilter("UserInfo", filter);
  
          MappingJacksonValue mapping = new MappingJacksonValue(result);
          // 새롭게 추가한 List에 HATEOAS가 적용된 model들이 포함되어 있다.
          mapping.setFilters(provider);
  
          return mapping;
      }
  ```

  ![users](https://user-images.githubusercontent.com/58682321/81246654-1e1b1c80-9053-11ea-878a-9b78a2285fca.PNG)
