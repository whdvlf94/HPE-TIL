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
