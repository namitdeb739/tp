package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpaidCommand;
import seedu.address.model.tenant.Phone;

/**
 * Contains tests for {@code UnpaidCommandParser}.
 */
public class UnpaidCommandParserTest {

    private UnpaidCommandParser parser = new UnpaidCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = String.format(UnpaidCommand.MESSAGE_USAGE);
        // Test with empty string
        assertParseFailure(parser, "", expectedMessage);
        // Test with spaces only
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsUnpaidCommand() {
        // Test with valid phone number
        Phone phone = new Phone("63213213");
        UnpaidCommand expectedCommand = new UnpaidCommand(phone);
        assertParseSuccess(parser, "63213213", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(UnpaidCommand.MESSAGE_USAGE);
        // Test with invalid phone number containing letters
        assertParseFailure(parser, "abc123", expectedMessage);
        // Test with incomplete phone number
        assertParseFailure(parser, "1234567", expectedMessage);
    }
}
