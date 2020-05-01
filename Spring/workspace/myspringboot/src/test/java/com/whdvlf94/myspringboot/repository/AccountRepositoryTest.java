package com.whdvlf94.myspringboot.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whdvlf94.myspringboot.entity.Account;

import javassist.bytecode.ExceptionsAttribute;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	AccountRepository repository;

	@Test
	public void findByUsername() throws Exception {
//		Account existAct = repository.findByUsername("spring");
//		assertThat(existAct).isNotNull();
//		
//		Account notExistAct = repository.findByUsername("test1");
//		assertThat(notExistAct).isNotNull();
//	
		Optional<Account> existOpt = repository.findByUsername("spring");
		System.out.println(existOpt.isPresent()); // true
		if (existOpt.isPresent()) {
			Account exisAcct = existOpt.get();
			System.out.println(exisAcct);
		}
		Account account = existOpt.orElseThrow(() -> new RuntimeException("존재하지 않는 username 입니다."));
		System.out.println("존재하는 Account " + account);

		Optional<Account> notExistOpt = repository.findByUsername("test2");
		System.out.println(notExistOpt.isPresent()); // false

		// orElseThrow()의 아규먼트 타입 : Supplier
		// Supplier의 추상메서드 T get()
		Account notExistAcct = notExistOpt.orElseThrow(() -> new RuntimeException("존재하지 않는 username 입니다."));
		System.out.println(notExistAcct);
		/*
		 * NoSuchElementException이 발생 Account notExistAcct = notExistOpt.get();
		 * System.out.println(notExistAcct);
		 */
	}

	@Test
	@Ignore
	public void account() throws Exception {
		Account account = new Account();
		account.setUsername("spring");
		account.setPassword("1234");

		Account saveAct = repository.save(account);
		System.out.println(saveAct);

		assertThat(saveAct).isNotNull();
	}
}
