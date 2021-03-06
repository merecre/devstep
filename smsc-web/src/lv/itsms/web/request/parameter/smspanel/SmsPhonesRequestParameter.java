package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SmsPhonesRequestParameter extends UserPageRequestParameter {

	public final static String PHONE_PARAMETER_KEY = "phone";

	public SmsPhonesRequestParameter() {
		super(PHONE_PARAMETER_KEY);
	}

	@Override
	public void update(HttpServletRequest request) {
		this.parameterValues = request.getParameterValues(parameterKey);
	}

}
