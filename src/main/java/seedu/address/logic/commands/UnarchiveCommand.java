package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tenant.Tenant;

/**
 * Archives a tenant identified using its displayed index from the filtered tenant list.
 */
public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";
    public static final String MESSAGE_SUCCESS = "You have successfully unarchived a tenant! Tenant: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchives the tenant by index in the archived list.";

    private final Index targetIndex;

    public UnarchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tenant> archivedList = model.getArchivedTenantList();
        if (targetIndex.getZeroBased() >= archivedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
        }

        Tenant tenantToUnarchive = archivedList.get(targetIndex.getZeroBased());
        if (!tenantToUnarchive.isArchived()) {
            throw new CommandException("This tenant is not archived.");
        }

        model.unarchiveTenant(tenantToUnarchive);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(tenantToUnarchive)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnarchiveCommand
                && targetIndex.equals(((UnarchiveCommand) other).targetIndex));
    }
}
