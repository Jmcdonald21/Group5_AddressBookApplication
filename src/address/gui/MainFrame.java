package address.gui;

import javax.swing.*;


public class MainFrame extends JFrame {

    private JPanel MainPanel;
    private JButton newButton;
    private JButton displayButton;
    private JButton removeButton;
    private JTextField firstNameDisplay;
    private JTextField lastNameDisplay;
    private JTextField addressDisplay;
    private JTextField emailDisplay;
    private JTextField phoneDisplay;
    private JScrollPane scrollPanel;
    private JTextPane textPanel;

    public MainFrame() {
        setContentPane(MainPanel);
        setTitle("Address Book");
        setSize(850, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton getDisplayButton() {
        return displayButton;
    }

    public JTextPane getTextPanel() {
        return textPanel;
    }


}
