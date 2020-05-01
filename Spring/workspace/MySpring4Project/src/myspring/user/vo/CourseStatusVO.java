package myspring.user.vo;

public class CourseStatusVO {
	private Integer statusId;
	private StudentVO student;
	private CourseVO course;
	private Integer courseScore;

	public CourseStatusVO() {

	}

	public CourseStatusVO(Integer statusId, StudentVO student, CourseVO course, Integer courseScore) {
		super();
		this.statusId = statusId;
		this.student = student;
		this.course = course;
		this.courseScore = courseScore;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public StudentVO getStudent() {
		return student;
	}

	public void setStudent(StudentVO student) {
		this.student = student;
	}

	public CourseVO getCourse() {
		return course;
	}

	public void setCourse(CourseVO course) {
		this.course = course;
	}

	public Integer getCourseScore() {
		return courseScore;
	}

	public void setCourseScore(Integer courseScore) {
		this.courseScore = courseScore;
	}

	@Override
	public String toString() {
		return "CourseStatusVO [statusId=" + statusId + ", student=" + student + ", course=" + course + ", courseScore="
				+ courseScore + "]";
	}
	
}
