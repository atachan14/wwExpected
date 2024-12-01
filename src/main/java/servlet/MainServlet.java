package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.SessionBoard;
import model.SessionRegulation;
import model.logic.CalcPer;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PATH_main = "WEB-INF/jsp/main.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		getCp(request).updateVillsPer();

		request.getRequestDispatcher(PATH_main).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	void updateSession(HttpServletRequest request) {
		
	}

	SessionRegulation getSr(HttpServletRequest request) {
		return (SessionRegulation) request.getSession().getAttribute("sr");

	}

	SessionBoard getSb(HttpServletRequest request) {
		return (SessionBoard) request.getSession().getAttribute("sb");
	}

	CalcPer getCp(HttpServletRequest request) {
		return (CalcPer) request.getSession().getAttribute("cp");
	}

}
