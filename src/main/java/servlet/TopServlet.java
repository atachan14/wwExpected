package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Fase;
import model.SessionBoard;
import model.SessionRegulation;

/**
 * Servlet implementation class Entrance
 */
@WebServlet("/top")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PATH_top = "WEB-INF/jsp/top.jsp";
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TopServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.getRequestDispatcher(PATH_top).forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		SessionRegulation sr = new SessionRegulation(request);
		SessionBoard d1 = new SessionBoard(sr,new Fase("d",1));
		request.getSession().setAttribute("sr",sr);
		request.getSession().setAttribute("d1",d1);

		response.sendRedirect("main");

	}

	

}
