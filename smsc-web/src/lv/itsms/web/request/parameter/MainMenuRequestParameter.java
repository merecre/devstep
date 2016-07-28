package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class MainMenuRequestParameter extends UserPageRequest {

	final static String MENU_PARAMETER = "id";

	final static String DEFAULT_MENU_PARAMETER = "Home";

	public MainMenuRequestParameter() {
		super(MENU_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {

		String menuId = request.getParameter(parameterKey);

		if ((menuId==null) || menuId.equals("")) {
			menuId = (parameterValue == null) ? DEFAULT_MENU_PARAMETER :  parameterValue ;
		}

		parameterValue = menuId;
	}

}
