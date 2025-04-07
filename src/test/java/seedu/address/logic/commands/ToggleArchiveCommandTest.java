package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ToggleArchiveCommand.MESSAGE_FAILURE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ToggleArchiveCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_emptyArchiveListException_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_FAILURE, true);
        assertCommandSuccess(new ToggleArchiveCommand(), model, expectedCommandResult, expectedModel);
    }
}
