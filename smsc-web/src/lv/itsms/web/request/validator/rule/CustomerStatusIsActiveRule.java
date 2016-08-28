package lv.itsms.web.request.validator.rule;

import transfer.domain.Customer;
import transfer.validator.Rule;

public class CustomerStatusIsActiveRule implements Rule {

	String errorMessage;

	public CustomerStatusIsActiveRule(String erorrMessage) {
		this.errorMessage = erorrMessage;
	}

	@Override
	public boolean doRule(Object object) {
		String status = (String) object;
		if (status!=null && status.equals(Customer.STATUS_ACTIVE)) {
			return true;
		}

		throw new RuntimeException(errorMessage);
	}
}
