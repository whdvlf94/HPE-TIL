package lambda;

public class UsingLambda {

	public static void main(String[] args) {

		// Thread ����

		// 1.
		Thread t1 = new Thread(new MyRunnable());
		t1.setName("������");
		t1.start();

		// 2. Anonymous Inner Class �͸�Ŭ���� ���·�
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		});
		t2.setName("Anonymous");
		t2.start();

		// 3. Lambda �� ���·�
		Thread t3 = new Thread(() -> System.out.println(Thread.currentThread().getName()));
		t3.setName("Lambda");
		t3.start();
	}

}

//�������� ���
//��ȸ������ ����ϴµ� �Ʒ��� ���� Ŭ������ �����ϴ� ���� ��ȿ�����̴�.
class MyRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
}
