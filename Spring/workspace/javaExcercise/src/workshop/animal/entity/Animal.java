package workshop.animal.entity;

/*
 * 추상 클래스 내에는 추상 메서드가 없어도 된다.
 * 추상 클래스는 스스로 객체를 생성할 수 없다. body가 없는 메서드가 존재할 수 있기 때문에 객체 생성을 막아놓았다.
 * body가 있는 concrete method를 선언할 수 있다.
 */
public abstract class Animal {
	
	protected int legs;
	
	protected Animal(int legs) {
		this.legs=legs;		
	}
	
	public abstract void eat();
	
	public void walk() {
		System.out.println("Animal은 " + this.legs + "다리로 걷는다 .");
		
	}
}
