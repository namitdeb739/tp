package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Tenant;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Tenant tenantToArchive = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        Tenant archivedTenant = tenantToArchive.archive();
        String expectedMessage = String.format(
                ArchiveCommand.MESSAGE_ARCHIVE_TENANT_SUCCESS,
                Messages.format(archivedTenant));

        Model expectedModel = new ModelManager(model.getTenantTracker(), new UserPrefs());
        expectedModel.archiveTenant(tenantToArchive); // <-- Fix here
        expectedModel.updateFilteredTenantList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredArchivedTenantList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTenantList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Tenant tenantToArchive = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        Tenant archivedTenant = tenantToArchive.archive();
        String expectedMessage = String.format(
                ArchiveCommand.MESSAGE_ARCHIVE_TENANT_SUCCESS,
                Messages.format(archivedTenant));

        Model expectedModel = new ModelManager(model.getTenantTracker(), new UserPrefs());
        expectedModel.archiveTenant(tenantToArchive);

        model.updateFilteredTenantList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredTenantList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredArchivedTenantList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTenantTracker().getTenantList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(targetIndex);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, archiveCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredTenantList(p -> false);

        assertTrue(model.getFilteredTenantList().isEmpty());
    }
}
