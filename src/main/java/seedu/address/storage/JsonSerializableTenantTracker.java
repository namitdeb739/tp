package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTenantTracker;
import seedu.address.model.TenantTracker;
import seedu.address.model.tenant.Tenant;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "tenanttracker")
class JsonSerializableTenantTracker {

    // public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedTenant> tenants = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTenantTracker(@JsonProperty("tenants") List<JsonAdaptedTenant> tenants) {
        this.tenants.addAll(tenants);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableTenantTracker(ReadOnlyTenantTracker source) {
        tenants.addAll(source.getTenantList().stream().map(JsonAdaptedTenant::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TenantTracker toModelType() throws IllegalValueException {
        TenantTracker addressBook = new TenantTracker();
        for (JsonAdaptedTenant jsonAdaptedPerson : tenants) {
            Tenant person = jsonAdaptedPerson.toModelType();
            // if (addressBook.hasTenant(person)) {
            // throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            // }
            addressBook.addTenant(person);
        }
        return addressBook;
    }

}
