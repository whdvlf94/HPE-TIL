package workshop.book.control;

import workshop.book.entity.Publication;
import workshop.book.entity.Magazine;
import workshop.book.entity.Novel;
import workshop.book.entity.ReferenceBook;

public class ManageBook {
	
	public static void main(String args[]) {
		
		ManageBook manage = new ManageBook();
		
		Publication[] pubs = new Publication[6];
		pubs[0]= new Magazine("마이크로소프트","2007-10-01",328,9900,"매월");
		pubs[1]= new Magazine("마이크로소프트","2007-10-01",328,9900,"매월");
		pubs[2]= new Magazine("경영과컴퓨터","2007-10-03",316,9000,"매월");
		pubs[3]= new Novel("빠삐용","2007-07-01",396,9800,"베르나르베르베르","현대소설");
		pubs[4]= new Novel("남한산성","2007-04-14",383,11000,"김훈","대하소설");
		pubs[5]= new ReferenceBook("실용주의프로그래머","2007-01-14",496,25000,"소프트웨어공학");
			
		System.out.println("==== Book 정보 출력 ====");
		for (Publication item : pubs) {
			
			System.out.println(item.getTitle());
		}
		
		System.out.println("==== 가격 변경 전 ====");
		System.out.println(pubs[2].getTitle() + " : " + pubs[2].getPrice());
		manage.modifyPrice(pubs[2]);
		System.out.println("==== 가격 변경 후 ====");
		System.out.println(pubs[2].getTitle() + " : " + pubs[2].getPrice());
		
	}
	
	//다형적 arguments(Polymorphic Argument)
	//Publication mag = new Magazine();
	//위의 경우 문제 발생 X, 자식 클래스의 인스턴스를 생성할 때, 타입형을 부모 클래스로해도 문제가 발생하지 않는다.
	public void modifyPrice(Publication pub) {
		int price=pub.getPrice();
		
		double rate = 0.0;
		
		if(pub instanceof Magazine) {
			rate = 0.4;
		}
		if(pub instanceof Novel) {
			rate = 0.2;
		}
		if(pub instanceof ReferenceBook) {
			rate = 0.1;
		}
		
		pub.setPrice(price-(int)(price*rate));
	}
	

}
