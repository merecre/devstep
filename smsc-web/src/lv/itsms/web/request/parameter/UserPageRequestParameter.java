package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

/**
 * Parses URL by user request parameters and stores URL parameter values 
 */

public abstract class UserPageRequestParameter {

	protected String parameterKey;

	protected String parameterValue;

	protected String[] parameterValues;

	public abstract void update(HttpServletRequest request);

	public UserPageRequestParameter(String parameterKey) {
		this.parameterKey = parameterKey;
	}

	public String getParameterKey() {
		return parameterKey;
	}

	public String getParameter() {
		return parameterValue;
	}

	public void setParameter(String parameter) {
		this.parameterValue = parameter;
	}

	public String[] getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String[] parameterValues) {
		this.parameterValues = parameterValues;
	}

	public boolean isRequested() {
		return  parameterValue!= null || parameterValues != null;
	}
}
