package lv.itsms.web.request.validator.sms;

import lv.itsms.web.request.validator.smsgroup.SmsGroupDescriptionValidator;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;
import transfer.validator.PhoneNumberValidator;
import transfer.validator.SmsDatetimeValidator;
import transfer.validator.SmsMessageValidator;
import transfer.validator.SmsSenderValidator;
import transfer.validator.UserRequestValidatorImpl;

public class SmsFieldsValidator extends UserRequestValidatorImpl {
		
	SmsMessageValidator messageValidator;
	SmsDatetimeValidator datetimeValidator;
	PhoneNumberValidator phoneNumberValidator;
	SmsSenderValidator smsSenderValidator;
	
	public SmsFieldsValidator() {
		messageValidator = new SmsMessageValidator();
		datetimeValidator = new SmsDatetimeValidator();
		phoneNumberValidator = new PhoneNumberValidator();
		smsSenderValidator = new SmsSenderValidator();
	}
	
	@Override
	public void prepareRules() {
		messageValidator.prepareRules();
		datetimeValidator.prepareRules();
		phoneNumberValidator.prepareRules();
		smsSenderValidator.prepareRules();
	}
	
	@Override
	public boolean validate(Object object) {
		Sms sms = (Sms)object;
		
		String sender = sms.getSender();
		smsSenderValidator.validate(sender);
		
		String phoneNumber = sms.getPhoneNumber();
		phoneNumberValidator.validate(phoneNumber);
		
		String smsGroupMessage = sms.getMessage();
		messageValidator.validate(smsGroupMessage);
		
		java.util.Date date = sms.getSendTime();
		datetimeValidator.validate(date);
		
		return true;
	}	
}
