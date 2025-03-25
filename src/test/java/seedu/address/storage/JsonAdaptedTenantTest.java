package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTenant.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTenants.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Email;
import seedu.address.model.tenant.Name;
import seedu.address.model.tenant.Phone;

public class JsonAdaptedTenantTest {
    private static final String INVALID_NAME = "    ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_GIVEN_NAME = BENSON.getGivenName();
    private static final String VALID_FAMILY_NAME = BENSON.getFamilyName();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS =
            BENSON.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedTenant tenant = new JsonAdaptedTenant(BENSON);
        assertEquals(BENSON, tenant.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTenant person = new JsonAdaptedTenant(INVALID_NAME, INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTenant person =
                new JsonAdaptedTenant(null, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedTenant person =
                new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, INVALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedTenant person = new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedTenant person = new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, VALID_PHONE,
                INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedTenant person = new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedTenant person = new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TAGS, false);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedTenant person =
                new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, VALID_PHONE, VALID_EMAIL, null,
                        VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTenant person = new JsonAdaptedTenant(VALID_GIVEN_NAME, VALID_FAMILY_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, invalidTags, false);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
