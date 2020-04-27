# Spring Boot 





## 1. Repository 인터페이스 수정 - Optional 객체 반환



### 1.1) Lambda란?



#### Thread,Lambda

- **Thread 생성(고전)**

  ```java
  package lambda;
  public class UsingLambda {
  	public static void main(String[] args) {
  		
  		//Thread 생성
  		Thread t1 = new Thread(new MyRunnable());
  		t1.setName("쓰레드");
  		t1.start();		
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
  
  --------------------------
  쓰레드
  ```

- **Anonymous Inner Class (익명 클래스)**

  ```java
  package lambda;
  public class UsingLambda {
  	public static void main(String[] args) {
  
  		//Anonymous Inner Class 익명클래스 형태로
  		Thread t2 = new Thread(new Runnable() {
  			
  			@Override
  			public void run() {
  				System.out.println(Thread.currentThread().getName());
  			}
  		});
  		t2.setName("Anonymous");
  		t2.start();
  	}
  }
  --------------------------
  Anonymous
  ```

- **Lambda**

  ```java
  package lambda;
  public class UsingLambda {
  	public static void main(String[] args) {
  
  		//Lambda 식 형태로
  		Thread t3 = new Thread(()->System.out.println(Thread.currentThread().getName()));
  		t3.setName("Lambda");
  		t3.start();
  	}
  }
  --------------------------
  Lambda
  ```






### 1.2) Optional

- **Optional** 는 존재할 수도 있지만, 안할 수도 있는 객체. 즉, Null 이 될 수도 있는 객체를 감싸고 있는 일종의 wrapper 클래스이다.

  ```java
  import java.util.Optional; 
   
  public interface AccountRepository extends JpaRepository<Account, Long>{       
      Optional<Account> findByUsername(String username);
  }
  ```

  - **Repository 인터페이스 작성**
  -  **AccountRepository**의 구현체를 따로 작성하지 않아도 **Spring-Data-JPA가 자동적으로 해당 문자열 username에 대한 인수를 받아 자동적으로 DB Table과 매핑**한다.
  
  
  
- **AccountRepositoryTest.java**

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class AccountRepositoryTest {
  
  	@Autowired
  	AccountRepository repository;
  
  	@Test
  	public void findByUsername() throws Exception {
  	
  		Optional<Account> existOpt = repository.findByUsername("spring");
  		System.out.println(existOpt.isPresent()); // true
  		if (existOpt.isPresent()) {
  			Account exisAcct = existOpt.get();
  			System.out.println(exisAcct);
  		}
          // Supplier의 추상메서드 T get()
  		Account account = existOpt.orElseThrow(() -> new RuntimeException("존재하지 않는 username 입니다."));
  		System.out.println("존재하는 Account " + account);
  
  		Optional<Account> notExistOpt = repository.findByUsername("test2");
  		System.out.println(notExistOpt.isPresent()); // false
  
  		// orElseThrow()의 아규먼트 타입 : Supplier
  		// Supplier의 추상메서드 T get()
  		Account notExistAcct = notExistOpt.orElseThrow(() -> new RuntimeException("존재하지 않는 username 입니다."));
  		System.out.println(notExistAcct);
  		/*
  		 * NoSuchElementException이 발생 Account notExistAcct = notExistOpt.get();
  		 * System.out.println(notExistAcct);
  		 */
  	}
  }
  ```

  - **Repository test class** 작성을 통해 **레코드 저장 및 조회가 가능**하다.
  - **Optional\<T>** 클래스와 **Lambda** 메서드를 활용





### 1.3) JPA를 사용한 데이터베이스 초기화

- **validate 속성 테스트** : Account 클래스에 email 속성(**Column**)을 추가한다.

- `application.properties`에서 **spring.jap.hibernate.ddl-auto** 값을 **validate**로 변경하고 테스트를 실행

  - **테이블**과 **매핑하려는 엔티티**가 맞지 않기 때문에 에러 발생

  - 즉, validate는 테이블과 엔티티 간의 유효성을 검증한다.

    

#### 새로운 속성을 추가하는 방법

- **update 속성 테스트** :   **spring.jap.hibernate.ddl-auto** 값을 **update**로 변경하고 테스트를 실행하면 **새로 추가한 email 속성이 테이블에 반영**된 것을 확인할 수 있다.

