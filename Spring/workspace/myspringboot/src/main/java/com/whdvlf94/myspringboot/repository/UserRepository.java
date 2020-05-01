package com.whdvlf94.myspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whdvlf94.myspringboot.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	//JpaRepository가 bean을 생성해 준다.
	Optional<User> findByName(String name);
	Optional<User> findByEmail(String email);

}
