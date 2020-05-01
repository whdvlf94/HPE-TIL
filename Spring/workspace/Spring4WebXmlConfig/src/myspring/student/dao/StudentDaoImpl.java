package myspring.student.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import myspring.user.dao.mapper.StudentMapper;
import myspring.user.vo.CourseStatusVO;
import myspring.user.vo.StudentVO;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {
	@Autowired
	StudentMapper mapper;
	
	@Override
	public StudentVO selectStudentById(int id) {
		return mapper.selectStudentById(id);
	}
	
	@Override
	public List<StudentVO> selectStudentDeptById() {
		return mapper.selectStudentDeptById();
	}
	
	@Override
	public List<StudentVO> selectStudentCourseStatusById() {
		return mapper.selectStudentCourseStatusById();
	}
	
	@Override
	public int insertStudent(StudentVO studentVO) {
		return mapper.insertStudent(studentVO);
	}
	
	@Override
	public int insertCourseStatus(CourseStatusVO courseStatusVO) {
		return mapper.insertCourseStatus(courseStatusVO);
	}
	
}
