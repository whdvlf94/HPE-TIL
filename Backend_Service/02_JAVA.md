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



- **Stack data / Heap data**



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



