package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ArchiveCommand object.
 */
public class UnarchiveCommandParser implements Parser<UnarchiveCommand> {
    @Override
    public UnarchiveCommand parse(String args, String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput, args);
            return new UnarchiveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.lastUserInput(userInput) + MESSAGE_INVALID_COMMAND_FORMAT,
                    UnarchiveCommand.MESSAGE_USAGE), pe);
        }
    }
}
