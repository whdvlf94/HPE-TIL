package myspring.user.vo;

public class DeptVO {
	private Integer deptid;
	private String deptname;
	
	public DeptVO() {
		
	}

	public DeptVO(Integer deptid) {
		this.deptid = deptid;
	}
	public DeptVO(Integer deptid, String deptname) {
		super();
		this.deptid = deptid;
		this.deptname = deptname;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	@Override
	public String toString() {
		return "Dept [deptid=" + deptid + ", deptname=" + deptname + "]";
	}
}
