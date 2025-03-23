package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tenant.AddressContainsKeywordsPredicate;

/**
 * Test class for {@code FilterCommandParser}.
 */
public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    /**
     * Tests parsing an empty argument, expecting a parse exception.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Tests parsing valid arguments, expecting a successful parse into a {@code FilterCommand}.
     */
    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new AddressContainsKeywordsPredicate(Arrays.asList("119077", "Ridge")));
        assertParseSuccess(parser, "119077 Ridge", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 119077 \n \t Ridge  \t", expectedFilterCommand);
    }
}
