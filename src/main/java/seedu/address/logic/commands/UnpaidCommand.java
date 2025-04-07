package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;

/**
 * Marks a tenant as not paid based on the provided phone number.
 */
public class UnpaidCommand extends Command {
    public static final String COMMAND_WORD = "unpaid";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the tenant with the specified phone number as not paid.\n"
            + "Parameters: PHONE\n" + "Example: " + COMMAND_WORD + " 82345678\n"
            + "The phone number should be a valid Singaporean phone number also.";
    public static final String MESSAGE_SUCCESS = "Tenant marked as not paid: %s";
    public static final String MESSAGE_TENANT_NOT_FOUND = "No tenant found with phone number: %s";
    public static final String MESSAGE_ALREADY_UNPAID = "Tenant with phone number: %s "
            + "has already been marked as not paid.";

    private final Phone phone;

    public UnpaidCommand(Phone phone) {
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Tenant tenant = model.findTenantByPhone(phone);
        if (tenant == null) {
            throw new CommandException(String.format(MESSAGE_TENANT_NOT_FOUND, phone));
        }

        if (!tenant.isPaid()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_UNPAID, phone));
        }

        model.unmarkTenantAsPaid(phone);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tenant.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UnpaidCommand)) {
            return false;
        }
        UnpaidCommand that = (UnpaidCommand) other;
        return phone.equals(that.phone);
    }

}
