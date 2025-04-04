package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAMILY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GIVEN_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTenantDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, String userInput) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GIVEN_NAME, PREFIX_FAMILY_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(userInput, argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.lastUserInput(userInput) + MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GIVEN_NAME, PREFIX_FAMILY_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS);

        EditTenantDescriptor editPersonDescriptor = new EditTenantDescriptor();


        if (argMultimap.getValue(PREFIX_GIVEN_NAME).isPresent()
                || argMultimap.getValue(PREFIX_FAMILY_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(userInput, argMultimap.getValue(PREFIX_GIVEN_NAME).get(),
                    argMultimap.getValue(PREFIX_FAMILY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(userInput, argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(userInput, argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor
                    .setAddress(ParserUtil.parseAddress(userInput, argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(userInput, argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(Messages.lastUserInput(userInput) + EditCommand.MESSAGE_NOT_EDITED));
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(String userInput, Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(userInput, tagSet));
    }

}
