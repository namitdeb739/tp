package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TenantTracker;
import seedu.address.testutil.TypicalTenants;

public class JsonSerializableTenantTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTenantTrackerTest");
    private static final Path TYPICAL_TENANTS_FILE = TEST_DATA_FOLDER.resolve("typicalTenantsTenantTracker.json");
    private static final Path INVALID_TENANT_FILE = TEST_DATA_FOLDER.resolve("invalidTenantTenantTracker.json");
    // private static final Path DUPLICATE_PERSON_FILE =
    // TEST_DATA_FOLDER.resolve("duplicateTenantTenantTracker.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableTenantTracker dataFromFile =
                JsonUtil.readJsonFile(TYPICAL_TENANTS_FILE, JsonSerializableTenantTracker.class).get();
        TenantTracker tenantTrackerFromFile = dataFromFile.toModelType();
        TenantTracker typicalTenantsTenantTracker = TypicalTenants.getTypicalTenantTracker();
        assertEquals(tenantTrackerFromFile, typicalTenantsTenantTracker);
    }

    @Test
    public void toModelType_invalidTenantFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTenantTracker dataFromFile =
                JsonUtil.readJsonFile(INVALID_TENANT_FILE, JsonSerializableTenantTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    // @Test
    // public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
    // JsonSerializableAddressBook dataFromFile =
    // JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class).get();
    // assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
    // dataFromFile::toModelType);
    // }

}
