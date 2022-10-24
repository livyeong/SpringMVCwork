package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.mvc")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 요청한 주소를 가져온다.
		String url = request.getRequestURI();
		// View로 사용할 JSP
		String viewName = null;

		if (url.contains("main.mvc")) {
			viewName = "main.jsp";
		} else if (url.contains("test1.mvc")) {
			viewName = "test1.jsp";
		} else if (url.contains("test2.mvc")) {
			viewName = "test2.jsp";
		}

		RequestDispatcher dis = request.getRequestDispatcher(viewName);
		dis.forward(request, response);
	}

}
