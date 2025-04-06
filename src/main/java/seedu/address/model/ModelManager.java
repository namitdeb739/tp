package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TenantTracker tenantTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Tenant> filteredTenants;
    private final FilteredList<Tenant> filteredArchivedTenants;

    private boolean isShowingArchived = false;

    /**
     * Initializes a ModelManager with the given tenantTracker and userPrefs.
     */
    public ModelManager(ReadOnlyTenantTracker tenantTracker, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(tenantTracker, userPrefs);

        logger.fine("Initializing with tenant tracker: " + tenantTracker + " and user prefs " + userPrefs);

        this.tenantTracker = new TenantTracker(tenantTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTenants = new FilteredList<>(this.tenantTracker.getActiveTenantList());
        this.filteredArchivedTenants = new FilteredList<>(this.tenantTracker.getArchivedTenantList());
    }

    public ModelManager() {
        this(new TenantTracker(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTenantTrackerFilePath() {
        return userPrefs.getTenantTrackerFilePath();
    }

    @Override
    public void setTenantTrackerFilePath(Path tenantTrackerFilePath) {
        requireNonNull(tenantTrackerFilePath);
        userPrefs.setTenantTrackerFilePath(tenantTrackerFilePath);
    }

    // =========== TenantTracker
    // ================================================================================

    @Override
    public void setTenantTracker(ReadOnlyTenantTracker tenantTracker) {
        this.tenantTracker.resetData(tenantTracker);
    }

    @Override
    public ReadOnlyTenantTracker getTenantTracker() {
        return tenantTracker;
    }

    @Override
    public boolean hasTenant(Tenant person) {
        requireNonNull(person);
        return tenantTracker.hasTenant(person);
    }

    @Override
    public boolean hasTenantWith(Tenant person, String field) {
        requireNonNull(person);
        return tenantTracker.hasTenantWith(person, field);
    }

    @Override
    public void deleteTenant(Tenant target) {
        tenantTracker.removeTenant(target);
    }

    @Override
    public void addTenant(Tenant person) {
        tenantTracker.addTenant(person);
        updateFilteredTenantList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setTenant(Tenant target, Tenant editedPerson) {
        requireAllNonNull(target, editedPerson);

        tenantTracker.setTenant(target, editedPerson);
    }

    @Override
    public void archiveTenant(Tenant tenant) {
        requireNonNull(tenant);
        Tenant archived = tenant.archive();
        tenantTracker.setTenant(tenant, archived);
        updateFilteredTenantList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredArchivedTenantList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void unarchiveTenant(Tenant tenant) {
        requireNonNull(tenant);
        Tenant unarchived = tenant.unarchive();
        tenantTracker.setTenant(tenant, unarchived);
        updateFilteredTenantList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredArchivedTenantList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void toggleArchiveView() {
        isShowingArchived = !isShowingArchived;
        if (isShowingArchived) {
            updateFilteredArchivedTenantList(PREDICATE_SHOW_ALL_PERSONS);
        } else {
            updateFilteredTenantList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    @Override
    public boolean isShowingArchived() {
        return isShowingArchived;
    }


    // =========== Filtered Person List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tenant} backed by the internal list of
     * {@code versionedTenantTracker}
     */
    @Override
    public ObservableList<Tenant> getFilteredTenantList() {
        return filteredTenants;
    }

    /**
     * Returns an unmodifiable view of the list of archived {@code Tenant} backed by the internal list
     * of the tracker.
     */
    @Override
    public ObservableList<Tenant> getArchivedTenantList() {
        return filteredArchivedTenants;
    }

    @Override
    public void updateFilteredArchivedTenantList(Predicate<Tenant> predicate) {
        requireNonNull(predicate);
        filteredArchivedTenants.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTenantList(Predicate<Tenant> predicate) {
        requireNonNull(predicate);
        filteredTenants.setPredicate(predicate);
    }

    /**
     * Marks a tenant as paid based on their phone number.
     *
     * @param phone the phone number of the tenant to mark as paid
     */
    @Override
    public void markTenantAsPaid(Phone phone) {
        requireNonNull(phone);

        // Find tenant by phone number
        Tenant tenantToMarkAsPaid = findTenantByPhone(phone);

        if (tenantToMarkAsPaid != null) {
            tenantToMarkAsPaid.markAsPaid(); // Mark tenant as paid
            setTenant(tenantToMarkAsPaid, tenantToMarkAsPaid);
        } else {
            throw new IllegalArgumentException("Tenant with the phone number " + phone + " not found.");
        }
    }

    /**
     * Returns the tenant with the specified phone number.
     */
    public Tenant findTenantByPhone(Phone phone) {
        for (Tenant tenant : tenantTracker.getActiveTenantList()) {
            if (tenant.getPhone().equals(phone)) {
                return tenant;
            }
        }
        return null;
    }

    /**
     * Marks a tenant as not paid based on their phone number.
     *
     * @param phone the phone number of the tenant to mark as not paid
     */
    @Override
    public void unmarkTenantAsPaid(Phone phone) {
        requireNonNull(phone);
        Tenant tenant = findTenantByPhone(phone);
        if (tenant != null && tenant.isPaid()) {
            tenant.setPaid(false);
            updateFilteredTenantList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return tenantTracker.equals(otherModelManager.tenantTracker) && userPrefs.equals(otherModelManager.userPrefs)
                && filteredTenants.equals(otherModelManager.filteredTenants)
                && filteredArchivedTenants.equals(otherModelManager.filteredArchivedTenants);
    }

}
