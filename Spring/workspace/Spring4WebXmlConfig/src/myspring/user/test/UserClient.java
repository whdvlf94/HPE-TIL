package myspring.user.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myspring.user.dao.UserDao;
import myspring.user.dao.mapper.StudentMapper;
import myspring.user.dao.mapper.UserMapper;
import myspring.user.service.UserService;
import myspring.user.vo.PersonVO;
import myspring.user.vo.StudentVO;
import myspring.user.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/beans.xml")
public class UserClient {
	@Autowired
	ApplicationContext context;
	@Autowired
	UserService service;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	SqlSession session;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Test @Ignore
	public void service() {
		UserVO user = service.getUser("gildong");
		System.out.println("=====> " + user);
	}
	
	
	@Test @Ignore
	public void insertSEQ() {
		int cnt = session.update("userNS.insertPerson", new PersonVO("9407231634744", "둘리", "경기", "1234"));
		System.out.println(cnt);
	}
	
	@Test //@Ignore
	public void updateUserTest() {
		service.updateUser(new UserVO("gildong", "연아3", "여3", "경기3"));
		System.out.println(service.getUser("gildong"));
		for(UserVO user : service.getUserList()) {
			System.out.println(user);
		}
	}

	@Test @Ignore
	public void deleteUserTest() {
		service.deleteUser("dooly");

		for (UserVO user : service.getUserList()) {
			System.out.println(user);
		}
	}
	

	@Test
	@Ignore
	public void insertUserTest() {
		service.insertUser(new UserVO("polar", "연아", "여", "경기"));
		for (UserVO user : service.getUserList()) {
			System.out.println(user);
		}
	}

	@Test
	@Ignore
	public void getUserTest() {
		UserVO user = userMapper.selectUserById("gildong");
		//UserVO user = session.selectOne("myspring.user.dao.UserMapper.selectUserById","gildong");
		System.out.println(user);
		
//		Assert.assertThat(user.getName(), is("홍길동"));
//		UserVO user = service.getUser("dooly");
//		System.out.println("User 정보 : " + user);
//		assertEquals("둘리", user.getName());
	}
	
	@Test @Ignore
	public void getStudentTest() {
		StudentVO student = studentMapper.selectStudentById(1002);
		System.out.println(student);
	}
	
	@Test @Ignore
	public void getUserDaoTest() {
		UserVO user = userDao.read("gildong");
		System.out.println(user);
	}

	@Test
	@Ignore
	public void dataSourceTest() {
		DataSource ds = (DataSource) context.getBean("dataSource");
		try {
			System.out.println(ds.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
