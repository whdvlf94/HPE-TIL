package lambda;


import java.util.HashSet;
import java.util.Set;

import jdbc.user.vo.MyDate;

public class MyDateHashCodeTest {

	public static void main(String[] args) {
		
		MyDate date1 = new MyDate(2020, 4, 24);
		System.out.println(date1);
		System.out.println(date1.hashCode());
		
		MyDate date2 = new MyDate(2020, 4, 24);
		System.out.println(date2);
		System.out.println(date2.hashCode());
		
		MyDate date3 = new MyDate(2020, 4, 1);
		System.out.println(date3);
		System.out.println(date3.hashCode());
		
		//주소비교
		System.out.println(date1==date2);
		System.out.println(date1.equals(date2)); //Object의 equals() 호출하므로 false
		// 따라서, equals 메서드를 MyDate 클래스에서 오버라이딩 해야 한다.
		
		//HashSet - 중복을 허용하지 않음
		Set<MyDate> set = new HashSet<>();
		set.add(date1);
		set.add(date2);
		set.add(date3);
		
		//주소가 다르기 때문에 hashCode 값이 다 다르게 나온다.
		//MyDate 클래스에서 Override 해야한다.
		set.forEach((date)-> System.out.println(date));
		
		
		String str1 = "hello";
		String str2 = new String("hello");
		
		System.out.println(str1 == str2); //false
		System.out.println(str1.equals(str2)); //true
		//String의 경우 Object의 equals를 Override 해놓았기 때문에 true 값이 출력된다.
	}

}
