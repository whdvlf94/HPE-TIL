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
- **MyBatis** 에서는 위의 절차를 자동으로 처리 해준다.
  - **단, query 문 은 직접 작성해야함**



#### 1. Driver 등록

```java
Class.forName( "com.microsoft.jdbc.sqlserver.SQLServerDriver" ); 
 
Class.forName( "org.gjt.mm.mysql.Driver" );
```

**※ `new` 연산자를 이용해 생성하지 않은 이유는 vendor 중립적으로 애플리케이션을 구현하기 위함이다.**

- 즉, `forName` 내에 있는 `class` 명만 변경해서 사용하면 된다.

- `forName`은 인자에 `class path`를 입력하면, **해당 클래스를 찾아서 객체를 생성**하는 역할 



#### 2. DMBS와 연결

```java
public static Connection getConnection( String url,String user, String password ) throws SQLException 
```

- `getConnection()` 은 **Factory Method**이다.

  

```java
Connection conn = DriverManager.getConnection( “jdbc:oracle:thin:@192.168.0.200:1521:VCC”,                “SEXXXXX”,             
 “SEXXXXX” );
// Connection conn = DriverManager.getConnection(url,user,pass)
```

- `idbc` : **protocol**
- `orcle:thin` : **sub protocol**
- `@192.~.~.1` : **IP** ,   `1521` : **port**
- `vcc` : **SID**



#### 3. Statement 생성

```java
Statement stmt = conn.createStatement(); 
```

- SQL 을 전송해주는 역할을 담당하는 `Statement` 객체 생성
- `Connection`은 인터페이스이며, 구현부에는 `createStatement()`가 존재한다(**Factory Method**). 이때, 사용자는 인터페이스만 참조하여 구현부를 나타낼 수 있다. 
- 생성된 `conn.createStatement()`는 `Statement` 인터페이스 내부의 구현부 객체를 생성해준다. 
- 이 역시도 벤더 중립적인 객체를 생성해주기 위한 메서드이다.



#### 4. SQL전송 , 결과 받기

- **Select**

  ```java
  String query = "SELECT ID, LAST_NAME FROM EMP"; 
  // query = select query 문
  
  //executeQuert() : 조회 메서드
  //ResultSet : Factory Method
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

  - 등록, 갱신, 삭제
  
  ```java
  String query = "UPDATE EMP" +
      		   "SET LAST_NAME  =  'KIM' "+                           			"WHERE  ID =  '100000'  ";  
	int result = stmt.executeUpdate( query ); 
  ```
  
  

#### 6. 닫기

: 사용했던 자원들 반납

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




- **close **

  ```java
   finally {
  			try {
  //finally 문은 코드가 정상 처리 혹은 에러가 나더라도 항상 실행하기 때문에
  //close() 메서드는 finally문에 작성해주도록 한다.
  				if(stmt != null) stmt.close();
  				if(con != null) con.close();
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  ```



#### JDBC 실습 예제

```java
package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeesRead {

	public static void main(String[] args) {

		// 1. Driver class loading
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Driver loading OK!!");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String pass = "hr";
		String sql = "select * from employees";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 2. DB 연결 : Connection 생성
			// getConnection() -> Factory Method
			// Connection con = new T4CConnection(); (X) vendor 종속적
			con = DriverManager.getConnection(url, user, pass);
			System.out.println(con.getClass().getName()); // T4CConnection

			// 3. SQL 전달하기 위한 준비: Statement 생성
			// Factory Method
			stmt = con.createStatement();
			System.out.println(stmt.getClass().getName()); // OracleStatementWrapper

			// 4. SQL 실행
			// Factory MEthod
			rs = stmt.executeQuery(sql);
			System.out.println(rs.getClass().getName()); // OracleResultSetImpl

			// 5. query 결과 처리
			while (rs.next()) {
				String id = rs.getString("employee_id");
				String name = rs.getString("first_name");
				Date hdate = rs.getDate("hire_date");
				float salary = rs.getFloat("salary");
				System.out.println(id + " " + name + " " + hdate + " " + salary);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				// finally 문은 코드가 정상 처리 혹은 에러가 나더라도 항상 실행하기 때문에
				// close() 메서드는 finally문에 작성해주도록 한다.
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
```







#### ORM(Object Relational Mapping)

**: MyBatis , JPA**



**매핑 Rule**

| java      | db          |
| --------- | ----------- |
| Class(VO) | Table       |
| Object    | Row(Record) |
| Variable  | Column      |

**※ MyBatis 에서는 위의 매핑 작업을 알아서 처리해준다.**


