package lv.itsms.web.page.smspanel;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsGroupBuilder;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.request.validator.UserRequestValidator;
import lv.itsms.web.request.validator.smsgroup.PhoneNumberValidator;
import lv.itsms.web.request.validator.smsgroup.SmsGroupFieldsValidator;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

public class DoSaveSmsGroupRecCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoSaveSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {
		SmsGroup smsGroup = getInputedSmsGroupRecord();
		String[] phoneNumbers = getInputedPhoneNumbers();
		
		factory.getSession().updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);				
		factory.getSession().updateSessionAttribute(SmsPhoneRequestParameter.PHONE_PARAMETER_KEY, phoneNumbers);
		
		boolean result = validateSmsGroupFields(smsGroup);
		if (result) { 
			result = validatePhoneNumbers(phoneNumbers);
			if (result) {		
				Repository repository = factory.getRepository();
				repository.updateSmsGroup(smsGroup, phoneNumbers);
			}
		}
	}
	
	private SmsGroup getInputedSmsGroupRecord() {
		SmsGroupBuilder smsGroupBuilder = new SmsGroupBuilder();
		return smsGroupBuilder.build(factory.getRequest());		
	}
	
	private String[] getInputedPhoneNumbers() {
		UserPageRequestParameter phoneNumberUserRequest = factory.getUserPageRequest(SmsPhoneRequestParameter.PHONE_PARAMETER_KEY);
		phoneNumberUserRequest.update(factory.getRequest());
		return phoneNumberUserRequest.getParameterValues();		
	}
	
	private boolean validateSmsGroupFields(SmsGroup smsGroup) {
		SmsGroupFieldsValidator smsGroupValidator = factory.getFieldsValidator();
		
		//smsGroupValidator.setSmsGroup(smsGroup);	
		smsGroupValidator.prepareRules();
		return smsGroupValidator.validate(smsGroup);			
	}
	
	private boolean validatePhoneNumbers(String[] phoneNumbers) {		

		PhoneNumberValidator phoneNumberValidator = factory.getPhoneNumberValidator();
		phoneNumberValidator.prepareRules();
		for (String phoneNumber : phoneNumbers) {
			if (!phoneNumberValidator.validate(phoneNumber)) {
				return false;
			}
		}		
		return true;
	}
}
