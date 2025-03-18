package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.tenant.Tenant;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Tenant> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getTenantTrackerFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTenantTrackerFilePath(Path tenantTrackerFilePath);

    /**
     * Replaces TenantTracker data with the data in {@code tenantTracker}.
     */
    void setTenantTracker(ReadOnlyTenantTracker tenantTracker);

    /**
     * Returns the TenantTracker
     */
    ReadOnlyTenantTracker getTenantTracker();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasTenant(Tenant tenant);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deleteTenant(Tenant target);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addTenant(Tenant tenant);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in
     * the address book. The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    void setPerson(Tenant target, Tenant editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Tenant> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTenantList(Predicate<Tenant> predicate);
}
