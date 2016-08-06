package lv.itsms.web.page.smspanel;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lv.itsms.web.request.validator.*;
import lv.itsms.web.request.validator.smsgroup.PhoneNumberValidator;
import lv.itsms.web.request.validator.smsgroup.SmsGroupFieldsValidator;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.command.UserRequestCommandManager;
import lv.itsms.web.request.parameter.menu.CustomerMenuRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

/**
 * Parses URL and executes User SMS panel page requests
 */

public class SmsPanelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static String smsPanelPageURL = "&" +
			CustomerMenuRequestParameter.MENU_URL_PARAMETER 
			+ "=" +
			CustomerMenuRequestParameter.SMS_REPORT_GROUP_LIST_URL_VALUE;

	final static String errorPageURL = "/WEB-INF/panel/sms/smspanelerror.jsp";

	Repository repository;

	Session session;
	
	CustomerPanelCommandFactory userRequestCommandFactory;

	UserRequestCommandManager pageCommandManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public SmsPanelController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();

		session = new Session();

		repository = new Repository();

		userRequestCommandFactory = new CustomerPanelCommandFactory (repository);	

		pageCommandManager = new UserRequestCommandManager(userRequestCommandFactory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session.setRequest(request);
		session.setSession(request.getSession());

		userRequestCommandFactory.setSession(session);
		userRequestCommandFactory.setRequest(request);

		SmsGroupFieldsValidator requestValidator = new SmsGroupFieldsValidator();		
		userRequestCommandFactory.setSmsGroupValidator(requestValidator);
		
		PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
		userRequestCommandFactory.setPhoneNumberValidator(phoneNumberValidator);
		
		try {		
			List<PageRequestCommand> commandsToBeExecuted = pageCommandManager.selectUserRequestedCommand(request);
			executeUserRequestCommand(commandsToBeExecuted);
		} catch (RuntimeException exception) {
			updateSessionExceptionError(exception, request);
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			redirectToPage(request, response);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

	private void executeUserRequestCommand(List<PageRequestCommand> commandExecutionSequence) {
		for (PageRequestCommand command : commandExecutionSequence) {
			command.execute();
		}		
	}
	
	private void redirectToPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
	}

	private void forwardToPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String forwardJSP = errorPageURL;

		try {
			ServletContext context = getServletContext();
			if (context != null) {
				RequestDispatcher dispatcher = context.getRequestDispatcher(forwardJSP);
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
	
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public CustomerPanelCommandFactory getCustomerPanelFactory() {
		return userRequestCommandFactory;
	}

	public void setCustomerPanelFactory(CustomerPanelCommandFactory customerPanelFactory) {
		this.userRequestCommandFactory = customerPanelFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public UserRequestCommandManager getPageManager() {
		return pageCommandManager;
	}

	public void setPageManager(UserRequestCommandManager pageManager) {
		this.pageCommandManager = pageManager;
	}
}
