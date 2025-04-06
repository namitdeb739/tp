package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Email;
import seedu.address.model.tenant.Name;
import seedu.address.model.tenant.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String userInput, String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Messages.lastUserInput(userInput) + MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        return parseIndex("", oneBasedIndex);
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String userInput, String given, String family) throws ParseException {
        requireNonNull(given);
        String trimmedGiven = given.trim();
        String trimmedFamily = family.trim();
        if (!Name.isValidName(trimmedGiven) || !Name.isValidName(trimmedFamily)) {
            throw new ParseException(Messages.lastUserInput(userInput) + Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedGiven, trimmedFamily);
    }

    public static Name parseName(String given, String family) throws ParseException {
        return parseName("", given, family);
    }



    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String userInput, String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Messages.lastUserInput(userInput) + Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    public static Phone parsePhone(String phone) throws ParseException {
        return parsePhone("", phone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String userInput, String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Messages.lastUserInput(userInput) + Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    public static Address parseAddress(String address) throws ParseException {
        return parseAddress("", address);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String userInput, String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Messages.lastUserInput(userInput) + Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    public static Email parseEmail(String email) throws ParseException {
        return parseEmail("", email);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String userInput, String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Messages.lastUserInput(userInput) + Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    public static Tag parseTag(String tag) throws ParseException {
        return parseTag("", tag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(String userInput, Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(userInput, tagName.isEmpty() ? tagName
                    : tagName.substring(0, 1).toUpperCase() + tagName.substring(1).toLowerCase()));
        }
        return tagSet;
    }

    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        return parseTags("", tags);
    }
}
