package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.tenant.Tenant;

/**
 * Panel containing the list of persons.
 */
public class TenantListPanel extends UiPart<Region> {
    private static final String FXML = "TenantListPanel.fxml";
    // private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Tenant> personListView;

    /**
     * Creates a {@code TenantListPanel} with the given {@code ObservableList}.
     */
    public TenantListPanel(ObservableList<Tenant> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new TenantListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a
     * {@code PersonCard}.
     */
    class TenantListViewCell extends ListCell<Tenant> {
        @Override
        protected void updateItem(Tenant tenant, boolean empty) {
            super.updateItem(tenant, empty);

            if (empty || tenant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TenantCard(tenant, getIndex() + 1).getRoot());
            }
        }
    }

}
