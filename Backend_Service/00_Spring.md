# Spring initializer



### 1. Spring initializer 기초

---



- `src` 아래에 `main`과 `test`로 나누어져 있다.

  - `test`에서 파일을 생성하고 `Alt + Enter`를 통해 `main` 폴더 내에 import 할 수 있다.

  



![spring](https://user-images.githubusercontent.com/58682321/74996725-b7488700-5497-11ea-8653-eea0d69206d9.PNG)





#### 계산기 만들기



- **TestCalculator**

> test 폴더 내에 존재

```java
package com.example.testdemo;

// xunit -> junit, jsunit, pyunit ...
// junit -> java unit
import org.junit.jupiter.api.Test;

/*
    Application 요구사항
    1. 2가지 숫자의 정수 덧셈
    2. 2가지 숫자의 정수 뺄셈
 */

public class TestCalculator {

    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        //처음에는 존재하지 않는 class이기 때문에 에러가 발생한다. 
        //이 때, Alt + Enter를 통해서 main 폴더 내에 Calculator class를 생성할 수 있다.

        int result = calc.add(10,20);
        // Alt + Enter를 통해 Calculator 내에 메서드로 추가할 수 있다.
        System.out.println(result == 30);
        // test로 실행할 때, print 문장은 항상 실행 성공으로 나타난다.

    }
    
    @Test
    public void testSubtract() {
        Calculator calc = new Calculator();

        int result = calc.subtract(20,10);
        System.out.println(result == 10);

    }
}

```

**※ `@Test`을 통해 부분적으로 코드를 테스트 해볼 수 있다.**



- **Calculator**

> main 폴더 내에 존재

```java
package com.example.testdemo;

public class Calculator {
    public int add(int a, int b) {
        return 0;
    }

    public int subtract(int a, int b) {
        return 0;
    }

}

```

**※ 직접 작성하지 않고, `TestCalculator`파일에서 생성한 코드 이다.( 단, 변수명, return은 예외)**





#### Error 발생 시키기

- **pom.xml**

```xml
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-library</artifactId>
            <scope>test</scope>
        </dependency>
```

**※ 아래 단계를 진행하기 위해 라이브러리 import**



- **TestCalculator**

```java
package com.example.testdemo;

// xunit -> junit, jsunit, pyunit ...

import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
// alt + enter를 통해 class를 import하는 것이 아닌 그 안에 method를 import 할 수 있다.

/*
    Application 요구사항
    1. 2가지 숫자의 정수 덧셈
    2. 2가지 숫자의 정수 뺄셈
 */

public class TestCalculator {

    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        int result = calc.add(20,10);
        assertTrue(result == 30);

    }

    @Test
    public void testSubtract() {
        Calculator calc = new Calculator();
        int result = calc.subtract(20,10);
        assertTrue(result == 10);
        // true

    }

    @Test
    public void testAddError() {
        Calculator calc = new Calculator();
        int result = calc.add(20,10);
        assertTrue(result != 30);
        // false

    }
    @Test
    public void testSubtractError() {
        Calculator calc = new Calculator();
        int result = calc.subtract(10,20);
        assertEquals(10,result, "결과는 -10 이어야 합니다.");
        
//org.opentest4j.AssertionFailedError: 결과는 -10 이어야 합니다. ==> 
//Expected :10
//Actual   :-10
        

    }
}

```

**※ `Ctrl + Click` 하면 해당 메서드가 포함되어 있는 `Class`로 이동한다.**



- **Calculator**

```java
package com.example.testdemo;

public class Calculator {
    public int add(int a, int b) {
        return a+b;
    }

    public int subtract(int a, int b) {
        return a-b;
    }

}
```









#### setter and getter 쉽게 사용하기



- **TestMember**

> test

```java
package com.example.testdemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMember {

    @Test
    public void testAddMember () {
        Member member = new Member();
        member.setName("yjp");

        assertEquals("yjp",member.getName());


    }

}


```



- **Member**

> main

```java
package com.example.testdemo;

import lombok.Data;
//1) File > Settings > Plugin > lombok install
//2) File > Settings > annotaiton 검색 후, Enable 설정

@Data
public class Member {

    private String name;
    private String ssn;
    private String birthDate;

}

```

※ `lombok`  import를 통해 class 내에 `setter and getter` class를 따로 생성하지 않아도 된다.

 

### 2. Git Upload

---



```
1. VCS -> Enable Version Control Integration 클릭 (git init)
2. 최상위 폴더 우측마우스 클릭 -> Git -> Add(git add)
3. 최상위 폴더 우픅마우스 클릭 -> Git -> Commit Change (git commit)
4. git commit and push -> commit message 작성 후, git URL 복사
```

