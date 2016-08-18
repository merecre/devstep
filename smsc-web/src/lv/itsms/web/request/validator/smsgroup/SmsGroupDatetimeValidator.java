package lv.itsms.web.request.validator.smsgroup;

import java.util.Calendar;

import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import lv.itsms.web.request.validator.rule.DateBeforeRule;
import lv.itsms.web.request.validator.rule.Rule;

public class SmsGroupDatetimeValidator extends UserRequestValidatorImpl {

	final static String DATE_ERROR = "Date.less.than.today";
	
	Rule dateBeforeRule;
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		java.util.Date today = Calendar.getInstance().getTime();
		Rule dateBeforeRule = new DateBeforeRule(today, DATE_ERROR);
		rules.add(dateBeforeRule);			
	}

}
