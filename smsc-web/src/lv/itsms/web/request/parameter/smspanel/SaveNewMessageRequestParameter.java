package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SaveNewMessageRequestParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "savemessage";

	public SaveNewMessageRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {	
		parameterValue = request.getParameter(parameterKey);
	}

	@Override
	public boolean isRequested() {
		return this.parameterValue != null;
	}	
}
