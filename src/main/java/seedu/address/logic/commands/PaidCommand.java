package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;

/**
 * Marks a tenant as paid based on the provided phone number.
 */
public class PaidCommand extends Command {
    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the tenant with the specified phone number as paid.\n"
            + "Parameters: PHONE\n"
            + "Example: " + COMMAND_WORD + " 12345678";

    public static final String MESSAGE_SUCCESS = "Successfully marked tenant as paid: %s";
    public static final String MESSAGE_TENANT_NOT_FOUND = "No tenant found with phone number: %s";
    public static final String MESSAGE_ALREADY_PAID = "Tenant with phone number: %s has already been marked as paid.";

    private final Phone phone;

    /**
     * Marks a tenant as paid identified using his phone number
     */
    public PaidCommand(Phone phone) {
        requireNonNull(phone);
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Tenant tenant = model.findTenantByPhone(phone);
        if (tenant == null) {
            throw new CommandException(String.format(MESSAGE_TENANT_NOT_FOUND, phone));
        }

        if (tenant.isPaid()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_PAID, phone));
        }

        model.markTenantAsPaid(phone);
        String successMessage = String.format(MESSAGE_SUCCESS, formatTenant(tenant));
        return new CommandResult(successMessage);
    }

    private String formatTenant(Tenant tenant) {
        return String.format("%s (Phone: %s)", tenant.getName(), tenant.getPhone());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PaidCommand)) {
            return false;
        }
        PaidCommand that = (PaidCommand) other;
        return phone.equals(that.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("phone", phone)
                .toString();
    }
}
