package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class SubMenuRequestParameter extends UserPageRequest {

	final static String MENU_PARAMETER = "subid";
	
	public SubMenuRequestParameter() {
		super(MENU_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		
		String menuId = request.getParameter(parameterKey);
	
		parameterValue = menuId;
	}

}
