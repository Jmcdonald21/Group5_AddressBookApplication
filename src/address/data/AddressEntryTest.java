package address.data;

import address.data.AddressEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Student Name
 * @version 1.0
 * @since 1.2
 *
 * purpose: This class is used to test the AddressEntry class
 */
public class AddressEntryTest {

    /**
     * Test method for {@link AddressEntry#AddressEntry()}
     */
    @Test
    public void testAddressEntry() {
        AddressEntry ae = new AddressEntry("John", "Doe", "street", "city", "state", 12345,
                "email@gmail.com", "123-456-7891", "1");
        String expected = "John Doe\n   street\n   city, state 12345\n   email@gmail.com\n   123-456-7891\n   1";
        assertEquals(expected, ae.toString());
    }

    /**
     * Test method for {@link AddressEntry#toString()}.
     */
    @Test
    public void testToString() {
        AddressEntry ae = new AddressEntry("John", "Doe", "street", "city", "state", 12345,
                                           "email@gmail.com", "123-456-7891", "2");
        String expected = "John Doe\n   street\n   city, state 12345\n   email@gmail.com\n   123-456-7891\n   2";
        assertEquals(expected, ae.toString());
    }

    /**
     * Test method for {@link AddressEntry#compareTo(AddressEntry)}.
     */
    @Test
    public void testCompareTo() {
        AddressEntry ae1 = new AddressEntry("John", "Doe", "street", "city", "state", 12345,
                "email@gmail.com", "123-456-7891", "1");

        AddressEntry ae2 = new AddressEntry("John", "Doe", "street", "city", "state", 12345,
                "email@gmail.com", "123-456-7891", "1");

        AddressEntry ae3 = new AddressEntry("John", "Dof", "street", "city", "state", 12345,
                "email@gmail.com", "123-456-7891", "1");

        AddressEntry ae4 = new AddressEntry("John", "A", "street", "city", "state", 12345,
                "email@gmail.com", "123-456-7891", "1");

        assertEquals(ae1.compareTo(ae2), 0);
        assertTrue(ae1.compareTo(ae3) < 0);
        assertTrue(ae1.compareTo(ae4) > 0);
    }

    /**
     * Test method for {@link Name#getFirstName()}
     *                 {@link Name#getLastName()}
     *                 {@link Address#getCity()}
     *                 {@link Address#getState()}
     *                 {@link Address#getZip()}
     *                 {@link Address#getStreet()}
     *                 {@link AddressEntry#getEmail()}
     *                 {@link AddressEntry#getPhone()}
     *                 {@link Name#setFirstName(java.lang.String)}
     *                 {@link Name#setLastName(java.lang.String)}
     *                 {@link Address#setCity(java.lang.String)}
     *                 {@link Address#setState(java.lang.String)}
     *                 {@link Address#setZip(int)}
     *                 {@link Address#setStreet(java.lang.String)}
     *                 {@link AddressEntry#setEmail(java.lang.String)}
     *                 {@link AddressEntry#setPhone(java.lang.String)}
     */
    @Test
    public void testGetSet() {
        AddressEntry ae = new AddressEntry();
        ae.name.setFirstName("John");
        ae.name.setLastName("Purcell");
        ae.address.setCity("Livermore");
        ae.address.setState("HI");
        ae.address.setZip(12345);
        ae.address.setStreet("Singletree");
        ae.setEmail("fake@yahoo.com");
        ae.setPhone("123-456-7891");
        ae.setId("1");

        assertEquals("John", ae.name.getFirstName());
        assertEquals("Purcell", ae.name.getLastName());
        assertEquals("Livermore", ae.address.getCity());
        assertEquals("HI", ae.address.getState());
        assertEquals(12345, ae.address.getZip());
        assertEquals("Singletree", ae.address.getStreet());
        assertEquals("fake@yahoo.com", ae.getEmail());
        assertEquals("123-456-7891", ae.getPhone());
        assertEquals("1", ae.getId());
    }
}