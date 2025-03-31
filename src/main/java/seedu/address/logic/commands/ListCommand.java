package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Here is the list of tenants!";
    public static final String MESSAGE_EMPTY_LIST = "There are no tenants in the list. Add some tenants!";

    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing ListCommand to list tenants.");

        assert model.getFilteredTenantList() != null : "Filtered tenant list should not be null";

        model.updateFilteredTenantList(PREDICATE_SHOW_ALL_PERSONS);

        if (model.getFilteredTenantList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }

        logger.info("Tenant list displayed successfully with " + model.getFilteredTenantList().size() + " tenants.");

        return buildCommandResult(MESSAGE_SUCCESS);
    }
}
