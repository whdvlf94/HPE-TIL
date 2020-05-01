package myspring.di.annot;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("helloA")
public class Hello {
	
	//<property name="name" value="Annotation"/>
	@Value("${name}")
	String name;
	
//	@Autowired
//	@Qualifier("stringPrinter")
//<property name="printer" ref="stringPrinter" />
	
//	@Qualifier("${stringPrinter}")는 지원하지 않는다. 
//	즉, Bean의 id를 properties에서 가져와서 사용하는 것은 지원하지 않는다.
	
	@Resource(name ="${myprinter}")
	Printer printer;
	List<String> names;
	Map<String,Integer> ages;

	public Hello() {
		System.out.println("Hello Default Constructor called...");
	}
//	@Autowired
//	public Hello(@Value("Annotation") String name, @Qualifier("stringPrinter") Printer printer) {
	
	public Hello(String name, Printer printer) {
		System.out.println("OverLoading Hello Constructor called...");
		this.name = name;
		this.printer = printer;
	}

	public List<String> getNames() {
		return this.names;
	}

	public void setNames(List<String> list) {
		this.names = list;
	}

//	public void setName(String name) {
//		System.out.println("Hello setName() called..." + name);
//		this.name = name;
//	}
//
//	public void setPrinter(Printer printer) {
//		System.out.println("Hello setPrinter() called..." + printer.getClass().getName());
//		this.printer = printer;
//	}

	public String sayHello() {
		return "Hello " + name;
	}

	public void print() {
		this.printer.print(sayHello());
	}
	
	public void setAges(Map<String, Integer> ages) {
		this.ages = ages;
	}
	
	public Map<String, Integer> getAges() {
		return ages;
	}

}
