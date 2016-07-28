package lv.itsms.web.page.profile;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoLoadProfilePageDataCommand implements PageRequestCommand {

	Session session;

	Repository repository;

	public DoLoadProfilePageDataCommand(Session session, Repository repository) {
		this.session = session;
		this.repository = repository;
	}

	@Override
	public void execute() {

		String sessionCustomerName = session.getSessionUserId();

		Customer customer = repository.getCustomerByLogin(sessionCustomerName);
		if (customer!=null) {
			session.updateSessionAttribute(Session.SESSION_PROFILE_PARAMETER, customer);
		}
	}
}
