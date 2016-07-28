package lv.itsms.web.page.registration;

import java.io.IOException;
import java.util.ArrayList;
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

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.CustomerBuilder;
import lv.itsms.web.request.validator.CustomerEmailNotEmpty;
import lv.itsms.web.request.validator.CustomerLoginNotEmpty;
import lv.itsms.web.request.validator.CustomerNameIsNotUsed;
import lv.itsms.web.request.validator.CustomerNameNotEmpty;
import lv.itsms.web.request.validator.CustomerPasswordNotEmpty;
import lv.itsms.web.request.validator.CustomerFieldFormValidator;
import lv.itsms.web.request.validator.Rule;
import lv.itsms.web.request.validator.UserRequestValidator;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

/**
 * Servlet implementation class RegistrationRequestController
 */
//@WebServlet("/RegistrationRequestController")
public class RegistrationRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Repository repository;

	Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationRequestController() {
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
		session.setSession(request.getSession());;

		Customer customer = null;		
		try {
			customer = validateUserInputtedRegistrationFormFieldsAndBuildCustomer(request);
			doCustomerRegistration(customer);
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

	private Customer validateUserInputtedRegistrationFormFieldsAndBuildCustomer(HttpServletRequest request) {
		CustomerBuilder customerBuilder = new CustomerBuilder();
		Customer customer = customerBuilder.build(request);

		List<Rule> rules = prepareCustomerRules(customer);
		UserRequestValidator customerValidator = new CustomerFieldFormValidator(rules, customer);
		customerValidator.validate();

		return customer;
	}

	private List<Rule> prepareCustomerRules(Customer customer) {
		List<Rule> customerRules = new ArrayList<>();

		Rule customerRule = new CustomerNameNotEmpty(customer.getName()); 
		customerRules.add(customerRule);		
		customerRule = new CustomerNameNotEmpty(customer.getSurname()); 
		customerRules.add(customerRule);
		customerRule = new CustomerEmailNotEmpty(customer.getEmail());
		customerRules.add(customerRule);
		customerRule = new CustomerPasswordNotEmpty(customer.getPassword());
		customerRules.add(customerRule);
		customerRule = new CustomerLoginNotEmpty(customer.getUserLogin());
		customerRules.add(customerRule);
		customerRule = new CustomerNameIsNotUsed (repository, customer.getUserLogin());
		customerRules.add(customerRule);
		return customerRules;
	}

	private void doCustomerRegistration(Customer customer) {
		PageRequestCommand pageRequestCommand = new DoRegistrationFormRequestCommand(customer, repository);
		pageRequestCommand.execute();

	}

	void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String errorJSP = "/WEB-INF/regerror.jsp";
		//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorJSP);
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

	private void returnToBackPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
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
