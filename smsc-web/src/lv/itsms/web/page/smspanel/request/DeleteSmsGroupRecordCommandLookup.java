package lv.itsms.web.page.smspanel.request;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class DeleteSmsGroupRecordCommandLookup extends UserRequestCommandLookup {

	public DeleteSmsGroupRecordCommandLookup(UserPageRequestParameter urlParameter) {
		super(urlParameter);
	}

	@Override
	public CommandType parse(HttpServletRequest request) {
		commandRequestID = CommandType.NO_COMMAND;

		pageRequestParameter.update(request);	
		if (pageRequestParameter.isRequested()) {
			commandRequestID = CommandType.CMD_DELETE_SMS_GROUP_REC;
		}

		return commandRequestID;
	}

}
