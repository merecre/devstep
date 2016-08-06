package lv.itsms.web.command;

import java.util.Map;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

/**
 * 
 * @author DMC
 *
 * Returns commands which would process user request.
 */

public abstract class CommandFactory {

	public PageRequestCommand make(CommandTypeParameter commandRequestID) {

		Map<CommandTypeParameter, Class<?>> commands = 
				CommandTypeSingleton.getInstance().getCommandToBeExecutedByCommandType ();
		
		return getCommandByCommandType(commands, commandRequestID);
	}

	public UserPageRequestParameter getUserPageRequest(String parametrKey) {
		return CommandTypeSingleton.getInstance().getUserRequestParameters().get(parametrKey);
	}

	protected abstract Class<? extends CommandFactory> getGlazz();
	
	private PageRequestCommand getCommandByCommandType( Map<CommandTypeParameter, Class<?>> commands, CommandTypeParameter commandType) {
		
		if (commands.containsKey(commandType)) {
			Class<?> command = commands.get(commandType);		
			Class<? extends CommandFactory> clazz = getGlazz();
			try {
				return (PageRequestCommand) command.getConstructor(clazz).newInstance(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ErrorSmsGroupCommand();
	}
}

