package lv.itsms.web.page.smspanel;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsGroupBuilder;
import lv.itsms.web.request.parameter.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
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

		UserPageRequestParameter phoneNumberUserRequest = factory.getUserPageRequest(SmsPhoneRequestParameter.PHONE_PARAMETER_KEY);
		phoneNumberUserRequest.update(factory.getRequest());
		String[] phoneNumbers = phoneNumberUserRequest.getParameterValues();
		
		/* /TODO/
		* VALIDATE USER INPUTED SMS GROUP DATA
		* AND PHONE NUMBER DATA
		*/
		
		Repository repository = factory.getRepository();
		if (phoneNumbers != null) {
			repository.updateSmsGroup(smsGroup, phoneNumbers);
		}
	}
}
