package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;

public class ViewSmsGroupRecRequestParameter extends UserPageRequestParameter {
	
	public final static String VIEW_SMS_GROUP_REV_URL = "viewGroup";
		
	public ViewSmsGroupRecRequestParameter() {
		super(VIEW_SMS_GROUP_REV_URL);
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
