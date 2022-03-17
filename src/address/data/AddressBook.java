package address.data;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;


/**
 * @author Student Name
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
    private JPanel mainPanel;
    private JList<AddressEntry> addressEntryJList;
    private JButton findButton;
    private JButton listButton;
    private JButton addButton;
    private JButton editButton;
    private JTextField findAddressEntry;
    private JButton removeButton;
    private JTextField firstNameEntry;
    private JTextField lastNameEntry;
    private JTextField streetEntry;
    private JTextField cityEntry;
    private JTextField stateEntry;
    private JTextField zipEntry;
    private JTextField emailEntry;
    private JTextField phoneEntry;
    private JTextField idEntry;
    private DefaultListModel<AddressEntry> listModel;

    //JList for scrollable panel
    public AddressBook() {
        setContentPane(mainPanel);
        setTitle("Address Book");
        setSize(750, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        listModel = new DefaultListModel<>();
        addressEntryJList.setModel(listModel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddressEntry addEntry = new AddressEntry(firstNameEntry.getText(), lastNameEntry.getText(), streetEntry.getText(), cityEntry.getText()
                                                            , stateEntry.getText(), Integer.valueOf(zipEntry.getText()), emailEntry.getText(), phoneEntry.getText(), idEntry.getText());
                addressEntryList.computeIfAbsent(addEntry.name.getLastName(), k -> new TreeSet<>()).add(addEntry);
                listModel.addElement(addEntry);
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
                listModel.removeElementAt(addressEntryJList.getSelectedIndex());
                addressEntryList.remove(addressEntryJList.getSelectedIndex());
            }
        });
        addressEntryJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
        });
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    /** a method which prints out all fields in all entries of the address book
     *
     */
    public void list() {

        this.toString();

    }

    /** a method which removes an address entry from the address book
     *
     * @param lastName is the last name(or some initial consecutive chars) of the person contained
     *                 in the AddressEntry to be removed
     *
     * First we get the prefixSet which is the set of all AddressEntry that have the first consecutive
     * of the lastName of AddressEntry match the lastName parameter passed. If the size of the set is 1 then
     * print out AddressEntry and prompt user if they wish to delete. If more than 1 element in set then print all
     * elements and ask user to select element based on index.
     */
    public void remove(String lastName) {
        //first obtain a set which contains all address entries in address book where
        //the first characters of their last name exactly match all of the chars in parameter lastname
        TreeSet<AddressEntry> s = this.getPrefixSet(lastName);
        Scanner keyboard = new Scanner(System.in);
        try {
            if (s.size() == 1) {
                System.out.println("The following entry was found in the address book.");
                System.out.printf("%-3s" + s.first() + "\n", " ");
                System.out.println("Hit 'y' to remove the entry or 'n' to return to main menu");
                if (keyboard.nextLine().compareTo("y") == 0)
                    addressEntryList.get(s.first().name.getLastName()).remove(s.first());
            } else if (s.size() > 1) {
                ArrayList<AddressEntry> list = new ArrayList<>();
                int i = 1;
                System.out.println("The following entries were found in the address book," +
                        "select number of entry you wish to remove:\n");
                for (AddressEntry entry : s) {
                    list.add(entry);
                    System.out.printf("%-3s" + entry + "\n\n", i + ":");
                    i++;
                }
                int removalIndex = keyboard.nextInt() - 1;
                keyboard.nextLine();
                if(removalIndex < list.size() && removalIndex >= 0)
                    System.out.println("Hit 'y' to remove the following entry or 'n' to return to main menu:\n");
                System.out.printf("%-3s" + list.get(removalIndex) + "\n\n", "  ");
                if (keyboard.nextLine().compareTo("y") == 0) {
                    TreeSet<AddressEntry> set = addressEntryList.get(list.get(removalIndex).name.getLastName());
                    set.remove(list.get(removalIndex));
                }
            } else
                System.out.println("No entries with last name " + lastName + " were found.");
        }
        catch(InputMismatchException e) {
            System.out.println("Error: You need to enter a valid integer. No action taken.");
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println("Error: Invalid element selection. No action taken.");
        }
    }

    /** a method which adds an address entry to the address book
     *
     * @param entry is an instance of AddressEntry to add to the AddressBook
     *
     * If the key has never been seen before then a new TreeSet is created to contain the entry.
     * If the key has been seen before then entry is simply added to the correct set.
     */
    public void add(AddressEntry entry) {
        addressEntryList.computeIfAbsent(entry.name.getLastName(), k -> new TreeSet<>()).add(entry);
        listModel.addElement(entry);
    }

    /** a method which reads in address entries from a text file and adds them to the address book
     *
     * @param filename is a string which is the name of a text file that contains address Entry data in a certain format
     *
     *the format is firstName\nlastName\nAdress\ncity\nState\nzip\nemail\nphoneNumber
     */
    public void readFromFile(String filename) {
        try{
            //open file
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            //count number of entries processed
            int count =0;

            //read from file
            while((line=br.readLine()) != null) {

                this.add(new AddressEntry(line, br.readLine(), br.readLine(), br.readLine(),
                                          br.readLine(), Integer.parseInt(br.readLine()), br.readLine(), br.readLine(), br.readLine()));

                count++;
            }
            System.out.println("\nProcessed "+ count + " new Address Entries");
        }
        catch(FileNotFoundException e) {
            //print out message for file not found
            System.out.println(e.getMessage());
        }
        catch(IOException ex) {
            //print out stack for other exceptions
            ex.printStackTrace();
        }
    }

    /** a method which displays one or multiple address entries
     *
     * @param startOf_lastName is a string which contains either a full last name or the first consecutive chars
     * of a last name in an AddressEntry
     */
    public void find(String startOf_lastName) {
        SortedMap<String, TreeSet<AddressEntry>> tempMap;
        tempMap = addressEntryList.subMap(startOf_lastName, startOf_lastName + Character.MAX_VALUE);
        if(tempMap.size() > 0) {
            int i = 1;
            //this line computes total number of Address entries in tempMap
            System.out.println("The following " + tempMap.values().stream().mapToInt(TreeSet::size).sum() +
                    " entries were found in the address book" +
                    " for a last name starting with " + "\"" + startOf_lastName + "\"");
            for(Map.Entry<String, TreeSet<AddressEntry>> entry : tempMap.entrySet()) {
                for(AddressEntry item : entry.getValue()) {
                    System.out.printf("%-3s" + item + "\n\n", i + ":");
                    i++;
                }
            }
        }
        else
            System.out.println("There were no entries were found in the address book" +
                    " for a last name starting with " + "\"" + startOf_lastName + "\"");
    }

    /** a method that returns a set of address entries in which the first characters in the
     *  last name of each entry in the returned set are an exact match for the characters passed
     *  to the function
     *
     * @param startOf_lastName full last name or start of last name
     * @return A TreeSet which contains all of the AddressEntry instances whose lastName field
     * matches from the start every char provided in startOf_lastName.
     */
    private TreeSet<AddressEntry> getPrefixSet(String startOf_lastName) {
        SortedMap<String, TreeSet<AddressEntry>> tempMap;
        TreeSet<AddressEntry> tempSet = new TreeSet<>();
        tempMap = addressEntryList.subMap(startOf_lastName, startOf_lastName + Character.MAX_VALUE);

        for(Map.Entry<String, TreeSet<AddressEntry>> entry : tempMap.entrySet()) {
            tempSet.addAll(entry.getValue());
        }
        return tempSet;
    }

    /**
     * removes all AddressEntry from the AddressBook
     */
    public void clear() {
        addressEntryList.clear();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int i = 1;
        for(Map.Entry<String, TreeSet<AddressEntry>> entry : addressEntryList.entrySet()) {
            for(AddressEntry item : entry.getValue()) {
                if(item != null) {
                    result.append(String.format("%-3s" + item + "\n\n", i + ":"));
                    i++;
                }
            }
        }
        return result.toString();
    }
}
