package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAMILY_NAME;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GIVEN_NAME;

// import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tenant.Tenant;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE =
            // COMMAND_WORD + ": Adds a person to the address book. " + "Parameters: " + PREFIX_NAME
            // + "NAME " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL " + PREFIX_ADDRESS
            // + "ADDRESS " + "[" + PREFIX_TAG + "TAG]...\n" + "Example: " + COMMAND_WORD + " "
            // + PREFIX_NAME + "John Doe " + PREFIX_PHONE + "98765432 " + PREFIX_EMAIL
            // + "johnd@example.com " + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            // + PREFIX_TAG + "friends " + PREFIX_TAG + "owesMoney";
            COMMAND_WORD + ": Adds a person to the address book. " + "Parameters: "
                    + PREFIX_GIVEN_NAME + "GIVEN NAME " + PREFIX_FAMILY_NAME + "FAMILY NAME "
                    + PREFIX_ADDRESS + "ADDRESS " /* + "[" + PREFIX_TAG + "TAG]...\n" */
                    + "Example: " + COMMAND_WORD + " " + PREFIX_GIVEN_NAME + "John Doe "
                    + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New tenant added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This tenant already exists in the tenant tracker";

    private final Tenant toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Tenant tenant) {
        requireNonNull(tenant);
        toAdd = tenant;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if (model.hasPerson(toAdd)) {
        // throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        // }

        model.addTenant(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
