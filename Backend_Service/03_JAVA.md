# JAVA





### 1. 다른 생성자 호출 - this()

---



- `this()`를 이용하여 다른 생성자를 호출한다.
- `this()` 를 이용하지 않을 경우 생성자 함수의 매개 변수를 다시 호출하는 루프가 발생할 수 있다.





### 2. Method

---

> 반드시 객체 내에 포함되어 있어야 한다.



- `Method` 이름은 가급적이면 소문자로 작성 ( `class` 이름의 경우 대문자로 작성)

  ```
  
  리턴 타입 ( [매개변수 선언], ... ) {
  
  		실행할 코드 작성(메소드 실행 블록)
  
  }
  
  
  [리턴 타입 有]
  
  String getName() { ... }
  int[] getScores() { ... }
  => 리턴 타입을 배열로 지정한 것은 복수 개의 데이터를 얻어 온다는 것을 의미한다.
  
  
  [리턴 타입 無]
  
  void run() { ... }
  ```

  **※ 반드시 `리턴 타입`을 명시해야 한다. `리턴 타입`이 없는 경우 `void`로 시작해야 한다.**



- **예제)**

  ```java
  [Demo1]
  
  package com.example.day3;
  
  public class Demo1 {
      public static void main(String[] args) {
          Calculator calc = new Calculator();
          int result = calc.add(10,5);
          double result1 = calc.add(10,5);
          System.out.println(result);
          System.out.println(result1);
          
  		int x=20;
          int y=40;
          int result3 = calc.add1(x,y);
  
          System.out.printf("%s, %s, %s",x,y,result3);
          
      }
  }
  
  
  ----------------------------------------------
  [Calculator]
      
  package com.example.day3;
  
  public class Calculator {
  
      int add(int x, int y) { return x+y;}
    //float add(int x, int y) {return x+y;}
    //매개 변수는 동일하고, 리턴 타입이 다른 것은 오버로딩이 아니다. 반드시 매개 변수가 달라야 한다.
      
      int add1(int x, int y) {
      // int x, int y 는 add라는 메서드 안에서만 유효한 지역 변수이다.
      // 따라서 Demo1 에서 20으로 설정한 x 값은 변하지 않고, result 값만 바뀌게 된다.
      x = x*2;
      return x+y;}
      
      
      double add(double x, double y) { return x+y;}
  
      int subtract(int x, int y) {return x-y;}
  
      int multiply(int x, int y) {return x*y;}
  
      int divide(int x, int y) {return x/y;}
  
  }
      
  ----------------------------------------------
  15
  15.0    
  20, 40, 80
  ```



- **배열 메서드**

  ```java
  [Demo1]
  
  package com.example.day3;
  
  public class Demo1 {
      public static void main(String[] args) {
          Calculator calc = new Calculator();
          
  
          int[] sum = {100, 200, 300, 400};
          int result4 = calc.add(sum);
          System.out.println(result4);
          
          System.out.println(calc.add1(1,2,3));
          System.out.println(calc.add1(1,2,3,4));
          System.out.println(calc.add1(1,2,3,4,5));        
      }
  }
  --------------------------------------------------
  [Calculator]
      
  package com.example.day3;
  
  public class Calculator {
  
  
  
      int add (int[] values) {
          int result = 0;
          
          //for each 문장 활용
          for(int value: values) {
              result += value;
  
          }
          return result;
      }
      
      // int...values : 가변 데이터를 사용할 때 사용
      // 매개 변수 개수에 상관없이 사용할 수 있다.
      int add1 (int...values) {
          int result = 0;
          for(int value: values) {
              result += value;
  
          }
          return result;
      }    
  
  
  }
  --------------------------------------------------
  1000    
  6
  10
  15    
  ```





### 3. 수정 권한 Modifier

---



- **접근 권한 modifier** : private < X(default) < protected < public
  - **private** : 같은 class 에서만 사용 가능. 그 이외에는 접근 불가 ( 같은 class )
  - **X(default)** : 같은 패키지 내에 있으면 사용 가능 ( 같은 class + 같은 package )
  - **protected** : 상속 관계가 있는 경우에만 접근 가능 ( 같은 class + 같은 package + 다른 package(단, 상속 관계))
  - **public** : 외부 어디에서든 접근 가능  (같은 class + 같은 package + 다른 package )

---



- **ScoresApp**

> day3 / scoresApp

```java
package com.example.day3;

import com.example.day2.Student;
// 외부에 있는 파일을 가져다 쓰기위해 작성해야 한다.

public class ScoresApp {
    public static void main(String[] args) {
        Student stu1 = new Student("AAA", 100,90,50);
        
        stu1.cal();
        stu1.display();        

    }
}

```



- **Student**

> day2 / Student

```java
package com.example.day2;

public class Student {
    String name;
    int kor;
    int eng;
    int mat;
    int sum;
    float avg;


    // public : 외부 어디에서든 접근 가능  (같은 class + 같은 package + 다른 package )
    // 따라서, day3/ScoresApp 에서 사용 가능
    
        public Student(String name, int kor, int eng, int mat) {
        this.name = name;
        this.kor = kor;
        this.eng = eng;
        this.mat = mat;

    }

    public void cal() {
        this.sum = this.kor + this.eng + this.mat;
        this.avg = this.sum / 3.0f;
    }

    public void display() {

        System.out.println(String.format("%s의 총점:%s, 평균:%.2f", this.name, this.sum, this.avg));
    }
}


----------------------------------------------
AAA의 총점:240, 평균:80.00
```





#### 배열로 출력

---



- **ScoresApp**

  ```java
  package com.example.day3;
  
  import com.example.day2.Student;
  // 외부에 있는 파일을 가져다 쓰기위해 작성해야 한다.
  
  public class ScoresApp {
      public static void main(String[] args) {
  //        Student stu1 = new Student("AAA", 100,90,50);
  //        stu1.cal();
  //        stu1.display();
  
          Student[] students = new Student[]{
                  new Student("AAA", 100, 90, 50),
                  new Student("BBB", 100, 50, 50),
                  new Student("CCC", 100, 100, 100)
          };
  
          // 각 학생의 총점, 평균 구하기
          for (Student stu : students) {
              stu.cal();
          }
  
          // 각 학생 데이터 출력
          for (Student stu : students) {
              String msg = stu.display();
              System.out.println(msg);
  // 학생들의 성적이 저장되어 있는 파일(DB) 에서 display() 메서드를 호출하여 이 곳에서 출력될 수 있도록 한다.
          }
      }
  }
  
  ```

  

- **Student**

  ```java
  package com.example.day2;
  
  public class Student {
      String name;
      int kor;
      int eng;
      int mat;
      int sum;
      float avg;
  
  
      public Student(String name, int kor, int eng, int mat) {
          this.name = name;
          this.kor = kor;
          this.eng = eng;
          this.mat = mat;
  
      }
  
      public void cal() {
          this.sum = this.kor + this.eng + this.mat;
          this.avg = this.sum / 3.0f;
      }
  
      public String display() {
  
          return String.format("%s의 총점:%s, 평균:%.2f",
                  this.name, this.sum, this.avg);
         // 기존에는 display method 에서 출력이 되도록 설정 했다. 하지만 이 경우 학생들의 성적 값이 노출 되는 문제가 발생할 수 있다.
         // 따라서, 학생들의 성적이 저장되어 있는 scoresApp에서 출력될 수 있도록 수정한다.
      }
  }
  
  ```

  

  **※ 더 간단하게 나타내는 방법**

  ```java
  [ScoresApp]
  
  public class ScoresApp {
      public static void main(String[] args) {
  
          // 각 학생 데이터 출력
          for (Student stu : students) {
  //            String msg = stu.display();
  //            System.out.println(msg);
              
              System.out.println(stu);
              //println(stu.toString()) 과 같은 의미
             // toString()는 default로 되어 있기 때문에, 설정하지 않으면 자동으로 적용 
          }
      }
  --------------------------------------
  [Student]
      
  @Override
  // override를 선언하게 되면 toString을 재정의 한 것을 의미
  // 또한 에러가 발생할 경우 가르쳐 준다.
  public String toString() {
  	return String.format("%s의 총점:%s, 평균:%.2f", this.name, 			
  			this.sum,this.avg);
      }
  }
  
  ```

  

#### 재정의

---



- **ScoresApp**

```java
[ScoresApp]

public class ScoresApp {
    public static void main(String[] args) {

        Student[] students = new Student[]{
                new Student("AAA", 100, 90, 50),
                new Student("BBB", 100, 50, 50),
                new Student("CCC", 100, 100, 100)
        };

System.out.println(students[0] == students[1]);
// 두 값의 주소 값을 비교하는 것이기 때문에 false로 출력된다.        
        
System.out.println(students[0].equals(students[1]));
// Student 파일에서 equals를 재정의(@override)하지 않았다면, 그 값은 false로 출력된다.
// students[0], students[1]의 kor 값이 100으로 동일하기 때문에, 재정의한 equals에 의해서 true로 출력된다.

    }
}

---------------
false    
true   
```



- **Student**

```java
    @Override
    public boolean equals(Object obj) {
        Student temp = (Student)obj;
        return  this.kor ==temp.kor;
        // equals 재정의
        // kor 값이 같으면 true로 출력
    }
```



#### Sort / getName / setName

---



- **ScoresApp**

  ```java
  package com.example.day3;
  
  import com.example.day2.Student;
  
  import java.util.Arrays;
  
  
  public class ScoresApp {
      public static void main(String[] args) {
  
  
          Student[] students = new Student[]{
                  new Student("AAA", 100, 90, 50),
                  new Student("BBB", 90, 50, 50),
                  new Student("CCC", 80, 100, 100)
          };
  
          // 각 학생의 총점, 평균 구하기
          for (Student stu : students) {
              stu.cal();
          }
  
          System.out.println("--------------before sorting");
  
          // 각 학생 데이터 출력
          for (Student stu : students) {
  
              System.out.println(stu);
              //println(stu.toString()) 과 같은 의미
          }
  
          Arrays.sort(students, new MyComparator());
  
          System.out.println("--------------after sorting");
  
          // 각 학생 데이터 출력
          for (Student stu : students) {
              System.out.println(stu);
              //println(stu.toString()) 과 같은 의미
          }
  
      }
  }
  ---------------------------------------------------
      
  --------------before sorting
  AAA의 총점:240, 평균:80.00     
  BBB의 총점:290, 평균:96.67    
  CCC의 총점:260, 평균:86.00    
  --------------after sorting    
  CCC의 총점:260, 평균:86.00
  BBB의 총점:290, 평균:96.67
  AAA의 총점:240, 평균:80.00    
  ```

  **※ 단순히 이름의 오름 차순으로만 정렬한다.**



- **MyComparator - Sort**

  ```java
  package com.example.day3;
  
  import com.example.day2.Student;
  
  import java.util.Comparator;
  
  public class MyComparator implements Comparator<Student> {
      // 정렬 상태를 도와주는 class
  
      @Override
      public int compare(Student s1, Student s2) {
  // s1 == s2 : 0 / s1 > s2 : 1 / s1 < s2 : -1
          
  //        if (s1.getKor() > s2.getKor()) {
  //            return 1;
  //        } else if (s1.getKor() < s2.getKor()) {
  //            return -1;
  //        } else {
  //            return 0;
  //        }
          
          //위의 표현을 아래와 같이 더 간단하게 나타낼 수 있다.
          //sorting 과정
          return s2.getName().compareTo(s1.getName());
  		// 알파벳의 오름차순으로 정렬        
  
      }
  
  }
  
  ```
  
  

- **Student - getName, setName**

  ```java
  package com.example.day2;
  
  public class Student {
      private String name;
      private int kor, eng, mat, sum;
      private float avg;
  
  // 정보 보호 기능
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public int getKor() {
          return kor;
      }
  
      public void setKor(int kor) {
          this.kor = kor;
      }
  
      public int getEng() {
          return eng;
      }
  
      public void setEng(int eng) {
          this.eng = eng;
      }
  
      public int getMat() {
          return mat;
      }
  
      public void setMat(int mat) {
          this.mat = mat;
      }
  
      public int getSum() {
          return sum;
      }
  
      public void setSum(int sum) {
          this.sum = sum;
      }
  
      public float getAvg() {
          return avg;
      }
  
      public void setAvg(float avg) {
          this.avg = avg;
      }
  
  
  
      public Student(String name, int kor, int eng, int mat) {
          this.name = name;
          this.kor = kor;
          this.eng = eng;
          this.mat = mat;
  
      }
  
      public void cal() {
          this.sum = this.kor + this.eng + this.mat;
          this.avg = this.sum / 3.0f;
      }
  
  
  
      public String toString() {
          return String.format("%s의 총점:%s, 평균:%.2f",
                  this.name, this.sum, this.avg);
      }
  }
  ```

  





#### Comparator ↔ Comparable

---

**※ 응용하기**

`총점`의 `내림 차순` 순으로 정렬하고, 총점이 같은 경우 `이름`의 `오름 차순`으로 정렬하기



**1) Comparator**



- **ScoresApp.java**	=>    `Arrays.sort(students, new MyComparator());`
  - Comparator의 경우 해당 파일(Mycomparator)에서 `compareTo` 메서드를 작성해서 호출해야 한다.



- **MyComparator**

  ```JAVA
  package com.example.day3;
  
  import com.example.day2.Student;
  
  import java.util.Comparator;
  
  public class MyComparator implements Comparator<Student> {
      // 정렬 상태를 도와주는 class
  
      @Override
      public int compare(Student s1, Student s2) {
          if (s2.getSum() > s1.getSum()) {
              return 1;
          } else if (s2.getSum() < s1.getSum()) {
              return  -1;
          } else {
              return s1.getName().compareTo(s2.getName());
          }
  
      }
  
  }
  
  
  ----------------------------------------------------
      
  --------------before sorting
  AAA의 총점:240, 평균:80.00
  BBB의 총점:300, 평균:100.00
  CCC의 총점:290, 평균:96.67
  DDD의 총점:210, 평균:70.00
  EEE의 총점:290, 평균:96.67
  --------------after sorting
  BBB의 총점:300, 평균:100.00
  CCC의 총점:290, 평균:96.67
  EEE의 총점:290, 평균:96.67
  AAA의 총점:240, 평균:80.00
  DDD의 총점:210, 평균:70.00
  ```



**2) Comparable**



- **ScoresApp.java**	=>    `Arrays.sort(students);`
  - comparable의 경우 `Student` 에 `compareTo()` 메서드를 작성하기 때문에 `Arrays.sort(students)`에 `new MyComparator`를 추가하지 않아도 된다.



- **Student**

  ```java
  public class Student implements Comparable <Student> {
      
      @Override
      public int compareTo(Student s2) {
          if (s2.getSum() > this.getSum()) {
              return 1;
          } else if (s2.getSum() < this.getSum()) {
              return -1;
          } else {
              return this.getName().compareTo(s2.getName());
          }
      }
  }    
  ```





### 4. 인스턴스

---



- **Demo3**

  ```java
  package com.example.day3;
  
  
  public class Demo3 {
      public static void main(String[] args) {
          Counter c1 = new Counter();
          // c1 이라는 인스턴스 생성, 정수형의 count(매개 변수)가 저장되어 있다.
          c1.count += 1;
          c1.count += 1;
          c1.count += 1;
          System.out.println(c1.count);
  
          Counter c2 = new Counter();
          c2.count += 2;
          c2.count += 2;
          c2.count += 2;
          System.out.println(c2.count);
          System.out.println(c1.count);
  
      }
  
  }
  --------------------------
  3
  9
  9     
  ```

  

- **Counter**

  ```java
  package com.example.day3;
  
  public class Counter {
     static int count;
  }
  
  ```



**=> `static int count`로 설정했기 때문에, 변수 명이 달라지더라도 그 값은 누적되어 사용된다. 즉, 인스턴스를 따로 생성할 필요가 없다.**



**※ 인스턴스 생성하지 않고 사용하는 방법**

- **Demo3**

  ```java
  package com.example.day3;
  
  
  public class Demo3 {
      public static void main(String[] args) {
  
          Counter.addCount();
          Counter.addCount();
          Counter.addCount();
  
          Counter c1 = new Counter();
          System.out.println(c1.getCount());
          System.out.println(Counter.getCount());
          // count를 private로 설정해 두었기 때문에 직접 이용하여 쓸 수 없다.
          // count 값의 수정이나 호출하기 위해서는 addCount(), getCount()를 이용해야 한다.
  
      }
  
  }
  
  ```

  

- **Counter**

  ```java
  package com.example.day3;
  
  public class Counter {
      // 보안을 위해 private로 설정 해두는 것이 좋다.
      private static int count;
      public static void addCount() {
          count ++;
      }
  
      public  static int getCount() {
          return count;
      }
  
  }
  
  ```

  



#### Singleton - getInstance()

---



- **Demo4**

  ```java
  package com.example.day3;
  
  public class Demo4 {
  
      private static Demo4 obj = new Demo4();
      //private static으로 obj(인스턴스 명)를 만든다.
      //private는 같은 class 내에서는 인스턴스를 생성할 수 있다.
      //외부에서 Demo4.getInstance 명령 실행 시, 인스턴스 생성과 동시에 그 값을 리턴해준다.
      
      private Demo4() {}
      // private 생성자로 외부에서 생성을 막는다.
      // 즉, getInstance로만 접근할 수 있다.
      
      public static synchronized Demo4 getInstance() {
  
              return obj;
      }
  
  }
  
  ```

  **※ 생성자를 `private`로 선언해서 생성 불가하게 하고, `getInstance()`로 받아서 쓰도록 함**

  

- **SingletonDemo**

  ```java
  package com.example.day3;
  
  import java.util.Calendar;
  
  public class singletonDemo {
      public static void main(String[] args) {
  //Demo4 obj1 = new Demo4();
  //private로 생성자 함수를 만들었기 때문에 위와 같은 new 연산자에 제약이 생긴다.
          Demo4 obj1 = Demo4.getInstance();
          Demo4 obj2 = Demo4.getInstance();
          System.out.println(obj1);
          System.out.println(obj2);
          System.out.println(obj1 == obj2);
  
      }
  }
  
  ```



**Singleton => 인스턴스를 요청(getInstance)할 때 마다 새로 생성하는 것이 아닌, 최초에 한번 실행하면 그 인스턴스를 반복해서 재사용하는 것을 의미한다.**



#### final

---

> 상수라고도 불리며, 변수 선언과 동시에 초기화하며 이후에 값을 수정할 수 없다.



- **Demo5**

  ```java
  package com.example.day3;
  
  public class Demo5 {
      final float point = 0.3f;
      // final : 한번 고정된 값은 수정하지 못하도록
  	
      point = 0.4f;
      // 에러 발생
      
      public static void main(String[] args) {
  
          int price = 1000;
          System.out.println("POINT" + (price * Constants.POINT));
  
          System.out.println(Math.max(10,20));
  
  
      }
  }
  
  ```

  

- **Constants**

  ```java
  package com.example.day3;
  
  public class Constants {
      public static final int POINT = 3;
      public static final String COMPANY  = "JAVA";
      public static final String OS = "Windows 10";
  
  }
  
  ```

  