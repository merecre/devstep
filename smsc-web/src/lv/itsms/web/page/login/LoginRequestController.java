package lv.itsms.web.page.login;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.command.UserRequestCommandManager;
import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.validator.LoginFieldsValidator;
import lv.itsms.web.request.validator.rule.CustomerNotEmptyRule;
import lv.itsms.web.request.validator.rule.Rule;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

/**
 * Servlet implementation class LoginRequestController
 */
//@WebServlet("/LoginRequestController")
public class LoginRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Repository repository;
	Session session;
	
	LoginPageFactory loginPageFactory;
	
	UserRequestCommandManager pageCommandManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginRequestController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		repository = new Repository();
		session = new Session();
		
		loginPageFactory = new LoginPageFactory();
		
		pageCommandManager = new UserRequestCommandManager(loginPageFactory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session.setRequest(request);
		session.setSession(request.getSession());			
		try {
			Customer customer = validateUserInputtedLoginFormData(request);
			String loginName = customer.getUserLogin();
			String userPassword = customer.getPassword();
			doLogging(loginName, userPassword, request, response);
			returnToBackPage(request, response);
		} catch (RuntimeException exception) {
			updateSessionExceptionError(exception, request);
			forwardToLoginErrorPage(request, response);
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Customer validateUserInputtedLoginFormData(HttpServletRequest request) {

		LoginFormRequestParameterBuilder parameterBuilder = new LoginFormRequestParameterBuilder();
		parameterBuilder.build(request);

		UserPageRequestParameter loginNameParameter = parameterBuilder.getLoginName();
		UserPageRequestParameter userPasswordParameter = parameterBuilder.getPassword();

		String loginName = loginNameParameter.getParameter();
		String userPassword = userPasswordParameter.getParameter();

		Customer customer = new Customer();
		customer.setUserLogin(loginName);
		customer.setPassword(userPassword);

		isCorrectCustomerLogin(customer);

		return customer;
	}

	private boolean isCorrectCustomerLogin(Customer customer) {
		LoginFieldsValidator validator = new LoginFieldsValidator();
		validator.prepareRules();
		return validator.validate(customer);
	}

	private void doLogging(String loginName, String userPassword, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Customer customer = repository.getCustomerByLoginAndPassword(loginName, userPassword);	
		isCorrectCustomerLogin(customer);
		
		loginPageFactory.setCustomer(customer);
		loginPageFactory.setSession(session);
		
		List<PageRequestCommand> commandsToBeExecuted = pageCommandManager.selectUserRequestedCommand(request);
		executeUserRequestCommand(commandsToBeExecuted);
	}

	private void executeUserRequestCommand(List<PageRequestCommand> commandExecutionSequence) {
		for (PageRequestCommand command : commandExecutionSequence) {
			command.execute();
		}		
	}
	
	private void returnToBackPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
	}

	private void forwardToLoginErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String language = session.getSessionLanguage();

		final String loginErrorJSP = "/login/loginerrpage.jsp";
		try {
			LoginInfo loginInfo = repository.getLoginInfoByLanguage(language);		
			session.updateSessionAttribute("logininfo", loginInfo);
			ServletContext context = getServletContext();
			if (context!=null) {
				RequestDispatcher dispatcher = context.getRequestDispatcher(loginErrorJSP);
				dispatcher.forward(request,response);
			}
		} catch (Exception e){
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

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public LoginPageFactory getLoginPageFactory() {
		return loginPageFactory;
	}

	public void setLoginPageFactory(LoginPageFactory loginPageFactory) {
		this.loginPageFactory = loginPageFactory;
	}

	public UserRequestCommandManager getPageCommandManager() {
		return pageCommandManager;
	}

	public void setPageCommandManager(UserRequestCommandManager pageCommandManager) {
		this.pageCommandManager = pageCommandManager;
	}
	
}
