package lv.itsms.web.page.smspanel;

import java.util.Map;

/*
 * Parses URL user requests and
 * returns correspondent request processing command. 
 */

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.ErrorSmsGroupCommand;
import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.SmsGroupNamesRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

public class CustomerPanelCommandFactory {

	public final static String SMS_REPORT_GROUP_LIST = "smsPanel";

	public final static String SMS_REPORT_GROUP_VIEW_FULL = "groupList";

	Repository repository;

	HttpServletRequest request;

	Session session;

	UserPageRequest pageRequest;

	Map<String, UserPageRequest> urlParameters;

	public CustomerPanelCommandFactory(Repository repository, Map<String, UserPageRequest> urlParameters) {
		this.repository = repository;
		this.urlParameters = urlParameters;
	}

	public PageRequestCommand make() {

		pageRequest = getUserPageRequest(CustomerMenuRequestParameter.MENU_PARAMETER);
		pageRequest.update(request);

		if (pageRequest.isRequested()) {
			String userRequestParameter = pageRequest.getParameter();
			String userRequestValue = pageRequest.getParameter();
			if (userRequestParameter.equals(SMS_REPORT_GROUP_LIST)) {
				return new DoGetSmsGroupNameCommand(this);
			} else if (userRequestValue.equals(SMS_REPORT_GROUP_VIEW_FULL)) {
				return new DoGetSmsGroupRecCommand(this); 
			}
		}

		UserPageRequest postRequestParameter = getUserPageRequest(DeleteSmsGroupPostRequestParameter.DELETE_COMMAND_PARAMETER);
		postRequestParameter.update(request);	
		if (postRequestParameter.isRequested()) {
			pageRequest = getUserPageRequest(SmsGroupIdPostRequestParameter.GROUP_ID_POST_PARAMETER);
			pageRequest.update(request);
			return new DoDeleteSmsGroupRecCommand(this);
		}

		pageRequest = getUserPageRequest(SaveSmsNewGroupRequestParameter.SAVE_COMMAND_PARAMETER);
		pageRequest.update(request);		
		if (pageRequest.isRequested()) {
			return new DoSaveSmsGroupRecCommand(this);
		}

		return new ErrorSmsGroupCommand();
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setUserPageRequest(	UserPageRequest pageRequest) {
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

	public UserPageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(UserPageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public Session getSession() {
		return session;
	}

	public UserPageRequest getUserPageRequest(String parametrKey) {
		return urlParameters.get(parametrKey);
	}
}
