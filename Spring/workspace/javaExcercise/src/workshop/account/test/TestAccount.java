package workshop.account.test;

import workshop.account.entity.Account;

public class TestAccount {
	
	//ctrl + f11 로 main class 실행 가능
	public static void main(String args[]) {
		
		Account account = new Account("A1100", "221-22-3477", 100000);
		
		System.out.println(account);
		
		//toString()메서드는 default로 설정되어 있다. 즉, account.toString()
		//하지만, 이 경우 Object의 toString() 메서드가 호출된다.
		//부모 클래스의 toString 메서드를 호출하기 위해서는 부모 클래스에서 Generate toSring()을 통해
		//Override로 재정의하여 부모 클래스의 toStirng()메서드를 호출할 수 있다.
		
		//Object toString()을 실행한 경우
		//workshop.account.entity.Account@383534aa

		//부모 클래스에서 toSring()을 Override한 경우
		//Account [custId=A1100, accId=221-22-3477, balance=100000]
		
		System.out.println(account.getBalance());
		
		//입금
		account.deposite(10000);
		System.out.println(account.getBalance());
		
		//출금
		account.withdraw(20000);
		System.out.println(account.getBalance());

	}

}
