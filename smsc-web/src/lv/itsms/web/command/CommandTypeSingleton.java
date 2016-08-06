package lv.itsms.web.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lv.itsms.web.page.login.DoLoginFormRequestCommand;
import lv.itsms.web.page.login.LoginNameRequestParameter;
import lv.itsms.web.page.login.LoginPasswordRequestParameter;
import lv.itsms.web.page.report.DoPrepareReportDiagramCommand;
import lv.itsms.web.page.report.ReportEndDateRequestParameter;
import lv.itsms.web.page.report.ReportStartDateRequestParameter;
import lv.itsms.web.page.report.ViewReportByDateRequestParameter;
import lv.itsms.web.page.smspanel.DoDeleteSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoOpenNewSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoSaveSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoViewSmsGroupNameCommand;
import lv.itsms.web.page.smspanel.DoViewSmsGroupRecCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ViewSmsGroupNameListRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ViewSmsGroupRecordRequestParameter;

public class CommandTypeSingleton {

	private static  Map<String, UserPageRequestParameter>  urlParameters;

	private static  Map<CommandTypeParameter, String> userRequestCommandLookups;

	private static  Map<CommandTypeParameter, Class<?>> userRequestCommands;

	private static CommandTypeSingleton instance = new CommandTypeSingleton();

	public static CommandTypeSingleton getInstance() {
		initializeURLParameters();
		linkCommandTypeToUserRequestParameter();
		linkCommandTypeToExecutedCommand();
		return instance; 
	}

	public  Map<String, UserPageRequestParameter> getUserRequestParameters() {				
		return urlParameters;
	}

	public  Map<CommandTypeParameter, String> getCommandTypeByUserRequestParameter () {		
		return userRequestCommandLookups;
	}

	public  Map<CommandTypeParameter, Class<?>> getCommandToBeExecutedByCommandType () {

		return userRequestCommands;
	}

	public  UserPageRequestParameter getUserPageRequestParameter (String key) {
		Map<String, UserPageRequestParameter> urlParameters = getUserRequestParameters();
		return urlParameters.get(key);
	}

	private static void initializeURLParameters() {
		if (urlParameters != null ) 
			return;

		final List<UserPageRequestParameter> urlParameterList = new ArrayList<>();
		urlParameterList.add(new ViewSmsGroupNameListRequestParameter());
		urlParameterList.add(new DeleteSmsGroupPostRequestParameter());
		urlParameterList.add(new SmsGroupIdPostRequestParameter());
		urlParameterList.add(new SaveSmsNewGroupRequestParameter());
		urlParameterList.add(new SmsPhoneRequestParameter());
		urlParameterList.add(new SmsGroupIdGetRequestParameter());
		urlParameterList.add(new OpenNewSmsGroupRecRequestParameter());
		urlParameterList.add(new ViewSmsGroupRecordRequestParameter());
		urlParameterList.add(new ReportStartDateRequestParameter());
		urlParameterList.add(new ReportEndDateRequestParameter());
		urlParameterList.add(new LoginPasswordRequestParameter());
		urlParameterList.add(new LoginNameRequestParameter());
		urlParameterList.add(new ViewReportByDateRequestParameter());

		Map<String, UserPageRequestParameter>  localUrlParameters = new HashMap<>();
		for (UserPageRequestParameter userRequest : urlParameterList) {
			localUrlParameters.put(userRequest.getParameterKey(), userRequest);
		}	

		urlParameters = Collections.unmodifiableMap(localUrlParameters);		
	}

	private static void linkCommandTypeToUserRequestParameter() {
		if (userRequestCommandLookups != null) 
			return;

		Map<CommandTypeParameter, String> localUserRequestCommandLookups = new EnumMap<>(CommandTypeParameter.class);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_REC, ViewSmsGroupRecordRequestParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_NAMES, ViewSmsGroupNameListRequestParameter.MENU_URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_OPEN_NEW_SMS_REC, OpenNewSmsGroupRecRequestParameter.OPEN_NEW_SMS_GROUP_URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_DELETE_SMS_GROUP_REC, DeleteSmsGroupPostRequestParameter.DELETE_COMMAND_URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_SAVE_SMS_GROUP_REC, SaveSmsNewGroupRequestParameter.SAVE_COMMAND_URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_DO_LOGIN_USER, LoginPasswordRequestParameter.LOGIN_PASSWORD_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_VIEW_REPORT_DIAGRAM, ViewReportByDateRequestParameter.URL_PARAMETER);		
		userRequestCommandLookups = Collections.unmodifiableMap(localUserRequestCommandLookups);
	}

	private static void linkCommandTypeToExecutedCommand() {
		if (userRequestCommands != null) 
			return;
		
		Map<CommandTypeParameter, Class<?>> localUserRequestCommands = new EnumMap<>(CommandTypeParameter.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_VIEW_REPORT_DIAGRAM, DoPrepareReportDiagramCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_DO_LOGIN_USER, DoLoginFormRequestCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_NAMES, DoViewSmsGroupNameCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_REC, DoViewSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_OPEN_NEW_SMS_REC, DoOpenNewSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_DELETE_SMS_GROUP_REC, DoDeleteSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_SAVE_SMS_GROUP_REC, DoSaveSmsGroupRecCommand.class);

		userRequestCommands = Collections.unmodifiableMap(localUserRequestCommands);
	}	
}