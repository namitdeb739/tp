package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnarchiveCommand;

public class UnarchiveCommandParserTest {

    private UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1", new UnarchiveCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", "Invalid command format! \n" + UnarchiveCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", "Invalid command format! \n" + UnarchiveCommand.MESSAGE_USAGE);
    }
}
