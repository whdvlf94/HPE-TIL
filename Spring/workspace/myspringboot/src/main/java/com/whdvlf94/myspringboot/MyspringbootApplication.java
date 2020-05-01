package com.whdvlf94.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.whdvlf94.myspringboot.listener.MyStartingEventListener;

@SpringBootApplication
//Spring Bean Container 생성

//@SpringBootApplication에는 아래와 같은 어노테이션들이 포함되어 있다.

//@SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan

//@EnableAutoConfiguration : 미리 설정되어 있는 Configuration 파일들을 활성화 시킨다.
//boot-autoconfigure.jar -> Meta-INF/spring.factories 파일 안에 Configuration 클래스 목록
public class MyspringbootApplication {

	public static void main(String[] args) {
		
		//WebApplication Type 변경 해보기
		//Default WebApplication Type = SERVLET
	
//		SpringApplication.run(MyspringbootApplication.class, args) 와 동일
		
		SpringApplication application = new SpringApplication(MyspringbootApplication.class);		
		application.setWebApplicationType(WebApplicationType.SERVLET);
		
		
//		application.setWebApplicationType(WebApplicationType.NONE);
		
		//Listener 객체를 등록
		application.addListeners(new MyStartingEventListener());
		application.run(args);
	}

}
