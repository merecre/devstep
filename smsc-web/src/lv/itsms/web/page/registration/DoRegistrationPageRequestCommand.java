package lv.itsms.web.page.registration;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.page.info.RegistrationInfo;
import lv.itsms.web.request.parameter.CustomerBuilder;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoRegistrationPageRequestCommand implements PageRequestCommand {
	
	HttpServletRequest request;

	Repository repository;

	Session session;
	
	public DoRegistrationPageRequestCommand(HttpServletRequest request, Repository repository, Session session) {
		this.request = request;
		this.repository = repository;
		this.session = session;
	}

	@Override
	public void execute() {		
		String language = session.getSessionLanguage();

		RegistrationInfo registrationInfo = repository.getRegistrationInfoByLanguage(language);	
		
		session.updateSessionAttribute(Session.SESSION_REGISTRATION_PARAMETER, registrationInfo);
	}
}
