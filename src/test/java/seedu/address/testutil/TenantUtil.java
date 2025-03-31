package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAMILY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GIVEN_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditTenantDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.tenant.Tenant;

/**
 * A utility class for Tenant.
 */
public class TenantUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Tenant tenant) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(tenant);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Tenant tenant) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_GIVEN_NAME + tenant.getName().givenName + " ");
        sb.append(PREFIX_FAMILY_NAME + tenant.getName().familyName + " ");
        sb.append(PREFIX_PHONE + tenant.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + tenant.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + tenant.getAddress().value + " ");
        tenant.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditTenantDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_GIVEN_NAME)
            .append(name.givenName).append(" ")
            .append(PREFIX_FAMILY_NAME).append(name.familyName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
