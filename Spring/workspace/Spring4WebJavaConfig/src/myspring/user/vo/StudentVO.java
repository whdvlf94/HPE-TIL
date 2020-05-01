package myspring.user.vo;

public class StudentVO {
	private Integer id;
	private String name;
	private Integer age;
	private String grade;
	private String daynight;

	private DeptVO dept;

	public StudentVO() {

	}

	public StudentVO(Integer id, String name, Integer age, String grade, String daynight, DeptVO dept) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.grade = grade;
		this.daynight = daynight;
		this.dept = dept;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDaynight() {
		return daynight;
	}

	public void setDaynight(String daynight) {
		this.daynight = daynight;
	}

	public DeptVO getDept() {
		return dept;
	}

	public void setDept(DeptVO dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Student2 [id=" + id + ", name=" + name + ", age=" + age + ", grade=" + grade + ", daynight=" + daynight
				+ ", deptid=" + ", dept=" + dept + "]";
	}

}