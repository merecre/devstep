package lv.itsms.web.page.login;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.request.parameter.LoginFormRequestParameterBuilder;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.request.validator.CustomerLoginNotEmpty;
import lv.itsms.web.request.validator.CustomerNotEmptyRule;
import lv.itsms.web.request.validator.CustomerPasswordNotEmpty;
import lv.itsms.web.request.validator.LoginFieldFormValidator;
import lv.itsms.web.request.validator.Rule;
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
		} catch (Exception e) {
			e.printStackTrace();
			session.updateSessionAttribute(Session.SESSION_ERROR_PARAMETER, e.getMessage());
			forwardToLoginErrorPage(request, response);
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

		UserPageRequest loginNameParameter = parameterBuilder.getLoginName();
		UserPageRequest userPasswordParameter = parameterBuilder.getPassword();

		String loginName = loginNameParameter.getParameter();
		String userPassword = userPasswordParameter.getParameter();

		Customer customer = new Customer();
		customer.setUserLogin(loginName);
		customer.setPassword(userPassword);

		isCorrectCustomerLogin(customer);

		return customer;
	}

	private boolean isCorrectCustomerLogin(Customer customer) {
		Rule rule = new CustomerNotEmptyRule(customer);
		rule = new CustomerLoginNotEmpty(customer.getUserLogin());
		rule = new CustomerPasswordNotEmpty(customer.getPassword());

		LoginFieldFormValidator validator = new LoginFieldFormValidator();
		validator.addRule(rule);

		return validator.validate();
	}

	private void doLogging(String loginName, String userPassword, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Customer customer = repository.getCustomerByLoginAndPassword(loginName, userPassword);	
		isCorrectCustomerLogin(customer);
		PageRequestCommand requestCommand = new DoLoginFormRequestCommand(customer, session);
		requestCommand.execute();
		returnToBackPage(request, response);
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

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(loginErrorJSP);
			dispatcher.forward(request,response);
		} catch (Exception e){
			e.printStackTrace();
		}
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
}
