package workshop.animal.entity;

public class Fish extends Animal implements Pet {
	private String name;
	
	public Fish() {
		super(0);
	}

	@Override
	public void setName(String name) {
		this.name=name;
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void play() {
		System.out.println("playing with dolphin");
		// TODO Auto-generated method stub

	}

	@Override
	public void eat() {
		System.out.println("bacteria");
		// TODO Auto-generated method stub

	}

}
