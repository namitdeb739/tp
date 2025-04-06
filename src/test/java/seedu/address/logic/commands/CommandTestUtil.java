package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAMILY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GIVEN_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TenantTracker;
import seedu.address.model.tenant.NameContainsKeywordsPredicate;
import seedu.address.model.tenant.Tenant;
import seedu.address.testutil.EditTenantDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_GIVEN_NAME_AMY = "Amy";
    public static final String VALID_FAMILY_NAME_AMY = "Bee";
    public static final String VALID_GIVEN_NAME_BOB = "Bob";
    public static final String VALID_FAMILY_NAME_BOB = "Choo";
    public static final String VALID_PHONE_AMY = "99765432";
    public static final String VALID_PHONE_BOB = "99965432";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1, 123456";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3, 987654";
    public static final String VALID_TAG_HDB = "Hdb";
    public static final String VALID_TAG_LANDED = "Landed";

    public static final String GIVEN_NAME_DESC_AMY = " " + PREFIX_GIVEN_NAME + VALID_GIVEN_NAME_AMY;
    public static final String FAMILY_NAME_DESC_AMY = " " + PREFIX_FAMILY_NAME + VALID_FAMILY_NAME_AMY;
    public static final String NAME_DESC_AMY = GIVEN_NAME_DESC_AMY + FAMILY_NAME_DESC_AMY;
    public static final String GIVEN_NAME_DESC_BOB = " " + PREFIX_GIVEN_NAME + VALID_GIVEN_NAME_BOB;
    public static final String FAMILY_NAME_DESC_BOB = " " + PREFIX_FAMILY_NAME + VALID_FAMILY_NAME_BOB;
    public static final String NAME_DESC_BOB = GIVEN_NAME_DESC_BOB + FAMILY_NAME_DESC_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_LANDED = " " + PREFIX_TAG + VALID_TAG_LANDED;
    public static final String TAG_DESC_HDB = " " + PREFIX_TAG + VALID_TAG_HDB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_GIVEN_NAME + "   " + PREFIX_FAMILY_NAME + "   ";

    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTenantDescriptor DESC_AMY;
    public static final EditCommand.EditTenantDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditTenantDescriptorBuilder().withName(VALID_GIVEN_NAME_AMY, VALID_FAMILY_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_LANDED).build();
        DESC_BOB = new EditTenantDescriptorBuilder().withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HDB, VALID_TAG_LANDED).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)} that
     * takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain
     * unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TenantTracker expectedTenantTracker = new TenantTracker(actualModel.getTenantTracker());
        List<Tenant> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTenantList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTenantTracker, actualModel.getTenantTracker());
        assertEquals(expectedFilteredList, actualModel.getFilteredTenantList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in
     * the {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTenantList().size());

        Tenant person = model.getFilteredTenantList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().familyName.split("\\s+");
        model.updateFilteredTenantList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTenantList().size());
    }

}
