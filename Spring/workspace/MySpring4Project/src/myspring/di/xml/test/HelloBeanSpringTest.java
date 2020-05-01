package myspring.di.xml.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myspring.di.xml.Hello;

//컨테이너를 자동으로 생성해주는 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)

//spring bean 정보가 들어있는 xml 파일의 경로를 입력해준다.
@ContextConfiguration(locations = "classpath:config/spring_beans.xml")

public class HelloBeanSpringTest {

	@Autowired
	@Qualifier("helloC")
	Hello hello;
	// Hello hello = (Hello) factory.getBean("hello"); 와 같은 기능

	@Test
	public void hellobean() {
		System.out.println(hello.sayHello());
		hello.print(); // @Qualifier에 의해 hello 대신 helloC 값이 적용된다.

		List<String> names = hello.getNames();
		// xml 파일에서 property를 이용하여 setNames에 values를 추가하였다.
		// 따라서 getNames() 메서드를 통해 이를 호출할 수 있다.
		for (String name : names) {
			System.out.println(name);

		}
		

		Map<String, Integer> ages = hello.getAges();
		
		//1. Map의 keySet() 사용
		
		//ages.keySet() => Set<String>
		for(String key: ages.keySet()) {
			System.out.println(key + " : " + ages.get(key));
		}
		
		
		//2. Map의 entrySet() 사용
		for(Map.Entry<String,Integer> entry : ages.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

}
