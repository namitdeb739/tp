package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tenant.Address;
import seedu.address.model.tenant.Tenant;

/**
 * Contains integration tests for {@code MapCommand}.
 */
public class MapCommandTest {

    private final Model model = new ModelManager(getTypicalTenantTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Tenant tenantToMap = model.getFilteredTenantList().get(INDEX_FIRST_PERSON.getZeroBased());
        Address address = tenantToMap.getAddress();
        String encodedAddress = URLEncoder.encode(address.toString(), StandardCharsets.UTF_8);
        String expectedUri = MapCommand.GOOGLE_URL + encodedAddress;

        // Use a test subclass to intercept the URI
        class TestMapCommand extends MapCommand {
            private String capturedUri = null;

            TestMapCommand(Index index) {
                super(index);
            }

            protected void openUri(String url) {
                this.capturedUri = url;
            }

            public String getCapturedUri() {
                return capturedUri;
            }
        }

        TestMapCommand mapCommand = new TestMapCommand(INDEX_FIRST_PERSON);
        CommandResult result = mapCommand.execute(model);

        assertEquals(MapCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedUri, mapCommand.getCapturedUri());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTenantList().size() + 1);

        // Use anonymous subclass to ensure openUri is never called
        MapCommand mapCommand = new MapCommand(outOfBoundsIndex) {
        };

        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MapCommand mapCommand1 = new MapCommand(INDEX_FIRST_PERSON);
        MapCommand mapCommand2 = new MapCommand(INDEX_FIRST_PERSON);
        MapCommand mapCommand3 = new MapCommand(INDEX_SECOND_PERSON);

        // same values -> true
        assertTrue(mapCommand1.equals(mapCommand2));

        // same object -> true
        assertTrue(mapCommand1.equals(mapCommand1));

        // null -> false
        assertFalse(mapCommand1.equals(null));

        // different index -> false
        assertFalse(mapCommand1.equals(mapCommand3));

        // different type -> false
        assertFalse(mapCommand1.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        MapCommand mapCommand = new MapCommand(INDEX_FIRST_PERSON);
        String expected = MapCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, mapCommand.toString());
    }
}
