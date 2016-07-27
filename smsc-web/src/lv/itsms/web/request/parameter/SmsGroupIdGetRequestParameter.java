package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class SmsGroupIdGetRequestParameter extends UserPageRequest {
	public final static String GROUP_ID_GET_PARAMETER = "groupID"; 
	
	public SmsGroupIdGetRequestParameter() {
		super(GROUP_ID_GET_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
		
		if (parameterValue == null ) {
			parameterValue = "";
		}			
	}	
}
