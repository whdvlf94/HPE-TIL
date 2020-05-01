package lambda;

public class UsingLambda {

	public static void main(String[] args) {

		// Thread 생성

		// 1.
		Thread t1 = new Thread(new MyRunnable());
		t1.setName("쓰레드");
		t1.start();

		// 2. Anonymous Inner Class 익명클래스 형태로
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		});
		t2.setName("Anonymous");
		t2.start();

		// 3. Lambda 식 형태로
		Thread t3 = new Thread(() -> System.out.println(Thread.currentThread().getName()));
		t3.setName("Lambda");
		t3.start();
	}

}

//고전적인 방법
//일회성으로 사용하는데 아래와 같이 클래스를 생성하는 것은 비효율적이다.
class MyRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
}
