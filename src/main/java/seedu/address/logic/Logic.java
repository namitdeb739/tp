package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyTenantTracker;
import seedu.address.model.tenant.Tenant;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the TenantTracker.
     *
     * @see seedu.address.model.Model#getTenantTracker()
     */
    ReadOnlyTenantTracker getTenantTracker();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Tenant> getFilteredTenantList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getTenantTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the archived tenant list
     */
    ObservableList<Tenant> getFilteredArchivedTenantList();

    /**
     * Toggles between archived list and unarchived list.
     */
    void toggleArchiveView();

    /**
     * Shows true if the current displayed list is archived
     */
    boolean isShowingArchived();
}
