package workshop.account.entity;

public class Account {
	private String custId, accId;
	private int balance;
	
	
//	// 생성자 함수
//	public Account() {
//		
//	}

	@Override
	public String toString() {
		return "Account [custId=" + custId + ", accId=" + accId + ", balance=" + balance + "]";
	}

	// Overloading된 생성자 선언
	public Account(String custId, String accId, int balance) {
		this.custId = custId;
		this.accId = accId;
		this.balance = balance;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	//입금
	public void deposite(int amount) {
		this.balance += amount;
	}
	
	//출금
	public void withdraw(int amount) {
		this.balance -= amount;
	}
	

}
