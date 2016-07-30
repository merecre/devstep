package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;

public class OpenNewSmsGroupRecRequestParameter extends UserPageRequestParameter {
	
	public final static String OPEN_NEW_SMS_GROUP_URL_PARAMETER = "newGroup";
		
	public OpenNewSmsGroupRecRequestParameter() {
		super(OPEN_NEW_SMS_GROUP_URL_PARAMETER);
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
