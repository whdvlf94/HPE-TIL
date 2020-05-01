package myspring.di.annot.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myspring.di.annot.Hello;
import myspring.di.annot.Printer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/annot.xml")
public class HelloBeanSpringTest {
	@Autowired
	private ApplicationContext context;

	
	@Test
	public void bean1() {
		System.out.println(context);
		Hello hello = (Hello) context.getBean(Hello.class);
		
		Assert.assertEquals("Hello ������̼�", hello.sayHello());
		hello.print();
		
		Printer printer = context.getBean("stringPrinter",Printer.class);
		Assert.assertEquals("Hello ������̼�", printer.toString());
	}	
}