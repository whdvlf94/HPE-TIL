package myspring.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import myspring.user.vo.StudentVO;
import myspring.user.vo.UserVO;

public interface StudentMapper {
	final static String SELECT_BY_DEPT_ID = "select s.stu_id,\r\n" + 
			"		s.stu_name,\r\n" + 
			"		s.stu_age,\r\n" + 
			"		s.stu_grade,\r\n" + 
			"		s.stu_daynight,\r\n" + 
			"		d.dept_id,\r\n" + 
			"		d.dept_name\r\n" + 
			"		from student s, dept d\r\n" + 
			"		where s.dept_id = d.dept_id";
	
	final static String INSERT = "insert into student (stu_id,stu_name,stu_age,stu_grade,stu_daynight,dept_id)\r\n" + 
			"		values(\r\n" + 
			"		#{id},\r\n" + 
			"		#{name},\r\n" + 
			"		#{age},\r\n" + 
			"		#{grade},#{daynight},#{dept.deptid} )";
	
	@Select(SELECT_BY_DEPT_ID)
	@Results(value = {
            @Result(property="id", column="stu_id"),
            @Result(property="name", column="stu_name"),
            @Result(property="age", column="stu_age"),
            @Result(property="grade", column="stu_grade"),
            @Result(property="daynight", column="stu_daynight"),
            @Result(property="dept.deptid", column="dept_id"),
            @Result(property="dept.deptname", column="dept_name")
        })
	public List<StudentVO> selectStudentDeptById();
	
	@Insert(INSERT)
	void insertUser(StudentVO studentVO);
	
}
