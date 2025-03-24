package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tenant.Tenant;

/**
 * Archives a tenant identified using its displayed index from the filtered tenant list.
 */
public class ArchiveCommand extends Command {
    public static final String COMMAND_WORD = "archive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the tenant identified by the index number"
            + " used in the displayed tenant list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_TENANT_SUCCESS = "You have successfully archived a tenant! Tenant: %1$s";
    public static final String MESSAGE_ALREADY_ARCHIVED = "This tenant is already archived.";

    private final Index targetIndex;

    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tenant> lastShownList = model.getFilteredTenantList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("The tenant index provided is invalid");
        }

        Tenant tenantToArchive = lastShownList.get(targetIndex.getZeroBased());
        if (tenantToArchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_ARCHIVED);
        }

        model.archiveTenant(tenantToArchive);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_TENANT_SUCCESS,
                Messages.format(tenantToArchive)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveCommand // instanceof handles nulls
                && targetIndex.equals(((ArchiveCommand) other).targetIndex));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetIndex", targetIndex).toString();
    }
}
