package lv.itsms.web.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lv.itsms.web.page.login.DoLoginFormRequestCommand;
import lv.itsms.web.page.login.LoginNameRequestParameter;
import lv.itsms.web.page.login.LoginPasswordRequestParameter;
import lv.itsms.web.page.report.DoLoadReportPageInfoCommand;
import lv.itsms.web.page.report.DoViewReportByPeriodCommand;
import lv.itsms.web.page.report.DoViewReportPerMessageCommand;
import lv.itsms.web.page.report.LoadReportPageInfoParameter;
import lv.itsms.web.page.report.ReportEndDateRequestParameter;
import lv.itsms.web.page.report.ReportSmsStatusRequestParameter;
import lv.itsms.web.page.report.ReportStartDateRequestParameter;
import lv.itsms.web.page.report.ViewReportByDateRequestPostParameter;
import lv.itsms.web.page.report.ViewReportPerMessagePostParameter;
import lv.itsms.web.page.smspanel.DoDeleteSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoEditSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoImportSmsGroupContacts;
import lv.itsms.web.page.smspanel.DoLoadSmsPanelPageDataCommand;
import lv.itsms.web.page.smspanel.DoOpenNewSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoSaveNewMessageRecCommand;
import lv.itsms.web.page.smspanel.DoSaveSmsGroupRecCommand;
import lv.itsms.web.page.smspanel.DoViewSmsGroupNameCommand;
import lv.itsms.web.page.smspanel.DoViewSmsGroupRecCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.EditSmsGroupRecordRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ImportSmsGroupContactsRequestParameter;
import lv.itsms.web.request.parameter.smspanel.LoadSmsGroupPageInfoRequestParameter;
import lv.itsms.web.request.parameter.smspanel.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SaveNewMessageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhonesRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ViewSmsGroupNameListRequestParameter;
import lv.itsms.web.request.parameter.smspanel.ViewSmsGroupRecordRequestParameter;

public class CommandTypeSingleton {

	private static  Map<String, UserPageRequestParameter>  urlParameters;

	private static  Map<CommandTypeParameter, String> userRequestCommandLookups;

	private static  Map<CommandTypeParameter, Class<?>> userRequestCommands;
	
	private static List<UserPageRequestParameter> urlParameterValues;
	
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

	public Map<CommandTypeParameter, Class<?>> getCommandToBeExecutedByCommandType () {

		return userRequestCommands;
	}
	
	public List<UserPageRequestParameter> getUrlParameterValues() {
		return urlParameterValues;
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
		urlParameterList.add(new SmsPhonesRequestParameter());
		urlParameterList.add(new SmsGroupIdGetRequestParameter());
		urlParameterList.add(new OpenNewSmsGroupRecRequestParameter());
		urlParameterList.add(new ViewSmsGroupRecordRequestParameter());
		urlParameterList.add(new ReportStartDateRequestParameter());
		urlParameterList.add(new ReportEndDateRequestParameter());
		urlParameterList.add(new LoginPasswordRequestParameter());
		urlParameterList.add(new LoginNameRequestParameter());
		urlParameterList.add(new ViewReportByDateRequestPostParameter());
		urlParameterList.add(new EditSmsGroupRecordRequestParameter());
		urlParameterList.add(new LoadSmsGroupPageInfoRequestParameter());
		urlParameterList.add(new LoadReportPageInfoParameter());
		urlParameterList.add(new ViewReportPerMessagePostParameter());
		urlParameterList.add(new SaveNewMessageRequestParameter());
		urlParameterList.add(new ReportSmsStatusRequestParameter());
		urlParameterList.add(new ImportSmsGroupContactsRequestParameter());
		
		Map<String, UserPageRequestParameter>  localUrlParameters = new LinkedHashMap<>();
		for (UserPageRequestParameter userRequest : urlParameterList) {
			localUrlParameters.put(userRequest.getParameterKey(), userRequest);
		}	

		urlParameters = Collections.unmodifiableMap(localUrlParameters);
		urlParameterValues = Collections.unmodifiableList(urlParameterList);
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
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_VIEW_REPORT_DIAGRAM, ViewReportByDateRequestPostParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_EDIT_SMS_GROUP_REC, EditSmsGroupRecordRequestParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_LOAD_SMS_PANEL_INFO, LoadSmsGroupPageInfoRequestParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_LOAD_REPORT_PAGE_INFO, LoadReportPageInfoParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_VIEW_REPORT_PER_MESSAGE, ViewReportPerMessagePostParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_SAVE_NEW_MESSAGE, SaveNewMessageRequestParameter.URL_PARAMETER);
		localUserRequestCommandLookups.put(CommandTypeParameter.CMD_IMPORT_CONTACTS, ImportSmsGroupContactsRequestParameter.URL_PARAMETER);
		userRequestCommandLookups = Collections.unmodifiableMap(localUserRequestCommandLookups);
	}

	private static void linkCommandTypeToExecutedCommand() {
		if (userRequestCommands != null) 
			return;
		
		Map<CommandTypeParameter, Class<?>> localUserRequestCommands = new EnumMap<>(CommandTypeParameter.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_VIEW_REPORT_DIAGRAM, DoViewReportByPeriodCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_DO_LOGIN_USER, DoLoginFormRequestCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_NAMES, DoViewSmsGroupNameCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_LOAD_SMS_GROUP_REC, DoViewSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_OPEN_NEW_SMS_REC, DoOpenNewSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_DELETE_SMS_GROUP_REC, DoDeleteSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_SAVE_SMS_GROUP_REC, DoSaveSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_EDIT_SMS_GROUP_REC, DoEditSmsGroupRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_LOAD_SMS_PANEL_INFO, DoLoadSmsPanelPageDataCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_LOAD_REPORT_PAGE_INFO, DoLoadReportPageInfoCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_VIEW_REPORT_PER_MESSAGE, DoViewReportPerMessageCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_SAVE_NEW_MESSAGE, DoSaveNewMessageRecCommand.class);
		localUserRequestCommands.put(CommandTypeParameter.CMD_IMPORT_CONTACTS, DoImportSmsGroupContacts.class);
		userRequestCommands = Collections.unmodifiableMap(localUserRequestCommands);
	}	
}
