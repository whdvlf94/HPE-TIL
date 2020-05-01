package myspring.di.annot;

import org.springframework.stereotype.Component;

@Component("consolePrinter")
// <bean id="consolePrinter" class="myspring.di.annot.ConsolePrinter"/>
public class ConsolePrinter implements Printer {
	public void print(String message) {
		System.out.println(message);
	}
}
