package lv.itsms.web.page.registration;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class RegistrationPageController
 */
//@WebServlet("/RegistrationPageController")
public class RegistrationPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Repository repository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationPageController() {
		super();
	}   

	@Override
	public void init() throws ServletException {
		repository = new Repository();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session session = new Session(request);
		PageRequestCommand requestCommand = new DoRegistrationPageRequestCommand(request, repository, session);

		try {
			requestCommand.execute();
		} catch (Exception e) {
			forwardToErrorPage(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String errorJSP = "/WEB-INF/regerror.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorJSP);
		dispatcher.forward(request,response);
	}
}
