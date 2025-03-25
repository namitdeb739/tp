package seedu.address.model.tenant.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateTenantException extends RuntimeException {
    public DuplicateTenantException() {
        super("Operation would result in duplicate persons");
    }
}
