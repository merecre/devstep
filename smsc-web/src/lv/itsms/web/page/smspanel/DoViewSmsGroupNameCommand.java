package lv.itsms.web.page.smspanel;

import java.util.List;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

public class DoViewSmsGroupNameCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoViewSmsGroupNameCommand(CustomerPanelCommandFactory factory) {
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
