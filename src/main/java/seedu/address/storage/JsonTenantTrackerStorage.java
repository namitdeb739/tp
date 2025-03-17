package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTenantTracker;

/**
 * A class to access TenantTracker data stored as a json file on the hard disk.
 */
public class JsonTenantTrackerStorage implements TenantTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTenantTrackerStorage.class);

    private Path filePath;

    public JsonTenantTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTenantTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTenantTracker> readTenantTracker() throws DataLoadingException {
        return readTenantTracker(filePath);
    }

    /**
     * Similar to {@link #readTenantTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTenantTracker> readTenantTracker(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTenantTracker> jsonTenantTracker =
                JsonUtil.readJsonFile(filePath, JsonSerializableTenantTracker.class);
        if (!jsonTenantTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTenantTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTenantTracker(ReadOnlyTenantTracker readOnlyTenantTracker) throws IOException {
        saveTenantTracker(readOnlyTenantTracker, filePath);
    }

    /**
     * Similar to {@link #saveTenantTracker(ReadOnlyTenantTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTenantTracker(ReadOnlyTenantTracker readOnlyTenantTracker, Path filePath) throws IOException {
        requireNonNull(readOnlyTenantTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTenantTracker(readOnlyTenantTracker), filePath);
    }

}
