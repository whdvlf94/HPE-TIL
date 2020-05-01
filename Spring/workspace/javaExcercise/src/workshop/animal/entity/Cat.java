package workshop.animal.entity;

public class Cat extends Animal implements Pet {
	private String name;
	

	
	public Cat(String name) {
		
		super(4);	// ����� �ٸ� �� ��, �θ� Ŭ������ �������Լ��� ���� ������ super()�� ���� ���� ���� ������ �ش�.
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
