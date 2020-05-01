package workshop.animal.entity;

public class Cat extends Animal implements Pet {
	private String name;
	

	
	public Cat(String name) {
		
		super(4);	// 고양이 다리 갯 수, 부모 클래스의 생성자함수가 없기 때문에 super()를 통해 직접 값을 지정해 준다.
		this.name=name;
	}
	
	public Cat() {
//		this("");
		super(4);
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void play() {
		System.out.println("playing with mouse");

	}

	@Override
	public void eat() {
		System.out.println("abc");

	}

}
