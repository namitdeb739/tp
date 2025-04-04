package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tenant.Tenant;

/**
 * An UI component that displays information of a {@code Tenant}.
 */
public class TenantCard extends UiPart<Region> {

    private static final String FXML = "TenantListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     *      level 4</a>
     */

    public final Tenant tenant;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private ImageView paidIcon;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code TenantCode} with the given {@code Tenant} and index to display.
     */
    public TenantCard(Tenant tenant, int displayedIndex) {
        super(FXML);
        this.tenant = tenant;
        id.setText(displayedIndex + ". ");
        name.setText(tenant.getName().toString() + "  ");
        phone.setText("Phone: " + tenant.getPhone().value);
        address.setText("Address: " + tenant.getAddress().value);
        email.setText("Email: " + tenant.getEmail().value);
        tenant.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        paidIcon.setImage(new Image("/images/PaidIcon.png"));
        paidIcon.visibleProperty().bind(tenant.isPaidProperty());
    }
}
