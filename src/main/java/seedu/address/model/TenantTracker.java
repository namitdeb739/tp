package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tenant.Tenant;
import seedu.address.model.tenant.UniqueTenantList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TenantTracker implements ReadOnlyTenantTracker {

    private final UniqueTenantList tenants;
    private final ObservableList<Tenant> activeTenants = FXCollections.observableArrayList();
    private final ObservableList<Tenant> archivedTenants = FXCollections.observableArrayList();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid
     * duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html Note that non-static init
     * blocks are not recommended to use. There are other ways to avoid duplication among
     * constructors.
     */
    {
        tenants = new UniqueTenantList();
    }

    public TenantTracker() {}

    /**
     * Creates an TenantTracker using the Persons in the {@code toBeCopied}
     */
    public TenantTracker(ReadOnlyTenantTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not
     * contain duplicate persons.
     */
    public void setPersons(List<Tenant> persons) {
        this.tenants.setPersons(persons);
        splitTenants();
    }

    /**
     * Resets the existing data of this {@code TenantTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyTenantTracker newData) {
        requireNonNull(newData);
        setPersons(newData.getTenantList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasTenant(Tenant tenant) {
        requireNonNull(tenant);
        return tenants.contains(tenant);
    }

    /**
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addTenant(Tenant p) {
        tenants.add(p);
        if (p.isArchived()) {
            archivedTenants.add(p);
        } else {
            activeTenants.add(p);
        }
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book. The person identity of {@code editedPerson}
     * must not be the same as another existing person in the address book.
     */
    public void setTenant(Tenant target, Tenant editedTenant) {
        requireNonNull(editedTenant);
        tenants.setTenant(target, editedTenant);
        splitTenants();
    }

    /**
     * Removes {@code key} from this {@code TenantTracker}. {@code key} must exist in the address
     * book.
     */
    public void removeTenant(Tenant key) {
        tenants.remove(key);
        activeTenants.remove(key);
        archivedTenants.remove(key);
    }

    //// util methods

    private void splitTenants() {
        activeTenants.setAll(tenants.asUnmodifiableObservableList().filtered(t -> !t.isArchived()));
        archivedTenants.setAll(tenants.asUnmodifiableObservableList().filtered(Tenant::isArchived));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tenants", tenants).toString();
    }

    @Override
    public ObservableList<Tenant> getTenantList() {
        return tenants.asUnmodifiableObservableList();
    }

    public ObservableList<Tenant> getActiveTenantList() {
        return FXCollections.unmodifiableObservableList(activeTenants);
    }

    public ObservableList<Tenant> getArchivedTenantList() {
        return FXCollections.unmodifiableObservableList(archivedTenants);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TenantTracker)) {
            return false;
        }

        TenantTracker otherTenantTracker = (TenantTracker) other;
        return tenants.equals(otherTenantTracker.tenants);
    }

    @Override
    public int hashCode() {
        return tenants.hashCode();
    }

    /**
     * Returns true if both tenants have the same identity and data fields.
     */
    public boolean hasTenantWith(Tenant tenant, String field) {
        return tenants.containsWith(tenant, field);
    }
}
