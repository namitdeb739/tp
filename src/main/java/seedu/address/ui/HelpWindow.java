package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103t-w12-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TitledPane commandExamplesPane;

    @FXML
    private TextArea addHelp;

    @FXML
    private TextArea archiveHelp;

    @FXML
    private TextArea deleteHelp;

    @FXML
    private TextArea editHelp;

    @FXML
    private TextArea filterHelp;

    @FXML
    private TextArea findHelp;

    @FXML
    private TextArea mapHelp;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        addHelp.setText("""
            add: Adds a tenant to TenantTrack.

            Parameters: givenN/ GIVEN_NAME familyN/ FAMILY_NAME phone/ PHONE email/ EMAIL address/ ADDRESS [tag/TAG]...

            Example:
            add givenN/ John familyN/ Doe phone/ 98765432 email/ johndoe@email.com
            address/ 21 Lower Kent Ridge Rd, S119077 tag/ hdb tag/ landed
            """);

        archiveHelp.setText("""
            archive: Archives the tenant identified by the index number
            used in the displayed tenant list.

            Parameters:
            INDEX (must be a positive integer).

            Example:
            archive 1
            """);

        deleteHelp.setText("""
            delete: Deletes the tenant identified by the index number
            used in the displayed tenant list.

            Parameters:
            INDEX (must be a positive integer)

            Example:
            delete 1
            """);

        editHelp.setText("""
            edit: Edits the details of the tenant identified by the index number
            used in the displayed tenant list.

            Parameters:
            INDEX (must be a positive integer) [givenN/ GIVEN NAME] [familyN/ FAMILY NAME]
            [phone/ PHONE] [email/ EMAIL] [address/ ADDRESS] [tag/ TAG]...

            Example:
            edit 1 phone/91234567 email/johndoe@example.com
            """);

        filterHelp.setText("""
            filter: Filters all tenants whose addresses contain any of the specified
            keywords (case-insensitive). Displays them in a list.

            Parameters:
            KEYWORD [MORE_KEYWORDS]...

            Example:
            filter Kent Ridge
            """);

        findHelp.setText("""
            find: Finds all tenants whose names contain any of the specified keywords (case-insensitive).
            Displays them in a list.

            Parameters: KEYWORD [MORE_KEYWORDS]...

            Example:
            find alice bob charlie
            """);

        mapHelp.setText("""
            map: Launches Google Maps for the tenant’s address at the given index.

            Parameters:
            INDEX (must be a positive integer)

            Example:
            map 1
            """);

        root.setMinWidth(600);
        root.setMinHeight(100);
        root.setWidth(600);
        root.setResizable(true);

        commandExamplesPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                getRoot().setHeight(500);
            } else {
                getRoot().setHeight(300);
            }
        });
    }


    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
