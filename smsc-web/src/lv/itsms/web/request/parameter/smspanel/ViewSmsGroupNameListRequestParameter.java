package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ViewSmsGroupNameListRequestParameter extends UserPageRequestParameter {

	public final static String MENU_URL_PARAMETER = "viewGroups";

	public final static String SMS_REPORT_GROUP_LIST_URL_VALUE = "true";
	
	public ViewSmsGroupNameListRequestParameter() {
		super(MENU_URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {

		String customerMenuId = request.getParameter(parameterKey);

		if (customerMenuId == null) {
			customerMenuId = "";
		}

		parameterValue = customerMenuId;
	}

	@Override
	public boolean isRequested() {		
		return this.parameterValue.equals(SMS_REPORT_GROUP_LIST_URL_VALUE);
	}
}
