package lv.itsms.web.page.smspanel;

import java.util.List;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
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
		UserPageRequestParameter groupIdRequest = 
				factory.getUserPageRequest(SmsGroupIdGetRequestParameter.GROUP_ID_GET_PARAMETER) ;
		session.updateParameter(groupIdRequest);

		String sessionGroupId = groupIdRequest.getParameter();
		if (Utils.isNumeric(sessionGroupId)) {
			int groupId = Integer.parseInt(sessionGroupId);
			Repository repository = factory.getRepository();
			SmsGroup smsGroup = repository.getSmsGroupById(groupId);

			if (smsGroup!=null) {
				session.updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);
			}

			List<PhoneGroup> phoneGroups = repository.getPhonesInGroupByGroupId(groupId);		
			session.updateSessionAttribute(Session.SESSION_PHONEGROUPS_PARAMETER, phoneGroups);
		}
	}

	
}
