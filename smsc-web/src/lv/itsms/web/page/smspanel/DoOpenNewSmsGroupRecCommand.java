package lv.itsms.web.page.smspanel;

import java.util.Calendar;
import java.util.Date;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

public class DoOpenNewSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoOpenNewSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {	
		Session session = factory.getSession();
		SmsGroup smsGroup = getInputedSmsGroupRecord();
		session.updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);
		
		String[] phoneNumbers = getPhoneNumbers();
		session.updateSessionAttribute(Session.SESSION_PHONEGROUPS_PARAMETER, phoneNumbers);
	}
	
	private SmsGroup getInputedSmsGroupRecord() {
		Date today = Calendar.getInstance().getTime();
		Object smsGroup = factory.getRequest().getSession().getAttribute(Session.SESSION_SMSGROUPREC_PARAMETER);
		if (smsGroup==null) {
			 smsGroup = new SmsGroup(today);
		}
		
		return (SmsGroup)smsGroup;
	}
	
	private String[] getPhoneNumbers() {
		try {
			Object[] phoneNumbers = (Object[])factory.getRequest().getSession().getAttribute(SmsPhoneRequestParameter.PHONE_PARAMETER_KEY);
			if (phoneNumbers!=null) {
				return (String[])phoneNumbers;			
			}
		} catch (ClassCastException e) {
			return null;
		}
		return null;
	}
}
