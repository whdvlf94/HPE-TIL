package myspring.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import myspring.user.service.UserService;
import myspring.user.vo.UserVO;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	// 사용자 목록 조회
	@RequestMapping("/userList.do")
	public ModelAndView userList() {
		List<UserVO> userList = userService.getUserList();

		// request.setAttribute(key, value)와 같은 기능을 한다.
		// "userList.jsp" : View page 이다.
		return new ModelAndView("userList", "userList", userList);
	}

	// 사용자 상세정보 조회
	@RequestMapping("/userDetail.do")

	// @RequestParam
	// String userid = request.getParameter("id");와 동일
	public String userDetail(@RequestParam String userid, Model model) {
		UserVO user = userService.getUser(userid);

		// (key, value)
		model.addAttribute("user", user);

		return "userDetail";
	}

	// 사용자 등록 Form 조회
	@RequestMapping("/userInsertForm.do")
	public String insetUserForm(HttpSession session) {
		List<String> genderList = new ArrayList<String>();
		genderList.add("남");
		genderList.add("여");

		// session 객체에 genderList 객체를 저장
		session.setAttribute("genderList", genderList);

		List<String> cityList = new ArrayList<String>();
		cityList.add("서울");
		cityList.add("경기");
		cityList.add("부산");
		cityList.add("대구");
		cityList.add("제주");

		// session 객체에 cityList 객체를 저장
		session.setAttribute("cityList", cityList);

		return "userInsert";
	}

	// 사용자 등록 처리
	// @RequestMapping은 default가 GET 방식이다. 따라서 POST 방식의 경우 method 를 추가해 주어야 한다.
	@RequestMapping(value = "/userInsert.do", method = RequestMethod.POST)
	public String userInsert(@ModelAttribute UserVO user) {
		System.out.println(">> UserVO " + user);
		userService.insertUser(user);

		// 사용자 목록조회를 처리하는 요청으로 포워딩을 하겠다
		return "redirect:/userList.do";
	}

	// 사용자 삭제 처리
	// userDelete.do?userId=gildong 기존 방식
	// userDelete.do/gildong
	@RequestMapping("userDelete.do/{id}")
	public String userDelete(@PathVariable("id") String userid) {
		userService.deleteUser(userid);

		return "redirect:/userList.do";
	}

	// 사용자 수정 Form
	@RequestMapping("/userUpdateForm.do")
	public ModelAndView updateUserForm(@RequestParam String id) {
		UserVO user = userService.getUser(id);
		List<String> genderList = new ArrayList<String>();
		genderList.add("남");
		genderList.add("여");
		List<String> cityList = new ArrayList<String>();
		cityList.add("서울");
		cityList.add("경기");
		cityList.add("부산");
		cityList.add("대구");
		cityList.add("제주");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		map.put("user", user);
		return new ModelAndView("userUpdate", "map", map);
	}
	
	//사용자 수정 처리
	@RequestMapping(value = "/userUpdate.do", method = RequestMethod.POST) 
	public String updateUser(@ModelAttribute UserVO user) {
//		System.out.println(">>> UserVO " + user);
		userService.updateUser(user);
		
		return "redirect:/userList.do";
	}
	
}
