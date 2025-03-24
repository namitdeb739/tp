package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTenants.JAMES;
import static seedu.address.testutil.TypicalTenants.MIKE;
import static seedu.address.testutil.TypicalTenants.OLIVER;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.AddressContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTenantTracker(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    /**
     * Tests the execution of {@code FilterCommand} when no keywords are provided.
     * Verifies that no tenants are found.
     */
    @Test
    public void execute_zeroKeywords_noTenantFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredTenantList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTenantList());
    }

    /**
     * Tests the execution of {@code FilterCommand} when multiple keywords are provided.
     * Verifies that tenants with addresses containing any of the keywords are found.
     */
    @Test
    public void execute_multipleKeywords_multipleTenantsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = preparePredicate("Lower Kent Ridge Rd");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredTenantList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JAMES, MIKE, OLIVER), model.getFilteredTenantList());
    }

    /**
     * Tests the execution of {@code FilterCommand} when a partial keyword is provided.
     * Verifies that tenants with addresses containing the partial keyword are found.
     */
    @Test
    public void execute_partialKeyword_tenantsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = preparePredicate("Kent");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredTenantList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JAMES, MIKE, OLIVER), model.getFilteredTenantList());
    }

    /**
     * Tests the execution of {@code FilterCommand} when a non-matching keyword is provided.
     * Verifies that no tenants are found.
     */
    @Test
    public void execute_nonMatchingKeyword_noTenantFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = preparePredicate("NonMatchingAddress");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredTenantList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTenantList());
    }

    /**
     * Tests the {@code toString} method of {@code FilterCommand}.
     * Verifies that the string representation of the command is correct.
     */
    @Test
    public void toStringMethod() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Arrays.asList("keyword"));
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
