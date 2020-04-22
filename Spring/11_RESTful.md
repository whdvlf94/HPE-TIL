# RESTful



**preview**

```
[Jackson : json Parser]
java Object → json
json → java Object

ORM(Object Releational Mapping) : MyBatis, JPA
OXM(Obeject XML Mapping) : JAXB, Jackson xml

JAXB(Java API for XML Binding)
java Object → xml
xml → java Object

```



- **RESTful API**는 HTTP와 URI 기반으로 자원에 접근할 수 있도록 제공하는 애플리케이션 개발 인터페이스이다.
- **RESTful**은 기존과 달리 **GET, POST, DELETE, PUT** 와 같은 4가지의 메서드를 사용하여 **CRUD**를 처리하며, **URI**는 제어하려는 자원을 나타낸다.



## 1. RESTful Controller



**Setting**

- **Jackson Databind**
  - **Jackson**은 **JSON 형태를 Java 객체**로, **Java 객체를 JSON 형태**로 **변환**해주는 **Java 용 JSON 라이브러리**





- **RestUserController.java**

  ```java
  @RestController
  //@Controller + @ResponseBody
  public class RestUserController {
  
  	@Autowired
  	UserService userService;
  
  	//사용자 목록
      //GET
  	@GetMapping("/users")
  	//java object -> json
  	public List<UserVO> userList() {
  		return userService.getUserList();
  	}
  	
  	//사용자 세부 정보
  	//GET
  	@GetMapping("/users/{id}")
      //(@pathVariable("id") String userid)와 동일
  	public UserVO userDetail(@PathVariable String id) {
  		return userService.getUser(id);
  	}
  
  	
  	//POST
  	@PostMapping("/users")
  	// json -> java object 변경(jackson)
  	// @RequestBody : 변경된 내용을 request에 담아주는 역할
  	public Boolean userInsert(@RequestBody UserVO user) {
  		if (user != null) {
  			userService.insertUser(user);
  			return Boolean.TRUE;
  		} else {
  			return Boolean.FALSE;
  		}
  
  	}
  	
  	//PUT
  	@PutMapping("/users")
  	public Boolean userUpdate(@RequestBody UserVO user) {
  		if (user != null) {
  			userService.updateUser(user);
  			return Boolean.TRUE;
  		} else {
  			return Boolean.FALSE;
  		}
  	}
  
  	//DELETE
  	@DeleteMapping("/users/{id}")
      //(@pathVariable("id") String userid)와 동일
  	public Boolean userDelete(@PathVariable String id) {
  		if (id != null) {
  			userService.deleteUser(id);
  			return Boolean.TRUE;
  		} else {
  			return Boolean.FALSE;
  		}
  	}
  		
  }
  ```

  - **@RestController** = **@Controller** + **@ResponseBody**
  - **@RequestBody** : 변경된 내용을 request에 Java 객체로 담아주는 역할
  - **@ResponseBody** : Java 객체를 HTTP Response Body로 전송





