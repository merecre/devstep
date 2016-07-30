package lv.itsms.web.page.smspanel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.page.smspanel.request.UserRequestCommandLookup;

/**
 * 
 * @author DMC
 * 
 * Returns list of commands that would be executed.
 * List of executed commands based on user requests.
 *
 */

public class CustomerPanelPageManager {

	CustomerPanelCommandFactory factory;

	private List<PageRequestCommand> commandExecutionSequence;

	Map<CommandType, UserRequestCommandLookup> userRequestCommandLookups;

	public CustomerPanelPageManager(CustomerPanelCommandFactory factory, 
			Map<CommandType, UserRequestCommandLookup> commandLookups) {		
		
		this.factory = factory;
		this.userRequestCommandLookups = commandLookups;
		this.commandExecutionSequence = new LinkedList<>();
	}

	public List<PageRequestCommand> selectUserRequestCommand(HttpServletRequest request) {		

		CommandType commandRequestID = parseUserRequestAndReturnCommandIdToExecute(request);		
		populateCommandsToExecute(commandRequestID);	
		return commandExecutionSequence;
	}

	private void populateCommandsToExecute(CommandType commandRequestID) {

		commandExecutionSequence.clear();		
		while (true) {
			PageRequestCommand command = factory.make(commandRequestID);			
			commandExecutionSequence.add(command);			
			commandRequestID = getNextSequencedCommand(commandRequestID);		
			if (!isMoreCommand(commandRequestID)) {
				break;
			}
		}
	}

	private boolean isMoreCommand(CommandType commandRequest) {
		return commandRequest != CommandType.NO_COMMAND;
	}

	private CommandType parseUserRequestAndReturnCommandIdToExecute(HttpServletRequest request) {

		for (CommandType commandToLookupID : userRequestCommandLookups.keySet()) {
			UserRequestCommandLookup commandLookup = userRequestCommandLookups.get(commandToLookupID); 
			CommandType commandToExecute = commandLookup.parse(request);
			if (commandLookup.isRequested()) {
				return commandToExecute;
			}
		}

		return CommandType.NO_COMMAND;
	}

	private CommandType getNextSequencedCommand (CommandType currentCommandID) {

		CommandType nextSequencedCommandID = CommandType.NO_COMMAND;
		if (currentCommandID == CommandType.CMD_DELETE_SMS_GROUP_REC) {
			nextSequencedCommandID = CommandType.CMD_LOAD_SMS_GROUP_NAMES;
		} else if (currentCommandID == CommandType.CMD_OPEN_NEW_SMS_REC) {
			nextSequencedCommandID = CommandType.CMD_LOAD_SMS_GROUP_NAMES;
		} 
		return nextSequencedCommandID;
	}
}
