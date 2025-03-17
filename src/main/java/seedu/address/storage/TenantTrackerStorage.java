package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTenantTracker;

/**
 * Represents a storage for {@link seedu.address.model.TenantTracker}.
 */
public interface TenantTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTenantTrackerFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTenantTracker}. Returns {@code Optional.empty()} if
     * storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTenantTracker> readTenantTracker() throws DataLoadingException;

    /**
     * @see #getTenantTrackerFilePath()
     */
    Optional<ReadOnlyTenantTracker> readTenantTracker(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTenantTracker} to the storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTenantTracker(ReadOnlyTenantTracker addressBook) throws IOException;

    /**
     * @see #saveTenantTracker(ReadOnlyTenantTracker)
     */
    void saveTenantTracker(ReadOnlyTenantTracker addressBook, Path filePath) throws IOException;

}
