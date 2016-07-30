package lv.itsms.web.page.smspanel.request;

import java.util.Map;

import lv.itsms.web.page.smspanel.CommandType;
import lv.itsms.web.request.parameter.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.ViewSmsGroupRecRequestParameter;

public class UserRequestCommandLookupFactory {

	Map<String, UserPageRequestParameter> urlParameters;
		
	public UserRequestCommandLookupFactory(Map<String, UserPageRequestParameter> urlParameters) {

		this.urlParameters = urlParameters;
	}

	public UserRequestCommandLookup make(String userURLParameterKey) {
		
		if (userURLParameterKey.equals(SaveSmsNewGroupRequestParameter.SAVE_COMMAND_URL_PARAMETER)) {
			return getSaveSmsGroupRecordLookup();
		}
		
		if (userURLParameterKey.equals(DeleteSmsGroupPostRequestParameter.DELETE_COMMAND_URL_PARAMETER)) {
			return getDeleteSmsGroupRecordLookup();
		}
		
		if (userURLParameterKey.equals(OpenNewSmsGroupRecRequestParameter.OPEN_NEW_SMS_GROUP_URL_PARAMETER)) {
			return getOpenNewSmsGroupRecordLookup();
		}

		if (userURLParameterKey.equals(ViewSmsGroupRecRequestParameter.VIEW_SMS_GROUP_REV_URL)) {
			return getViewSmsGroupRecordLookup();
		}
		
		if (userURLParameterKey.equals(CustomerMenuRequestParameter.MENU_URL_PARAMETER)) {
			return getViewSmsGroupLookup();
		}
		
		return null;
	}
	
	private UserRequestCommandLookup getSaveSmsGroupRecordLookup() {

		String userURLParameterKey = SaveSmsNewGroupRequestParameter.SAVE_COMMAND_URL_PARAMETER;
		UserPageRequestParameter userURLParameter = urlParameters.get(userURLParameterKey);	
		return 	new SaveSmsGroupRecordCommandLookup(userURLParameter);
	}
	
	private UserRequestCommandLookup getDeleteSmsGroupRecordLookup() {

		String userURLParameterKey = DeleteSmsGroupPostRequestParameter.DELETE_COMMAND_URL_PARAMETER;
		UserPageRequestParameter userURLParameter = urlParameters.get(userURLParameterKey);		
		return new DeleteSmsGroupRecordCommandLookup(userURLParameter);	
	}

	private UserRequestCommandLookup getOpenNewSmsGroupRecordLookup() {
		String userURLParameterKey = OpenNewSmsGroupRecRequestParameter.OPEN_NEW_SMS_GROUP_URL_PARAMETER;
		UserPageRequestParameter userURLParameter = urlParameters.get(userURLParameterKey);
		return	new OpenNewSmsGroupRecordCommandLookup(userURLParameter);
			
	}

	private UserRequestCommandLookup getViewSmsGroupRecordLookup() {
		String userURLParameterKey = ViewSmsGroupRecRequestParameter.VIEW_SMS_GROUP_REV_URL;
		UserPageRequestParameter userURLParameter = urlParameters.get(userURLParameterKey); 
		return new ViewSmsGroupRecordCommandLookup(userURLParameter);		
	}

	private UserRequestCommandLookup getViewSmsGroupLookup() {

		String  userURLParameterKey = CustomerMenuRequestParameter.MENU_URL_PARAMETER;
		UserPageRequestParameter userURLParameter = urlParameters.get(userURLParameterKey);		
		return new ViewSmsGroupNameCommandLookup(userURLParameter);		
	}
}
