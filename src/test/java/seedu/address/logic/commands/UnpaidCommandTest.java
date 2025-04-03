package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnpaidCommand}.
 */
public class UnpaidCommandTest {

    private Model model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());

    @Test
    public void execute_tenantIsPaid_success() {
        Tenant tenantToUnpay = model.getFilteredTenantList().get(0);

        tenantToUnpay.markAsPaid();

        Phone phone = tenantToUnpay.getPhone();
        UnpaidCommand unpaidCommand = new UnpaidCommand(phone);

        String expectedMessage = String.format(UnpaidCommand.MESSAGE_SUCCESS, tenantToUnpay.getName());

        ModelManager expectedModel = new ModelManager(model.getTenantTracker(), new UserPrefs());
        Tenant expectedTenant = new Tenant(tenantToUnpay.getName(), tenantToUnpay.getPhone(), tenantToUnpay.getEmail(),
                tenantToUnpay.getAddress(), tenantToUnpay.getTags(), tenantToUnpay.isArchived(), false);

        expectedModel.setTenant(tenantToUnpay, expectedTenant);

        assertCommandSuccess(unpaidCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_tenantAlreadyUnpaid_throwsCommandException() {
        Tenant unpaidTenant = model.getFilteredTenantList().get(0);
        unpaidTenant.setPaid(false);

        UnpaidCommand unpaidCommand = new UnpaidCommand(unpaidTenant.getPhone());
        String expectedMessage = String.format(UnpaidCommand.MESSAGE_ALREADY_UNPAID, unpaidTenant.getPhone().value);

        assertCommandFailure(unpaidCommand, model, expectedMessage);
    }

}
