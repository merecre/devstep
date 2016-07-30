package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class MainMenuRequestParameter extends UserPageRequestParameter {

	final static String MENU_URL_PARAMETER = "id";

	final static String DEFAULT_MENU_PARAMETER_VALUE = "Home";

	public MainMenuRequestParameter() {
		super(MENU_URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {

		String menuId = request.getParameter(parameterKey);

		if ((menuId==null) || menuId.equals("")) {
			menuId = (parameterValue == null) ? DEFAULT_MENU_PARAMETER_VALUE :  parameterValue ;
		}

		parameterValue = menuId;
	}

}
