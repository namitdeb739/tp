package seedu.address.testutil;

import seedu.address.model.TenantTracker;
import seedu.address.model.tenant.Tenant;

/**
 * A utility class to help with building TenantTracker objects. Example usage: <br>
 * {@code TenantTracker tt = new TenantTrackerBuilder().withPerson("John", "Doe").build();}
 */
public class TenantTrackerBuilder {

    private TenantTracker tenantTracker;

    public TenantTrackerBuilder() {
        tenantTracker = new TenantTracker();
    }

    public TenantTrackerBuilder(TenantTracker addressBook) {
        this.tenantTracker = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public TenantTrackerBuilder withPerson(Tenant person) {
        tenantTracker.addTenant(person);
        return this;
    }

    public TenantTracker build() {
        return tenantTracker;
    }
}
