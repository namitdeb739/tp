package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.tenant.Phone;
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
    void setTenant(Tenant target, Tenant editedTenant);

    /**
     * Returns an unmodifiable view of the filtered tenant list
     */
    ObservableList<Tenant> getFilteredTenantList();

    /**
     * Returns an unmodifiable view of the filtered list of archived tenants.
     */
    ObservableList<Tenant> getArchivedTenantList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTenantList(Predicate<Tenant> predicate);

    /**
     * Updates the filter of the archived tenant list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null
     */
    void updateFilteredArchivedTenantList(Predicate<Tenant> predicate);

    /**
     * Marks a tenant as archived and moves them to the archived list.
     */
    void archiveTenant(Tenant tenant);

    /**
     * Marks a tenant as unarchived and moves them to the tenant list.
     */
    void unarchiveTenant(Tenant tenant);

    /**
     * Toggles between archived list and unarchived list.
     */
    void toggleArchiveView();

    /**
     * Shows true if the current displayed list is archived
     */
    boolean isShowingArchived();

    /**
     * Marks the tenant as paid based on their phone number.
     */
    void markTenantAsPaid(Phone phone);

    /**
     * Finds a tenant based on their phone number.
     */
    Tenant findTenantByPhone(Phone phone);

    /**
     * Marks the tenant as not paid based on their phone number.
     */
    void unmarkTenantAsPaid(Phone phone);

    boolean hasTenantWith(Tenant editedTenant, String field);
}
