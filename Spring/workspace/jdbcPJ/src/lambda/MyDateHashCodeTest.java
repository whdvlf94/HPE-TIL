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
		
		//�ּҺ�
		System.out.println(date1==date2);
		System.out.println(date1.equals(date2)); //Object�� equals() ȣ���ϹǷ� false
		// ����, equals �޼��带 MyDate Ŭ�������� �������̵� �ؾ� �Ѵ�.
		
		//HashSet - �ߺ��� ������� ����
		Set<MyDate> set = new HashSet<>();
		set.add(date1);
		set.add(date2);
		set.add(date3);
		
		//�ּҰ� �ٸ��� ������ hashCode ���� �� �ٸ��� ���´�.
		//MyDate Ŭ�������� Override �ؾ��Ѵ�.
		set.forEach((date)-> System.out.println(date));
		
		
		String str1 = "hello";
		String str2 = new String("hello");
		
		System.out.println(str1 == str2); //false
		System.out.println(str1.equals(str2)); //true
		//String�� ��� Object�� equals�� Override �س��ұ� ������ true ���� ��µȴ�.
	}

}
