package myspring.di.xml.test;

// static import : static method import
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import myspring.di.xml.Hello;
import myspring.di.xml.Printer;
import myspring.di.xml.StringPrinter;

public class HelloBeanJunitTest {
	BeanFactory factory;

	// @Test 메서드가 실행되기 전에 먼저 실행되는 메서드
	// 컨테이너를 먼저 생성해야 객체를 생성할 수 있기 때문에, @Before 어노테이션에 지정해 준다.
	@Before
	public void init() {

		// 1. Spring Bean Container 생성
		// ResourceLocation - Spring Bean Config xml 정보를 설정해서 , 파싱작업이 이루어질 수 있게 해야한다.
		// 컨테이너를 생성하는 구현부 : GenericXmlApplicationContext
		// xml 데이터를 읽어 파싱작업을 한다.
		factory = new GenericXmlApplicationContext("config/spring_beans.xml");
	}

	/*
	 * TestCase 메서드를 선언할 때 규칙 1. @Test 어노테이션을 반드시 가장 처음에 선언한다. 2. 테스트 메서드의 접근 제한자는
	 * 반드시 public void 이여야 한다.
	 * 
	 */
	@Test
	public void hello() {

		// 2. Container에게 Bean을 요청
		Hello hello = (Hello) factory.getBean("hello"); // factory.getBean은 Object 이므로 형변환을 해주어야 한다.
		Hello hello2 = factory.getBean("hello", Hello.class);

		//System.out.println(hello == hello2); // true , 싱글톤 패턴이기 때문에
//		또한, 싱글톤 패턴이므로 Hello.java에 입력해 놓았던 println이 한번만 출력된다.

		// 2.1 Assert.assertSame() 메서드를 사용해서 주소 비교
		// 주소 값을 비교
		// import static Assert 를 했기 때문에 Assert.assertSame으로 작성하지 않아도 된다.
		assertSame(hello, hello2);

		// 2.2 Assert.assertEquals() 메서드를 사용해서 값을 비교
		assertEquals("Hello Spring", hello.sayHello());

		hello.print();

		// 3. Container에게 StringPrinter Bean을 요청

		// sPrinter 이지만, 상위 클래스로 입력한다. getBean("Bean id"
//		Printer printer = factory.getBean("sPrinter", Printer.class);
//		assertEquals("Hello Spring", printer.toString());
		
		StringPrinter sprinter = factory.getBean("sPrinter", StringPrinter.class);
		assertEquals("Hello Spring", sprinter.toString());

	}

}
