package myspring.user.config.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import myspring.user.config.AppConfig;
import myspring.user.dao.mapper.StudentMapper;
import myspring.user.service.UserService;
import myspring.user.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from the OrderServiceConfig class
@ContextConfiguration(classes=AppConfig.class, loader=AnnotationConfigContextLoader.class)

public class AppConfigTest {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Test @Ignore
	public void studentbyId() {
		
		System.out.println(studentMapper.selectStudentDeptById());
	}
	
	
	@Test //@Ignore
	public void service() {
		System.out.println(userService.getUser("gildong"));
		for(UserVO user : userService.getUserList()) {
			System.out.println(user);
		}
	}
	
	@Test @Ignore
	public void ds() {
		System.out.println(dataSource);
		try {
			System.out.println(dataSource.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserVO user = sqlSession.selectOne("userNS.selectUserById","gildong");
		System.out.println(user);
	}
}