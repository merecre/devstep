package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.service.Repository;

public class DeleteSmsGroupPostRequestParameter extends UserPageRequest {

		public final static String DELETE_COMMAND_PARAMETER = "delete";
			
		//SmsGroupCommand deleteSmsGroup;
		Repository repository;
		
		public DeleteSmsGroupPostRequestParameter() {
			super(DELETE_COMMAND_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			
			parameterValue = request.getParameter(parameterKey);
			
		}
}
