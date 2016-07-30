package lv.itsms.web.page.smspanel.request;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

/**
 * Parses URL parameter and returns ID of executed command.
 */

public abstract class UserRequestCommandLookup {

	public final static int NO_COMMAND = 0;

	CommandType commandRequestID;

	UserPageRequestParameter pageRequestParameter;

	public UserRequestCommandLookup(UserPageRequestParameter urlParameter) {
		this.pageRequestParameter = urlParameter;
	}

	public abstract CommandType parse(HttpServletRequest request);

	public UserPageRequestParameter getPageRequest() {
		return pageRequestParameter;
	}
	public void setPageRequest(UserPageRequestParameter pageRequest) {
		this.pageRequestParameter = pageRequest;
	} 

	public boolean isRequested() {		
		return commandRequestID != CommandType.NO_COMMAND;
	}

	public CommandType getCommandIdToBeExecuted() {
		return commandRequestID;
	}
}
