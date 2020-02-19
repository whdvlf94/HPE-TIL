# JAVA



### 1. 데이터 타입

---



- **기본 타입**
  - 정수 타입
    - byte, char, short, int, long
  - 실수 타입
    - float
  - 논리 타입
    - boolean



- **참조 타입**
  - 배열 타입, 열거 타입, 클래스, 인터페이스





### 2. 메모리 구조

---

> [참조](https://wanzargen.tistory.com/17)



- **Stack data**

  - 지역 변수(Local variable)와 매개 변수(Parameter)가 저장된다.
  - 프로그램 실행 과정에서 `임시로 할당`되고, 과정이 끝나면(`출력`) 소멸되는 것들이 저장된다.

  

- **Heap data(객체)**

  - 지역 변수가 참조하고 있는 실제 값들을 가지고 있다.
  - 힙 영역에 보관되는 메모리는 메소드 호출이 끝나도 사라지지 않고 유지된다.



**=> 힙 영역에는 실제 값이 저장되고, 스택 메모리에는 해당 주소만 저장이 된다.**



- **String**

```java
package com.example;

public class Example3 {
    public static void main(String[] args) {

        String name = "yjp";
        String hobby = "java";
        //stack , Heap data가 서로 다르다

        String name1 = "java";
        String name2 = "java";
        // stack : name1, name2 
        // 서로 다른 스택이지만 같은 Heap data를 공유한다.

        String newName1 = new String("YJP");
        String newName2 = new String("YJP");
        // new를 이용해서 String 데이터(Heap data)를 생성하면, literal 값이 같더라도
        // Heap data를 공유하지 않는다. 


        System.out.println(name == hobby);			//false
        System.out.println(name1 == name2);			//true
        System.out.println(newName1 == newName2);	//false
    }
}


```





- **String.equals()**

```java
package com.example.day2;

public class Example2 {

    public static void main(String[] args) {

        String hello = "Hello";
        String world = "World";
        String name = "Hello";
        String newName = new String("Hello");

        System.out.println(name + "/" + name.length());
        System.out.println(newName + "/" + newName.length());
        System.out.println(hello == name);
        System.out.println(name == newName);    
        // 서로 다른 힙 영역에 저장되어 있기 때문에 false
        System.out.println(name.equals(newName));
        //String class equals method
        // String.equals() : 주소 값(힙 영역)을 비교하는 것이 아니라, 문자열을 비교한다. 따라서 두 힙 데이터가 다름에도 불구하고 true 값이 출력된다.

    }
}

```





### 3. 배열

---



- `변수`는 `Stack`에 저장, `배열 값`은 `Heap`에 저장



- **배열 선언**

```java
package com.example.day2;

public class Array {

    public static void main(String[] args) {

		
        String[] names = {"A", "BB", "CCC", "DDDD", "EEEEE"};
        System.out.println(names.length);
        System.out.println(names[1]);
        names[4] = "JAVA"; // 배열 속성 값 재할당
        System.out.println(names[4]);
        
---------------------------------------------------        
[방법 2]
        String[] names = new String[] {"A", "BB", "CCC", "DDDD", "EEEEE"}
        scores = new int[]{1,2,3,4,5,6,7};
        
        //int[] scores;
        //scores = {1,2,3,4,5,6,7}
        // 배열 선언은 반드시 한줄로 입력해야 한다.
        
---------------------------------------------------
[방법 3]
		String[] animals = new String[5];
        animals[0] = new String("tiger");
        animals[1] = new String("lion");
        animals[2] = new String("rat");
        animals[3] = new String("dog");
        animals[4] = "cat";
		
        1)[For 함수]
            
        for (int i=0; i < animals.length; i++){
            System.out.println(animals[i]);
            
        2) [For each 함수]
		// 배열 내에 있는 속성 값들을 하나씩 출력
        // react 의 $.each와 동일한 기능
        // 인덱스 값이 존재 하지 않고, 배열을 순서대로만 출력 해 준다.
		for (String a : animals) {
            System.out.println(a);
        }
            
	}
}
```

**※ 배열 선언 방법 :  데이터 타입[ ] 변수명**	ex) int[] newArray



- **형 변환(Casting)**

```java
package com.example.day2;

public class Array {

    public static void main(String[] args) {

        double[] d = new double[5];

        d[0] = 0.0;             // double = double
        d[1] = 3.14f;           // double = float
        d[2] = 100;             // double = int
        d[3] = 2_200_000_000L;  // double = Long
        // int는 21억까지만 지원한다. 
        // 따라서 int 형태로 사용하고 싶으면 L(or l)을 써주면 된다.
        d[4] = 'A';             // double = character

        // 위의 경우 float, int, Long, character 가 모두 double(8byte) 보다 byte가 작거나 같기 때문에 오류가 발생하지 않는다.

        for (double _d : d) {
            System.out.println(_d);
        }

    }
}

```



- **String[] args**

```java
package com.example.day2;

public class Demo3 {
    public static void main(String[] args) {
        // void main => vm 이 실행시켜 주는 함수
        System.out.println(args.length);
        System.out.println(args[0]);
    }
}
---------------------------------------
0
Exception ~ (에러 발생)
```

- Terminal

```java
C:\Users\HPE\IdeaProjects\FirstProject\out\production\FirstProject>java com.example.day2.Demo3
----------------------------
0
Error!
    
C:\Users\HPE\IdeaProjects\FirstProject\out\production\FirstProject>java com.example.day2.Demo3 Hello World    
-----------------------------
2
Hello
```



**※ intelliJ에서 Terminal과 같이 실행하는 방법**

> Demo3 > Edit Configurations > Program arguments

![캡처](https://user-images.githubusercontent.com/58682321/74795855-622b3a80-530a-11ea-895f-ac3f53a7e6ed.PNG)





- **다차원 배열**

```java
package com.example.day2;

public class Demo3 {
    public static void main(String[] args) {
        int[][] scores = new int[2][3];
        // 2행 3열 배열 생성 => 2개 배열을 생성하고 그 안에 3개의 배열을 추가 생성
        // 다른 표현 방식
//      int[] scores[] = new int[2][3]
        System.out.println(scores.length);
        // 행(row) 의 길이 => 첫 번째 배열의 길이
        System.out.println(scores[0].length);
        System.out.println(scores[1].length);
        // scores[0] : scores 0번 째 배열의 길이 -> 3
    }
}

```



```java
package com.example.day2;

public class Demo3 {
    public static void main(String[] args) {
        int[][] scores = new int[2][3];
        // 2개의 배열을 생성하고, 그 안에 3개의 배열 생성 => 2행 3열

        scores[0] = new int[2];
        // 첫 번째 배열 안에 있는 배열을 2개의 배열로 수정
        scores[1] = new int[4];
        // 두 번째 배열 안에 있는 배열을 4개의 배열로 수정

        System.out.println(scores.length);		// 2
        System.out.println(scores[0].length);	// 2
        System.out.println(scores[1].length);	// 4
    }
}

```





- **예제)**

```java
package com.example.day2;

public class Demo3 {
    public static void main(String[] args) {
        int[][] scores = new int[2][];

        scores[0] = new int[2];
        scores[1] = new int[4];


        scores[0][0] = 100;
        scores[0][1] = 200;
//      scores[0][2] = 300;

        scores[1][0] = 400;
        scores[1][1] = 500;
        scores[1][2] = 600;
        scores[1][3] = 700;


---------------------------------------------------
        for( int i=0; i<scores.length; i++){
            for (int j=0; j<scores[i].length; j++) {

                System.out.print(
                        String.format("[%s][%s]=%s\t", i,j,scores[i][j]));
            }
            System.out.println();
        }

        
        
[0][0]=100	[0][1]=200	
[1][0]=400	[1][1]=500	[1][2]=600	[1][3]=700	
---------------------------------------------------
        for (int[] row : scores) {
            for(int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }
}


100	200	
400	500	600	700	
```







### 4. 배열 복사

---



- **arraycopy**

  ```
  arraycopy(Object src, int srcPos, Object  dest, int destPos, int length)
  ```



```java
package com.example.day2;

public class Demo4 {

    public static void main(String[] args) {

        String[] strArray = new String[3];
        // Stack                   heap
        // strArray    --->     [0][1][2]

        strArray[0] = "Java 1.8";
        strArray[1] = "Java 1.12";
        strArray[2] = new String("Java 1.13");

        String[] newArray = new String[3];
        System.arraycopy(strArray, 0, newArray, 0, strArray.length);
        for (String str: newArray) {
            System.out.println(str);
        }
    }
}

----------------------------------------------------
Java 1.8
Java 1.12
Java 1.13 
```





### 5. Class 구조

---

- Class 관계

  - 사용
  - 상속
  - 집합

  

```java
package com.example.day2;

public class Member {
    
    //[nested class]
    // class 내에 존재하는 class , 최상위 class만 public으로 선언
    class VipMember {
        
    }
    
    //[fields]
    int age = 10;
    String name = "Java";

    
    // [methods]
    
    1)
    //반환 값 x
    void 메소드이름() {
        
    }
    
    2)
    //반환 값 => 숫자 , 반환 값이 있는 경우 반드시 return을 작성해야 한다.
    int 메소드이름2() {
        return 1; 
    }
    
    
    // constructor method => 객체가 만들어 질 때, 단 한번 생성되는 메서드
}

```







#### 사용 관계

---

- **Public class , Public static **
  - public static은 접근 불가



- Member.java - 

```java
package com.example.day2;

// 외부에서 접근 가능
public class Member {
    String name;
    int age;

    void displayInfo() {
        System.out.println(name + ":" + age);
    }
}

```



- Demo5.java

```java
package com.example.day2;

public class Demo5 {
    public static void main(String[] args) {
        Member mem1 = new Member(); // 인스턴스 화 (mem1 -> 인스턴스, 객체)
        Member mem2 = new Member();

//        Member mem2; => 값이 설정되어 있지 않으면 인스턴스가 아니다.
        mem1.age = 10;
        mem1.name = "Java";
        mem1.displayInfo();

        mem2.age = 20;
        mem2.name = "C++";
        mem2.displayInfo();

    }
}
----------------------------------------
Java:10
C++:20
```

**※ Member 파일이 public class로 선언 되어 있기 때문에 외부에 접근해서 메서드를 통해 접근이 가능하다.**





#### 상속 관계

---

```java
package com.example.day2;

// 외부에서 접근 가능
public class Member {
    String name;
    int age;
    Project finalProject;
    // Member를 만들 때, 반드시 Project class가 필요하다.

    void displayInfo() {
        System.out.println(name + ":" + age);
    }
}
class Project {
    String name;
    String period;
    void budget () {

    }
}

```





#### 생성자 함수

---

> 초기 단계에서만 설정해주면 된다.



- Member

```java
package com.example.day2;

// 외부에서 접근 가능
public class Member {
    String name;
    int age;

    // 생성자 함수 : class 명과 동일
    Member() {
        name = "Java";
        age = 10;

    }
    void displayInfo() {
        System.out.println(name + ":" + age);
    }
}



```



- Demo5

```java
package com.example.day2;

public class Demo5 {
    public static void main(String[] args) {
        Member mem1 = new Member(); // 인스턴스 화 (mem1 -> 인스턴스, 객체)
        Member mem2 = new Member();
        
//        mem1.age = 10;
//        mem1.name = "Java";
        mem1.displayInfo();

        mem2.age = 20;
        mem2.name = "C++";
        mem2.displayInfo();


    }
}

-----------------------------
Java:10
C++:20
```





**※ 더 간단하게 나타내는 방법**

```java
[Member]
-------------------------------------------
package com.example.day2;

// 외부에서 접근 가능
public class Member {
    String name;
    int age;

    // 생성자 함수 : class 명과 동일
    Member(String name, int age) {
        this.name = name;
        this.age = age;
        //this => class 블록에 있는 변수를 의미
        // name 값을 class(this.name)에 넣겠다.
    }
    void displayInfo() {
        System.out.println(name + ":" + age);
    }
}


[Demo5]
-------------------------------------------

package com.example.day2;

import javax.xml.namespace.QName;

public class Demo5 {
    public static void main(String[] args) {
        Member mem1 = new Member("Java", 10); // 인스턴스 화 (mem1 -> 인스턴스, 객체)
        Member mem2 = new Member("C++", 20);

        mem1.displayInfo();

        mem2.displayInfo();


    }
}

```





- **Overloading - 생성자 메서드 추가**

>같은 클래스에서 메소드 이름은 같고, 파라미터 타입이나 개수가 다름

```java
[Member]
package com.example.day2;

// 외부에서 접근 가능
public class Member {
    String name;
    int age;
	
    
// Member 라는 동일한 이름의 생성자 함수 이지만, 그 함수 내용 값은 다르다
    
    Member(String name) {
        this.name = name;
    }

    // 생성자 함수 : class 명과 동일
    Member(String name, int age) {
        this.name = name;
        this.age = age;
        //this => class 에 블록에 있는 변수를 의미
        // Java 라는 값을 class(line 5)에 넣겠다.
    }
    void displayInfo() {
        System.out.println(name + ":" + age);
    }
}


[Demo5]

package com.example.day2;

import javax.xml.namespace.QName;

public class Demo5 {
    public static void main(String[] args) {
        Member mem1 = new Member("Java", 10); // 인스턴스 화 (mem1 -> 인스턴스, 객체)
        Member mem2 = new Member("C++", 20);
        Member mem3 = new Member("Python");


        mem1.displayInfo();
        mem2.displayInfo();
        mem3.displayInfo();


    }
}


```





#### 예제)

---

> 성적 구하기



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

    Student(String name, int kor, int eng, int mat) {
        this.name = name;
        this.kor = kor;
        this.eng = eng;
        this.mat = mat;

    }

    void cal() {
        this.sum = this.kor + this.eng + this.mat;
        this.avg = this.sum / 3.0f;
    }

    void display() {

        System.out.println(String.format("%s의 총점:%s, 평균:%.2f", this.name, this.sum, this.avg));
    }
}

```



- **Scores**

```java
package com.example.day2;

public class Scores {
    public static void main(String[] args) {
        Student stu1 = new Student("AAA", 100,90,50);
        Student stu2 = new Student("BBB", 90,80,80);
        Student stu3 = new Student("CCC", 100,100,80);

        stu1.cal(); stu1.display();
        stu2.cal(); stu2.display();
        stu3.cal(); stu3.display();

    }
}

```

