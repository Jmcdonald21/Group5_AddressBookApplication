package address;

import java.sql.*;
import address.data.AddressEntry;

public class DataBaseConnect {

    public static void testAddressEntryTable() throws SQLException, ClassNotFoundException{

        // Load the Oracle JDBC driver
        Class.forName ("oracle.jdbc.OracleDriver"); //name of driver may change w/ versions

        //check Oracle documentation online
        // Or could do DriverManager.registerDriver (new oracle.jdbc.OracleDriver());

        // Connect to the database
        // generic host url = jdbc:oracle:thin:login/password@host:port/SID for Oracle SEE Account INFO you
        // were given by our CS tech in an email ---THIS WILL BE DIFFERENT
        //jdbc:oracle:thin:@//adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu
        Connection conn =
                DriverManager.getConnection("jdbc:oracle:thin:mcs1016/hbXylEFo@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");

        // Create a Statement
        Statement stmt = conn.createStatement();

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

        }

        //Close access to everything...will otherwise happen when disconnect
        // from database.
        rset.close();
        stmt.close();
        conn.close();

    }
}
