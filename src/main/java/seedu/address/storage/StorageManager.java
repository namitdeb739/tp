package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTenantTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TenantTrackerStorage tenantTrackerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and
     * {@code UserPrefStorage}.
     */
    public StorageManager(TenantTrackerStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.tenantTrackerStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getTenantTrackerFilePath() {
        return tenantTrackerStorage.getTenantTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyTenantTracker> readTenantTracker() throws DataLoadingException {
        return readTenantTracker(tenantTrackerStorage.getTenantTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyTenantTracker> readTenantTracker(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tenantTrackerStorage.readTenantTracker(filePath);
    }

    @Override
    public void saveTenantTracker(ReadOnlyTenantTracker addressBook) throws IOException {
        saveTenantTracker(addressBook, tenantTrackerStorage.getTenantTrackerFilePath());
    }

    @Override
    public void saveTenantTracker(ReadOnlyTenantTracker addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tenantTrackerStorage.saveTenantTracker(addressBook, filePath);
    }

}
