package com.whdvlf94.myspringboot.listener;

import java.util.Date;

import javax.xml.crypto.Data;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

//@Component 사용 불가
//이유: 컨테이너 생성 전에 호출되는 클래스이기 때문
public class MyStartingEventListener implements ApplicationListener<ApplicationStartingEvent>{
	
	@Override
	public void onApplicationEvent(ApplicationStartingEvent event) {
		System.out.println("Spring Bean 컨테이너 생성에 호출됨 ApplicationStarting Event " + new Date(event.getTimestamp()));
		
	}

}
