package lv.itsms.web.page.profile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class ProfilePageController
 */
//@WebServlet("/ProfilePageController")
public class ProfilePageController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Repository repository;

	Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfilePageController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		repository = new Repository();
		session = new Session();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session.setRequest(request);
		session.setSession(request.getSession());

		PageRequestCommand requestCommand = new DoLoadProfilePageDataCommand(session, repository);

		try {
			requestCommand.execute();			
		} catch (Exception exception) {
			exception.printStackTrace();
			updateSessionExceptionError(exception, request);
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
		final String errorJSP = "/WEB-INF/profilerror.jsp";
		try {
			ServletContext context = getServletContext();
			if (context != null) {
				RequestDispatcher dispatcher = context.getRequestDispatcher(errorJSP);
				dispatcher.forward(request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSessionExceptionError(Exception e, HttpServletRequest request) {
		String exceptionMessage = e.getMessage();
		session.updateSessionAttribute(Session.SESSION_ERROR_PARAMETER, exceptionMessage);
	}	
}
