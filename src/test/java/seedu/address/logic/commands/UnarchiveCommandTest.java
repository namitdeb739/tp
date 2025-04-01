package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Tenant;

public class UnarchiveCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());
        Tenant tenantToArchive = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.archiveTenant(tenantToArchive);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Tenant tenantToUnarchive = model.getArchivedTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        Tenant unarchivedTenant = tenantToUnarchive.unarchive();
        String expectedMessage = String.format(
                UnarchiveCommand.MESSAGE_SUCCESS,
                Messages.format(unarchivedTenant));

        Model expectedModel = new ModelManager(model.getTenantTracker(), new UserPrefs());
        expectedModel.unarchiveTenant(tenantToUnarchive);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getArchivedTenantList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_PERSON);

        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));
        assertTrue(unarchiveFirstCommand.equals(new UnarchiveCommand(INDEX_FIRST_PERSON)));
        assertFalse(unarchiveFirstCommand.equals(null));
        assertFalse(unarchiveFirstCommand.equals(1));
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(targetIndex);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }
}
