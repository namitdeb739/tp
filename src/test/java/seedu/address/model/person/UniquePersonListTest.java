package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTenants.ALICE;
import static seedu.address.testutil.TypicalTenants.BOB;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tenant.Tenant;
import seedu.address.model.tenant.UniqueTenantList;
import seedu.address.model.tenant.exceptions.DuplicatePersonException;
import seedu.address.model.tenant.exceptions.PersonNotFoundException;
import seedu.address.testutil.TenantBuilder;

public class UniquePersonListTest {

    private final UniqueTenantList uniqueTenantList = new UniqueTenantList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueTenantList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueTenantList.add(ALICE);
        assertTrue(uniqueTenantList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTenantList.add(ALICE);
        Tenant editedAlice =
                new TenantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)/* .withTags(VALID_TAG_HUSBAND) */.build();
        assertTrue(uniqueTenantList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTenantList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueTenantList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.setTenant(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.setTenant(ALICE, null));
    }

    // @Test
    // public void setTenant_targetTenantNotInList_throwsTenantNotFoundException() {
    // assertThrows(TenantNotFoundException.class, () -> uniqueTenantList.setTenant(ALICE, ALICE));
    // }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTenantList.add(ALICE);
        uniqueTenantList.setTenant(ALICE, ALICE);
        UniqueTenantList expectedUniquePersonList = new UniqueTenantList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniqueTenantList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueTenantList.add(ALICE);
        Tenant editedAlice =
                new TenantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)/* .withTags(VALID_TAG_HUSBAND) */.build();
        uniqueTenantList.setTenant(ALICE, editedAlice);
        UniqueTenantList expectedUniquePersonList = new UniqueTenantList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniqueTenantList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueTenantList.add(ALICE);
        uniqueTenantList.setTenant(ALICE, BOB);
        UniqueTenantList expectedUniquePersonList = new UniqueTenantList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueTenantList);
    }

    // @Test
    // public void setTenant_editedTenantHasNonUniqueIdentity_throwsTenantPersonException() {
    // uniqueTenantList.add(ALICE);
    // uniqueTenantList.add(BOB);
    // assertThrows(DuplicateTenantException.class, () -> uniqueTenantList.setTenant(ALICE, BOB));
    // }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueTenantList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTenantList.add(ALICE);
        uniqueTenantList.remove(ALICE);
        UniqueTenantList expectedUniquePersonList = new UniqueTenantList();
        assertEquals(expectedUniquePersonList, uniqueTenantList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.setPersons((UniqueTenantList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueTenantList.add(ALICE);
        UniqueTenantList expectedUniquePersonList = new UniqueTenantList();
        expectedUniquePersonList.add(BOB);
        uniqueTenantList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueTenantList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTenantList.setPersons((List<Tenant>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueTenantList.add(ALICE);
        List<Tenant> personList = Collections.singletonList(BOB);
        uniqueTenantList.setPersons(personList);
        UniqueTenantList expectedUniquePersonList = new UniqueTenantList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueTenantList);
    }

    // @Test
    // public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
    // List<Tenant> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
    // assertThrows(DuplicateTenantException.class,
    // () -> uniqueTenantList.setPersons(listWithDuplicatePersons));
    // }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueTenantList
                ./* Placeholder comment because checkstyle is a bitch */ asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTenantList.asUnmodifiableObservableList().toString(), uniqueTenantList.toString());
    }
}
