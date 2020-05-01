package myspring.di.xml.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import myspring.di.xml.Hello;
import myspring.di.xml.Printer;

public class HelloBeanTest {
	public static void main(String[] args) {
		ApplicationContext context = 
				new GenericXmlApplicationContext("config/beans.xml");
		Hello hello = (Hello) context.getBean("hello");
		System.out.println(hello.sayHello());
		hello.print();
		Printer printer = (Printer) context.getBean("printer");
		System.out.println(printer.toString());

		Hello hello2 = context.getBean("hello", Hello.class);
		hello2.print();
		
		System.out.println(hello == hello2);
	}
}