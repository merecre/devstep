package lv.itsms.web.request.validator.smsgroup;

import transfer.domain.SmsGroup;
import transfer.validator.SmsDatetimeValidator;
import transfer.validator.SmsMessageValidator;
import transfer.validator.SmsSenderValidator;
import transfer.validator.UserRequestValidatorImpl;

public class SmsGroupFieldsValidator extends UserRequestValidatorImpl {
		
	SmsGroupDescriptionValidator descriptionValidator;
	SmsMessageValidator messageValidator;
	SmsDatetimeValidator datetimeValidator;
	SmsSenderValidator smsSenderValidator;
	
	public SmsGroupFieldsValidator() {
		this.descriptionValidator = new SmsGroupDescriptionValidator();
		messageValidator = new SmsMessageValidator();
		datetimeValidator = new SmsDatetimeValidator();
		smsSenderValidator = new SmsSenderValidator();
	}
	
	@Override
	public void prepareRules() {
		descriptionValidator.prepareRules();
		messageValidator.prepareRules();
		datetimeValidator.prepareRules();
		smsSenderValidator.prepareRules();
	}
	
	@Override
	public boolean validate(Object object) {
		SmsGroup smsGroup = (SmsGroup)object;

		String smsGroupDescription = smsGroup.getSmsGroupName();
		descriptionValidator.validate(smsGroupDescription);

		String smsSender = smsGroup.getSender();
		smsSenderValidator.validate(smsSender);
		
		String smsGroupMessage = smsGroup.getGroupMessage();
		messageValidator.validate(smsGroupMessage);
		
		java.util.Date date = smsGroup.getSendTime();

		datetimeValidator.validate(date);
		return true;
	}	
}
