package seedu.address.testutil;

import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Name;
import seedu.address.model.tenant.Tenant;

/**
 * A utility class to help with building Person objects.
 */
public class TenantBuilder {

    public static final String DEFAULT_GIVEN_NAME = "Amy";
    public static final String DEFAULT_FAMILY_NAME = "Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111, 123456";

    private Name name;
    // private Phone phone;
    // private Email email;
    private Address address;
    // private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public TenantBuilder() {
        name = new Name(DEFAULT_GIVEN_NAME, DEFAULT_FAMILY_NAME);
        // phone = new Phone(DEFAULT_PHONE);
        // email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        // tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TenantBuilder(Tenant personToCopy) {
        name = personToCopy.getName();
        // phone = personToCopy.getPhone();
        // email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        // tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public TenantBuilder withName(String given, String family) {
        this.name = new Name(given, family);
        return this;
    }

    // /**
    // * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are
    // * building.
    // */
    // public TenantBuilder withTags(String... tags) {
    // this.tags = SampleDataUtil.getTagSet(tags);
    // return this;
    // }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public TenantBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    // /**
    // * Sets the {@code Phone} of the {@code Person} that we are building.
    // */
    // public TenantBuilder withPhone(String phone) {
    // this.phone = new Phone(phone);
    // return this;
    // }

    // /**
    // * Sets the {@code Email} of the {@code Person} that we are building.
    // */
    // public TenantBuilder withEmail(String email) {
    // this.email = new Email(email);
    // return this;
    // }

    public Tenant build() {
        return new Tenant(name, /* phone, email, */ address/* , tags */);
    }

}
