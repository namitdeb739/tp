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
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyTenantTracker> readAddressBook(String filePath) throws Exception {
        return new JsonTenantTrackerStorage(Paths.get(filePath))
                .readTenantTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatTenantTracker.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidTenantTenantTracker.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidTenantAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidTenantTenantTracker.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
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
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTenantTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveTenantTracker(ReadOnlyTenantTracker addressBook, String filePath) {
        try {
            new JsonTenantTrackerStorage(Paths.get(filePath)).saveTenantTracker(addressBook,
                    addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTenantTracker(new TenantTracker(), null));
    }
}
