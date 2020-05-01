package jdbc.user.vo;

public class MyDate {
	private int year;
	private int month;
	private int day;

	public MyDate() {
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public MyDate(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public String toString() {
		return "MyDate [year=" + year + ", month=" + month + ", day=" + day + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}
	//hascode, equals 자동 생성
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyDate other = (MyDate) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
//	hashcode, equals Override 해보기
//	//Object euqals() Override
//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof MyDate) {
//			MyDate myDate = (MyDate) obj;
//			if ((this.year == myDate.year) && (this.month == myDate.month) && (this.day == myDate.day)) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	@Override
//	public int hashCode(){
//		return this.year ^ this.month ^ this.day;
//	}
	
	

}
