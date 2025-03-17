---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The _**Architecture Diagram**_ given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its _API_ in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

f

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Our target users are landlords who list their multiple properties to rent on a platform like Airbnb.

**Value proposition**: TenantTrack provides fast and efficient contact management for landlords handling multiple rental properties. Optimized for power users who prefer a CLI, it enables quick access, organization, and retrieval of tenant and client details, streamlining communication and reducing administrative hassle while retaining the benefits of a GUI.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                             | I want to …​                                     | So that I can…​                                                     |
| -------- | ------------------------------------------------- | ---------------------------------------------- | ------------------------------------------------------------------- |
| `* * *`  | Landlord of multiple properties                  | group my tenants/properties by building/neighborhood/zone | better organize my properties and visits. |
| `* * *`  | Landlord                                         | update tenant contact details (phone/email)   | remain in touch with my tenants in the event their contacts change. |
| `* * *`  | Landlord                                         | access analytics like total income from properties | better manage my personal finances. |
| `* * *`  | Landlord                                         | see which tenants' rents are overdue or coming up in the next week | remind them to pay rent on time. |
| `* * *`  | Landlord with properties housing multiple people | assign multiple tenant contacts to one property | contact any of the residents of a single property easily. |
| `* * *`  | Landlord with properties housing multiple people | filter properties by occupancy status | access which properties are available for rent so I can list them on the market. |
| `* * *`  | Landlord                                         | search for a tenant's contact details by name | quickly get their contact to communicate with them efficiently. |
| `* * *`  | Landlord                                         | add the property a tenant is staying at to their contact details | know which property the tenant is renting. |
| `* * *`  | Landlord                                         | add a duration of stay to a tenant's contact detail | effectively manage my rental scheduling. |
| `* *`    | Landlord                                         | sort tenants by attributes like gender | derive the demographics for the tenants. |
| `* *`    | Landlord                                         | tag tenants with custom tags | categorize and manage them effectively. |
| `* *`    | Landlord                                         | update property details | ensure important details like lease information can be updated. |
| `* *`    | Landlord                                         | export tenant contact list | integrate them with external CRM or communication tools. |
| `* *`    | Landlord                                         | mark tenants as active or inactive | distinguish between current and past tenants without deleting records. |
| `* *`    | Landlord                                         | add multiple phone numbers or emails for a tenant | contact them through different channels. |
| `* *`    | Landlord                                         | set a primary contact for a property with multiple tenants | know who to reach first in case of urgent matters. |
| `* *`    | Landlord                                         | add a profile picture to a tenant’s contact details | visually recognize them easily. |
| `* *`    | Landlord                                         | undo accidental deletions of contacts | avoid losing important information. |
| `* *`    | Landlord                                         | archive past tenants instead of deleting them | keep historical records without cluttering my main contact list. |
| `* *`    | Landlord                                         | set up auto-complete for addresses | enter property details faster. |
| `* *`    | Landlord                                         | retrieve an address through tenant name and phone number | know where the tenant is staying. |
| `* *`    | Landlord                                         | check for validity of tenant occupancy | confirm whether the tenant is legally living there. |
| `* *`    | Landlord                                         | retrieve lease agreements | check the specifics on the agreement. |
| `* *`    | Landlord                                         | retrieve addresses through tenant history | track who lived in which apartment over time. |
| `* *`    | Landlord                                         | retrieve tenant's past rental history | know whether a tenant has rented with me before. |
| `* *`    | Landlord                                         | import tenant contacts from a CSV file | quickly set up my database. |
| `*`      | Landlord                                         | list my tenants' names | get in touch with them for property-related matters. |
| `*`      | Landlord                                         | list my tenants' phone numbers | get in touch with them for property-related matters. |
| `*`      | Landlord                                         | list my tenants' emails | get in touch with them for property-related matters. |
| `*`      | Landlord                                         | list my tenants' properties | get in touch with them for property-related matters. |
| `*`      | Landlord                                         | delete tenant name when a lease ends | keep my database current. |
| `*`      | Landlord                                         | delete tenant email when a lease ends | keep my database current. |
| `*`      | Landlord                                         | delete tenant contact number when a lease ends | keep my database current. |
| `*`      | Landlord                                         | delete tenant property of residence when a lease ends | keep my database current. |
| `*`      | Landlord                                         | add a new tenant name | record tenant's name. |
| `*`      | Landlord                                         | add a new tenant phone number | record tenant's phone number. |
| `*`      | Landlord                                         | add a new tenant email | record tenant's email. |
| `*`      | Landlord                                         | add a new tenant property | record tenant's property. |

_{More to be added}_

### Use cases

(For all use cases below, the **System** is the `TenantTrack` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a new tenant (Create)**

**MSS**

1. Landlord requests to add a new tenant.
2. System prompts for tenant details (name, phone number, email, property, etc.).
3. Landlord enters the tenant details and confirms.
4. System saves the new tenant.

    Use case ends.

**Extensions**

* 2a. Required fields are missing.
  * 2a1. System shows an error message.

      Use case resumes at step 2.

* 3a. The tenant already exists.
  * 3a1. System shows a warning and asks for confirmation.

      Use case resumes at step 2.

---

**Use case: Search for a tenant by name (Read)**

**MSS**

1. Landlord requests to search for a tenant by name.
2. System displays matching tenant details.

    Use case ends.

**Extensions**

* 2a. No matching tenant is found.
  * 2a1. System shows an error message.

      Use case ends.

---

**Use case: Update tenant contact details (Update)**

**MSS**

1. Landlord requests to update a tenant’s contact details.
2. System displays the tenant’s current contact details.
3. Landlord updates phone number and/or email.
4. System saves the updated contact details.

    Use case ends.

**Extensions**

* 2a. The tenant does not exist.
  * 2a1. System shows an error message.

      Use case ends.

* 3a. The new contact details are in an invalid format.
  * 3a1. System shows an error message.

      Use case resumes at step 2.

---

**Use case: Delete a tenant (Delete)**

**MSS**

1. Landlord requests to delete a tenant.
2. System asks for confirmation.
3. Landlord confirms the deletion.
4. System deletes the tenant record.

    Use case ends.

**Extensions**

* 2a. The tenant does not exist.
  * 2a1. System shows an error message.

      Use case ends.

* 3a. The tenant has active lease agreements.
  * 3a1. System prevents deletion and notifies the landlord.

      Use case ends.

---

**Use case: See overdue or upcoming rent payments**

**MSS**

1. Landlord requests a list of tenants with overdue or upcoming rent payments.
2. System displays the list of tenants with due dates.

    Use case ends.

**Extensions**

* 2a. No tenants have overdue or upcoming rent.
  * 2a1. System shows a message indicating no upcoming payments.

      Use case ends.

_{More to be added}_

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
1. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
1. The final product should be an evolution of the given codebase, with incremental changes leading to a working product at each step. Full code replacement is allowed if done gradually.
1. The product should cater to users who type fast and prefer keyboard input over other means of interaction.
1. The software should be a single-user application, meaning no multi-user access, shared file storage, or concurrent users on the same system.
1. The development process should follow a breadth-first incremental approach, ensuring consistent progress throughout the project duration.
1. All data should be stored locally in a human-editable text file, allowing advanced users to manipulate data directly.
1. A database management system (DBMS) should not be used to store data, encouraging object-oriented data management.
1. The software must follow the object-oriented programming (OOP) paradigm, though minor deviations for justifiable reasons are acceptable.
1. The application should be platform-independent, working on Windows, Linux, and macOS without relying on OS-specific features.
1. The software should be compatible with Java 17 and must not require any other Java version to run.
1. The application should be portable and run without requiring an installer.
1. The software should not depend on a remote server, ensuring usability even after the project ends.
1. Third-party libraries, frameworks, or services are allowed only if they are free, open-source, have permissive licenses, do not require user installation, and comply with all other constraints.
1. The GUI should work smoothly at standard screen resolutions (1920x1080 and above) and scale settings (100% and 125%), while remaining usable at 1280x720 and 150% scaling.
1. The entire application should be packaged into a single JAR file, or if necessary, a single ZIP file containing all required resources.
1. The deliverable file sizes should not exceed 100MB for the application and 15MB per PDF document to ensure ease of downloading and testing.
1. The user guide and developer guide should be PDF-friendly, avoiding expandable panels, embedded videos, and animated GIFs.
1. The application should be optimized for a command-line interface (CLI) first, ensuring fast command-based input for target users while allowing visual feedback through a GUI.
1. The product should maintain realistic use cases, targeting scenarios where a standalone desktop application is a viable solution.

_{More to be added}_

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
