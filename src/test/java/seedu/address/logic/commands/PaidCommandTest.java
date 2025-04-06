package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Phone;
import seedu.address.model.tenant.Tenant;
import seedu.address.testutil.TenantBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for PaidCommand.
 */
public class PaidCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());
        expectedModel = new ModelManager(model.getTenantTracker(), new UserPrefs());
    }

    @Test
    public void execute_tenantNotFound_throwsCommandException() {
        Phone nonExistentPhone = new Phone("99999999");
        PaidCommand paidCommand = new PaidCommand(nonExistentPhone);

        String expectedMessage = String.format(PaidCommand.MESSAGE_TENANT_NOT_FOUND, nonExistentPhone);
        assertCommandFailure(paidCommand, model, expectedMessage);
    }

    @Test
    public void execute_tenantAlreadyPaid_throwsCommandException() {
        Tenant alreadyPaidTenant = model.getFilteredTenantList().get(0);
        alreadyPaidTenant.setPaid(true);
        PaidCommand paidCommand = new PaidCommand(alreadyPaidTenant.getPhone());

        String expectedMessage = String.format(PaidCommand.MESSAGE_ALREADY_PAID, alreadyPaidTenant.getPhone());
        assertCommandFailure(paidCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Tenant firstTenant = model.getFilteredTenantList().get(0);
        Tenant secondTenant = new TenantBuilder().withPhone("61231231").build();
        PaidCommand paidFirstCommand = new PaidCommand(firstTenant.getPhone());
        PaidCommand paidSecondCommand = new PaidCommand(secondTenant.getPhone());

        // same object -> returns true
        assertEquals(paidFirstCommand, paidFirstCommand);

        // same values -> returns true
        PaidCommand paidFirstCommandCopy = new PaidCommand(firstTenant.getPhone());
        assertEquals(paidFirstCommand, paidFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(paidFirstCommand, 1);

        // null -> returns false
        assertNotEquals(paidFirstCommand, null);

        // different tenant -> returns false
        assertNotEquals(paidFirstCommand, paidSecondCommand);
    }
}
