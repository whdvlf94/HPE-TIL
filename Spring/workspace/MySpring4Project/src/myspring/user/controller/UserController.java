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

	// ����� ��� ��ȸ
	@RequestMapping("/userList.do")
	public ModelAndView userList() {
		List<UserVO> userList = userService.getUserList();

		// request.setAttribute(key, value)�� ���� ����� �Ѵ�.
		// "userList.jsp" : View page �̴�.
		return new ModelAndView("userList", "userList", userList);
	}

	// ����� ������ ��ȸ
	@RequestMapping("/userDetail.do")

	// @RequestParam
	// String userid = request.getParameter("id");�� ����
	public String userDetail(@RequestParam String userid, Model model) {
		UserVO user = userService.getUser(userid);

		// (key, value)
		model.addAttribute("user", user);

		return "userDetail";
	}

	// ����� ��� Form ��ȸ
	@RequestMapping("/userInsertForm.do")
	public String insetUserForm(HttpSession session) {
		List<String> genderList = new ArrayList<String>();
		genderList.add("��");
		genderList.add("��");

		// session ��ü�� genderList ��ü�� ����
		session.setAttribute("genderList", genderList);

		List<String> cityList = new ArrayList<String>();
		cityList.add("����");
		cityList.add("���");
		cityList.add("�λ�");
		cityList.add("�뱸");
		cityList.add("����");

		// session ��ü�� cityList ��ü�� ����
		session.setAttribute("cityList", cityList);

		return "userInsert";
	}

	// ����� ��� ó��
	// @RequestMapping�� default�� GET ����̴�. ���� POST ����� ��� method �� �߰��� �־�� �Ѵ�.
	@RequestMapping(value = "/userInsert.do", method = RequestMethod.POST)
	public String userInsert(@ModelAttribute UserVO user) {
		System.out.println(">> UserVO " + user);
		userService.insertUser(user);

		// ����� �����ȸ�� ó���ϴ� ��û���� �������� �ϰڴ�
		return "redirect:/userList.do";
	}

	// ����� ���� ó��
	// userDelete.do?userId=gildong ���� ���
	// userDelete.do/gildong
	@RequestMapping("userDelete.do/{id}")
	public String userDelete(@PathVariable("id") String userid) {
		userService.deleteUser(userid);

		return "redirect:/userList.do";
	}

	// ����� ���� Form
	@RequestMapping("/userUpdateForm.do")
	public ModelAndView updateUserForm(@RequestParam String id) {
		UserVO user = userService.getUser(id);
		List<String> genderList = new ArrayList<String>();
		genderList.add("��");
		genderList.add("��");
		List<String> cityList = new ArrayList<String>();
		cityList.add("����");
		cityList.add("���");
		cityList.add("�λ�");
		cityList.add("�뱸");
		cityList.add("����");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		map.put("user", user);
		return new ModelAndView("userUpdate", "map", map);
	}
	
	//����� ���� ó��
	@RequestMapping(value = "/userUpdate.do", method = RequestMethod.POST) 
	public String updateUser(@ModelAttribute UserVO user) {
//		System.out.println(">>> UserVO " + user);
		userService.updateUser(user);
		
		return "redirect:/userList.do";
	}
	
}
