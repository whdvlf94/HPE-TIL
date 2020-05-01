package com.whdvlf94.myspringboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.whdvlf94.myspringboot.entity.Account;
import com.whdvlf94.myspringboot.entity.User;
import com.whdvlf94.myspringboot.exception.CustomException;
import com.whdvlf94.myspringboot.exception.ResourceNotFoundException;
import com.whdvlf94.myspringboot.repository.UserRepository;
import com.whdvlf94.myspringboot.service.AccountService;

@Controller
public class UserController {

	@Autowired
	private UserRepository userReposity;
	
	@Autowired
	private AccountService accountService;

	//마이페이지
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}
	
	//Add Account
	@GetMapping("/accountForm")
	public String accountForm(Account account) {

		return "add-account";
	}
	
	@PostMapping("/addAccount")
	public String addAccount(@ModelAttribute Account account) {
		Account addAccount = accountService.createAccount(account.getUsername(), account.getPassword());
		System.out.println(" >>>>>>> 등록된 Account : " + addAccount);
		return "redirect:/index.html";
//		index.html 로 표기해야 static/index로 이동한다. html을 입력하지 않으면 template/index로 이동
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		return new ModelAndView("error/generic_error", "errMsg", ex.getMessage());
	}
	
	@ExceptionHandler(CustomException.class)
	public ModelAndView handleCustomException(CustomException ex) {
		ModelAndView model = new ModelAndView("error/generic_error");
		model.addObject("errCode", ex.getErrCode());
		System.out.println(">>>>>>>>>>" + ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
		return model;
	}
	
	//사용자 삭제
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable long id) {
//		User user = userReposity.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		User user = userReposity.findById(id).orElseThrow(() -> new CustomException("E001", String.format("요청하신 ID %s가(이) 존재하지 않습니다.", id)));
		userReposity.delete(user);
		return "redirect:/index";
	}
	

	//사용자 수정 화면
	@GetMapping("/edit/{id}")
	public ModelAndView showUpdateForm(@PathVariable long id) {
		User user = userReposity.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		System.out.println(user);
		return new ModelAndView("update-user", "user", user);
		
	}
	
	//사용자 수정 (update-user로 부터 값을 받아온다.)
	@PostMapping("/edituser/{id}")
	public String updateUser(@PathVariable long id, @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}
		userReposity.save(user);
		return "redirect:/index";
	}
	
	
	//사용자 등록 화면 전환
	@GetMapping("/signup")
	public String showSignForm(User user) {
		return "add-user";
	}
	
	//사용자 등록
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "add-user";
		}
		userReposity.save(user);
		return "redirect:/index";
	}

	//사용자 리스트
	@GetMapping("/index")
	public String index(Model model) {

		model.addAttribute("users", userReposity.findAll());
		return "index";

	}

	@GetMapping("/leaf")
	public ModelAndView leaf() {
		return new ModelAndView("leaf", "name", "타임리프!");
	}
}
