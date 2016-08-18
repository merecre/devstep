package lv.itsms.web.page.smspanel;

import java.util.List;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.Utils;
import transfer.domain.SmsGroup;

public class DoViewSmsGroupNameCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoViewSmsGroupNameCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() throws Exception {
		Session session = factory.getSession();
		String sessionCustomerId = session.getSessionCustomerId();
		if (Utils.isNumeric(sessionCustomerId)) {
			int userId = Integer.parseInt(session.getSessionCustomerId());
			Repository repository = factory.getRepository();
			List<SmsGroup> smsGroups = repository.getSmsGroupByUserId(userId);
			updateSession(smsGroups);
		}
	}

	private void updateSession(List<SmsGroup> smsGroups) {
		Session session = factory.getSession();
		session.updateSessionAttribute(Session.SESSION_SMSGROUPS_PARAMETER, smsGroups);
	}

}
