package address;
import address.data.AddressBook;
import java.sql.*;

/**
 * @author Group 5
 * @version 1.0
 * @since 1.2
 *
 * purpose: This class is used to initialize an instance of AddressBook and load entries from the database
 */
public class AddressBookApplication {

    /**
     * creates an AddressBook initializes the AddressBook with some AddressEntry's and
     * then prompts the user to add, delete, list, and search for entries.
     * @param args command line arguments passed to main
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //create instance of AddressBook for application
        AddressBook ab = new AddressBook();
        //load address entries from database
        ab.loadAddressEntryTable();

    }
}
