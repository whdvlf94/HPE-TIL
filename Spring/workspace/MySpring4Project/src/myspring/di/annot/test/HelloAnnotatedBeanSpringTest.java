package myspring.di.annot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import myspring.di.annot.Hello;
import myspring.di.annot.Printer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring_beans.xml")

public class HelloAnnotatedBeanSpringTest {
	@Autowired
	@Qualifier("helloA")	//Hello 클래스는 하나이기 때문에 @Qualifier를 생략해도 런타임 에러가 발생하지 않는다.
	Hello hello;
	
	@Autowired	// 타입으로 해당되는 Bean을 찾아서 주입해주는 어노테이션
	@Qualifier("stringPrinter")	//Printer의 클래스가 2개가 있기 때문에 @Qualifier를 설정하지 않으면 런타임 에러가 난다.
	
//	Printer consolePrinter; // @Qualifier를 사용하고 싶지 않은 경우, 하지만 @Qualifier 권장
	Printer printer;
	
	@Test
	public void hello() {
		System.out.println(hello.sayHello());
		hello.print();
	}

}
