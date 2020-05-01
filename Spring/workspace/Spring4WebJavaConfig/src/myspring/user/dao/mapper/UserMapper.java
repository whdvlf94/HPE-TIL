package myspring.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import myspring.user.vo.UserVO;

public interface UserMapper {
	@Select("select * from users where userid=#{id}")
	UserVO selectUserById(@Param("id") String id);

	@Select("select * from users order by userid")
	List<UserVO> selectUserList();
	
	@Insert("insert into users values(#{userId},#{name},#{gender},#{city})")
	void insertUser(UserVO userVO);
	
	@Update("update users set\r\n" + 
			"		name = #{name},\r\n" + 
			"		gender = #{gender},\r\n" + 
			"		city = #{city}\r\n" + 
			"		where userid = #{userId}")
	void updateUser(UserVO userVO);
	
	@Delete("delete from users where\r\n" + 
			"		userid = #{id}")
	void deleteUser(@Param("id") String id);
}
