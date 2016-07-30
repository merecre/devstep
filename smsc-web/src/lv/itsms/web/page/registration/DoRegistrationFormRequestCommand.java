package lv.itsms.web.page.registration;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.LoginFormRequestParameterBuilder;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.validator.CustomerNotEmptyRule;
import lv.itsms.web.request.validator.LoginFieldFormValidator;
import lv.itsms.web.request.validator.Rule;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoRegistrationFormRequestCommand implements PageRequestCommand {

	private Customer customer;
	private Repository repository;

	public DoRegistrationFormRequestCommand(Customer customer, Repository repository) {
		this.customer = customer;
		this.repository = repository;
	}

	@Override
	public void execute() throws RuntimeException {	
		repository.insertCustomer(customer);
	}
}
