package lv.itsms.web.page.smspanel.request;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ViewSmsGroupNameCommandLookup extends UserRequestCommandLookup {

	public ViewSmsGroupNameCommandLookup(UserPageRequestParameter urlParameter) {
		super(urlParameter);
	}

	@Override
	public CommandType parse(HttpServletRequest request) {

		commandRequestID = CommandType.NO_COMMAND;	
		pageRequestParameter.update(request);

		if (pageRequestParameter.isRequested()) {
			String userRequestParameter = pageRequestParameter.getParameter();
			if (userRequestParameter.equals(CustomerMenuRequestParameter.SMS_REPORT_GROUP_LIST_URL_VALUE)) {
				commandRequestID = CommandType.CMD_LOAD_SMS_GROUP_NAMES;
			} 
		}

		return commandRequestID;
	}

}
