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

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.CustomerBuilder;
import lv.itsms.web.request.validator.UserRequestValidator;
import lv.itsms.web.request.validator.customer.CustomerRegistrationFieldsValidator;
import lv.itsms.web.request.validator.rule.CustomerNameIsNotUsed;
import lv.itsms.web.request.validator.rule.CustomerNotEmptyRule;
import lv.itsms.web.request.validator.rule.Rule;
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
		} catch (RuntimeException exception) {
			updateSessionExceptionError(exception, request);
		} catch (Exception e) {
			e.printStackTrace();
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

		UserRequestValidator customerValidator = new CustomerRegistrationFieldsValidator(repository);
		customerValidator.prepareRules();
		customerValidator.validate(customer);

		return customer;
	}

	private void doCustomerRegistration(Customer customer) {
		PageRequestCommand pageRequestCommand = new DoRegistrationFormRequestCommand(customer, repository);
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
