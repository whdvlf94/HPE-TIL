package workshop.person.entity;

public class PersonEntity {
	private String name, ssn, address, phone;
	private char gender;
	
	public PersonEntity() {
		
	}
	

	public PersonEntity(String name, String ssn, String address, String phone) {
		this.name=name;
//		this.ssn=ssn;
		setSsn(ssn);
		this.address=address;
		this.phone=phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn=ssn;
		
		if ('1'==this.ssn.charAt(6) || '3'==this.ssn.charAt(6)) {
			setGender('³²');
		} else {
			setGender('¿©');
		}
	}

	@Override
	public String toString() {
		return "PersonEntity [name=" + name + ", ssn=" + ssn + ", address=" + address + ", phone=" + phone + ", gender="
				+ gender + "]";
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
	
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

}
