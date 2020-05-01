package myspring.student.dao;

import java.util.List;

import myspring.user.vo.CourseStatusVO;
import myspring.user.vo.StudentVO;

public interface StudentDao {

	StudentVO selectStudentById(int id);

	List<StudentVO> selectStudentDeptById();

	List<StudentVO> selectStudentCourseStatusById();

	int insertStudent(StudentVO studentVO);

	int insertCourseStatus(CourseStatusVO courseStatusVO);
	
}