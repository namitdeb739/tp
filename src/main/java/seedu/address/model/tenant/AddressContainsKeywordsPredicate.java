package seedu.address.model.tenant;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Tenant}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Tenant> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tenant tenant) {
        return keywords.stream().anyMatch(keyword -> {
            String sanitizedAddress = tenant.getAddress().value.replaceAll("[^a-zA-Z0-9\\s]", "");
            return StringUtil.containsWordIgnoreCase(sanitizedAddress, keyword)
                    || sanitizedAddress.toLowerCase().matches(".*\\b" + keyword.toLowerCase() + ".*");
        });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressContainsKeywordsPredicate)) {
            return false;
        }

        AddressContainsKeywordsPredicate otherPredicate = (AddressContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
