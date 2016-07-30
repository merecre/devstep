package lv.itsms.web.page.smspanel.request;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class OpenNewSmsGroupRecordCommandLookup extends UserRequestCommandLookup {

	public OpenNewSmsGroupRecordCommandLookup(UserPageRequestParameter urlParameter) {
		super(urlParameter);
	}

	@Override
	public CommandType parse(HttpServletRequest request) {
		commandRequestID = CommandType.NO_COMMAND;

		pageRequestParameter.update(request);

		if (pageRequestParameter.isRequested()) {
			String userRequestValue = pageRequestParameter.getParameter();
			if (userRequestValue.equals("true")) {
				commandRequestID = CommandType.CMD_OPEN_NEW_SMS_REC;
			}	
		}

		return commandRequestID;
	}

}
