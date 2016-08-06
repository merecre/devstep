package lv.itsms.web.page.smspanel;

import java.util.List;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import lv.itsms.web.utils.Utils;
import transfer.domain.PhoneGroup;
import transfer.domain.SmsGroup;

public class DoViewSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoViewSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {

		Session session = factory.getSession();
		
		clearNewSmsGroupAttributes(session);
		
		UserPageRequestParameter groupIdRequest = 
				factory.getUserPageRequest(SmsGroupIdGetRequestParameter.GROUP_ID_GET_PARAMETER);
		session.updateParameter(groupIdRequest);
		String sessionGroupId = groupIdRequest.getParameter();

		if (Utils.isNumeric(sessionGroupId)) {
			String sessionCustomerId = session.getSessionCustomerId();
			if (Utils.isNumeric(sessionCustomerId)) {
				int groupId = Integer.parseInt(sessionGroupId);
				Repository repository = factory.getRepository();
				SmsGroup smsGroup = repository.getSmsGroupById(groupId);

				if (smsGroup!=null) {
					session.updateSessionAttribute(Session.SESSION_VIEW_SMSGROUPREC_PARAMETER, smsGroup);
				}

				List<PhoneGroup> phoneGroups = repository.getPhonesInGroupByGroupId(groupId);		
				session.updateSessionAttribute(Session.SESSION_PHONEGROUPS_PARAMETER, phoneGroups);
			}
		}
	}

	private void clearNewSmsGroupAttributes(Session session) {
		session.updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, null);
		session.updateSessionAttribute(SmsPhoneRequestParameter.PHONE_PARAMETER_KEY, null);
	}
}
