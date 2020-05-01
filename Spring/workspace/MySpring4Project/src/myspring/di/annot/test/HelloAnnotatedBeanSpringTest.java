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
	@Qualifier("helloA")	//Hello Ŭ������ �ϳ��̱� ������ @Qualifier�� �����ص� ��Ÿ�� ������ �߻����� �ʴ´�.
	Hello hello;
	
	@Autowired	// Ÿ������ �ش�Ǵ� Bean�� ã�Ƽ� �������ִ� ������̼�
	@Qualifier("stringPrinter")	//Printer�� Ŭ������ 2���� �ֱ� ������ @Qualifier�� �������� ������ ��Ÿ�� ������ ����.
	
//	Printer consolePrinter; // @Qualifier�� ����ϰ� ���� ���� ���, ������ @Qualifier ����
	Printer printer;
	
	@Test
	public void hello() {
		System.out.println(hello.sayHello());
		hello.print();
	}

}
