package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private String lastUserInput;

    /**
     * Sets the user input for the command.
     *
     * @return CommandType of the command.
     */
    public Command withUserInput(String userInput) {
        this.lastUserInput = userInput;
        return this;
    }

    public String getLastUserInput() {
        return Messages.lastUserInput(lastUserInput);
    }

    public CommandResult buildCommandResult(String feedbackToUser) {
        return new CommandResult(String.format(this.getLastUserInput() + feedbackToUser));
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

}
