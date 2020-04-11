# JDBC(Java Database Connectivity)





## 1. JDBC

> 자바 언어에서 DB에 접근할 수 있게 해주는 Programming API



![jdbc](https://user-images.githubusercontent.com/58682321/79037738-b1a52d00-7c0e-11ea-9e4f-4f9dc84d4e24.PNG)



- **Oracle JDBC Driver** :  `ojdbc.jar`
- **Interface와 구현부를 나눈 이유는?**
  - 특정 DB에 종속적이지 않고, vendor 중립적으로 애플리케이션을 구현할 수 있다.
  - vendor : Oracle, My SQL, Sybase ..



### 1.1 JDBC Coding 절차

- Driver 등록 → DBMS와 연결(**Connection**) → Statement 생성(**Query 실행**) → SQL 전송 → 결과 받기 → 닫기



#### 1. Driver 등록

```java
Class.forName( “com.microsoft.jdbc.sqlserver.SQLServerDriver” ); 
 
Class.forName( "org.gjt.mm.mysql.Driver“ );
```

**※ `new` 연산자를 이용해 생성하지 않은 이유는 vendor 중립적으로 애플리케이션을 구현하기 위함이다.**



#### 2. DMBS와 연결

```java
public static Connection getConnection( String url,String user, String password ) throws SQLException 
```

- `getConnection()` 은 **Factory Method**이다.

  

```java
Connection conn = DriverManager.getConnection( “jdbc:oracle:thin:@192.168.0.200:1521:VCC”,                “SEXXXXX”,             
 “SEXXXXX” );
```

- `idbc` : **protocol**
- `orcle:thin` : **sub protocol**
- `@192.~.~.1` : **IP** ,   `1521` : **port**
- `vcc` : **SID**



#### 3. Statement 생성

```java
Statement stmt = conn.createStatement(); 
```



#### 4. SQL전송 , 결과 받기

- **Select**

  ```java
  String query = "SELECT ID, LAST_NAME FROM EMP"; 
  ResultSet rset = stmt.executeQuery( query ); 
  while ( rset.next() ) {     
      System.out.println( rset.getString( "ID" ) + "\t" +                                      rset.getString( 2 ) );
  	}
  ```

  - **ResultSet**은 인터페이스 이다. 종속성을 없애기 위해 인터페이스 사용
  - **executeQuery()**는 **sql select query문을 인자 값으로 받는다.  **
  - **`next()`는 table에서 row 단위로 읽어온다. return 타입은 boolean으로 while에서 주로 이용된다.**
  - `getString("ID")` : table 중 `column`명이 ID 인 것의 인자 값을 가져온다.
  - `getString(2)` : `column`의 인덱스 값을 의미한다.

  

- **DML**

  ```java
  String query = "UPDATE EMP" +
      		   "SET LAST_NAME  =  'KIM' "+                           			"WHERE  ID =  '100000'  ";  
  int result = stmt.executeUpdate( query ); 
  ```

  

#### 6. 닫기

- **Select**

  ```java
  rset.close(); 
  stmt.close(); 
  conn.close()
  ```

  

- **DML**

  ```java
  stmt.close(); 
  conn.close();
  ```

  