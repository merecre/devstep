package lv.itsms.web.page.profile;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.page.info.ProfileInfo;
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
		String sessionLanguage = session.getSessionLanguage();

		ProfileInfo profileInfo = repository.getProfileInfoByLanguage(sessionLanguage);
		Customer customer = repository.getCustomerByLogin(sessionCustomerName);

		if (customer!=null) {
			session.updateSessionAttribute(Session.SESSION_PROFILE_PARAMETER, customer);
			session.updateSessionAttribute(Session.SESSION_PROFILEINFO_PARAMETER, profileInfo);
		}

		//session.updateSessionAttribute(Session.SESSION_ERROR_PARAMETER, profileInfo);
	}
}
