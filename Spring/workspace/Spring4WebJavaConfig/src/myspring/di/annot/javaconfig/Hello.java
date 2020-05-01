package myspring.di.annot.javaconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component("hello")
public class Hello {
	//@Value("어노테이션")
	String name;

//	@Autowired
//	@Qualifier("stringPrinter")
//	@Resource(name="${printer1}")
	Printer printer;

	
	public String sayHello() {
		return "Hello " + name;
	}

	public void print() {
		this.printer.print(sayHello());
	}
	
//	@Value("어노테이션")
	public void setName(String name) {
		this.name = name;
	}
	
//	@Autowired
//	@Qualifier("stringPrinter")
//	@Required
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
//@Autowired 
//public Hello(@Value("${db.username}") String name, @Qualifier("${printer}") Printer printer) {
/*
 * 	public Hello() {
	}

	public Hello(String name, Printer printer) {
		this.name = name;
		this.printer = printer;
	}

 */
