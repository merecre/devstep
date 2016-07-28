package lv.itsms.web.page.smspanel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

public class DoGetSmsGroupNameCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;
	
	public DoGetSmsGroupNameCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {
		Session session = factory.getSession();
		int userId = Integer.parseInt(session.getSessionCustomerId());
		Repository repository = factory.getRepository();
		List<SmsGroup> smsGroups = repository.getSmsGroupByUserId(userId);
		updateSession(smsGroups);
		
	}
	
	private void updateSession(List<SmsGroup> smsGroups) {
		Session session = factory.getSession();
		session.updateSessionAttribute(Session.SESSION_SMSGROUPS_PARAMETER, smsGroups);
	}

}
