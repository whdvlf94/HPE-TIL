package myspring.user.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.LogManager;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myspring.user.dao.mapper.StudentMapper;
import myspring.user.service.UserService;
import myspring.user.vo.DeptVO;
import myspring.user.vo.StudentVO;
import myspring.user.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring_beans.xml")
public class MyBatisTest {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	DataSource dataSource;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	UserService userService;

	@Autowired
	StudentMapper studentMapper;
	
	@Test @Ignore
	public void stuMapper() {
		
		// Test ���̽� : StudentMapper -> SqlSession -> StudentMapper.xml
		
//		StudentVO student =new StudentVO(123, "Mapper", 24, "4�г�", "�ְ�", new DeptVO(10));
//		int cnt = studentMapper.insertStudent(student);
//		System.out.println("��ϵ� �л� �Ǽ� : " + cnt );
		
		 List<StudentVO> selectStudentDeptById = studentMapper.selectStudentDeptById();
		 for (StudentVO studentVO : selectStudentDeptById) {
			 System.out.println(studentVO);
					}
		
	}
	
	@Test //@Ignore
	public void service() {
		//UserService -> UserDao -> SqlSession -> SqlSessionFactory -> DataSource
		
		UserVO user = userService.getUser("spring");
		System.out.println(user);
	}
	
	
	@Test @Ignore
	public void sql2() {
		//SqlSession -> SqlSessionFactory -> DataSource
		
		// Ư�� user�� update
		UserVO updateUser = new UserVO("java", "�ڹ�2", "��2", "����2");
		int cnt = sqlSession.update("userNS.updateUser", updateUser);
		logger.info(">>> update cnt " + cnt);

		List<UserVO> selectList = sqlSession.selectList("userNS.selectUserList");
		for (UserVO userVO : selectList) {
			System.out.println(userVO);
			logger.debug(userVO);
		}
	}

	@Test
	@Ignore
	public void sql() {
		// SqlSession�� selectOne()
		UserVO user = sqlSession.selectOne("userNS.selectUserById", "gildong");
		System.out.println(user);

		UserVO insertUser = new UserVO("java", "�ڹ�", "��", "����");
		int cnt = sqlSession.insert("insertUser", insertUser);
		System.out.println("��ϵ� �Ǽ� : " + cnt);
	}

	@Test
	@Ignore
	public void mybatis_spring() {
		System.out.println(sqlSessionFactory.getClass().getName());
		System.out.println(sqlSession.getClass().getName());

	}

	// DataSource dataSource = new BasicDataSource();
	@Test
	@Ignore
	public void con() {
		try {
			Connection con = dataSource.getConnection();
			System.out.println(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
