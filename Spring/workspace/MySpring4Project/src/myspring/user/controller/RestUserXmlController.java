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
//Class���� ���������ν� �޼��忡 �������� �ʾƵ� �ȴ�.
public class RestUserXmlController {

	@Autowired
	UserService userService;

	// ����� ��� xml ����
	@GetMapping("/usersxml")
	public UserVOXML userListxml() {
		List<UserVO> userList = userService.getUserList();
		return new UserVOXML("success", userList);
	}

}
