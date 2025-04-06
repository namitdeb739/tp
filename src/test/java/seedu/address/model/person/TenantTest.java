package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GIVEN_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HDB;
import static seedu.address.testutil.TypicalTenants.ALICE;
import static seedu.address.testutil.TypicalTenants.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.tenant.Tenant;
import seedu.address.testutil.TenantBuilder;

public class TenantTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        // Tenant tenant = new PersonBuilder().build();
        // assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Tenant editedAlice = new TenantBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HDB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new TenantBuilder(ALICE).withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        // Tenant editedBob =
        // new TenantBuilder(BOB).withName(VALID_GIVEN_NAME_BOB.toLowerCase(),
        // VALID_FAMILY_NAME_BOB).build();
        // assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        // String nameWithTrailingSpaces = VALID_GIVEN_NAME_BOB + " ";
        // editedBob = new TenantBuilder(BOB).withName(nameWithTrailingSpaces,
        // VALID_FAMILY_NAME_BOB).build();
        // assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Tenant aliceCopy = new TenantBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Tenant editedAlice = new TenantBuilder(ALICE).withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TenantBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
        editedAlice = new TenantBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TenantBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
        editedAlice = new TenantBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TenantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TenantBuilder(ALICE).withTags(VALID_TAG_HDB).build();
        assertFalse(ALICE.equals(editedAlice));
        editedAlice = new TenantBuilder(ALICE).withTags(VALID_TAG_HDB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Tenant.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", archived=" + ALICE.isArchived() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
