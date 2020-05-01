package myspring.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import myspring.user.service.UserService;
import myspring.user.vo.UserVO;
import myspring.user.vo.UserVOXML;

@RestController
//@Controller + @ResponseBody

//@RequestMapping("/users") 
//Class위에 선언함으로써 메서드에 선언하지 않아도 된다.
public class RestUserController {

	@Autowired
	UserService userService;

	// 사용자 목록
	@GetMapping("/users") // GET
	//java object -> json
	public List<UserVO> userList() {
		return userService.getUserList();
	}
	
	
	//GET
	@GetMapping("/users/{id}")
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
	public Boolean userDelete(@PathVariable String id) {
		if (id != null) {
			userService.deleteUser(id);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	

}
