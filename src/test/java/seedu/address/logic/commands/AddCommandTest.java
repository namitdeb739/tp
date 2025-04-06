package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTenants.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTenantTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TenantTracker;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;
import seedu.address.testutil.TenantBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Tenant validPerson = new TenantBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    // @Test
    // public void execute_duplicatePerson_throwsCommandException() {
    // Tenant validPerson = new TenantBuilder().build();
    // AddCommand addCommand = new AddCommand(validPerson);
    // ModelStub modelStub = new ModelStubWithPerson(validPerson);

    // assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () ->
    // addCommand.execute(modelStub));
    // }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals() {
        Tenant alice = new TenantBuilder().withName("Alice", "Smith").build();
        Tenant bob = new TenantBuilder().withName("Bob", "Lee").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTenantTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTenantTrackerFilePath(Path tenantTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTenant(Tenant person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTenantTracker(ReadOnlyTenantTracker tenantTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTenantTracker getTenantTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTenant(Tenant person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTenant(Tenant target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTenant(Tenant target, Tenant editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tenant> getFilteredTenantList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTenantList(Predicate<Tenant> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredArchivedTenantList(Predicate<Tenant> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tenant> getArchivedTenantList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void archiveTenant(Tenant tenant) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unarchiveTenant(Tenant tenant) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void toggleArchiveView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isShowingArchived() {
            return false;
        }

        @Override
        public void markTenantAsPaid(Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Tenant findTenantByPhone(Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unmarkTenantAsPaid(Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTenantWith(Tenant editedTenant, String field) {
            throw new AssertionError("This method should not be called.");
        }
    }

    // /**
    // * A Model stub that contains a single person.
    // */
    // private class ModelStubWithPerson extends ModelStub {
    // private final Tenant person;

    // ModelStubWithPerson(Tenant person) {
    // requireNonNull(person);
    // this.person = person;
    // }

    // @Override
    // public boolean hasTenant(Tenant person) {
    // requireNonNull(person);
    // return this.person.isSamePerson(person);
    // }
    // }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Tenant> personsAdded = new ArrayList<>();

        @Override
        public boolean hasTenant(Tenant person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addTenant(Tenant person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyTenantTracker getTenantTracker() {
            return new TenantTracker();
        }

        @Override
        public void markTenantAsPaid(Phone phone) {
            for (Tenant tenant : personsAdded) {
                if (tenant.getPhone().equals(phone)) {
                    tenant.markAsPaid();
                    return;
                }
            }
        }
    }

}
