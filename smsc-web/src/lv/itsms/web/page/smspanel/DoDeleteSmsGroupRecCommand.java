package lv.itsms.web.page.smspanel;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdPostRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

public class DoDeleteSmsGroupRecCommand implements PageRequestCommand {

	private final static String COMMAND_INFO = "Message.deleted.successesfully";
	
	CustomerPanelCommandFactory factory;

	public DoDeleteSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() throws Exception {	
		String[] groupIds = getSmsGroups();
		if (groupIds!=null) {
			Repository repository = factory.getRepository();
			repository.deleteSmsGroupsByGroupId(groupIds);
			factory.getSession().updateSessionAttribute(Session.SESSION_INFORMATION_PARAMETER, COMMAND_INFO);
		}
	}

	private String[] getSmsGroups () {
		UserPageRequestParameter userRequest = factory.getUserPageRequest(SmsGroupIdPostRequestParameter.GROUP_ID_POST_PARAMETER);
		userRequest.update(factory.getRequest());
		return userRequest.getParameterValues();
	}

}
