package address.data;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.io.*;


/**
 * @author Group 5
 * @version 1.0
 * @since 1.2
 *
 * The purpose of this class is to represent a generic address book
 */
public class AddressBook extends JFrame {

    /**
     * the data structures that will hold the data for the address book. Composed of a TreeMap
     * where the key is a String(the last name of the AddressEntry and the value is the a TreeSet
     * AddressEntry. This is because java does not contain a multiset in standard libraries.
     * Tree is used instead of hash because tree preserves the natural ordering of key which makes printing in
     * sorted order by last name(key) easy.
     */
    private final TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
    /**
     * main JPanel GUI for the address book application
     */
    private JPanel mainPanel;
    /**
     * creates a JList that allows address entries to be seen within the GUI's scrollable list
     */
    private JList<AddressEntry> addressEntryJList;
    /**
     * Button for find method
     */
    private JButton findButton;
    /**
     * Button for clear method
     */
    private JButton clearButton;
    /**
     * Button for add method
     */
    private JButton addButton;
    /**
     * Button for edit method
     */
    private JButton editButton;
    /**
     * TextField for find method
     */
    private JTextField findAddressEntry;
    /**
     * Button for remove method
     */
    private JButton removeButton;
    /**
     * TextField for first name
     */
    private JTextField firstNameEntry;
    /**
     * TextField for last name
     */
    private JTextField lastNameEntry;
    /**
     * TextField for street name
     */
    private JTextField streetEntry;
    /**
     * TextField for city name
     */
    private JTextField cityEntry;
    /**
     * TextField for state name
     */
    private JTextField stateEntry;
    /**
     * TextField for zip code
     */
    private JTextField zipEntry;
    /**
     * TextField for email address
     */
    private JTextField emailEntry;
    /**
     * TextField for phone number
     */
    private JTextField phoneEntry;
    /**
     * TextField for entry ID
     */
    private JTextField idEntry;
    /**
     * Creates the List Model for the JList to be populated and edited
     */
    private DefaultListModel<AddressEntry> listModel;

    /**
     * Default constructor for AddressBook. This constructs all GUI aspects of the Address Book as well as handles all anonymous
     * event handlers for each button and text field to be useable by the user.
     */
    public AddressBook() {
        setContentPane(mainPanel);
        setTitle("Address Book");
        setSize(750, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        listModel = new DefaultListModel<>();
        addressEntryJList.setModel(listModel);

        /**
         * anonymous event listener for the add button. This event listener ensures that address entries are added to the local memory data structure
         * as well as the Oracle database connected to the address book.
         */
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddressEntry addEntry = new AddressEntry(firstNameEntry.getText(), lastNameEntry.getText(), streetEntry.getText(), cityEntry.getText()
                                                            , stateEntry.getText(), Integer.valueOf(zipEntry.getText()), emailEntry.getText(), phoneEntry.getText(), idEntry.getText());
                addressEntryList.computeIfAbsent(addEntry.name.getLastName(), k -> new TreeSet<>()).add(addEntry);
                listModel.addElement(addEntry);
                String first = firstNameEntry.getText();
                String last = lastNameEntry.getText();
                String street = streetEntry.getText();
                String city = cityEntry.getText();
                String state = stateEntry.getText();
                int zip = Integer.valueOf(zipEntry.getText());
                String email = emailEntry.getText();
                String phone = phoneEntry.getText();
                String id = idEntry.getText();

                try {
                    Class.forName ("oracle.jdbc.OracleDriver");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                try {
                    Connection conn =
                            DriverManager.getConnection("jdbc:oracle:thin:mcs1016/hbXylEFo@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
                    Statement stmt = conn.createStatement();
                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ADDRESSENTRYTABLE(FIRSTNAME, LASTNAME, ADDRESS, CITY, STATE, ZIP, PHONENUMBER, EMAIL, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    pstmt.setString(1, first);
                    pstmt.setString(2, last);
                    pstmt.setString(3, street);
                    pstmt.setString(4, city);
                    pstmt.setString(5, state);
                    pstmt.setInt(6, zip);
                    pstmt.setString(7, phone);
                    pstmt.setString(8, email);
                    pstmt.setString(9, id);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                firstNameEntry.setText("");
                lastNameEntry.setText("");
                streetEntry.setText("");
                cityEntry.setText("");
                stateEntry.setText("");
                zipEntry.setText("");
                emailEntry.setText("");
                phoneEntry.setText("");
                idEntry.setText("");
            }
        });

        /**
         * anonymous event listener for the remove button. This event listener ensures that selected address entries are removed
         * from both the local memory data structure as well as the connected Oracle database
         */
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstNameEntry.setText("");
                lastNameEntry.setText("");
                streetEntry.setText("");
                cityEntry.setText("");
                stateEntry.setText("");
                zipEntry.setText("");
                emailEntry.setText("");
                phoneEntry.setText("");
                idEntry.setText("");
                AddressEntry ab = addressEntryJList.getSelectedValue();
                listModel.removeElementAt(addressEntryJList.getSelectedIndex());
                addressEntryList.remove(ab.name.getLastName());

                try {
                    Class.forName ("oracle.jdbc.OracleDriver");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                try {
                    Connection conn =
                            DriverManager.getConnection("jdbc:oracle:thin:mcs1016/hbXylEFo@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM ADDRESSENTRYTABLE WHERE ID = " + ab.getId());
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        /**
         * anonymous list listener for the JList allowing users to see entries populated in the data structure. This list listener
         * allows for selected address entries to populate their associated text fields with the appropriate data.
         */
        addressEntryJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    firstNameEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).name.getFirstName());
                    lastNameEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).name.getLastName());
                    streetEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).address.getStreet());
                    cityEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).address.getCity());
                    stateEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).address.getState());
                    zipEntry.setText(Integer.toString(listModel.getElementAt(addressEntryJList.getSelectedIndex()).address.getZip()));
                    emailEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).getEmail());
                    phoneEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).getPhone());
                    idEntry.setText(listModel.getElementAt(addressEntryJList.getSelectedIndex()).getId());
                }
            }
        });

        /**
         * anonymous event listener for the clear button. This even listener ensures that the Jlist shows all current address entries.
         * This button is used to clear the address entries found using the find button as well as clear the text fields so that new
         * address entries can be added.
         */
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                for (Map.Entry<String, TreeSet<AddressEntry>> map : addressEntryList.entrySet()) {
                    listModel.addElement(map.getValue().first());
                }
                firstNameEntry.setText("");
                lastNameEntry.setText("");
                streetEntry.setText("");
                cityEntry.setText("");
                stateEntry.setText("");
                zipEntry.setText("");
                emailEntry.setText("");
                phoneEntry.setText("");
                idEntry.setText("");
                findAddressEntry.setText("");
            }
        });

        /**
         * anonymous event listener for the find button. This even listener ensures that the scrollable list is repopulated
         * with only address entries that match the searched criteria in the associated find text field.
         */
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String findName = findAddressEntry.getText();
                listModel.clear();
                for (String s : addressEntryList.keySet()) {
                    if (s.contains(findName)) {
                        listModel.addElement(addressEntryList.get(s).first());
                    }
                }


            }


        });
        /**
         * anonymous event listener for the edit button. This event listener ensures that the edit button allows for the editing and storing of
         * edited data related to the selected address entry. The data edited affects both the local memory data structure as well as the
         * connected Oracle database
         */
        editButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("oracle.jdbc.OracleDriver");
                } catch (ClassNotFoundException ex){
                    ex.printStackTrace();
                }
                try{
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:mcs1016/hbXylEFo@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
                    Statement stmt = conn.createStatement();
                    String query = "UPDATE ADDRESSENTRYTABLE SET FIRSTNAME = ?, LASTNAME = ?, ADDRESS = ?, CITY = ?, STATE = ?, ZIP = ? , PHONENUMBER = ? , EMAIL = ?, ID = ? WHERE ID = " + idEntry.getText();
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1,firstNameEntry.getText());
                    ps.setString(2,lastNameEntry.getText());
                    ps.setString(3,streetEntry.getText());
                    ps.setString(4,cityEntry.getText());
                    ps.setString(5,stateEntry.getText());
                    ps.setInt(6,Integer.valueOf(zipEntry.getText()));
                    ps.setString(7,emailEntry.getText());
                    ps.setString(8,phoneEntry.getText());
                    ps.setString(9,idEntry.getText());
                    ps.executeUpdate();
                    stmt.close();
                    conn.close();
                    AddressEntry editEntry = new AddressEntry(firstNameEntry.getText(), lastNameEntry.getText(), streetEntry.getText(), cityEntry.getText()
                            , stateEntry.getText(), Integer.valueOf(zipEntry.getText()), emailEntry.getText(), phoneEntry.getText(), idEntry.getText());
                    listModel.setElementAt(editEntry,addressEntryJList.getSelectedIndex());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        });
    }

    /**
     * method connects to the specified Oracle database and allows for the injection of all information
     * from the database into the local memory data structure.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void loadAddressEntryTable() throws SQLException, ClassNotFoundException{

        // Load the Oracle JDBC driver
        Class.forName("oracle.jdbc.OracleDriver"); //name of driver may change w/ versions

        //check Oracle documentation online
        // Or could do DriverManager.registerDriver (new oracle.jdbc.OracleDriver());

        // Connect to the database
        // generic host url = jdbc:oracle:thin:login/password@host:port/SID for Oracle SEE Account INFO you
        // were given by our CS tech in an email ---THIS WILL BE DIFFERENT
        //jdbc:oracle:thin:@//adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu
        Connection conn =
                DriverManager.getConnection("jdbc:oracle:thin:mcs1016/hbXylEFo@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");

        // Create a Statement
        Statement stmt = conn.createStatement ();

        // Select the all (*) from the table JAVATEST

        ResultSet rset = stmt.executeQuery("SELECT * FROM ADDRESSENTRYTABLE");

        // Iterate through the result and print the employee names

        while (rset.next ()) { //get next row of table returned
            String firstName = rset.getString("firstName");
            String lastName = rset.getString("lastName");
            String address = rset.getString("ADDRESS");
            String city = rset.getString("CITY");
            String state = rset.getString("STATE");
            int zip = rset.getInt("ZIP");
            String phone = rset.getString("phoneNumber");
            String email = rset.getString("EMAIL");
            String id = rset.getString("ID");

            AddressEntry ae = new AddressEntry(firstName, lastName, address, city, state, zip, phone, email, id);
            addressEntryList.computeIfAbsent(ae.name.getLastName(), k -> new TreeSet<>()).add(ae);
            listModel.addElement(ae);
        }
        //Close access to everything...will otherwise happen when disconnect
        // from database.
        rset.close();
        stmt.close();
        conn.close();
    }
}
