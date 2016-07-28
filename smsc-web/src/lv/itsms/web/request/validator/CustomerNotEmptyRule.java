package lv.itsms.web.request.validator;
import transfer.domain.Customer;

public class CustomerNotEmptyRule implements Rule {

	Customer customer;

	public CustomerNotEmptyRule(Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean doRule() throws RuntimeException {
		if ((customer==null) || (customer.getId()==0)) {
			throw new RuntimeException("Incorrect Login or Password");
		}	

		return true;
	}

}
