package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class EditSmsGroupRecordRequestParameter extends UserPageRequestParameter {
	public final static String URL_PARAMETER = "editGroup"; 

	public EditSmsGroupRecordRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);

		if (parameterValue == null ) {
			parameterValue = "";
		}			
	}

	@Override
	public boolean isRequested() {
		return this.parameterValue.equals("true");
	}
	
	
}
