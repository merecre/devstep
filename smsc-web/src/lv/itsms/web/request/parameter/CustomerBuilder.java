package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import transfer.domain.Customer;

public class CustomerBuilder {

	public Customer build(HttpServletRequest request) {

		Customer customer = new Customer();
		customer.setStatus(Customer.STATUS_INACTIVE);
		
		UserPageRequestParameter userRequest = new CustomerFirstnameRequest();
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

	private class CustomerFirstnameRequest extends UserPageRequestParameter {
		private final static String URL_PARAMETER = "fname";
		public CustomerFirstnameRequest() {
			super(URL_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}		
	}

	private class CustomerLastnameRequest extends UserPageRequestParameter {
		private final static String URL_PARAMETER = "lname";
		public CustomerLastnameRequest() {
			super(URL_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}		
	}

	private class CustomerEmailRequest extends UserPageRequestParameter {
		private final static String URL_PARAMETER = "email";
		public CustomerEmailRequest() {
			super(URL_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}	
	}

	private class CustomerLoginRequest extends UserPageRequestParameter {
		private final static String URL_PARAMETER = "uname";
		public CustomerLoginRequest() {
			super(URL_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}	
	}

	private class CustomerPasswordRequest extends UserPageRequestParameter {
		private final static String URL_PARAMETER = "pass";
		public CustomerPasswordRequest() {
			super(URL_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}	
	}
}
