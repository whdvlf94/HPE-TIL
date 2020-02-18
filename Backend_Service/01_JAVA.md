# JAVA

> Intellij



[java api document](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/io/PrintStream.html)



- **Class 구조**
  - Nested Class(class 내에 존재하는 calss)
  - Field
  - Method



- **Overloading **
  - 같은 Class 내에서 다양한 방식으로 사용되는 Method





#### 실습)

---



### 1. 문자열

---



```java
package com.example;

import java.lang.System;
// React : import System from 'java.lang'

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
//      java에서는 문자열에 ''을 지원하지 않는다. 반드시 "" 로 작성

        int hour = 3;
        int minute = 5;

        System.out.println("지금은 " + hour + "시 " + minute + "분 입니다.");
        // 아래 방법을 더 많이 사용
        System.out.println(
                String.format("지금은 %s시 %s분 입니다", hour, minute ));

        System.out.println(1+2+3);          // 6
        System.out.println(1+2+ "=" + 3);   // 3=+3 -> 3=3
        System.out.println("문제:"+1+2+3);  // 문졔:123 -> 문자열 + 숫자 => 문자열

        // 숫자를 문자열로 바꾸는 방법
        System.out.println(String.valueOf(1));
        System.out.println(""+1);

    };
}


```



```java
package com.example;

import java.lang.System;

public class HelloWorld {
    public static void main(String[] args) {

//        \", \', \n, \t
        System.out.println("나는 \"자바\"를 좋아합니다.");
        System.out.println("나는 \n자바를 좋아합니다.");	//Enter
        System.out.println("나는 \t자바를 좋아합니다.");	//Tap
		System.out.println("나는 \u00A3자바를 좋아합니다."); //unicode


    };
}


-------------------------
    
나는 "자바"를 좋아합니다.
나는 
자바를 좋아합니다.
나는 	자바를 좋아합니다.
나는 £자바를 좋아합니다.
```





### 2. Casting

---

>  큰 허용 범위 타입을 작은 허용 범위 타입으로 강제로 나누어 한 조각만 저장



- **실수 타입**

```java
package com.example;

import java.lang.System;

public class HelloWorld {
    public static void main(String[] args) {

    byte a = 10;

    // float pi (4 byte) = 3.14(8 byte) -> double
    // 해결 방법
    double pi = 3.14;
    float pi2 = 3.14f; // f: double -> float
    
    float pi3 = (float)3.14;
    // 3.14라는 8 byte data를 4 byte로 변환하겠다.
    // 위와 같은 방법은 data에 오류가 발생할 수 있다.

    Parent P = new Parent();

    System.out.println(a * a * pi);

    };
}
```

**※ float(4 byte) , double(8 byte)**



```java
package com.example;

import java.lang.System;

public class HelloWorld {
    public static void main(String[] args) {

        int intValue = 65;
        char charValue = (char)intValue;
        System.out.println(charValue);
        // 65가 문자열로 변환되면서 A 출력 (A의 ASCII CODE 값 65)

        double doubleValue = 3.14;
        int intValue1 = (int) doubleValue;
        System.out.println(intValue1);
        // 소수점 데이터가 정수형으로 강제로 형 변환
    };
}
```





- **String -> int**

> Integer.parseInt()

```java
package com.example;

        import java.awt.datatransfer.StringSelection;
        import java.lang.System;

public class HelloWorld {
    public static void main(String[] args) {

        String str = "30000";
        int value = Integer.parseInt(str);
        System.out.println(1 + str); // 130000
        System.out.println(1 + value); // 30001
    };
}
```



- **String -> Float**

> Float.parseFloat()

```java
package com.example;

        import java.awt.datatransfer.StringSelection;
        import java.lang.System;

public class HelloWorld {
    public static void main(String[] args) {

        String score = "85.5";
        float total = 0;

        total += Float.parseFloat(score);
        System.out.println(total);
    };
}


```





### 3. Scanner

---

> 입력 시 사용하면 명령어



```java
package com.example;

        import java.awt.datatransfer.StringSelection;
        import java.lang.System;
        import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Scanner s = new Scanner(System.in);

        
        System.out.print("국어점수=");  
        // print : println과 달리 새로운 line을 개행하지 않는다.
        
        String kor = s.next();  //사용자로부터 값을 입력 받을 수 있다.
//      위의 문장이 실행 되어야 그 다음 명령어들이 수행된다.

        System.out.println(kor);
    };
}

```

**※ System.in은 1byte 밖에 읽지 못하기 때문에 입력한 데이터를 모두 읽을 수 있는 Scanner ㄱ체 안에 매개 변수로써 사용한다.** 



- **예제)**

```java
package com.example;

        import java.awt.datatransfer.StringSelection;
        import java.lang.System;
        import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Scanner s = new Scanner(System.in);
        System.out.print("국어점수=");
        String strkor = s.next();
        System.out.print("영어점수=");
        String streng = s.next();
        System.out.print("수학점수=");
        String strmat = s.next();

        int kor = Integer.parseInt(strkor);
        int eng = Integer.parseInt(streng);
        int mat = Integer.parseInt(strmat);
        int total = kor + eng + mat ;
        float avg = total/3;

        System.out.println("총점 =" + total);
        System.out.println("평균 =" + avg);
        
		String result = String.format("총점 %s, 평균 %s", total, avg);
        String result2 = String.format("총점 %s, 평균 %.2f", total, avg);
        System.out.println(result);
        System.out.println(result2);
        
        
        
    };
}
---------------------------------------------------
Hello World!
국어점수=100
영어점수=90
수학점수=90
    
총점 =280
평균 =93.0

[result]
총점 280, 평균 93.333336

[result2]
총점 280, 평균 93.33
```



**※ 280/3 은 나누어 떨어지지 않는데, 평균이 93.0 으로 계산되는 문제**

**=> `total/3.0f`로 바꾸어 입력하면 소수점까지 계산되어 출력된다.**



- **s.nextInt()**

```java
Scanner s = new Scanner(System.in);
System.out.print("국어점수=");
String strkor = s.next();

int kor = Integer.parseInt(strkor);


---------------------------------------
    
Scanner s = new Scanner(System.in);
System.out.print("국어점수=");
int kor = s.nextInt();


// s.nextInt()를 사용하면, 더 간단하게 나타낼 수 있다.
```





### 4. 연산자

---



- **Example2**

```java
package com.example;


    public class Example2 {
        public  static  void  main(String[] args) {
            int a =1;
            int b =2;
            int c = a++ + ++b;  // c = 1 + 3 = 4, a = 2 , b = 3
            System.out.println(a + "," + a++ +
                                ", " + ++b +
                                "," + c);


        }
    }

------------------------------
2,2, 4,4

```



- **instanceof**

```
a instanceof String
=> a 가 String 형식인가?
------------------
true(or false)
```



```java
package com.example;


    public class Example2 {
        public  static  void  main(String[] args) {
            int a =1;
            int b =2;
            int c = a++ + ++b;  // c = 1 + 3 = 4, a = 2 , b = 3
            System.out.println(a + "," + a++ +
                                ", " + ++b +
                                "," + c);
            String name = "YJP";
            System.out.println(name instanceof String);
            System.out.println(name instanceof Object);
            // Object 는 모든 클래스를 포함하고 있는 최상위 클래스 이다.
            // 따라서 String type 역시 Object의 일부이다.
        }
    }


```



```java
package com.example;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Example2 {
        public  static  void  main(String[] args) {

            List list1 = new ArrayList();
            Collection list2 = new ArrayList();

            System.out.println(list1 instanceof List);
            System.out.println(list1 instanceof Collection);

            System.out.println(list2 instanceof List);
            System.out.println(list2 instanceof Collection);

        }
    }
--------------------------------------
true
true
true
true

```



![](https://t1.daumcdn.net/cfile/tistory/2614AF3655269C1129)

​																				[출처](https://hackersstudy.tistory.com/26)







- **currentTimeMillis ↔ StringBuilder**

```java
package com.example;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Example2 {
        public  static  void  main(String[] args) {
            
           // [currentTimeMillis()]
            long startTime = System.currentTimeMillis();

            String str = "A";
            // String str = new String("A")로 표기할 수 도 있음
            
            for (int i=0; i < 100_000; i++) {
                str += "A";
            }
            long endTime = System.currentTimeMillis();

            System.out.println("Elapsed time:" + (endTime - startTime));

            startTime = System.currentTimeMillis();

            
			// [StringBuilder()]
            StringBuilder sb = new StringBuilder("A");
            for (int i=0; i < 1_000_000; i++) {
                sb.append("A");
            }

            endTime = System.currentTimeMillis();

            System.out.println("\n(StirngBuilder)Elapsed time:" + (endTime - startTime));
        }
    }
-------------------------------------
    
Elapsed time:1154

(StirngBuilder)Elapsed time:22
    
// Stirngbuilder의 속도가 월등히 빠르다
```





### 5. 조건문 반복문

---



- **Switch case**

```java
package com.example;


import com.sun.jdi.IntegerValue;

import java.text.BreakIterator;
import java.util.Scanner;

public class Example2 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.print("국어 점수=");
        int kor = s.nextInt();
        System.out.print("영어 점수=");
        int eng = s.nextInt();
        System.out.print("수학 점수=");
        int mat = s.nextInt();

        int total = kor + eng + mat;
        float avg = total/3.0f;

        System.out.println("총점 =" + total);
        System.out.println("평균 =" + avg);
			
		// switch는 정수형 byte, short, int, char, String 형태만 올 수 있다.
        // avg의 경우 float 이므로 (int)avg/10 와 같이 변환하여 사용해야 한다.
        // (int)avg/10 => 평균의 첫 째 자리를 정수로 바꿔 계산하는 방법
        switch ((int)avg/10) {
            case 10:
            case 9:
                System.out.println("A");
                break;
            case 8:
                System.out.println("B");
                break;
            case 7:
                System.out.println("C");
                break;
            case 6:
                System.out.println("D");
                break;
            default:
                System.out.println("F");
                break;
        }

    }
}

```





#### 예제

---



- **약수 출력하기**

```java
package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Example2 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.print("숫자를 입력하세요 :");
        int x = s.nextInt();
        List list = new ArrayList();
        
        for (int i=1; i<=x; i++) {
            if (x % i == 0) {
                list.add(i);
            } 
        }
            System.out.println(list);

    }
}

```



- **소수 구하기**

```java
package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Example2 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.print("숫자를 입력하세요 :");
        int x = s.nextInt();
        List list = new ArrayList();

        int count = 0;
        for (int i = 2; i <= x; i++) {
            for (int j = 2; j <= i; j++) {
                if (i % j == 0) {
                    count++;
                }
            }
            if (count == 1) {
                list.add(i);
                System.out.println(list);
            }
            count = 0;
        }

    }
}

```



- **피보나치 수열 구하기**

```java
package com.example;

public class Example3 {
    public static void main(String[] args) {

    int previousNumber = 1;
    int nextNumber = 1;
    int currentNumber =0;

        System.out.println(previousNumber);
        System.out.println(nextNumber);

        int count = 0;
        while (currentNumber <= 1000) {
            currentNumber = previousNumber + nextNumber;
            System.out.println(currentNumber);
            previousNumber = nextNumber;
            nextNumber = currentNumber;
            count++;
        }
        System.out.println("1000번을 넘은 숫자는 =>" + count);
    }
}


```

