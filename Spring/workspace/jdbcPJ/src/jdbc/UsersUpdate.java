package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersUpdate {

	public static void main(String[] args) {
//		String sql = "update users set name='길동쓰',gender='female',city='seoul' where userid='gildong';";
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

			stmt.setString(1, "마이바티스");
			stmt.setString(2, "남");
			stmt.setString(3, "서울");
			stmt.setString(4, "gildong");

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
