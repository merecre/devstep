package lv.itsms.web.page.registration;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.CustomerBuilder;
import lv.itsms.web.request.validator.customer.CustomerRegistrationFieldsValidator;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;
import transfer.validator.UserRequestValidator;

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
		
		try {
			Customer customer = validateUserInputtedRegistrationFormFieldsAndBuildCustomer(request);
			doCustomerRegistration(customer);
			updateInformationMessage(request, response);
		} catch (RuntimeException exception) {
			updateSessionExceptionError(exception, request);
		} catch (Exception e) {
			e.printStackTrace();
			updateSessionExceptionError(e, request);
		} finally {
			returnToBackPage(request, response);			
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
		storeInRegFormAlreadyInputedRegistrationData(customer);
		
		UserRequestValidator customerValidator = new CustomerRegistrationFieldsValidator(repository);
		customerValidator.prepareRules();
		customerValidator.validate(customer);

		return customer;
	}

	private void storeInRegFormAlreadyInputedRegistrationData(Customer customer) {
		session.updateSessionAttribute(Session.SESSION_CUSTOMER_REGISTRATION_PARAMETER, customer);
	}

	private void doCustomerRegistration(Customer customer) throws Exception {
		PageRequestCommand pageRequestCommand = new DoRegistrationFormRequestCommand(customer, repository, session);
		pageRequestCommand.execute();

	}

	void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String errorJSP = "/WEB-INF/regerror.jsp";
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
		exceptionMessage = getLocalisedMessage(exceptionMessage);
		session.updateSessionAttribute(Session.SESSION_ERROR_PARAMETER, exceptionMessage);
	}
	
	private void updateInformationMessage(HttpServletRequest request, HttpServletResponse response) {
		String information = (String) request.getSession().getAttribute(Session.SESSION_INFORMATION_PARAMETER);
		if (information != null) {
			information = getLocalisedMessage(information);
			session.updateSessionAttribute(Session.SESSION_INFORMATION_PARAMETER, information);
		}		
	}
	
	private String getLocalisedMessage(String message) {
		
		String localisedMessage = message;
		
		String lng = session.getSessionLanguage(); 
		
		Locale currentLocale = new Locale(lng, lng.toUpperCase());
		ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
		if (messages.containsKey(message)) {
			localisedMessage = messages.getString(message);
		}
		
		return localisedMessage;
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
