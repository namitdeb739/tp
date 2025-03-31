package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand and returns an
     * MapCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args, String userInput) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.lastUserInput(userInput) + MESSAGE_INVALID_COMMAND_FORMAT,
                    MapCommand.MESSAGE_USAGE), pe);
        }

        return new MapCommand(index);
    }
}
