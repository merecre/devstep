package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;

public class CustomerMenuRequestParameter extends UserPageRequest {

	public final static String MENU_PARAMETER = "cusMenu";

	public CustomerMenuRequestParameter() {
		super(MENU_PARAMETER);
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
