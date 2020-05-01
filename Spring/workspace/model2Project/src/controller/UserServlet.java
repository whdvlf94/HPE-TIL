package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.user.dao.UserDAO;
import jdbc.user.vo.UserVO;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("*.do")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO dao;
	private RequestDispatcher rd;

	@Override
	public void init() throws ServletException {
		System.out.println("UserServlet init() method called!!");
		dao = new UserDAO();
	}

	@Override
	public void destroy() {
		System.out.println("UserServlet destroy() method called!");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserServlet doGet() method called!" + request.getMethod());
		// 요청(request) 데이터의 인코딩
		request.setCharacterEncoding("utf-8");

		String cmd = request.getParameter("cmd");
		System.out.println(cmd);

		// 분기 로직
		if (cmd.equals("userList")) {
			userList(request, response);
		} else if (cmd.equals("userDetail")) {
			userDetail(request, response);
		} else if (cmd.equals("userForm")) {
			userForm(request, response);
		} else if (cmd.equals("userInsert")) {
			userInsert(request, response);
		}
	}

	// userInsert
	private void userInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. userFrom 데이터를 추출해서 UserVO에 저장한다.
		UserVO user = new UserVO(request.getParameter("userid"), request.getParameter("name"),
				request.getParameter("gender").charAt(0), request.getParameter("city"));

		System.out.println(user);
		
		int cnt = dao.insertUser(user);
		if(cnt ==1) { //정상 실행
			userList(request, response);
			
		}

	}

	// userForm
	private void userForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("userForm.html");
		// 객체를 넘기지 않고, 해당 페이지에 포워딩만 해준다.
	}

	// userDetail
	private void userDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userid = request.getParameter("id");
		System.out.println(userid);

		// 웹 브라우저에서 이름을 클릭하면 Console에 해당 아이디가 출력된다.

		// 1. DAO 호출
		UserVO userVO = dao.getUser(userid);
		System.out.println("-------------------");
		System.out.println(userVO);

		// 2. DAO로 받아 온 List 객체를 JSP에서 사용할 수 있도록 Request 객체에 저장
		request.setAttribute("user", userVO); // (key, value)

		// 3. 결과를 출력해 줄 JSP - userDetail.jsp를 포워딩
		rd = request.getRequestDispatcher("userDetail.jsp");
		rd.forward(request, response);
	}

	// userList
	private void userList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. DAO 호출
		List<UserVO> users = dao.getUsers();
		System.out.println("-------------------");
		System.out.println(users + "\n");

		// 2. DAO로 받아 온 List 객체를 JSP에서 사용할 수 있도록 Request 객체에 저장
		request.setAttribute("userList", users);

		// 3. 결과를 출력해 줄 JSP - userList.jsp를 포워딩
		rd = request.getRequestDispatcher("userList.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserServlet doPost() method called!");

		doGet(request, response);
	}

}
