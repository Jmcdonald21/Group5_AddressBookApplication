package address.data;

public class Name {
    /**
     * first name
     */
    private String firstName;
    /**
     * last name
     */
    private String lastName;

    /**
     * initializes default values for Name
     */
    public Name() {
        firstName = "";
        lastName = "";
    }
    /**
     * returns a Name initialized with the data in the parameters provided
     * @param firstName
     * @param lastName
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**
     * method to set firstName variable for Name
     */
    public void setFirstName(String firstname) {
        this.firstName = firstName;
    }
    /**
     * method to set lastName variable for Name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * method to return the firstName variable from Name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * method to return the lastName variable from Name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + "\n   ";
    }
}
