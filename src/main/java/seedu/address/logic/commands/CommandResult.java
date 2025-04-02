package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Archive list and active list should be toggled
     */
    private boolean isToggleArchiveView;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and other fields
     * set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and isarchived value,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean isToggleArchiveView) {
        this(feedbackToUser, false, false);
        this.isToggleArchiveView = isToggleArchiveView;
    }

    public String getFeedbackToUser() {
        return feedbackToUser.substring(feedbackToUser.indexOf('\n') + 1);
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isToggleArchiveView() {
        return isToggleArchiveView;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.substring(feedbackToUser.indexOf('\n') + 1).equals(
                otherCommandResult.feedbackToUser.substring(otherCommandResult.feedbackToUser.indexOf('\n') + 1))
                && showHelp == otherCommandResult.showHelp && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("feedbackToUser", feedbackToUser).add("showHelp", showHelp)
                .add("exit", exit).toString();
    }

}
