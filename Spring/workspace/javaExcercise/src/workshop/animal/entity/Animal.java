package workshop.animal.entity;

/*
 * �߻� Ŭ���� ������ �߻� �޼��尡 ��� �ȴ�.
 * �߻� Ŭ������ ������ ��ü�� ������ �� ����. body�� ���� �޼��尡 ������ �� �ֱ� ������ ��ü ������ ���Ƴ��Ҵ�.
 * body�� �ִ� concrete method�� ������ �� �ִ�.
 */
public abstract class Animal {
	
	protected int legs;
	
	protected Animal(int legs) {
		this.legs=legs;		
	}
	
	public abstract void eat();
	
	public void walk() {
		System.out.println("Animal�� " + this.legs + "�ٸ��� �ȴ´� .");
		
	}
}
