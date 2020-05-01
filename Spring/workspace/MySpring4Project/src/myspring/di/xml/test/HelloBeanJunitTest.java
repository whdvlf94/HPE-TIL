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

	// @Test �޼��尡 ����Ǳ� ���� ���� ����Ǵ� �޼���
	// �����̳ʸ� ���� �����ؾ� ��ü�� ������ �� �ֱ� ������, @Before ������̼ǿ� ������ �ش�.
	@Before
	public void init() {

		// 1. Spring Bean Container ����
		// ResourceLocation - Spring Bean Config xml ������ �����ؼ� , �Ľ��۾��� �̷���� �� �ְ� �ؾ��Ѵ�.
		// �����̳ʸ� �����ϴ� ������ : GenericXmlApplicationContext
		// xml �����͸� �о� �Ľ��۾��� �Ѵ�.
		factory = new GenericXmlApplicationContext("config/spring_beans.xml");
	}

	/*
	 * TestCase �޼��带 ������ �� ��Ģ 1. @Test ������̼��� �ݵ�� ���� ó���� �����Ѵ�. 2. �׽�Ʈ �޼����� ���� �����ڴ�
	 * �ݵ�� public void �̿��� �Ѵ�.
	 * 
	 */
	@Test
	public void hello() {

		// 2. Container���� Bean�� ��û
		Hello hello = (Hello) factory.getBean("hello"); // factory.getBean�� Object �̹Ƿ� ����ȯ�� ���־�� �Ѵ�.
		Hello hello2 = factory.getBean("hello", Hello.class);

		//System.out.println(hello == hello2); // true , �̱��� �����̱� ������
//		����, �̱��� �����̹Ƿ� Hello.java�� �Է��� ���Ҵ� println�� �ѹ��� ��µȴ�.

		// 2.1 Assert.assertSame() �޼��带 ����ؼ� �ּ� ��
		// �ּ� ���� ��
		// import static Assert �� �߱� ������ Assert.assertSame���� �ۼ����� �ʾƵ� �ȴ�.
		assertSame(hello, hello2);

		// 2.2 Assert.assertEquals() �޼��带 ����ؼ� ���� ��
		assertEquals("Hello Spring", hello.sayHello());

		hello.print();

		// 3. Container���� StringPrinter Bean�� ��û

		// sPrinter ������, ���� Ŭ������ �Է��Ѵ�. getBean("Bean id"
//		Printer printer = factory.getBean("sPrinter", Printer.class);
//		assertEquals("Hello Spring", printer.toString());
		
		StringPrinter sprinter = factory.getBean("sPrinter", StringPrinter.class);
		assertEquals("Hello Spring", sprinter.toString());

	}

}
