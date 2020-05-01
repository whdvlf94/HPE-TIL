package myspring.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import myspring.user.service.UserService;
import myspring.user.vo.UserVO;
import myspring.user.vo.UserVOXML;

@RestController
//@Controller + @ResponseBody

//@RequestMapping("/users") 
//Class위에 선언함으로써 메서드에 선언하지 않아도 된다.
public class RestUserXmlController {

	@Autowired
	UserService userService;

	// 사용자 목록 xml 형식
	@GetMapping("/usersxml")
	public UserVOXML userListxml() {
		List<UserVO> userList = userService.getUserList();
		return new UserVOXML("success", userList);
	}

}
