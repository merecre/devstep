package lv.itsms.web.page.smspanel;

import java.util.List;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhonesRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.Utils;
import transfer.domain.PhoneGroup;
import transfer.domain.SmsGroup;

public class DoEditSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoEditSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() throws Exception {
		
		Session session = factory.getSession();
		
		if (isRecordAlreadySpecified()) {
			return;
		}
		
		String sessionSmsGroupRecordId = getSmsGroupId(session);	
		if (Utils.isNumeric(sessionSmsGroupRecordId)) {
			String sessionCustomerId = session.getSessionCustomerId();
			if (Utils.isNumeric(sessionCustomerId)) {
				int smsGroupRecordId = Integer.parseInt(sessionSmsGroupRecordId);
				int customerId = Integer.parseInt(sessionCustomerId);
				getSmsGroupRecordAndSetSessionAttribute(session, smsGroupRecordId, customerId);
				getSmsGroupPhoneNumbersAndSetSessionAtttribute(session, smsGroupRecordId);
			}
		}	
	}
	
	private boolean isRecordAlreadySpecified() {
		return factory.getRequest().getSession().getAttribute(Session.SESSION_SMSGROUPREC_PARAMETER)!=null;
	}
	
	private String getSmsGroupId(Session session) {
		UserPageRequestParameter groupIdRequest = 
				factory.getUserPageRequest(SmsGroupIdGetRequestParameter.GROUP_ID_GET_PARAMETER);
		session.updateParameter(groupIdRequest);
		return groupIdRequest.getParameter();		
	}
	
	private void getSmsGroupRecordAndSetSessionAttribute(Session session, int groupId, int customerId) throws Exception {

		Repository repository = factory.getRepository();
		SmsGroup smsGroup = repository.getSmsGroupByIdAndCustomerId(groupId, customerId);

		if (smsGroup!=null) {
			session.updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);
		}
	}
	
	private void getSmsGroupPhoneNumbersAndSetSessionAtttribute(Session session, int groupId) {
		Repository repository = factory.getRepository();
		List<PhoneGroup> phoneGroups = repository.getPhonesInGroupByGroupId(groupId);
		String[] phoneNumbers = phoneGroups
			.stream()
			//.sorted((p1, p2) -> p1.getPhonenumber().compareTo(p2.getPhonenumber()))
			.map(s -> s.getPhonenumber())
			.toArray(String[]::new);
		
		session.updateSessionAttribute(SmsPhonesRequestParameter.PHONE_PARAMETER_KEY, phoneNumbers);		
	}
}
