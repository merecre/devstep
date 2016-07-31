package lv.itsms.web.request.parameter.menu;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class CustomerMenuRequestParameter extends UserPageRequestParameter {

	public final static String MENU_URL_PARAMETER = "cusMenu";

	public final static String SMS_REPORT_GROUP_LIST_URL_VALUE = "smsPanel";
	
	public CustomerMenuRequestParameter() {
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

}
