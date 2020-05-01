package com.whdvlf94.myspringboot.controller;

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

import com.whdvlf94.myspringboot.entity.Account;
import com.whdvlf94.myspringboot.entity.Account;
import com.whdvlf94.myspringboot.exception.ResourceNotFoundException;
import com.whdvlf94.myspringboot.repository.AccountRepository;

@RestController
public class AccountRestController {
	@Autowired
	private AccountRepository repository;

	@PostMapping("/list")
	public Account create(@RequestBody Account acct) {

		return repository.save(acct);
	}

	@GetMapping("/list")
	public List<Account> getAccounts() {
		return repository.findAll();
	}

	@GetMapping("/list/{id}")
	public Account getAccount(@PathVariable Long id) {
		Optional<Account> acctOpt = repository.findById(id);
		Account acct = acctOpt.orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
		return acct;
	}

	@PutMapping("/list/{id}")
	public Account updateAccount(@PathVariable Long id, @RequestBody Account acctDetail) {
		Account acct = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
		acct.setUsername(acctDetail.getUsername());
		acct.setEmail(acctDetail.getEmail());
		
		Account updateAccount = repository.save(acct);
		return updateAccount;
	}

	@DeleteMapping("/list/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		Account acct = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
		repository.delete(acct);
		return new ResponseEntity<String>("id : " + acct.getId() + " deleted", HttpStatus.OK);

	}

}
