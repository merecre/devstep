package lv.itsms.web.request.validator.rule;

import java.util.Date;

public class DateBeforeRule implements Rule {

	Date toCompareDate;
	String errorMessage;

	public DateBeforeRule(Date toCompareDate, String errorMessage) {
		this.toCompareDate = toCompareDate;
		this.errorMessage = errorMessage;
	}

	@Override
	public boolean doRule(Object object) {
		if (object == null) 
			throw new RuntimeException(errorMessage);

		Date date = (Date)object;
		if (date.before(toCompareDate)) {
			throw new RuntimeException(errorMessage);
		}

		return true;
	}

}
