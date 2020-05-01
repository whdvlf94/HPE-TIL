package myspring.di.annot.config.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import myspring.di.annot.Hello;
import myspring.di.annot.config.AnnotatedHelloBeanConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnnotatedHelloBeanConfig.class, loader = AnnotationConfigContextLoader.class )
public class HelloConfigTest {
	@Autowired
	Hello hello;
	
	@Test
	public void hello() {
		System.out.println(hello.sayHello());
	}
}
