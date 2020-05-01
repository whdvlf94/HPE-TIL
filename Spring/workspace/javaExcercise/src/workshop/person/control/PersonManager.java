package workshop.person.control;

import java.util.*;

import workshop.person.entity.PersonEntity;

public class PersonManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		if(args.length < 1 ) {
//			System.out.println("���� ���� �Է��ϼ���");
//			System.exit(0);
//		}
		PersonManager pManager = new PersonManager();	
		PersonEntity[] persons = new PersonEntity[10];	
		pManager.fillPersons(persons);
		pManager.showPersons(persons);
		
		Scanner sc = new Scanner(System.in);
		System.out.print("������ �Է��ϼ��� : ");
		char gender = sc.next().charAt(0);
		
		System.out.println("\n �Էµ� ���� ���� " + gender);
		System.out.println("���� " + gender + "(��)�� " + pManager.findbyGender(persons,gender) + "�� �Դϴ�. \n");
		
		System.out.print("�̸��� �Է��ϼ��� : ");
		String name = sc.next();
		System.out.println("\n �̸� " + name + " ���� ã�� ����Դϴ�.");
		pManager.showPersons(persons,name);
		sc.close();
		

	}

	public void showPersons(PersonEntity[] persons, String name) {
		for (PersonEntity item : persons) {
			if(name.equals(item.getName())) {
				System.out.println("[�̸�] " + item.getName());
				System.out.println("[����] " + item.getGender());
				System.out.println("[��ȭ��ȣ] " + item.getPhone());
				
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
		
		//foreach �Է� ��, ctrl + space �ϸ� for each ���� �ڵ��ϼ�
		for(PersonEntity item:persons) {
			System.out.println("[�̸�] " + item.getName() + "\t [����] " + item.getGender() + "\t [��ȭ��ȣ] " + item.getPhone());
		}
		// TODO Auto-generated method stub
		
	}

	public void fillPersons(PersonEntity[] persons) {
		// TODO Auto-generated method stub
		persons[0]=new PersonEntity("�̼�ȣ","7212121028102", "��õ ��籸", "032-392-2932");
		persons[1]=new PersonEntity("���ϴ�","7302132363217", "���� ������", "02-362-1932");
		persons[2]=new PersonEntity("�ڿ���","7503111233201", "���� ���ϱ�", "02-887-1542");
		persons[3]=new PersonEntity("���μ�","7312041038988", "���� ������", "032-384-2223");
		persons[4]=new PersonEntity("ȫ����","7606221021341", "���� ��õ��", "02-158-7333");
		persons[5]=new PersonEntity("�̹̼�","7502142021321", "���� ������", "02-323-1934");
		persons[6]=new PersonEntity("�ڼ���","7402061023101", "���� ���α�", "02-308-0932");
		persons[7]=new PersonEntity("������","7103282025101", "���� ����", "02-452-0939");
		persons[8]=new PersonEntity("Ȳ����","7806231031101", "��õ �߱�", "032-327-2202");
		persons[9]=new PersonEntity("��ö��","7601211025101", "��õ ��籸", "032-122-7832");
	}

}
