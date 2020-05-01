package myspring.user.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myspring.student.dao.StudentDao;
import myspring.user.vo.CourseStatusVO;
import myspring.user.vo.CourseVO;
import myspring.user.vo.DeptVO;
import myspring.user.vo.StudentVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/beans.xml")
public class StudentClient {
	
	@Autowired
	SqlSession session;
	
	@Autowired
	StudentDao studentDao;
	
	@Test @Ignore
	public void insertCourseStatus() {
		StudentVO stu = new StudentVO();
		stu.setId(1003);
		CourseVO course = new CourseVO();
		course.setCourseId(3);
		CourseStatusVO cs = new CourseStatusVO(5, stu, course, 85);
		int cnt = studentDao.insertCourseStatus(cs);
		System.out.println("등록된 건수 :" + cnt);
	}
	
	@Test @Ignore
	public void insertStudent() {
		StudentVO stu = new StudentVO(1003, "박소율",21, "2학년","야간",new DeptVO(40));
		int cnt = studentDao.insertStudent(stu);
		System.out.println("등록된 건수 :" + cnt);
	}
	
	@Test //@Ignore
	public void selectStudentCourseStatusById() {
		List<StudentVO> list = studentDao.selectStudentCourseStatusById();
		for (StudentVO studentVO : list) {
			System.out.println(studentVO);
		}
	}
	
	@Test @Ignore
	public void selectStudentDeptById() {
		List<StudentVO> list = studentDao.selectStudentDeptById();
		for (StudentVO studentVO : list) {
			System.out.println(studentVO);
		}
	}
	
	@Test @Ignore
	public void studentById() {
		StudentVO student = studentDao.selectStudentById(1002);
		System.out.println(student);
	}
	
	@Test @Ignore
	public void student() {
		List<StudentVO> list = session.selectList("studentNS.selectStudentCourseStatusById");
		for (StudentVO student : list) {
			System.out.println(student);
		}
	}	
	
	@Test @Ignore
	public void student2() {
		
		StudentVO stu = new StudentVO(1005, "백명숙2",21, "2학년","야간",new DeptVO(40));
		//session.update("studentNS.insertStudent", stu);
		
	
		stu = new StudentVO(1003, "박소율2",22, "2학년","주간2",new DeptVO(40));
		//session.update("studentNS.updateStudent", stu);
		
		StudentVO	student2  = session.selectOne("studentNS.selectStudentByName", "길");
		System.out.println(student2);
		
		List<StudentVO> list = session.selectList("studentNS.selectStudentByGradeorDay2", 
				new StudentVO(1003, null,22, "1학년","야간",null));
		System.out.println(list);
	}
	
}
