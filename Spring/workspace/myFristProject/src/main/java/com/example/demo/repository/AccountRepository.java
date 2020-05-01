package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Optional<Account> findByUsername(String username);	//CrudRepository Method
	Optional<Account> findByEmail(String email);

}
