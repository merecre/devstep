package lv.itsms.web.page.smspanel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.PhoneGroup;
import transfer.domain.SmsGroup;

public class DoGetSmsGroupRecCommand implements PageRequestCommand {
	
	CustomerPanelCommandFactory factory;
	
	public DoGetSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {
		
		Session session = factory.getSession();
		UserPageRequest groupIdRequest = 
				factory.getUserPageRequest(SmsGroupIdGetRequestParameter.GROUP_ID_GET_PARAMETER) ;
		session.updateParameter(groupIdRequest);
		
		String sessionGroupId = groupIdRequest.getParameter();
		
		if (isNumeric(sessionGroupId)) {
			int groupId = Integer.parseInt(sessionGroupId);
			Repository repository = factory.getRepository();
			SmsGroup smsGroup = repository.getSmsGroupById(groupId);
		
			if (smsGroup!=null) {
				session.updateSessionAttribute("smsgrouprec", smsGroup);
			}
			
			List<PhoneGroup> phoneGroups = repository.getPhonesInGroupByGroupId(groupId);
			if (phoneGroups!=null && phoneGroups.size()>0) {
				session.updateSessionAttribute("phonegroups", phoneGroups);
			}
		}
	}
	
	private boolean isNumeric(String s) {  
	    return s!= null && s.matches("[-+]?\\d*\\.?\\d+");  
	} 

}
