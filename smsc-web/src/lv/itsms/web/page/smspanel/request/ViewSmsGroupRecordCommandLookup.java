package lv.itsms.web.page.smspanel.request;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.ViewSmsGroupRecRequestParameter;

public class ViewSmsGroupRecordCommandLookup extends UserRequestCommandLookup {

	public ViewSmsGroupRecordCommandLookup(UserPageRequestParameter urlParameter) {
		super(urlParameter);
	}

	@Override
	public CommandType parse(HttpServletRequest request) {

		commandRequestID = CommandType.NO_COMMAND;
		pageRequestParameter.update(request);

		if (pageRequestParameter.isRequested()) {
			String userRequestValue = pageRequestParameter.getParameter();
			if (userRequestValue.equals("true")) {
				commandRequestID = CommandType.CMD_LOAD_SMS_GROUP_REC;
			}
		}
		return commandRequestID;
	}

}
