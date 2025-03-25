package seedu.address.model.tenant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Tenant in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Tenant {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final boolean isArchived;

    /**
     * Every field must be present and not null.
     */
    public Tenant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, boolean isArchived) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.isArchived = isArchived;
    }

    /**
     * Constructs a Tenant with default archive status (false).
     */
    public Tenant(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, tags, false);
    }

    public Name getName() {
        return name;
    }

    public String getGivenName() {
        return name.givenName;
    }

    public String getFamilyName() {
        return name.familyName;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if modification
     * is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Returns a copy of this tenant marked as archived.
     */
    public Tenant archive() {
        return new Tenant(name, phone, email, address, tags, true);
    }

    /**
     * Returns a copy of this tenant marked as active.
     */
    public Tenant unarchive() {
        return new Tenant(name, phone, email, address, tags, false);
    }

    /**
     * Returns true if both persons have the same name. This defines a weaker notion of equality between
     * two persons.
     */
    public boolean isSamePerson(Tenant otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger
     * notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tenant)) {
            return false;
        }

        Tenant otherPerson = (Tenant) other;
        return name.equals(otherPerson.name) && address.equals(otherPerson.address) && tags.equals(otherPerson.tags)
                && phone.equals(otherPerson.phone) && email.equals(otherPerson.email)
                && isArchived == otherPerson.isArchived;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, isArchived);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).add("phone", phone).add("email", email)
                .add("address", address).add("tags", tags)
                .add("archived", isArchived).toString();
    }

}
