package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ImportSmsGroupContactsRequestParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "importfile";

	public ImportSmsGroupContactsRequestParameter() {
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
