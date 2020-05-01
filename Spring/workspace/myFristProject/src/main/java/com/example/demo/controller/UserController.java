package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	//초기 화면
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("users", repository.findAll());
		return "index";
	}
	
	//사용자 등록 버튼
	@GetMapping("/signup")
	public String showSignForm(User user) {
		return "add-user";
	}
	
	//add-user submit 버튼 클릭 이후 로직
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "add-user";
		}
		repository.save(user);
		return "redirect:/index";
	}
	
	//사용자 수정 버튼
	@GetMapping("/edit/{id}")
	public ModelAndView showUpdateForm(@PathVariable Long id) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		return new ModelAndView("update-user", "user", user);
		
	}
	
	//update-user submit 이후 로직
	@PostMapping("/edituser/{id}")
	public String updateUSer(@PathVariable Long id, @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "update-user";
		}
		
		repository.save(user);
		return "redirect:/index";
		
	}
	
	//사용자 삭제
	@GetMapping("/delete/{id}")
	public String showDeleteForm(@PathVariable Long id) {
//		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		User user = repository.findById(id).orElseThrow(() -> new CustomException("E001", "요청하신 ID가 존재하지 않습니다.") );
		repository.delete(user);
		return "redirect:/index";
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		return new ModelAndView("error/generic_error", "errMsg", ex.getMessage());
	}
	
//	@ExceptionHandler(CustomException.class)
//	public ModelAndView handleException(CustomException ex) {
//		ModelAndView model = new ModelAndView("error/generic_error");
//		model.addObject("errCode", ex.getErrCode());
//		model.addObject("errMsg", ex.getErrMsg());
//		return model;
//	}
}
