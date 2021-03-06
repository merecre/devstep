package lv.itsms.web.page.smspanel;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.request.parameter.SmsGroupBuilder;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhonesRequestParameter;
import lv.itsms.web.request.validator.smsgroup.SmsGroupFieldsValidator;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;
import transfer.validator.PhoneNumberValidator;

public class DoSaveSmsGroupRecCommand implements PageRequestCommand {

	private final static String COMMAND_INFO = "Message.saved.successesfully";
	
	CustomerPanelCommandFactory factory;

	public DoSaveSmsGroupRecCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() throws Exception {
		
		SmsGroup smsGroup = getInputedSmsGroupRecord();
		String[] phoneNumbers = getInputedPhoneNumbers();
		
		storeUserInputedSmsGroupData(smsGroup, phoneNumbers);
		
		boolean result = validateSmsGroupFields(smsGroup);
		if (result) { 
			result = validatePhoneNumbers(phoneNumbers);
			if (result) {		
				Repository repository = factory.getRepository();
				
				smsGroup.setStatus(SmsGroup.STATUS_ACTIVE);
				repository.updateSmsGroup(smsGroup, phoneNumbers);
				factory.getSession().updateSessionAttribute(Session.SESSION_INFORMATION_PARAMETER, COMMAND_INFO);
			}
		}
	}
	
	private void storeUserInputedSmsGroupData(SmsGroup smsGroup, String[] phoneNumbers) {
			
		factory.getSession().updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);		
		factory.getSession().updateSessionAttribute(SmsPhonesRequestParameter.PHONE_PARAMETER_KEY, phoneNumbers);
	}
	
	private SmsGroup getInputedSmsGroupRecord() {
		SmsGroupBuilder smsGroupBuilder = new SmsGroupBuilder();
		return smsGroupBuilder.build(factory.getRequest());		
	}
	
	private String[] getInputedPhoneNumbers() {
		UserPageRequestParameter phoneNumberUserRequest = factory.getUserPageRequest(SmsPhonesRequestParameter.PHONE_PARAMETER_KEY);
		phoneNumberUserRequest.update(factory.getRequest());
		return phoneNumberUserRequest.getParameterValues();		
	}
	
	private boolean validateSmsGroupFields(SmsGroup smsGroup) {
		SmsGroupFieldsValidator smsGroupValidator = factory.getFieldsValidator();
		
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
