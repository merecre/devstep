package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequest;

public class SaveSmsNewGroupRequestParameter extends UserPageRequest {

	public final static String SAVE_COMMAND_PARAMETER = "save";
		
	public SaveSmsNewGroupRequestParameter() {
		super(SAVE_COMMAND_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {	
		parameterValue = request.getParameter(parameterKey);
	}
}
