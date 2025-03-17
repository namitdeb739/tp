package seedu.address.model.tenant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.StringTokenizer;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names can contain any characters, and it should not be blank or purely whitespace";


    /*
     * The name can contain any character, and it should not be blank or purely whitespace
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String givenName;

    public final String familyName;

    /**
     * Constructs a {@code Name}.
     *
     * @param given A valid name.
     */
    public Name(String given, String family) {
        requireNonNull(given);
        requireNonNull(family);
        checkArgument(isValidName(given), MESSAGE_CONSTRAINTS);
        checkArgument(isValidName(family), MESSAGE_CONSTRAINTS);
        givenName = capitaliseName(given);
        familyName = capitaliseName(family);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return givenName + " " + familyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return familyName.equals(otherName.familyName);
    }

    @Override
    public int hashCode() {
        return familyName.hashCode();
    }

    /**
     * Capitalizes the first letter of each word in the name.
     */
    private String capitaliseName(String name) {
        StringTokenizer tokenizer = new StringTokenizer(name);
        StringBuilder capitalizedName = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            capitalizedName.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase()).append(" ");
        }

        return capitalizedName.toString().trim();
    }

}
