package lv.itsms.web.command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

/**
 * 
 * @author DMC
 * 
 * Returns list of commands that would be executed.
 * List of executed commands based on user requests.
 *
 */

public class UserRequestCommandManager {

	CommandFactory commandFactory;

	private List<PageRequestCommand> commandExecutionSequence;

	public UserRequestCommandManager(CommandFactory userRequestCommandFactory) {		
		this.commandFactory = userRequestCommandFactory;
		this.commandExecutionSequence = new LinkedList<>();
	}

	public List<PageRequestCommand> selectUserRequestedCommand(HttpServletRequest request) {		
		List<CommandTypeParameter> commandTypeParameters = parseUserRequestAndReturnCommandIdToExecute(request);

		commandExecutionSequence.clear();	
		for (CommandTypeParameter commandRequestID:commandTypeParameters) {
			populateCommandsToExecute(commandRequestID);	
		}
		return commandExecutionSequence;
	}

	private List<CommandTypeParameter> parseUserRequestAndReturnCommandIdToExecute(HttpServletRequest request) {

		List<CommandTypeParameter> commandTypeParameters = new ArrayList<>();
		Map<CommandTypeParameter, String> userRequestCommandLookups = CommandTypeSingleton.getInstance().getCommandTypeByUserRequestParameter();
		List<UserPageRequestParameter> urlParameters = CommandTypeSingleton.getInstance().getUrlParameterValues();

		for (CommandTypeParameter commandToLookupID : userRequestCommandLookups.keySet()) {
			String userRequestParameterKey = userRequestCommandLookups.get(commandToLookupID);

			for (UserPageRequestParameter userRequest : urlParameters) {
				if (userRequest.getParameterKey().equals(userRequestParameterKey)) {
					userRequest.update(request);
					if (userRequest.isRequested()) {
						commandTypeParameters.add(commandToLookupID);
					}
				}
			}
		}

		if (commandTypeParameters.isEmpty()) {
			commandTypeParameters.add(CommandTypeParameter.NO_COMMAND);
		}

		return commandTypeParameters;
	}

	private void populateCommandsToExecute(CommandTypeParameter commandRequestID) {	
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
