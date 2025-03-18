package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Tenant;
import seedu.address.testutil.TenantBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());
    }

    @Test
    public void execute_newTenant_success() {
        Tenant validPerson = new TenantBuilder().build();

        Model expectedModel = new ModelManager(model.getTenantTracker(), new UserPrefs());
        expectedModel.addTenant(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)), expectedModel);
    }

    // @Test
    // public void execute_duplicatePerson_throwsCommandException() {
    // Tenant personInList = model.getTenantTracker().getTenantList().get(0);
    // assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    // }

}
