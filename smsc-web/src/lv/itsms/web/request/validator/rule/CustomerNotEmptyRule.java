package lv.itsms.web.request.validator.rule;
import transfer.domain.Customer;
import transfer.validator.Rule;

public class CustomerNotEmptyRule implements Rule {

	Customer customer;

	public CustomerNotEmptyRule(Customer customer) {
		this.customer = customer;
	}

	public CustomerNotEmptyRule() {

	}

	@Override
	public boolean doRule(Object object) {
		Customer customer = (Customer)object;

		if ((customer==null) || (customer.getId()==0)) {
			throw new RuntimeException("Incorrect Login or Password");
		}	

		return true;
	}
}
