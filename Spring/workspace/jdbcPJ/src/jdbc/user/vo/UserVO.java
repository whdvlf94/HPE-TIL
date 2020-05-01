package jdbc.user.vo;

/**
 * VO(value object)
 * 변수 선언, 생성자 , getter & setter
 * 
 * Java Beans, DTO(Data Transfer Obejct), Entity 라고도 불림
 *
 */
public class UserVO {
	private String userid;
	private String name;
	private char gender;
	private String city;

	public UserVO() {
	}

	public UserVO(String userid, String name, char gender, String city) {
		super();
		this.userid = userid;
		this.name = name;
		this.gender = gender;
		this.city = city;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "UserVO [userid=" + userid + ", name=" + name + ", gender=" + gender + ", city=" + city + "]";
	}

	
}
