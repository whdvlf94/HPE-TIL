# JDBC



### 1. ArrayList 를 이용한 SELECT(조회) 

- **UserSelect.java**

  ```java
  package jdbc;
  
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.sql.ResultSet;
  import java.sql.SQLException;
  import java.sql.Statement;
  import java.util.ArrayList;
  import java.util.List;
  
  import jdbc.user.vo.UserVO;
  
  public class UsersSelect {
  
  	public static void main(String[] args) {
  
  		// 1. Driver class Loading
  		try {
  			Class.forName("oracle.jdbc.OracleDriver");
  			System.out.println("Driver Loading");
  		} catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
  
  		Connection con = null;
  		Statement stmt = null;
  		ResultSet rs = null;
  		String sql = "select * from users";
  
  		String url = "jdbc:oracle:thin:@localhost:1521:xe";
  		String user = "scott";
  		String pass = "tiger";
  
  		try {
  			// 2. Connection 생성
  			con = DriverManager.getConnection(url, user, pass);
  			System.out.println("Connection : " + con);
  
  			// 3. Statement 생성
  			stmt = con.createStatement();
  			System.out.println("Statement : " + stmt);
  
  			// 4. sql문 실행 - excuteQuery()
  			rs = stmt.executeQuery(sql);
  			System.out.println("ResultSest : " + rs);
  
  			// 5. query 결과 값 처리
  			UserVO userVO = null;
  
  			List<UserVO> userlist = new ArrayList<>();
  
  			while (rs.next()) {
  				String userid = rs.getString("userid");
  				String name = rs.getString("name");
  				char gender = rs.getString("gender").charAt(0);
  				String city = rs.getString("city");
  
  				// UserVO 객체 저장
  				userVO = new UserVO(userid, name, gender, city);
  
  				// UserVO 객체를 ArrayList에 저장
  				userlist.add(userVO);
  
  			}
  
  			// 생성된 객체들을 userlist에 추가 후, foreach 문을 통해 출력
  			for (UserVO item : userlist) {
  
  				System.out.println(item);
  			}
  
  		} catch (SQLException e) {
  			e.printStackTrace();
  		} finally {
  			try {
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

  

### 2. prepareStatement를 이용한 UPDATE(수정)

- **같은 Query를 반복 수행해야 하는 경우 성능이 좋다.**

  - loop 이용 시 용이함

    

- **UsersUpdate.java**

  ```java
  package jdbc;
  
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.sql.PreparedStatement;
  import java.sql.SQLException;
  
  public class UsersUpdate {
  
  	public static void main(String[] args) {
  
  		String sql = "update users set name=?,gender=?,city=? where userid=?";
  
  		// class loading
  		try {
  			Class.forName("oracle.jdbc.OracleDriver");
  			System.out.println("Driver Loading");
  		} catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
  
  		Connection con = null;
  		PreparedStatement stmt = null;
  
  		String url = "jdbc:oracle:thin:@localhost:1521:xe";
  		String user = "scott";
  		String pass = "tiger";
  
  		try {
  			con = DriverManager.getConnection(url, user, pass);
  
  			// Statement는 prepareStatement의 상위 인터페이스 이다.
  			stmt = con.prepareStatement(sql);
  			
  // setString은 prepareStatement에 있는 메서드 이기 때문에
  // stmt는 Statement 타입이 아닌 prepareStatement로 선언해야 한다.
  
  // String sql = "update users set name=?,gender=?,city=? where userid=?";
  			stmt.setString(1, "마이바티스");	//name
  			stmt.setString(2, "남");	//gender
  			stmt.setString(3, "서울"); //city
  			stmt.setString(4, "gildong"); //userid
  			
  // SQL command 에서는 수정 후 commit을 해야하지만, JDBC에서는 Autocommit을 지원하므로 executeUpdate()만 실행하면 된다.
  			int cnt = stmt.executeUpdate();
  			System.out.println("갱신된 건 수 : " + cnt);
  
  		} catch (SQLException e) {
  			e.printStackTrace();
  		} finally {
  			try {
  				if (stmt != null)
  					stmt.close();
  				if (con != null)
  					con.close();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  		}
  
  	}
  }
  -----------------
  생성된 건 수 : 1
  ```

  **※  Query 문이 정상적으로 작동하면 갱신된 건 수가 1로 출력된다.**

