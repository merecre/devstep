package lv.itsms.web.page.smspanel;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsBuilder;
import lv.itsms.web.request.parameter.SmsGroupBuilder;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhonesRequestParameter;
import lv.itsms.web.request.validator.sms.SmsFieldsValidator;
import lv.itsms.web.request.validator.smsgroup.SmsGroupFieldsValidator;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;
import transfer.validator.PhoneNumberValidator;

public class DoSaveNewMessageRecCommand implements PageRequestCommand {

	private final static String COMMAND_INFO = "Message.saved.successesfully";
	
	CustomerPanelCommandFactory factory;

	public DoSaveNewMessageRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() throws Exception {
		Sms sms = getInputedSmsRecord();

		factory.getSession().updateSessionAttribute(Session.SESSION_SMSDATA_PARAMETER, sms);				

		boolean result = validateSmsGroupFields(sms);
		if (result) { 
			Repository repository = factory.getRepository();
			repository.insertSms(sms);
			factory.getSession().updateSessionAttribute(Session.SESSION_INFORMATION_PARAMETER, COMMAND_INFO);
		}
	}
	
	private Sms getInputedSmsRecord() {
		SmsBuilder smsBuilder = new SmsBuilder();
		return smsBuilder.build(factory.getRequest());		
	}
	
	private boolean validateSmsGroupFields(Sms sms) {
		SmsFieldsValidator smsValidator = factory.getSmsFieldsValidator();
		
		smsValidator.prepareRules();
		return smsValidator.validate(sms);			
	}
}
