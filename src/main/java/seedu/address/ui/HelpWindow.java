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
    private TextArea AddHelp;

    @FXML
    private TextArea ArchiveHelp;

    @FXML
    private TextArea DeleteHelp;

    @FXML
    private TextArea EditHelp;

    @FXML
    private TextArea FilterHelp;

    @FXML
    private TextArea FindHelp;

    @FXML
    private TextArea MapHelp;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        AddHelp.setText("""
            add: Adds a tenant to TenantTrack.
                        
            Parameters: g/ GIVEN NAME f/ FAMILY NAME p/ PHONE e/ EMAIL a/ ADDRESS [t/TAG]...
                        
            Example:
            add g/ John f/ Doe p/ 98765432 e/ johndoe@email.com
            a/ 21 Lower Kent Ridge Rd, 119077 t/ hdb t/ landed
            """);

        ArchiveHelp.setText("""
            archive: Archives the tenant identified by the index number
            used in the displayed tenant list.
                        
            Parameters:
            INDEX (must be a positive integer).
                        
            Example:
            archive 1
            """);

        DeleteHelp.setText("""
            delete: Deletes the tenant identified by the index number
            used in the displayed tenant list.
                       
            Parameters:
            INDEX (must be a positive integer)
                       
            Example:
            delete 1
            """);

        EditHelp.setText("""
            edit: Edits the details of the tenant identified by the index number
            used in the displayed tenant list.
                        
            Parameters:
            INDEX (must be a positive integer) [g/ GIVEN NAME] [f/ FAMILY NAME] [p/ PHONE] [e/ EMAIL] [a/ ADDRESS] [t/ TAG]...
                        
            Example:
            edit 1 p/91234567 e/johndoe@example.com
            """);

        FilterHelp.setText("""
            filter: Filters all tenants whose addresses contain any of the specified
            keywords (case-insensitive). Displays them in a list.
                        
            Parameters:
            KEYWORD [MORE_KEYWORDS]...
                        
            Example:
            filter Kent Ridge
            """);

        FindHelp.setText("""
            find: Finds all tenants whose names contain any of the specified keywords (case-insensitive). Displays them in a list.
                        
            Parameters: KEYWORD [MORE_KEYWORDS]...
                        
            Example:
            find alice bob charlie
            """);

        MapHelp.setText("""
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
