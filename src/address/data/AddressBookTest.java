package address.data;

import address.data.AddressBook;
import address.data.AddressEntry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Group 5
 * @version 1.0
 * @since 1.2
 *
 * purpose: This class is used to test the AddressBook class
 */
public class AddressBookTest {

    /**
     * this will be the stream that System writes to during the test
     */
    private static final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();

    /**
     * The AddressBook that the test class will use to test
     */
    private AddressBook ab;

    /**
     * An instance of AddressEntry to load into AddressBook
     */
    private final AddressEntry ae1 = new AddressEntry("John", "A", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", "1");

    /**
     * An instance of AddressEntry to load into AddressBook
     */
    private final AddressEntry ae2 = new AddressEntry("John", "Doe", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", "2");

    /**
     * An instance of AddressEntry to load into AddressBook
     */
    private final AddressEntry ae3 = new AddressEntry("John", "Dof", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", "3");

    /**
     * An instance of AddressEntry to load into AddressBook
     */
    private final AddressEntry ae4 = new AddressEntry("John", "A", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", "4");

}