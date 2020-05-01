package myspring.user.dao.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import myspring.user.vo.CourseStatusVO;
import myspring.user.vo.StudentVO;

@MyMapper
public interface StudentMapper {
	@ResultMap("studentResultMap")
	@Select("select * from student where stu_id=#{id}")
	StudentVO selectStudentById(@Param("id") int id);
	List<StudentVO> selectStudentDeptById();
	List<StudentVO> selectStudentCourseStatusById();
	int insertStudent(StudentVO studentVO);
	int updateStudent(StudentVO studentVO);
	int insertCourseStatus(CourseStatusVO courseStatusVO);
	
}

