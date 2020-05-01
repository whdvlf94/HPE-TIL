package myspring.di.annot.config;
//���� (3) ������̼����� ���� ��ü, XML ��� X

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// <context:component-scan base-package="myspring.di.annot"/>

@ComponentScan(basePackages = {"myspring.di.annot"})
@PropertySource("classpath:config/values.properties")
public class AnnotatedHelloBeanConfig {

}
