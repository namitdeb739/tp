package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnpaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tenant.Phone;

/**
 * Parses input arguments and creates a new UnpaidCommand object
 */
public class UnpaidCommandParser implements Parser<UnpaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnpaidCommand
     * and returns an UnpaidCommand object for execution.
     *
     * @param args the user input arguments.
     * @param userInput the full user input string.
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnpaidCommand parse(String args, String userInput) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpaidCommand.MESSAGE_USAGE));
        }

        Phone phone;
        try {
            phone = ParserUtil.parsePhone(trimmedArgs);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpaidCommand.MESSAGE_USAGE), pe);
        }

        return new UnpaidCommand(phone);
    }
}

