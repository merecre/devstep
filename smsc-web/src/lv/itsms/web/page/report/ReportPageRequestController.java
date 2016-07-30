package lv.itsms.web.page.report;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.request.parameter.ReportEndDateRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class ReportPageRequestController
 */
//@WebServlet("/ReportPageRequestController")
public class ReportPageRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Repository repository;

	Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportPageRequestController() {
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

		UserPageRequestParameter reportStartDateUserParameter = new ReportEndDateRequestParameter();
		reportStartDateUserParameter.update(request);
		String reportStartDate = reportStartDateUserParameter.getParameter();

		UserPageRequestParameter reportEndDateUserParameter = new ReportEndDateRequestParameter();
		reportEndDateUserParameter.update(request);
		String reportEndDate = reportEndDateUserParameter.getParameter();

		/* TODO
		 * User inputed date validation
		 */

		try {
			PageRequestCommand requestCommand = new DoPrepareReportDiagramCommand(session, request, repository);
			requestCommand.execute();
			returnToBackPage(request, response);
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

	private void returnToBackPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
	}

	private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String errorJSP = "/WEB-INF/reporterror.jsp";
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
