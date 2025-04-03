package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the archived TenantTracker to the user.
 */
public class ToggleArchiveCommand extends Command {
    public static final String COMMAND_WORD = "togglearchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles the view between active and archived tenants.";
    public static final String MESSAGE_SUCCESS = "Toggled between archived tenants";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.toggleArchiveView();
        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
