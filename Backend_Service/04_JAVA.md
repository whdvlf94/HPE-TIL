# JAVA 





### 1. 상속관계

---

> Overriding



![Untitled Diagram (3)](https://user-images.githubusercontent.com/58682321/75003442-12d04000-54ab-11ea-99aa-7ef9d4cb2034.png)



#### Parent

---

- **CommonCar **

  ```java
  package com.example.day4;
  
  public class CommonCar {
      protected String brandName;
      protected String carName;
      protected Engine engine;
      // 상속 관계에 있는 class 간 호출이 가능하다.
  
      public CommonCar() {
          System.out.println("Parent class");
          this.brandName = "Hyundai(현대자동차)";
          this.engine = new Engine();
  
  
      }
  }
  
  ```



- **Engine**

  ```java
  package com.example.day4;
  
  public class Engine {
      public Engine() {
          System.out.println("좋은 엔진 입니다.");
      }
  }
  ```



#### Child

---

- **Car**

  ```java
  package com.example.day4;
  
  public class Car extends CommonCar {
  //상속 관계: extends CommonCar를 통해 brandName, carName, enging을 하나의 class 에서 관리
      private int doorCount;
  
  
      public Car(String carName, int doorCount) {
          System.out.println("Child class");
          this.carName = carName;
          this.doorCount = doorCount;
      }
  
      public void displayInfo() {
          System.out.printf("%s, %s(도어 수: %s)\n", brandName, carName, doorCount);
      }
  }
  
  ```

  

- **SportsCar**

  ```java
  package com.example.day4;
  
  public class SportsCar extends CommonCar {
  //상속 관계: extends CommonCar를 통해 brandName, carName, enging을 하나의 class 에서 관리    
  
      private int doorCount;
      private int maxspeed;
  
      public SportsCar(String carName, int doorCount) {
          System.out.println("Child class");
          this.carName = carName;
          this.doorCount = doorCount;
          this .maxspeed = 300;
      }
  
      public void displayInfo() {
          System.out.printf("%s, %s(도어 수: %s), 최고 속도:%s km/h\n", brandName, carName, doorCount, maxspeed);
      }
  }
  
  ```



- **Bus**

  ```java
  package com.example.day4;
  
  public class Bus extends CommonCar {
      private int capacity;
  
      public Bus(String carName, int capacity) {
          this.carName = carName;
          this.capacity = capacity;
      }
  
      public void displayInfo() {
          System.out.println("Child class");
          System.out.printf("%s, %s(승객 수: %s)\n", brandName, carName, capacity);
      }
  
  }
  
  ```



#### Display

---

- **CarFactory**

  ```java
  package com.example.day4;
  
  public class CarFactory {
      public static void main(String[] args) {
          Car car1 = new Car("쏘나타 YF", 4);
          car1.displayInfo();
  
          Bus car2 = new Bus("Bus",40);
          car2.displayInfo();
  
          SportsCar car3 = new SportsCar("SP One",2);
          car3.displayInfo();
  
  
      }
  }
  ------------------------------------------------------
  Parent class
  좋은 엔진 입니다.
  Child class
  Hyundai(현대자동차), 쏘나타 YF(도어수 4)
  
  Parent class
  좋은 엔진 입니다.
  Child class
  Hyundai(현대자동차), Bus(승객 수: 40)
  
  Parent class
  좋은 엔진 입니다.
  Child class
  Hyundai(현대자동차), SP One(도어수 2), 최고 속도:300 km/h    
  ```







#### 상속 관계 예제

---



#### Parent

---

- **Member**

  ```java
  package com.example.day4;
  
  public abstract class Member {
      protected String id;
      protected String grade;
      protected double point;
  
      public abstract void setPoint(int point);
  
      public abstract boolean isAuthorized();
  
      public abstract void display();
      
      //body 내용들은 각 Child class에 포함되어 있다.
  
  }
  
  ```

  



#### Child

---

- **GeneralMember**

  ```java
  package com.example.day4;
  
  public class GeneralMember extends Member {
      public GeneralMember(String id, String grade) {
          this.id = id;
          this.grade =grade;
      }
  
      @Override
      public void setPoint(int point) {
          this.point = point * 0.3;
      }
  
      @Override
      public boolean isAuthorized() {
          return false;
      }
  
      @Override
      public void display() {
          System.out.printf("%s, %s, %s\n", super.id, super.grade, this.point);
      }
  }
  ```

  

- **VipMember**

  ```java
  package com.example.day4;
  
  public class VipMember extends Member {
      public VipMember(String id, String grade) {
          this.id = id;
          this.grade = grade;
      }
      @Override
      public void setPoint(int point) {
          this.point = point * 0.5;
      }
  
      @Override
      public boolean isAuthorized() {
          return true;
      }
  
      @Override
      public void display() {
          System.out.println("**************************");
          System.out.printf("%s, %s, %s\n", super.id, super.grade, this.point);
      }
  }
  ```

  

- **VvipMember**

  ```java
  package com.example.day4;
  
  public class VvipMember extends Member {
      VvipMember(String id) {
          this.id = id;
          this.grade = "V1";
      }
  
      @Override
      public void setPoint(int point) {
          this.point = point * 0.7;
      }
  
      @Override
      public boolean isAuthorized() {
          return true;
      }
  
      @Override
      public void display() {
          System.out.println("#############################");
          System.out.printf("%s, %s, %s\n", super.id, super.grade, this.point);
          System.out.println("#############################");
      }
  }
  ```

  

#### Display

---

- **MemberApp**

  ```java
  package com.example.day4;
  
  public class MemberApp {
      public static void main(String[] args) {
          // Abstract class는  instance 생성 못함
          GeneralMember mem1 = new GeneralMember("user1", "A");
          mem1.setPoint(100);mem1.display();
  
          VipMember mem2 = new VipMember("vip1", "S1");
          mem2.setPoint(100);mem2.display();
  
          VvipMember mem3 = new VvipMember("vvip1");
          mem3.setPoint(100);mem3.display();
      }
  }
  ```







#### 상속 관계 응용 - IMemberFunc

---



#### Parent

- **Member**

  ```java
  package com.example.day4;
  
  import java.util.Date;
  
  
  public class Member {
       String id;
       String grade;
       double point;
       Date joinDate;
      // joinDate 새로 추가
      // Child class에도 새롭게 추가해주어야 한다.
  
  
  }
  
  ```

  **※ 모든 추상 메소드를 `Member class`와 분리하였다.**



- **IMemberFunc**

  ```java
  package com.example.day4;
  
  // 정상 메소드 -> public void add() {...}
  // 추상 메소드 -> public void add(); body(x)
  // abstract class 내의 메소드가 모두 Abstract 메소드 이면?
  // -> interface (추상 메소드 , 약속)으로 나타낼 수 있다.
  
  public interface IMemberFunc {
  // interface로 선언했기 때문에 public abstract를 선언하지 않아도 된다.
  
       void setPoint(int point);
       boolean isAuthorized();
       void display();
  
  }
  
  ```





#### Child

- **GeneralMember**

  ```java
  package com.example.day4;
  
  import java.util.Date;
  
  public class GeneralMember extends Member implements IMemberFunc {
  // Class의 상속 : extends , Instance 상속 : implements
      public GeneralMember(String id, String grade) {
          this.id = id;
          this.grade =grade;
          this.joinDate = new Date();
      }
  
      @Override
      public void setPoint(int point) {
          this.point = point * 0.3;
      }
  
      @Override
      public boolean isAuthorized() {
          return false;
      }
  
      @Override
      public void display() {
          System.out.printf("%s, %s, %s, %s\n", super.id, super.grade, this.point, super.joinDate);
          // point 빼고, 모두 Member(Parent)에 있는 값을 호출하기 때문에 super로 작성해준다.
      }
  }
  ```

  **※ VipMember, VvipMember 도 위와 동일하게 적용**





#### Display

- **MemberApp2**

  ```java
  package com.example.day4;
  
  public class MemberApp2 {
      public static void main(String[] args) {
          // 5명의 member를 추가 (member x2 , vip x 2, vvip x 1)
          GeneralMember mem1 = new GeneralMember("user1", "A");
          GeneralMember mem2 = new GeneralMember("user2", "B");
          VipMember vip1 = new VipMember("vip1", "S1");
          VipMember vip2 = new VipMember("vip2", "S2");
          VvipMember vvip1 = new VvipMember("vvip1");
  
          //Array
  
          // 부모의 reference 변수는 자식의 인스턴스를 가르킬 수 있다.
          // Member m1 = mem1;
          // Member = new GeneralMember("user1", "A")
  
          IMemberFunc[] members = new IMemberFunc[5];
          members[0] = mem1;
          members[1] = mem2;
          members[2] = vip1;
          members[3] = vip2;
          members[4] = vvip1;
  
  System.out.println("---------------For each로 출력하기-----------------");
  
          for (IMemberFunc member : members) {
  //            System.out.println(member.id + "/" + member.joinDate);
              member.setPoint(100);
              ;
              member.display();
          }
  
          //객체로 실행해 보기
          Object[] obj = new Object[3];
          obj[0] = new GeneralMember("user3", "A");
          obj[1] = new VipMember("vip3", "B");
          obj[2] = new VvipMember("vvip3");
  
  System.out.println("---------------Object로 출력하기-----------------");
  
          for (Object o : obj) {
              //casting 작업
              // 각 형태에 맞게 변경해주어야 한다. 아래와 같이 실행하면 vip3과 vvip3이 실행되지 않는다.
              // ex) VipMember a = (VipMember)o;
  
              IMemberFunc member = null;
              if (o instanceof GeneralMember) {
                  member = (GeneralMember) o;
              } else if (o instanceof VipMember) {
                  member = (VipMember) o;
              } else if (o instanceof VvipMember) {
                  member = (VvipMember) o;
              }
              member.setPoint(200);
              member.display();
  
          }
      }
  }
  
  
  -----------------------------------------------------------
      
  ---------------For each로 출력하기-----------------
  user1, A, 30.0, Fri Feb 21 14:59:10 KST 2020
  user2, B, 30.0, Fri Feb 21 14:59:10 KST 2020
  vip1, S1, 50.0, Fri Feb 21 14:59:10 KST 2020
  vip2, S2, 50.0, Fri Feb 21 14:59:10 KST 2020
  vvip1, V1, 70.0, Fri Feb 21 14:59:10 KST 2020
  ---------------Object로 출력하기-----------------
  user3, A, 60.0, Fri Feb 21 14:59:10 KST 2020
  vip3, B, 100.0, Fri Feb 21 14:59:10 KST 2020
  vvip3, V1, 140.0, Fri Feb 21 14:59:10 KST 2020
  ```

  