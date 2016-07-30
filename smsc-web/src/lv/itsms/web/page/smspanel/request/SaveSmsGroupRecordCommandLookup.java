package lv.itsms.web.page.smspanel.request;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SaveSmsGroupRecordCommandLookup extends UserRequestCommandLookup {

	public SaveSmsGroupRecordCommandLookup(UserPageRequestParameter urlParameter) {
		super(urlParameter);
	}

	@Override
	public CommandType parse(HttpServletRequest request) {
		commandRequestID = CommandType.NO_COMMAND;

		pageRequestParameter.update(request);		
		if (pageRequestParameter.isRequested()) {
			commandRequestID = CommandType.CMD_SAVE_SMS_GROUP_REC;
		}

		return commandRequestID;

	}

}
