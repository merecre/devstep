package lv.itsms.web.page.smspanel;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdPostRequestParameter;
import lv.itsms.web.service.Repository;

public class DoDeleteSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoDeleteSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {	
		String[] groupIds = getSmsGroups();
		for (String smsGroupId : groupIds) {
			int groupId = Integer.parseInt(smsGroupId);
			Repository repository = factory.getRepository();
			repository.deleteSmsGroupByGroupId(groupId);
		}
	}

	private String[] getSmsGroups () {
		UserPageRequestParameter userRequest = factory.getUserPageRequest(SmsGroupIdPostRequestParameter.GROUP_ID_POST_PARAMETER);
		userRequest.update(factory.getRequest());
		return userRequest.getParameterValues();
	}

}
