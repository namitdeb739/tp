package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTenants.ALICE;
import static seedu.address.testutil.TypicalTenants.HOON;
import static seedu.address.testutil.TypicalTenants.IDA;
import static seedu.address.testutil.TypicalTenants.getTypicalTenantTracker;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTenantTracker;
import seedu.address.model.TenantTracker;

public class JsonTenantTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTenantTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTenantTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTenantTracker(null));
    }

    private java.util.Optional<ReadOnlyTenantTracker> readTenantTracker(String filePath) throws Exception {
        return new JsonTenantTrackerStorage(Paths.get(filePath))
                .readTenantTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTenantTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTenantTracker("notJsonFormatTenantTracker.json"));
    }

    @Test
    public void readTenantTracker_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTenantTracker("invalidAndValidTenantTenantTracker.json"));
    }

    @Test
    public void readTenantTracker_invalidAndValidTenant_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTenantTracker("invalidAndValidTenantTenantTracker.json"));
    }

    @Test
    public void readAndSaveTenantTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTenantTracker.json");
        TenantTracker original = getTypicalTenantTracker();
        JsonTenantTrackerStorage jsonTenantTrackerStorage = new JsonTenantTrackerStorage(filePath);

        // Save in new file and read back
        jsonTenantTrackerStorage.saveTenantTracker(original, filePath);
        ReadOnlyTenantTracker readBack = jsonTenantTrackerStorage.readTenantTracker(filePath).get();
        assertEquals(original, new TenantTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTenant(HOON);
        original.removeTenant(ALICE);
        jsonTenantTrackerStorage.saveTenantTracker(original, filePath);
        readBack = jsonTenantTrackerStorage.readTenantTracker(filePath).get();
        assertEquals(original, new TenantTracker(readBack));

        // Save and read without specifying file path
        original.addTenant(IDA);
        jsonTenantTrackerStorage.saveTenantTracker(original); // file path not specified
        readBack = jsonTenantTrackerStorage.readTenantTracker().get(); // file path not specified
        assertEquals(original, new TenantTracker(readBack));

    }

    @Test
    public void saveTenantTracker_nullTenantTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTenantTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code tenantTracker} at the specified {@code filePath}.
     */
    private void saveTenantTracker(ReadOnlyTenantTracker tenantTracker, String filePath) {
        try {
            new JsonTenantTrackerStorage(Paths.get(filePath)).saveTenantTracker(tenantTracker,
                    addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTenantTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTenantTracker(new TenantTracker(), null));
    }
}
