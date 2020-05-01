package myspring.user.vo;

public class PersonVO {
	private int seq;
	private String ssn;
	private String name;
	private String address;
	private String phone;

	public PersonVO() {
	}

	public PersonVO(int seq, String ssn, String name, String address, String phone) {
		super();
		this.seq = seq;
		this.ssn = ssn;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public PersonVO(String ssn, String name, String address, String phone) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
