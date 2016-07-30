package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class SmsPhoneRequestParameter extends UserPageRequestParameter {

	public final static String PHONE_PARAMETER_KEY = "phone";

	public SmsPhoneRequestParameter() {
		super(PHONE_PARAMETER_KEY);
	}

	@Override
	public void update(HttpServletRequest request) {
		this.parameterValues = request.getParameterValues(parameterKey);
	}

}
