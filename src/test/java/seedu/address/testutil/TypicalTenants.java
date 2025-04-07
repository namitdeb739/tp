package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GIVEN_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GIVEN_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TenantTracker;
import seedu.address.model.tenant.Tenant;

/**
 * A utility class containing a list of {@code Tenant} objects to be used in tests.
 */
public class TypicalTenants {

    public static final Tenant ALICE =
            new TenantBuilder().withName("Alice", "Pauline").withAddress("123, Jurong West Ave 6, #08-111, 123456")
                    .withEmail("alice@example.com").withPhone("98765432").withTags("friends").build();
    public static final Tenant BENSON =
            new TenantBuilder().withName("Benson", "Meier").withAddress("311, Clementi Ave 2, #02-25, 123456")
                    .withPhone("98765433").withEmail("benson@example.com").withTags("owesMoney", "friends").build();
    public static final Tenant CARL = new TenantBuilder().withName("Carl", "Kurz").withPhone("98765434")
            .withEmail("heinz@example.com").withAddress("wall street, 123456").build();
    public static final Tenant DANIEL = new TenantBuilder().withName("Daniel", "Meier").withPhone("98765435")
            .withEmail("cornelia@example.com").withAddress("10th street, 123456").withTags("friends").build();
    public static final Tenant ELLE = new TenantBuilder().withName("Elle", "Meyer").withPhone("98765436")
            .withEmail("werner@example.com").withAddress("michegan ave, 123456").build();
    public static final Tenant FIONA = new TenantBuilder().withName("Fiona", "Kunz").withPhone("98765437")
            .withEmail("lydia@example.com").withAddress("little tokyo, 123456").build();
    public static final Tenant GEORGE = new TenantBuilder().withName("George", "Best").withPhone("98765438")
            .withEmail("anna@example.com").withAddress("4th street, 123456").build();

    // Manually added
    public static final Tenant HOON = new TenantBuilder().withName("Hoon", "Meier").withPhone("98765439")
            .withEmail("stefan@example.com").withAddress("little india, 123456").build();
    public static final Tenant IDA = new TenantBuilder().withName("Ida", "Mueller").withPhone("98765430")
            .withEmail("hans@example.com").withAddress("chicago ave, 123456").build();

    // Added Manually for FilterCommandTest
    public static final Tenant JAMES = new TenantBuilder().withName("James", "Smith").withPhone("98765431")
            .withEmail("james@example.com").withAddress("21 Lower Kent Ridge Rd, 119077").build();

    public static final Tenant MIKE = new TenantBuilder().withName("Mike", "Johnson").withPhone("98765422")
            .withEmail("mike@example.com").withAddress("21 Lower Kent Ridge Rd, 119077").build();

    public static final Tenant OLIVER = new TenantBuilder().withName("Oliver", "Jones").withPhone("98765423")
            .withEmail("oliver@example.com").withAddress("21 Lower Kent Ridge Rd, 119077").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Tenant AMY =
            new TenantBuilder().withName(VALID_GIVEN_NAME_AMY, VALID_FAMILY_NAME_AMY).withPhone(VALID_PHONE_AMY)
                    .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();
    public static final Tenant BOB = new TenantBuilder().withName(VALID_GIVEN_NAME_BOB, VALID_FAMILY_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTenants() {} // prevents instantiation

    /**
     * Returns an {@code TenantTracker} with all the typical persons.
     */
    public static TenantTracker getTypicalTenantTracker() {
        TenantTracker ab = new TenantTracker();
        for (Tenant tenant : getTypicalTenants()) {
            ab.addTenant(tenant);
        }
        return ab;
    }

    public static List<Tenant> getTypicalTenants() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JAMES, MIKE, OLIVER));
    }

    public static TenantTracker getEmptyTenantTracker() {
        return new TenantTracker();
    }
}
