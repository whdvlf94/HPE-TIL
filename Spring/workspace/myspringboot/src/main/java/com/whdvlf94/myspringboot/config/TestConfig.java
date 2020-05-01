package com.whdvlf94.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {
	
	@Bean
	public String hello() {
		return "테스트 환경";
	}

}
