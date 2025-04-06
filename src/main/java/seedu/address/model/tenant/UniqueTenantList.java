package seedu.address.model.tenant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tenant.exceptions.TenantNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls. A
 * person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such,
 * adding and updating of persons uses Person#isSamePerson(Person) for equality so as to ensure that
 * the person being added or updated is unique in terms of identity in the UniquePersonList.
 * However, the removal of a person uses Person#equals(Object) so as to ensure that the person with
 * exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Tenant#isSamePerson(Tenant)
 */
public class UniqueTenantList implements Iterable<Tenant> {

    private final ObservableList<Tenant> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tenant> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Tenant toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list. The person must not already exist in the list.
     */
    public void add(Tenant toAdd) {
        requireNonNull(toAdd);
        // if (contains(toAdd)) {
        //     throw new DuplicatePersonException();
        // }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}. {@code target} must
     * exist in the list. The person identity of {@code editedPerson} must not be the same as
     * another existing person in the list.
     */
    public void setTenant(Tenant target, Tenant editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TenantNotFoundException();
        }

        // if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
        //     throw new DuplicatePersonException();
        // }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list. The person must exist in the list.
     */
    public void remove(Tenant toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TenantNotFoundException();
        }
    }

    public void setPersons(UniqueTenantList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}. {@code persons} must not contain
     * duplicate persons.
     */
    public void setPersons(List<Tenant> persons) {
        requireAllNonNull(persons);
        // if (!personsAreUnique(persons)) {
        //     throw new DuplicatePersonException();
        // }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tenant> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tenant> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueTenantList)) {
            return false;
        }

        UniqueTenantList otherUniquePersonList = (UniqueTenantList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean containsWith(Tenant tenant, String field) {
        return internalList.stream().anyMatch(t -> t.equalsWith(tenant, field));
    }

    //     for (int i = 0; i < persons.size() - 1; i++) {
    //         for (int j = i + 1; j < persons.size(); j++) {
    //             if (persons.get(i).isSamePerson(persons.get(j))) {
    //                 return false;
    //             }
    //         }
    //     }
    //     return true;
    // }
}
