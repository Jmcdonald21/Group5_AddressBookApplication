package address.data;

public class Address {

    /**
     * street
     */
    private String street;
    /**
     * city
     */
    private String city;
    /**
     * state
     */
    private String state;
    /**
     * zip
     */
    private Integer zip;

    /**
     * initializes default values for Address
     */
    public Address() {
        street = "";
        city = "";
        state = "";
        zip = 0;
    }
    /**
     * returns an Address initialized with the data in the parameters provided
     * @param street
     * @param city
     * @param state
     * @param zip
     */
    public Address(String street, String city, String state, int zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    /**
     * method to set street variable for Address
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * method to set city variable for Address
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * method to set state variable for Address
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * method to set zip variable for Address
     * @param zip
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * method to return the street variable from Address
     * @return
     */
    public String getStreet() {
        return street;
    }

    /**
     * method to return the city variable from Address
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * method to return the state variable from Address
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * method to return the zip variable from Address
     * @return
     */
    public int getZip() {
        return zip;
    }

    /**
     * Override method for returning String information about the specified variables(street, city, state, zip).
     * @return
     */
    @Override
    public String toString() {
        return street + "\n   " + city + ", " + state + " " + zip;
    }
}
