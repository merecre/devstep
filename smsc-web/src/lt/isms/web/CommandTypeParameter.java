package lt.isms.web;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ViewSmsGroupNameListRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ViewSmsGroupRecordRequestParameter;

public enum CommandTypeParameter {

	NO_COMMAND, 
	CMD_DELETE_SMS_GROUP_REC,
	CMD_LOAD_SMS_GROUP_NAMES, 
	CMD_LOAD_SMS_GROUP_REC,
	CMD_OPEN_NEW_SMS_REC, 
	CMD_SAVE_SMS_GROUP_REC;

	public static Map<String, UserPageRequestParameter> getUserRequestParameters() {

		List<UserPageRequestParameter> urlParameterList = new ArrayList<>();		
		urlParameterList.add(new ViewSmsGroupNameListRequestParameter());
		urlParameterList.add(new DeleteSmsGroupPostRequestParameter());
		urlParameterList.add(new SmsGroupIdPostRequestParameter());
		urlParameterList.add(new SaveSmsNewGroupRequestParameter());
		urlParameterList.add(new SmsPhoneRequestParameter());
		urlParameterList.add(new SmsGroupIdGetRequestParameter());
		urlParameterList.add(new OpenNewSmsGroupRecRequestParameter());
		urlParameterList.add(new ViewSmsGroupRecordRequestParameter());

		Map<String, UserPageRequestParameter>  urlParameters = new HashMap<>();
		for (UserPageRequestParameter userRequest : urlParameterList) {
			urlParameters.put(userRequest.getParameterKey(), userRequest);
		}	

		return urlParameters;
	}

	public static Map<CommandTypeParameter, String> getUserRequestCommandLookups () {

		Map<CommandTypeParameter, String> userRequestCommandLookups = new EnumMap<>(CommandTypeParameter.class);
		userRequestCommandLookups.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_REC, ViewSmsGroupRecordRequestParameter.URL_PARAMETER);
		userRequestCommandLookups.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_NAMES, ViewSmsGroupNameListRequestParameter.MENU_URL_PARAMETER);
		userRequestCommandLookups.put(CommandTypeParameter.CMD_OPEN_NEW_SMS_REC, OpenNewSmsGroupRecRequestParameter.OPEN_NEW_SMS_GROUP_URL_PARAMETER);
		userRequestCommandLookups.put(CommandTypeParameter.CMD_DELETE_SMS_GROUP_REC, DeleteSmsGroupPostRequestParameter.DELETE_COMMAND_URL_PARAMETER);
		userRequestCommandLookups.put(CommandTypeParameter.CMD_SAVE_SMS_GROUP_REC, SaveSmsNewGroupRequestParameter.SAVE_COMMAND_URL_PARAMETER);

		return userRequestCommandLookups;
	}


}
