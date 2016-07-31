package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.Repository;

public class DeleteSmsGroupPostRequestParameter extends UserPageRequestParameter {

	public final static String DELETE_COMMAND_URL_PARAMETER = "delete";

	//SmsGroupCommand deleteSmsGroup;
	Repository repository;

	public DeleteSmsGroupPostRequestParameter() {
		super(DELETE_COMMAND_URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {

		parameterValue = request.getParameter(parameterKey);

	}
}
