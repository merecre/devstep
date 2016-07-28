package lv.itsms.web.page.registration;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.page.info.RegistrationInfo;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

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
		//Session session = new Session(request);
		String language = session.getSessionLanguage();
		
		RegistrationInfo registrationInfo = repository.getRegistrationInfoByLanguage(language);
	//System.out.println("registrationInfo"+registrationInfo+" ln="+language);	
		session.updateSessionAttribute(Session.SESSION_REGISTRATION_PARAMETER, registrationInfo);
	}

}
