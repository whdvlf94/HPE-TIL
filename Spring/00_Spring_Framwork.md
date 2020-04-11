# Spring Framwork

> 이클립스를 이용한 JAVA 복습



## 실습) Encapsulation 개념 연습하기



#### Account.java

```java
package workshop.account.entity;

public class Account {
	private String custId, accId;
	private int balance;
	
	
	// 생성자 함수
	public Account() {
		
	}

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

```





#### TestAccount.java

```java
package workshop.account.test;

import workshop.account.entity.Account;

public class TestAccount {
	
	//ctrl + f11 로 main class 실행 가능
	public static void main(String args[]) {
		
		Account account = new Account("A1100", "221-22-3477", 100000);
		
		System.out.println(account);
		
	//toString()메서드는 default로 설정되어 있다. 
    //즉, account.toString()
	//하지만, 이 경우 Object의 toString() 메서드가 호출된다.
	//부모 클래스의 toString 메서드를 호출하기 위해서는 
    //부모 클래스에서 Generate toSring()을 통해
	//Override로 재정의하여 부모 클래스의 toStirng()메서드를 호출할 수 있다.
		
		//Object toString()을 실행한 경우
		//workshop.account.entity.Account@383534aa

		//부모 클래스에서 toSring()을 Override한 경우
		//Account [custId=A1100, accId=221-22-3477, balance=100000]
		
		System.out.println(account.getBalance());
		
		//입금
		account.deposite(10000);
		System.out.println(account.getBalance()); // 110000
		
		//출금
		account.withdraw(20000);
		System.out.println(account.getBalance()); // 90000

	}

}

```







## 배열을 이용한 실습



#### PersonEntity

```java
package workshop.person.entity;

public class PersonEntity {
	private String name, ssn, address, phone;
	private char gender;
	
	public PersonEntity() {
		
	}
	

	public PersonEntity(String name, String ssn, String address, String phone) {
		this.name=name;
//		this.ssn=ssn; 
// 입력된 인자 값은 setSsn()으로 입력되어, 성별을 구분한다.
		setSsn(ssn);
		this.address=address;
		this.phone=phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn=ssn;
		
		if ('1'==this.ssn.charAt(6) || '3'==this.ssn.charAt(6)) {
			setGender('남');
		} else {
			setGender('여');
		}
	}

	@Override
	public String toString() {
		return "PersonEntity [name=" + name + ", ssn=" + ssn + ", address=" + address + ", phone=" + phone + ", gender="
				+ gender + "]";
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

}

```



#### PersonManager

```java
package workshop.person.control;

import java.util.*;

import workshop.person.entity.PersonEntity;

public class PersonManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		if(args.length < 1 ) {
//			System.out.println("성별 값을 입력하세요");
//			System.exit(0);
//		}
		PersonManager pManager = new PersonManager();	
		PersonEntity[] persons = new PersonEntity[10];	
		pManager.fillPersons(persons);
		pManager.showPersons(persons);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("성별을 입력하세요");
		
		char gender = sc.next().charAt(0);
		System.out.println("입력된 성별 값은 " + gender);
		System.out.println("성별 " + gender + "(은)는 " + pManager.findbyGender(persons,gender) + "명 입니다.");
		
		System.out.println("이름을 입력하세요");
		String name = sc.next();
		System.out.println("이름 " + name + " 으로 찾기 결과입니다.");
		pManager.showPersons(persons,name);
		sc.close();
		

	}

	public void showPersons(PersonEntity[] persons, String name) {
		for (PersonEntity item : persons) {
			if(name.equals(item.getName())) {
				System.out.println("[이름] " + item.getName());
				System.out.println("[성별] " + item.getGender());
				System.out.println("[전화번호] " + item.getPhone());
				
			}
			
		}
	}

	public int findbyGender(PersonEntity[] persons, char gender) {
		int cnt=0;
		for (PersonEntity item : persons) {
			if(item.getGender() == gender) {
				cnt ++;
			}
		}
		return cnt;
	}

	public void showPersons(PersonEntity[] persons) {
		
		//foreach 입력 후, ctrl + space 하면 for each 문장 자동완성
		for(PersonEntity item:persons) {
			System.out.println("[이름] " + item.getName() + "\t [성별] " + item.getGender() + "\t [전화번호] " + item.getPhone());
		}
		// TODO Auto-generated method stub
		
	}

	public void fillPersons(PersonEntity[] persons) {
		// TODO Auto-generated method stub
		persons[0]=new PersonEntity("이성호","7212121028102", "인천 계양구", "032-392-2932");
		persons[1]=new PersonEntity("김하늘","7302132363217", "서울 강동구", "02-362-1932");
		persons[2]=new PersonEntity("박영수","7503111233201", "서울 성북구", "02-887-1542");
		persons[3]=new PersonEntity("나인수","7312041038988", "대전 유성구", "032-384-2223");
		persons[4]=new PersonEntity("홍정수","7606221021341", "서울 양천구", "02-158-7333");
		persons[5]=new PersonEntity("이미숙","7502142021321", "서울 강서구", "02-323-1934");
		persons[6]=new PersonEntity("박성구","7402061023101", "서울 종로구", "02-308-0932");
		persons[7]=new PersonEntity("유성미","7103282025101", "서울 은평구", "02-452-0939");
		persons[8]=new PersonEntity("황재현","7806231031101", "인천 중구", "032-327-2202");
		persons[9]=new PersonEntity("최철수","7601211025101", "인천 계양구", "032-122-7832");
	}

}

```





## 추상화와 인터페이스



![캡처](https://user-images.githubusercontent.com/58682321/78780839-36f3cc00-79da-11ea-8893-9f2571095688.PNG)

- *이탤릭체* : 추상 클래스(or 메서드)
- **#** : 인터페이스
- **-** : private 선언 , **+** : public 선언
- **getName(): String**  : return 타입 **String**
- play() : return 타입 없음





#### Animal(추상화 클래스)

```java
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
```



#### Pet(인터페이스)

```java
package workshop.animal.entity;

public interface Pet {
	
	void setName(String name) ;
	
	String getName();
	
	void play() ;
}
```



#### Cat(자식 클래스)

```java
package workshop.animal.entity;

public class Cat extends Animal implements Pet {
	private String name;
	

	
	public Cat(String name) {
		
		super(4);	// 고양이 다리 갯 수, 부모 클래스의 생성자함수가 없기 때문에 super()를 통해 직접 값을 지정해 준다.
		this.name=name;
	}
	
	public Cat() {
		this("");
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
```



#### TestAnimals

````java
package workshop.animal.control;

import workshop.animal.entity.Animal;
import workshop.animal.entity.Cat;
import workshop.animal.entity.Pet;

public class TestAnimals {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Cat 객체를 생성하는 방법 3가지
		Cat cat1 = new Cat();
		cat1.setName("플러피");
		System.out.println(cat1.getName());
		cat1.play();
		cat1.eat();
		cat1.walk();
		
		Animal cat2 = new Cat("플러피2");
		cat2.eat();
		cat2.walk();
		
		Pet cat3 = new Cat();
		cat3.setName("플러피3");
		System.out.println(cat3.getName());
		cat3.play();
	}

}
````

