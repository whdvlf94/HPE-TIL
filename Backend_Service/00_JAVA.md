# JAVA

> 객체 지향 프로그래밍(OOP: Object-Oriented Programming)



- **특징**
  - 모든 운영체제에서 실행 가능
  - 객체 지향 프로그래밍(OOP)
  - 메모리 자동 정리





#### 자바 개발 도구 설치

---

>  ※ 자바 개발 도구 : JDK , JRE



- JDK(Java Development Kit)
  - version : SE / ME / EE	ex) **Java SE 11.0. 2 (LTS)**







#### 실습)

---

> HelloWorld.java



```java
import java.lang.*;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
// System 속 out이라는 객체 내에 있는 println method를 사용하겠다.
    }
}
```

**※ class 명과 파일 명은 동일해야 한다.**

- `fucntion` 대신에 `method`로 표기한다.





- **Complie → Runtime**

```java
import java.lang.*;

public class HelloWorld {

    public static void main(String[] args) {
        String name = null;
        System.out.println("Hello World!");
        System.out.println(name.length());
     }
}

// name 값의 length을 구하라

// Complie : 문법 적인 오류는 없으므로 정상적으로 작동한다

// Runtime : name의 값이 null 이기 때문에, NullPointerException 오류가 발생한다.
```

- **terminal 실행 **

> Complie 이후 Runtime



- 파일이 있는 위치에서 `javac HelloWorld` 입력 => 파일이 컴파일 된다.
- 아무런 정보가 뜨지 않는다면, 정상적으로 컴파일 완료
- 컴파일 완료 후,  `.class` 파일이 생성된다.
  - `HelloWorld.class`

**※ 컴파일은 단순히 문법적인 오류만 판단한다.**





**※ java 파일은 어떤 운영체제(Windows, Linux, MaxOS ..) 에서든 실행할 수 있으나, 운영 체제가 다른 경우 컴파일을 다시 진행해야 한다.**





#### InteliJ - 규칙

---

- 모든 실행문 끝에는 반드시 세미콜론(;)을 붙여서 실행문이 끝났음을 표시

- 모든 `Java` 파일의 시작점은 `public static void main(String[] args){}` 이다.





- **package**

```java
package com.example;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    };
}


// HelloWorld.java 파일을 com.example package 내로 이동
```

- 파일을 com/example 아래에 이동 시키면 **refactor** 이라는 문구가 뜬다.

  **Refactoring**: 만들어졌던 코드들의 재설계



- **Search**
  - src 폴더 클릭 후, `ctrl`+`shift`+`F`(전역 검색 기능)



**※ window terminal에서 complie하는 경우**

1) inteliJ 에서 Run

2)

```shell
C:\Users\HPE\IdeaProjects\FirstProject\out\production\FirstProject>
java HelloWorld

#Error!

C:\Users\HPE\IdeaProjects\FirstProject\out\production\FirstProject>
java com.example.HelloWorld

#Hello World!
```



