package lv.itsms.web.page.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsGroupBuilder;
import lv.itsms.web.request.parameter.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import transfer.domain.SmsGroup;

public class DoSaveSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoSaveSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {

		SmsGroupBuilder smsGroupBuilder = new SmsGroupBuilder();
		SmsGroup smsGroup = smsGroupBuilder.build(factory.getRequest());

		UserPageRequest phoneNumberUserRequest = factory.getUserPageRequest(SmsPhoneRequestParameter.PHONE_PARAMETER_KEY);
		phoneNumberUserRequest.update(factory.getRequest());
		String[] phoneNumbers = phoneNumberUserRequest.getParameterValues();
		Repository repository = factory.getRepository();
		repository.updateSmsGroup(smsGroup, phoneNumbers);
	}
}
