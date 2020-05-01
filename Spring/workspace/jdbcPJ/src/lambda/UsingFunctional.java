package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class UsingFunctional {
	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("java");
		list.add("scalar");
		list.add("python");
		
		for (String value : list) {
			System.out.println(value);
		}
		
		//anonymous inner class
		//Interface Consumer<T> : void accept(T t)
		list.forEach(new Consumer<String>() {
			@Override
			public void accept(String value) {
				System.out.println(value);
			}
		});
		
		//lambda
		//void accept(T t)
		list.forEach(val -> System.out.println(val));
		
		list.forEach(System.out::println);
		
		List<Student> stuList = List.of(new Student(100,"a"),new Student(90,"b"),new Student(80,"c"));
		
		for (Student student : stuList) {
			System.out.println(student);
		}
		stuList.forEach(val -> System.out.println(val));
		
		
		List<Student> stuList2 = List.of(new Student(100,"가"),new Student(90,"나"),new Student(80,"다"));
		
		//List -> Stream 변환
		//점수가 90보다 높은 학생이름을 List<String>으로 출력
//		Stream<Student> stream = stuList2.stream();
		
		stuList2.stream()
		//boolean test(T t)
		.filter(stu->stu.getId()>=90) //Stream<Student>
		.map(stu -> stu.getName()) //Stream<String>
		.forEach(stu -> System.out.println(stu));
	}
}

class Student {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	
	public Student(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	
}