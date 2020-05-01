package myspring.di.annot.config;
//전략 (3) 어노테이션으로 전부 대체, XML 사용 X

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// <context:component-scan base-package="myspring.di.annot"/>

@ComponentScan(basePackages = {"myspring.di.annot"})
@PropertySource("classpath:config/values.properties")
public class AnnotatedHelloBeanConfig {

}
