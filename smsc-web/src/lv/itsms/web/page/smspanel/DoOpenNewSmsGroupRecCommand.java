package lv.itsms.web.page.smspanel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lv.itsms.web.page.PageRequestCommand;

import lv.itsms.web.session.Session;
import transfer.domain.PhoneGroup;
import transfer.domain.SmsGroup;

public class DoOpenNewSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoOpenNewSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {	
		Session session = factory.getSession();
		Date today = Calendar.getInstance().getTime();
		SmsGroup smsGroup = new SmsGroup(today);
		session.updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);
				
		session.updateSessionAttribute(Session.SESSION_PHONEGROUPS_PARAMETER, "");
	}
}
