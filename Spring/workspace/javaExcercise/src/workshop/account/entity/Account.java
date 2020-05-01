package workshop.account.entity;

public class Account {
	private String custId, accId;
	private int balance;
	
	
//	// ������ �Լ�
//	public Account() {
//		
//	}

	@Override
	public String toString() {
		return "Account [custId=" + custId + ", accId=" + accId + ", balance=" + balance + "]";
	}

	// Overloading�� ������ ����
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
	
	//�Ա�
	public void deposite(int amount) {
		this.balance += amount;
	}
	
	//���
	public void withdraw(int amount) {
		this.balance -= amount;
	}
	

}
