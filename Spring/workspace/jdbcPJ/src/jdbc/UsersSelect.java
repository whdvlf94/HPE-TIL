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
			// 2. Connection ����
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection : " + con);

			// 3. Statement ����
			stmt = con.createStatement();
			System.out.println("Statement : " + stmt);

			// 4. sql�� ���� - excuteQuery()
			rs = stmt.executeQuery(sql);
			System.out.println("ResultSest : " + rs);

			// 5. query ��� �� ó��
			UserVO userVO = null;

			List<UserVO> userlist = new ArrayList<>();

			while (rs.next()) {
				String userid = rs.getString("userid");
				String name = rs.getString("name");
				char gender = rs.getString("gender").charAt(0);
				String city = rs.getString("city");

				// UserVO ��ü ����
				userVO = new UserVO(userid, name, gender, city);

				// UserVO ��ü�� ArrayList�� ����
				userlist.add(userVO);

			}

			// ������ ��ü���� userlist�� �߰� ��, foreach ���� ���� ���
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
