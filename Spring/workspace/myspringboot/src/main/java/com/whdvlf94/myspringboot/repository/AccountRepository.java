package com.whdvlf94.myspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whdvlf94.myspringboot.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Optional<Account> findByUsername(String username);	//CrudRepository Method
	Optional<Account> findByEmail(String email);

}
