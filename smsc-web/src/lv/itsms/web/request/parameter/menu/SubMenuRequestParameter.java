package lv.itsms.web.request.parameter.menu;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SubMenuRequestParameter extends UserPageRequestParameter {

	final static String MENU_URL_PARAMETER = "subid";

	public SubMenuRequestParameter() {
		super(MENU_URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {

		String menuId = request.getParameter(parameterKey);

		parameterValue = menuId;
	}

}
