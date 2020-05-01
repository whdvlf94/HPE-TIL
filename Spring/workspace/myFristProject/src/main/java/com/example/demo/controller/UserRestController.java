package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.entity.Users;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@RestController
//@Controller + @Responsebody
public class UserRestController {
	@Autowired
	private UserRepository repository;

	@GetMapping("/users")
	public List<User> getUsers() {
		return repository.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable Long id) {
		Optional<User> userOpt = repository.findById(id);
		System.out.println(repository.findById(id));
		User user = userOpt.orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		return user;
	}

	@PostMapping("/users")
	public User create(@RequestBody User user) {
		return repository.save(user);
	}

	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User useredit) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		user.setName(useredit.getName());
		user.setEmail(useredit.getEmail());
		User updateuser = repository.save(user);
		return updateuser;
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		repository.delete(user);
		return new ResponseEntity<String>("id : " + user.getId() + " deleted", HttpStatus.OK);
	}

	@GetMapping(value = "/usersxml", produces = { "application/xml" })
	public Users getUserXml() {
		Users users = new Users();
		users.setUsers(repository.findAll());
		return users;

	}
}
