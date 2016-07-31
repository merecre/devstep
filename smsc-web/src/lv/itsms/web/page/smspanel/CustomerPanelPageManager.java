package lv.itsms.web.page.smspanel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lt.isms.web.CommandTypeParameter;
import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.UserPageRequestParameter;

/**
 * 
 * @author DMC
 * 
 * Returns list of commands that would be executed.
 * List of executed commands based on user requests.
 *
 */

public class CustomerPanelPageManager {

	CustomerPanelCommandFactory commandFactory;

	private List<PageRequestCommand> commandExecutionSequence;

	public CustomerPanelPageManager(CustomerPanelCommandFactory factory) {		

		this.commandFactory = factory;
		this.commandExecutionSequence = new LinkedList<>();
	}

	public List<PageRequestCommand> selectUserRequestedCommand(HttpServletRequest request) {		

		CommandTypeParameter commandRequestID = parseUserRequestAndReturnCommandIdToExecute(request);		
		populateCommandsToExecute(commandRequestID);	
		return commandExecutionSequence;
	}

	private CommandTypeParameter parseUserRequestAndReturnCommandIdToExecute(HttpServletRequest request) {

		Map<CommandTypeParameter, String> userRequestCommandLookups = CommandTypeParameter.getUserRequestCommandLookups();
		Map<String, UserPageRequestParameter> urlParameters = CommandTypeParameter.getUserRequestParameters();

		for (CommandTypeParameter commandToLookupID : userRequestCommandLookups.keySet()) {

			String userRequestParameterKey = userRequestCommandLookups.get(commandToLookupID);
			UserPageRequestParameter userRequest = urlParameters.get(userRequestParameterKey);
			userRequest.update(request);
			if (userRequest.isRequested()) {
				return commandToLookupID;
			}
		}

		return CommandTypeParameter.NO_COMMAND;
	}
	
	private void populateCommandsToExecute(CommandTypeParameter commandRequestID) {

		commandExecutionSequence.clear();		
		while (true) {
			PageRequestCommand command = commandFactory.make(commandRequestID);			
			commandExecutionSequence.add(command);			
			commandRequestID = getNextSequencedCommand(commandRequestID);		
			if (!isMoreCommand(commandRequestID)) {
				break;
			}
		}
	}

	private boolean isMoreCommand(CommandTypeParameter commandRequest) {
		return commandRequest != CommandTypeParameter.NO_COMMAND;
	}

	private CommandTypeParameter getNextSequencedCommand (CommandTypeParameter currentCommandID) {

		CommandTypeParameter nextSequencedCommandID = CommandTypeParameter.NO_COMMAND;
		if (currentCommandID == CommandTypeParameter.CMD_DELETE_SMS_GROUP_REC) {
			nextSequencedCommandID = CommandTypeParameter.CMD_LOAD_SMS_GROUP_NAMES;
		} else if (currentCommandID == CommandTypeParameter.CMD_OPEN_NEW_SMS_REC) {
			nextSequencedCommandID = CommandTypeParameter.CMD_LOAD_SMS_GROUP_NAMES;
		} 
		return nextSequencedCommandID;
	}
}
