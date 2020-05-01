package workshop.account.test;

import workshop.account.entity.Account;

public class TestAccount {
	
	//ctrl + f11 �� main class ���� ����
	public static void main(String args[]) {
		
		Account account = new Account("A1100", "221-22-3477", 100000);
		
		System.out.println(account);
		
		//toString()�޼���� default�� �����Ǿ� �ִ�. ��, account.toString()
		//������, �� ��� Object�� toString() �޼��尡 ȣ��ȴ�.
		//�θ� Ŭ������ toString �޼��带 ȣ���ϱ� ���ؼ��� �θ� Ŭ�������� Generate toSring()�� ����
		//Override�� �������Ͽ� �θ� Ŭ������ toStirng()�޼��带 ȣ���� �� �ִ�.
		
		//Object toString()�� ������ ���
		//workshop.account.entity.Account@383534aa

		//�θ� Ŭ�������� toSring()�� Override�� ���
		//Account [custId=A1100, accId=221-22-3477, balance=100000]
		
		System.out.println(account.getBalance());
		
		//�Ա�
		account.deposite(10000);
		System.out.println(account.getBalance());
		
		//���
		account.withdraw(20000);
		System.out.println(account.getBalance());

	}

}
