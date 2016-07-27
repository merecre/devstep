package lv.itsms.web.page;

public class ErrorSmsGroupCommand implements PageRequestCommand {

	private final static String ERROR_COMMAND = "Unknown command";
	
	@Override
	public void execute() {
		System.err.println(ERROR_COMMAND);
	}

}
