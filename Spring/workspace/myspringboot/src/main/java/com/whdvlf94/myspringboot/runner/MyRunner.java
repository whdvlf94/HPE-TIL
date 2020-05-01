package com.whdvlf94.myspringboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.whdvlf94.myspringboot.property.Whdvlf94Properties;

@Component
@Order(1)
//@Order(1) : Runner 클래스 중에서 실행 순서가 가장 먼저

//java -jar myspringboot-0.0.1-SNAPSHOT.jar --spring.profile.active=production
 
public class MyRunner implements ApplicationRunner {
	@Value("${whdvlf94.name}")
	private String name;

	@Value("${whdvlf94.age}")
	private int age;
	
	@Value("${whdvlf94.fullName}")
	private String fullName;

	@Autowired
	Whdvlf94Properties properties;
	
	@Autowired
	String hello;
	
	private Logger logger = LoggerFactory.getLogger(MyRunner.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		logger.debug(">> Property Name : " + name);
		logger.debug(">> Property FullName : " + fullName);
		logger.debug(">> Property Age : " + age);
		
		logger.debug(">> Property Name : " + properties.getName());
		logger.debug(">> Property FullName : " + properties.getFullName());
		logger.debug(">> Property Age : " + properties.getAge());
		 
		logger.info("SourceAgrs : " + args.getOptionNames());
		logger.info("Program Arguments : " + args.containsOption("bar"));
		logger.info("VM Arguments : " + args.containsOption("foo"));
		logger.info(hello);
	}     
}
