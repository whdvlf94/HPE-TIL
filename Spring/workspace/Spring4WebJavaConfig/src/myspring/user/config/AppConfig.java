package myspring.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

//@Import({DatabaseConfig.class,MyBatisConfig.class})
@Import({DatabaseConfig.class,MyBatisConfig.class,MvcConfig.class})
@ComponentScan(basePackages = {"myspring.user.service","myspring.user.dao","myspring.aop.annot","myspring.user.controller"})
@EnableAspectJAutoProxy
public class AppConfig {

}
