package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Email;
import seedu.address.model.tenant.Name;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;

/**
 * Jackson-friendly version of {@link Tenant}.
 */
class JsonAdaptedTenant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tenant's %s field is missing!";

    private final String givenName;
    private final String familyName;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isArchived;

    @JsonCreator
    public JsonAdaptedTenant(@JsonProperty("givenName") String givenName,
                             @JsonProperty("familyName") String familyName,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("isArchived") boolean isArchived) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.isArchived = isArchived;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    public JsonAdaptedTenant(Tenant source) {
        givenName = source.getGivenName();
        familyName = source.getFamilyName();
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        isArchived = source.isArchived();
        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    public Tenant toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (givenName == null || familyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(givenName) || !Name.isValidName(familyName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        return new Tenant(
                new Name(givenName, familyName),
                new Phone(phone),
                new Email(email),
                new Address(address),
                new HashSet<>(personTags),
                isArchived
        );
    }
}
