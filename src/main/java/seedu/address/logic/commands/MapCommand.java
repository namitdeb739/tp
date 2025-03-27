package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Tenant;

/**
 * Launches Google Maps for the address of a tenant at the specified index.
 * Also handles cross-platform browser launching.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Launches Google Maps for the address of the tenant "
        + "specified by the index in the displayed list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Maps launched successfully";

    public static final String GOOGLE_URL = "https://www.google.com/maps/search/?api=1&query=";
    private static final String MESSAGE_EXTERNAL_LINK_FAILURE = "The current OS does not support "
        + "opening external links.";
    private static final String MESSAGE_INVALID_URI = "The URI specified is invalid.";
    private static final String MESSAGE_OS_ACCESS_FAILURE = "Access to open external links is "
        + "denied by the security manager.";
    private static final String MESSAGE_NO_DEFAULT_BROWSER = "Default browser not found or failed "
        + "to launch. Please try again.";

    private final Index index;
    private final Desktop desktop;

    /**
     * Creates a {@code MapCommand} with the given tenant index.
     *
     * @param index Index of the tenant in the filtered tenant list.
     */
    public MapCommand(Index index) {
        requireNonNull(index);
        this.index = index;

        Desktop tmpDesktop;
        try {
            tmpDesktop = (!isLinux() && Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
        } catch (HeadlessException e) {
            tmpDesktop = null;
        }
        this.desktop = tmpDesktop;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tenant> lastShownList = model.getFilteredTenantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
        }

        Tenant tenant = lastShownList.get(index.getZeroBased());
        Address address = tenant.getAddress();
        assert address != null : "Tenant address should not be null";
        String encodedAddress = URLEncoder.encode(address.toString(), StandardCharsets.UTF_8);
        assert encodedAddress != null : "Encoded address should not be null";
        String uriLink = GOOGLE_URL + encodedAddress;
        assert uriLink.startsWith(GOOGLE_URL) : "URI link should start with Google Maps base URL";

        openUri(uriLink);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Opens the given URL using platform-specific methods.
     *
     * @param url The URL to be opened.
     * @throws CommandException If the operation fails.
     */
    protected void openUri(String url) throws CommandException {
        requireNonNull(url);

        try {
            URI uri = new URI(url);

            if (isLinux()) {
                openOnLinux(url);
            } else {
                openOnDesktop(uri);
            }
        } catch (URISyntaxException e) {
            throw new CommandException(MESSAGE_INVALID_URI, e);
        } catch (SecurityException e) {
            throw new CommandException(MESSAGE_OS_ACCESS_FAILURE, e);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_NO_DEFAULT_BROWSER, e);
        }
    }

    /**
     * Opens the URL on Linux using {@code xdg-open}.
     */
    private void openOnLinux(String url) throws IOException, CommandException {
        if (isCommandAvailable("xdg-open")) {
            Runtime.getRuntime().exec(new String[]{"xdg-open", url});
        } else {
            throw new CommandException(MESSAGE_EXTERNAL_LINK_FAILURE);
        }
    }

    /**
     * Opens the URI using the {@code Desktop} API on non-Linux systems.
     */
    private void openOnDesktop(URI uri) throws IOException, CommandException {
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(uri);
        } else {
            throw new CommandException(MESSAGE_EXTERNAL_LINK_FAILURE);
        }
    }

    /**
     * Checks whether the current operating system is Linux-based.
     */
    private boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        return os.contains("nix") || os.contains("nux") || os.contains("aix");
    }

    /**
     * Checks whether the specified command is available on the system.
     */
    private boolean isCommandAvailable(String command) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"which", command});
            return process.getInputStream().read() != -1;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof MapCommand
            && index.equals(((MapCommand) other).index));
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{index=" + index + "}";
    }
}
