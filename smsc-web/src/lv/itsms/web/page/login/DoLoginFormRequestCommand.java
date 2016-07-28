package lv.itsms.web.page.login;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.LoginFormRequestParameterBuilder;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.request.validator.CustomerNotEmptyRule;
import lv.itsms.web.request.validator.LoginFieldFormValidator;
import lv.itsms.web.request.validator.Rule;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoLoginFormRequestCommand implements PageRequestCommand {

	Customer customer;

	Session session;

	public DoLoginFormRequestCommand(Customer customer, Session session) {
		this.customer = customer;
		this.session = session;
	}

	@Override
	public void execute() throws RuntimeException {
		session.updateSessionAttribute(Session.SESSION_CUSTOMER_LOGIN_PARAMETER, customer.getUserLogin());
		session.updateSessionAttribute(Session.SESSION_CUSTOMER_ID_PARAMETER, customer.getId());
	}
}
