package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import transfer.domain.Customer;

public class CustomerBuilder {

	public Customer build(HttpServletRequest request) {

		Customer customer = new Customer();

		UserPageRequest userRequest = new CustomerFirstnameRequest();
		userRequest.update(request);
		customer.setName(userRequest.getParameter());

		userRequest = new CustomerLastnameRequest();
		userRequest.update(request);
		customer.setSurname(userRequest.getParameter());

		userRequest = new CustomerEmailRequest();
		userRequest.update(request);
		customer.setEmail(userRequest.getParameter());

		userRequest = new CustomerLoginRequest();
		userRequest.update(request);
		customer.setUserLogin(userRequest.getParameter());

		userRequest = new CustomerPasswordRequest();
		userRequest.update(request);
		customer.setPassword(userRequest.getParameter());

		return customer;
	}

	private class CustomerFirstnameRequest extends UserPageRequest {
		private final static String FIRST_NAME_PAREMETER = "fname";
		public CustomerFirstnameRequest() {
			super(FIRST_NAME_PAREMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}		
	}

	private class CustomerLastnameRequest extends UserPageRequest {
		private final static String LAST_NAME_PAREMETER = "lname";
		public CustomerLastnameRequest() {
			super(LAST_NAME_PAREMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}		
	}

	private class CustomerEmailRequest extends UserPageRequest {
		private final static String EMAIL_PAREMETER = "email";
		public CustomerEmailRequest() {
			super(EMAIL_PAREMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}	
	}

	private class CustomerLoginRequest extends UserPageRequest {
		private final static String LOGIN_PAREMETER = "uname";
		public CustomerLoginRequest() {
			super(LOGIN_PAREMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}	
	}

	private class CustomerPasswordRequest extends UserPageRequest {
		private final static String PASSWORD_PAREMETER = "pass";
		public CustomerPasswordRequest() {
			super(PASSWORD_PAREMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}	
	}
}
