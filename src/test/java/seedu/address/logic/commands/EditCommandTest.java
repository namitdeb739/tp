package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GIVEN_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditTenantDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TenantTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Tenant;
import seedu.address.testutil.EditTenantDescriptorBuilder;
import seedu.address.testutil.TenantBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Tenant editedPerson = new TenantBuilder().build();
        EditTenantDescriptor descriptor = new EditTenantDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TenantTracker(model.getTenantTracker()), new UserPrefs());
        expectedModel.setTenant(model.getFilteredTenantList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredTenantList().size());
        Tenant lastPerson = model.getFilteredTenantList().get(indexLastPerson.getZeroBased());

        TenantBuilder tenantInList = new TenantBuilder(lastPerson);
        Tenant editedPerson = tenantInList.withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB)
                /* .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND) */.build();

        EditTenantDescriptor descriptor =
                new EditTenantDescriptorBuilder().withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB)
                        // .withPhone(VALID_PHONE_BOB) .withTags(VALID_TAG_HUSBAND)
                        .build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TenantTracker(model.getTenantTracker()), new UserPrefs());
        expectedModel.setTenant(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditTenantDescriptor());
        Tenant editedPerson = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TenantTracker(model.getTenantTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }



    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Tenant personInFilteredList = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tenant editedPerson =
                new TenantBuilder(personInFilteredList).withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTenantDescriptorBuilder().withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new TenantTracker(model.getTenantTracker()), new UserPrefs());
        expectedModel.setTenant(model.getFilteredTenantList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // @Test
    // public void execute_duplicatePersonUnfilteredList_failure() {
    //     Tenant firstPerson = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
    //     EditTenantDescriptor descriptor = new EditTenantDescriptorBuilder(firstPerson).build();
    //     EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

    //     assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    // }

    // @Test
    // public void execute_duplicatePersonFilteredList_failure() {
    //     showPersonAtIndex(model, INDEX_FIRST_PERSON);

    //     // edit person in filtered list into a duplicate in address book
    //     Tenant personInList = model.getTenantTracker().getTenantList().get(INDEX_SECOND_PERSON.getZeroBased());
    //     EditCommand editCommand =
    //             new EditCommand(INDEX_FIRST_PERSON, new EditTenantDescriptorBuilder(personInList).build());

    //     assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    // }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTenantList().size() + 1);
        EditTenantDescriptor descriptor =
                new EditTenantDescriptorBuilder().withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of
     * address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTenantTracker().getTenantList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTenantDescriptorBuilder().withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTenantDescriptor copyDescriptor = new EditTenantDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditTenantDescriptor editPersonDescriptor = new EditTenantDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editTenantDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
