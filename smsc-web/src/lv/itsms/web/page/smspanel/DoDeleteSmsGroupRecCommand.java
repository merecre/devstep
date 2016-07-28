package lv.itsms.web.page.smspanel;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import lv.itsms.web.service.jdbc.JDBCSmsGroupDAO;
import transfer.domain.SmsGroup;

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
		UserPageRequest userRequest = factory.getPageRequest();
		return userRequest.getParameterValues();
	}

}
