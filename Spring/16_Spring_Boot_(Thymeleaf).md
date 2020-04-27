# Spring Boot Web MVC 



## 1. Thymeleaf



### 1.1 Thymeleaf 예제(User 리스트)



**Thymeleaf 흐름**

`index.html`(**static**) → `UserController.java` → `UserRepository.java` → **DB** → `UserRepository.java` → `UserController.java` → **templates**



- **static**에 있는 `index.html`에서는 `<a href>` 태그를 통해 **Controller** 에게 정보를 전달한다.



- **UserController.java**

  ```java
  @Controller
  public class UserController {
  	
  	@Autowired
  	private UserRepository userReposity; //DB와의 연동을 위해
      //@Autowired 의존하는 객체를 자동 주입
  	
  	@GetMapping("/index") // href 태그와 매핑
  	public String index(Model model) {
  		
  		model.addAttribute("users", userReposity.findAll());
  		return "index";
  //return new ModelAndView("index","users",userReposity.findAll())
  		
  	}
  
  	@GetMapping("/leaf")
  	public ModelAndView leaf() {
  		return new ModelAndView("leaf", "name", "타임리프!");
  	}
  }
  ```



- **leaf.html(template)**

  ```html
  <html xmlns:th="http://www.thymeleaf.org">
  <!-- 생략 -->
  <body>
  <!-- name이라는 키 값을 이용해 문자열 나타내는 방법 -->
  <h2 th:text="${name}">Name</h2>
  <h2>Hello, <span th:text="${name}"></span></h2>
  <h2>다른 방법, [[${name}]]</h2>
  </body>
  </html>
  ```

  

- **index.html(template)**

  ```html
  <html xmlns:th="http://www.thymeleaf.org">
  <!-- 생략 -->
  <body>
  	<h3>사용자 리스트</h3>
  	<table>
  		<tr>
  			<th>Name</th>
  			<th>Email</th>
  		</tr>
  		<tr th:each="user:${users}">
  			<td th:text="${user.name}"></td>
  			<td th:text="${user.email}"></td>
  		</tr>
  
  
  	</table>
  
  </body>
  </html>
  ```

  

**※ 주의)  `<html xmlns:th="http://www.thymeleaf.org">`를 반드시 명시해야 템플릿이 제대로 렌더링 된다.**



### 1.2 User 등록

**User 등록 흐름**

`index.html`(**template**) → **등록** → `UserController.java`(**Mapping**) → `add-user.html` → `UserController.java`



- **UserController.java**

  ```java
  @Controller
  public class UserController {
  	
  	@Autowired
  	private UserRepository userReposity;
  	
  	@GetMapping("/signup")
  	public String showSignForm(User user) {
  		return "add-user";
  	}
      
      @PostMapping("/adduser")
  	public String addUser(@Valid User user, BindingResult result) {
  		if(result.hasErrors()) {		
  			return "add-user";
  		}
  		userReposity.save(user);
  		return "redirect:/index";
  	}
  	
  ```

  - `User.java` 에서 **Name, Email**변수 선언 위에 **@NotBlank(message="")** 어노테이션을 추가한다. 해당 어노테이션을 설정하면, User 등록을 위해 **Name, Email** 값을 필수로 선언해야 한다.

  - **@Valid** = **@ModelAttribute** + **유효성 검증**

    - `add-user.html`에서 입력한 값을 **user** 객체에 대입하고, 그 값이 유효한지에 대해 파악하는 어노테이션이다.
    - server에서 입력항목 검증 validation api - javax.validation 사용

    

- **add-user.html**

  ```html
  <html xmlns:th="http://www.thymeleaf.org">
  <body>
  	<h3>사용자 등록 Form</h3>
  	<form action="#" th:action="@{/adduser}" th:object="${user}"
  		method="post">
  		
  		<!-- Name -->
  		<label for="name">Name : </label> <input type="text"
  			th:field="*{name}" id="name">
  		<!-- th:object=${user.name}을 이처럼 표현할 수 있다. -->
  		<span th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></span> 
  		<br/>
  		
  		<!-- Email -->
  		<label for="email">Email : </label> <input type="text" th:field="*{email}"
  			id="email">
  		<span th:if="${#fields.hasErrors('email')}" th:errors="*{email}"></span>
  		<br/>
  		<input type="submit" value="Add-User">
  	</form>
  </body>
  </html>
  ```

  - **@{ } - Link URL** : @표현식을 이용하여 URL 표현
  - **${ } - Variable** : 해당 Context에 포함된 변수들을 사용할 수 있음
  - ***{ } - Select Variable** : **th:object** 변수에 포함된 값을 나타낼 수 있음
  - **#{ } - Message** :  미리 정의된 message.properties 파일이 있다면, #표현식으로 나타낼 수 있음
  - **th:if="${#fields.hasErrors('name')}"** : `User.java`에서 선언한 **@NotBlank** 어노테이션의 에러를 발견한다.
  - **th:errors="*{name}"** : **@NotBlank**에 선언한 **message**를 출력한다.



### 1.3 User 수정

- `index.html`(**Static**) 에 `<a href>`를 사용해 **Update** 링크 생성

- **UserController.java**

  ```java
  	//사용자 수정 화면
  	@GetMapping("/edit/{id}")
  	public ModelAndView showUpdateForm(@PathVariable long id) {
  		User user = userReposity.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  		System.out.println(user);
  		return new ModelAndView("update-user", "user", user);
  		
  	}
  	
  	//사용자 수정 (update-user.html로 부터 값을 받아온다.)
  	@PostMapping("/edituser/{id}")
  	public String updateUser(@PathVariable long id, @Valid User user, BindingResult result) {
  		if(result.hasErrors()) {
  			user.setId(id);
  			return "update-user";
  		}
  		userReposity.save(user);
  		return "redirect:/index";
  	}
  ```

  

### 1.4 User 삭제

- `index.html`(**Static**) 에 `<a href>`를 사용해 **Delete** 링크 생성

- **UserController.java**

  ```java
  	//사용자 삭제
  	@GetMapping("/delete/{id}")
  	public String deleteUser(@PathVariable long id) {
  		User user = userReposity.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  		userReposity.delete(user);
  		return "redirect:/index";
  	}
  ```

  

### 1.5 예외 처리



- **CustomException.java**

  ```java
  public class CustomException extends RuntimeException{
  	private String errCode;
  	private String errMsg;
  	
  	public CustomException(String errCode, String errMsg) {
  		super();
  		this.errCode = errCode;
  		this.errMsg = errMsg;
  	}
  	getter()
  }
  
  ```

  

- **UserController.java**

  ```java
  	@ExceptionHandler(CustomException.class)
  	public ModelAndView handleCustomException(CustomException ex) {
  		ModelAndView model = new ModelAndView("error/generic_error");
  		model.addObject("errCode", ex.getErrCode());
  		System.out.println(ex.getErrCode());
  		model.addObject("errMsg", ex.getErrMsg());
  		return model;
  	}
  ```

  - **사용자 삭제** 과정에서 **orElseThrow**가 발생할 경우, **errCode, errMsg**에 예외 처리 값이 삽입된다.
  - **ModelAndView**에 값을 대입하여 **return**



- **template/error/generic_error.html** 

  ```html
  <html xmlns:th="http://www.thymeleaf.org">
  
  <body>
  	<h2>Error Page</h2>
      
  	<!-- 코드 에러 -->
  	<div th:if="!${#strings.isEmpty(errCode)}">
  <!-- delete 작업에서 오류가 나면 위의 if 문이 true이므로 아래에 해당되는 내용이 화면에 출력된다.-->
  		<h5>에러 코드 : <span th:text="${errCode}"></span></h5>
  	</div>
  	
  	<!-- System error -->
  	<div th:if="${#strings.isEmpty(errCode)}">
  		<h5>System Error</h5>
  	</div>
  	
  	<div>
  		<h4 th:text="${errMsg}">error message</h4>
  	</div>
  
  </body>
  ```

  - **사용자 삭제(delete)**작업의 경우 **orElseThrow**를 통해 **errCode** 에 값이 삽입되므로 **코드 에러**에 해당되는 값이 화면에 출력된다. 그 이외의 경우 **System error**에 해당되는 내용이 화면에 출력.

    

