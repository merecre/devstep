package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SaveSmsNewGroupRequestParameter extends UserPageRequestParameter {

	public final static String SAVE_COMMAND_URL_PARAMETER = "save";

	public SaveSmsNewGroupRequestParameter() {
		super(SAVE_COMMAND_URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {	
		parameterValue = request.getParameter(parameterKey);
	}
}
