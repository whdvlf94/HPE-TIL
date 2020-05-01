package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	@Test
	public void findBy() throws Exception {

		Optional<User> existOpt = repository.findByName("test1");
		System.out.println(existOpt.isPresent()); // true
		if (existOpt.isPresent()) {
			User existUser = existOpt.get();
			System.out.println(existUser);
		}
		User user = existOpt.orElseThrow(() -> new RuntimeException("존재하지 않는 username 입니다."));
		System.out.println("존재하는 Account " + user);

	}
	
	@Test @Ignore
	public void user() throws Exception {
		User user = new User();
		user.setName("test1");
		user.setEmail("1@abc.com");
		
		User saveUser = repository.save(user);
		System.out.println(saveUser);
		
		assertThat(saveUser).isNotNull();
	}

}
