package address.data;

/**
 * @author Group 5
 * @version 1.0
 * @since 1.2
 *
 * purpose: This class is used to contain and provide data necessary to represent an
 * AddressEntry
 */
public class AddressEntry implements Comparable<AddressEntry>{

    /**
     * create new instance of Name
     */
    Name name;
    /**
     * create new instance of Address
     */
    Address address;
    /**
     * phone
     */
    private String phone;
    /**
     * email
     */
    private String email;
    /**
     * id
     */
    private String id;

    /**
     * default constructor for AddressEntry
     */
    public AddressEntry() {
        name = new Name();
        address = new Address();
        phone = "";
        email = "";
        id = "";
    }

    /**
     * returns an AddressEntry initialized with the data in the parameters provided
     * @param name
     * @param address
     * @param phone
     * @param email
     * @param id
     */
    public AddressEntry(Name name, Address address, String phone, String email, String id) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    /**
     * returns an AddressEntry initialized with the data in the parameters provided
     * @param firstName
     * @param lastName
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param email
     * @param phone
     * @param id
     */
    public AddressEntry(String firstName, String lastName, String street,
                        String city, String state, int zip, String email, String phone, String id)
    {
        this.name = new Name(firstName, lastName);
        this.address = new Address(street, city, state, zip);
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    /**
     * Override method for comparing address entries. Used to ensure no duplicate entries within the data structure.
     * @param other
     * @return
     */
    @Override
    public int compareTo(AddressEntry other) {
        if(this.name.getLastName().compareTo(other.name.getLastName()) != 0)
            return this.name.getLastName().compareTo(other.name.getLastName());
        else if(this.name.getFirstName().compareTo(other.name.getFirstName()) == 0 &&
                this.address.getCity().compareTo(other.address.getCity()) == 0 &&
                this.phone.compareTo(other.phone) == 0 &&
                this.address.getState().compareTo(other.address.getState()) == 0 &&
                this.address.getStreet().compareTo(other.address.getStreet()) == 0 &&
                this.email.compareTo(other.email) == 0 &&
                this.address.getZip() == other.address.getZip() &&
                this.id.compareTo(other.id) == 0) {
            return 0;
        }
        else
            return 1;
    }

    /**
     * Override method for returning String info about specified variable(name object).
     * @return
     */
    @Override
    public String toString() {
        return name.toString();
    }


    /**
     * method to set the phone variable for AddressEntry
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /** method to set the email variable for AddressEntry
     *
     * @param email is an email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * method to set the id variable for AddressEntry
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    /** method to return the phone number of the address entry
     *
     * @return a String which represents phone number
     */
    public String getPhone() {
        return phone;
    }
    /** method to return the email address of the address entry
     *
     * @return a String which represents email
     */
    public String getEmail() {
        return email;
    }
    /**
     * method to return the id variable of the address entry
     * @return
     */
    public String getId() {
        return id;
    }
}
