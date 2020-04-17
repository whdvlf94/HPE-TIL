# Spring MVC



**Setting**

- **Maven Repository 에서 Spring Web MVC 받아와 pom.xml에 추가한다.**
- Spring Beans Configuration XML 정보를 Tomcat에 알려줘야 함
- FrontController 역할을 수행하는 DispatcherServlet 클래스를 설정



## 0. Spring MVC 주요 구성요소



**DispatcherServlet**

- 클라이언트의 요청을 받아서 Controller에게 클라이언트의 요청을 전달하고, 리턴한 결과값을 View에게 전달하여 알맞은 응답을 생성

**HandlerMapping**

- URL과 요청 정보를 기준으로 어떤 핸들러 객체를 사용할지 결정하는 객체이며, DispatcherServlet은 하나 이상의 핸들러 매핑을 가질 수 있다.

**Controller**

- 클라이언트의 요청을 처리한 뒤, Model을 호출하고, 그 결과를 DispatcherServlet에게 알려줌

**ModelAndView**

- Controller가 처리한 데이터 및 화면에 대한 정보를 보유한 객체

**View**

- Controller의 처리 결과 화면에 대한 정보를 보유한 객체

**ViewResolver**

- Controller가 리턴한 뷰 이름을 기반으로 Controller 처리 결과를 생성할 뷰를 결정





## 1.  Controller Class



- **UserController.java**

  ```java
  package myspring.user.controller;
  
  import java.util.List;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.servlet.ModelAndView;
  
  import myspring.user.service.UserService;
  import myspring.user.vo.UserVO;
  
  @Controller
  public class UserController {
  	@Autowired
  	private UserService userService;
  	
  	//사용자 목록 조회
  	@RequestMapping("/userList.do")
  	public ModelAndView userList() {
  		List<UserVO> userList = userService.getUserList();
  		
          // request.setAttribute(key, value)와 같은 기능을 한다.
  		// "userList.jsp" : View page 이다.
  		return new ModelAndView("userList.jsp", "userList", userList);
          
  
  //Spring MVC 프레임워크를 사용하지 않을 경우 아래와 같이 진행한다.
         
  		//request.setAttribute("userList", users);
  		//rd = request.getRequestDispatcher("userList.jsp");
  		//rd.forward(request, response);
          
  	}
      
      //사용자 상세정보 조회
  	@RequestMapping("/userDetail.do")
  	
  	//@RequestParam
  	//String userid = request.getParameter("id");와 동일
  	public String userDetail(@RequestParam String userid, Model model) {
  		UserVO user = userService.getUser(userid);
         
  		
  		//ModelAndView 에서 ("userList", userList) 부분
  		model.addAttribute("user", user);
  		
          //ModelAndView 에서 "userList.jsp"(view) 부분
  		return "userDetail.jsp";
  	}
  
  }
  ```

  - **@Controller** : Controller 클래스를 Bean으로 등록
  - **@RequestMapping** : HTTP 요청 URL을 처리할 **Controller 메서드 정의**
    - **@RequestMapping("/userList.do")** 요청이 들어오면 **HandlerMapping** 이 가지고 있는 매핑 정보를 통해 **ModelAndView userList() {..}** 와 매핑 해준다.
  - **@RequestParam** : query String 형태로 보낸 문자열의 값을 추출
  - **ModelAndView(userList)** 또는 **Model(userDetail)** 타입을 이용하여 브라우저 상에서 JSP를 실행할 수 있다.
    - **Model** : **addAttribute(modelName, modelObject)**와 동일

