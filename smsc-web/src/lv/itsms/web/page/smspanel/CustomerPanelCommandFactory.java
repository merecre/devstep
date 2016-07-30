package lv.itsms.web.page.smspanel;

import java.util.Map;

/*
 * Parses URL user requests and
 * returns correspondent request processing command. 
 */

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.ErrorSmsGroupCommand;
import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

/**
 * 
 * @author DMC
 *
 * Returns commands which will processes user request.
 */

public class CustomerPanelCommandFactory {

	Repository repository;

	HttpServletRequest request;

	Session session;

	UserPageRequestParameter pageRequest;

	Map<String, UserPageRequestParameter> urlParameters;

	public CustomerPanelCommandFactory(Repository repository, Map<String, UserPageRequestParameter> urlParameters) {
		this.repository = repository;
		this.urlParameters = urlParameters;
	}

	public PageRequestCommand make(CommandType commandRequestID) {

		switch (commandRequestID) {
		case CMD_LOAD_SMS_GROUP_REC: 
			return new DoViewSmsGroupRecCommand(this);
		case CMD_OPEN_NEW_SMS_REC:
			return new DoOpenNewSmsGroupRecCommand(this);
		case CMD_LOAD_SMS_GROUP_NAMES:
			return new DoViewSmsGroupNameCommand(this);
		case CMD_DELETE_SMS_GROUP_REC:
			return new DoDeleteSmsGroupRecCommand(this);
		case CMD_SAVE_SMS_GROUP_REC:
			return new DoSaveSmsGroupRecCommand(this);
		case NO_COMMAND:
			return new ErrorSmsGroupCommand();
		default:
			break;
		}

		return new ErrorSmsGroupCommand();
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setUserPageRequest(	UserPageRequestParameter pageRequest) {
		this.pageRequest = pageRequest;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public UserPageRequestParameter getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(UserPageRequestParameter pageRequest) {
		this.pageRequest = pageRequest;
	}

	public Session getSession() {
		return session;
	}

	public UserPageRequestParameter getUserPageRequest(String parametrKey) {
		return urlParameters.get(parametrKey);
	}
}
