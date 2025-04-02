package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaidCommand;
import seedu.address.model.tenant.Phone;

/**
 * Tests for {@code PaidCommandParser}.
 */
public class PaidCommandParserTest {
    private PaidCommandParser parser = new PaidCommandParser();


    /* public void parse_emptyArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    } */

    /**
     * Tests parsing valid arguments, expecting a successful parse into a {@code PaidCommand}.
     */
    @Test
    public void parse_validArgs_returnsPaidCommand() {
        Phone phone = new Phone("12345678");
        PaidCommand expectedCommand = new PaidCommand(phone);

        // Test without leading and trailing whitespaces
        assertParseSuccess(parser, "12345678", expectedCommand);

        // Test with leading and trailing whitespaces
        assertParseSuccess(parser, "  12345678  ", expectedCommand);
    }

    /*public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE);

        // Test with invalid phone number containing letters
        assertParseFailure(parser, "abc123", expectedMessage.trim());

        // Test with an incomplete phone number
        assertParseFailure(parser, "1234567", expectedMessage.trim());
    }*/
}

