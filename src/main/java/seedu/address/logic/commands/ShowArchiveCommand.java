package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the archived TenantTracker to the user.
 */
public class ShowArchiveCommand extends Command {
    public static final String COMMAND_WORD = "showarchive";
    public static final String MESSAGE_SUCCESS = "Listed all archived tenants";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredArchivedTenantList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
