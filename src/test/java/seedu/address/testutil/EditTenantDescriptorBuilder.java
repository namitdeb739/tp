package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditTenantDescriptor;
import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Name;
import seedu.address.model.tenant.Tenant;

/**
 * A utility class to help with building EditTenantDescriptor objects.
 */
public class EditTenantDescriptorBuilder {

    private EditTenantDescriptor descriptor;

    public EditTenantDescriptorBuilder() {
        descriptor = new EditTenantDescriptor();
    }

    public EditTenantDescriptorBuilder(EditTenantDescriptor descriptor) {
        this.descriptor = new EditTenantDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditTenantDescriptorBuilder(Tenant person) {
        descriptor = new EditTenantDescriptor();
        descriptor.setName(person.getName());
        // descriptor.setPhone(person.getPhone());
        // descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        // descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTenantDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    // /**
    // * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
    // */
    // public EditTenantDescriptorBuilder withPhone(String phone) {
    // descriptor.setPhone(new Phone(phone));
    // return this;
    // }

    // /**
    // * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
    // */
    // public EditTenantDescriptorBuilder withEmail(String email) {
    // descriptor.setEmail(new Email(email));
    // return this;
    // }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTenantDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    // /**
    // * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
    // * {@code EditPersonDescriptor} that we are building.
    // */
    // public EditTenantDescriptorBuilder withTags(String... tags) {
    // Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
    // descriptor.setTags(tagSet);
    // return this;
    // }

    public EditTenantDescriptor build() {
        return descriptor;
    }
}
