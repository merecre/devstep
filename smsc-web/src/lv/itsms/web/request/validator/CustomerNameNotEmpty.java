package lv.itsms.web.request.validator;

public class CustomerNameNotEmpty implements Rule {

	final static String ERROR_MESSAGE = "Customer name is mandatory."; 

	String customerName;

	public CustomerNameNotEmpty(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public boolean doRule() throws RuntimeException {

		if (customerName!=null && !customerName.isEmpty()) {
			return true;
		}

		throw new RuntimeException(ERROR_MESSAGE);
	}
}
