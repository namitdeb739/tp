package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the archived TenantTracker to the user.
 */
public class ToggleArchiveCommand extends Command {
    public static final String COMMAND_WORD = "togglearchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles the view between active and archived tenants.";
    public static final String MESSAGE_FAILURE = "There are no archived tenants to display.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        boolean currentlyArchived = model.isShowingArchived();
        boolean hasArchivedTenants = !model.getArchivedTenantList().isEmpty();

        if (!currentlyArchived && !hasArchivedTenants) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        model.toggleArchiveView();
        return new CommandResult(
                currentlyArchived ? "Switched to active tenants view." : "Switched to archived tenants view.",
                true
        );
    }
}
