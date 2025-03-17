package seedu.address.testutil;

import seedu.address.model.TenantTracker;
import seedu.address.model.tenant.Tenant;

/**
 * A utility class to help with building Addressbook objects. Example usage: <br>
 * {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TenantTracker addressBook;

    public AddressBookBuilder() {
        addressBook = new TenantTracker();
    }

    public AddressBookBuilder(TenantTracker addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Tenant person) {
        addressBook.addTenant(person);
        return this;
    }

    public TenantTracker build() {
        return addressBook;
    }
}
