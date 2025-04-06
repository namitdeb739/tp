package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaidCommand;
import seedu.address.model.tenant.Phone;

/**
 * Tests for {@code PaidCommandParser}.
 */
public class PaidCommandParserTest {
    private PaidCommandParser parser = new PaidCommandParser();


    @Test
    public void parse_emptyArgs_throwsParseException() {
        String expectedMessage = String.format(PaidCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    /**
     * Tests parsing valid arguments, expecting a successful parse into a {@code PaidCommand}.
     */
    @Test
    public void parse_validArgs_returnsPaidCommand() {
        Phone phone = new Phone("63213213");
        PaidCommand expectedCommand = new PaidCommand(phone);

        // Test without leading and trailing whitespaces
        assertParseSuccess(parser, "63213213", expectedCommand);

        // Test with leading and trailing whitespaces
        assertParseSuccess(parser, "  63213213  ", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(PaidCommand.MESSAGE_USAGE);

        // Test with invalid phone number containing letters
        assertParseFailure(parser, "abc123", expectedMessage.trim());

        // Test with an incomplete phone number
        assertParseFailure(parser, "1234567", expectedMessage.trim());
    }
}

