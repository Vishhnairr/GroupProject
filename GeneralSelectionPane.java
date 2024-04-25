import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project 5 - GeneralSelectionPane
 * <p>
 * This class allows for the customization and generation of panes that involve a dropdown box of options, or when
 * some text just needs to be displayed to the user. the createSelectionPane method is the method to generate a basic
 * pane that just has an enter button with a dropdown box of options, with the potential to add a logout button.
 * The createCalendarPane method is used for when the user is displayed a calendar along with a dropdown box of
 * valid appointment times in that calendar so that the user can reschedule their appointment. This method is also
 * used to display all a users approved appointments with a dropdown box to allow them to choose which one they want
 * to edit. The showCalendar method is only for when a customer wants to view a calendar for a store, it just has a
 * JScrollArea to display all the text of the calendar, there is no other buttons. Likewise, the showAppointments
 * method is for when the user just wants to be displayed appointments. The method just displays appointments to the
 * user in a JScrollArea with no other buttons.
 */


public class GeneralSelectionPane {
    private String[] choices;  // all the choices in the dropdown box
    private JComboBox<String> cb;  // the dropdown box object
    private JFrame frame;  // the JFrame object
    private JButton buttonEnter;  // The enter button object
    private JButton buttonLogOut;  // The logout button object

    private String option;  // The string that holds the selected option from the dropdown
    private String dropDownLabel; // the label for the dropdown box;
    private boolean buttonHit;  // boolean to tell if any button has been hit, including the button to close the window
    private boolean open;  // boolean to check if the window is open

    // default constructor, // you must use the setters to individually change each component
    public GeneralSelectionPane() {
        this.choices = null;
        this.cb = null;
        this.frame = null;
        this.buttonEnter = null;
        this.buttonLogOut = null;
        this.option = "none";
        this.buttonHit = false;
        this.open = true;
        this.dropDownLabel = "";
    }

    // returns the frame
    public JFrame getFrame() {
        return frame;
    }

    // changes the choices in the dropdown box
    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    // set the dropdown box based on the choices
    public void setCb() {
        this.cb = new JComboBox<String>();
        for (String choice : choices) {
            cb.addItem(choice);
        }

    }

    // setting the enter button
    public void setButtonEnter() {
        this.buttonEnter = new JButton("Enter");
    }

    // setting the logout button
    public void setButtonLogOut() {
        this.buttonLogOut = new JButton("Log out");
    }

    // returns the option
    public String getOption() {
        return option;
    }

    // returns if a button was hit
    public boolean isButtonHit() {
        return buttonHit;
    }

    // returns if the frame is open
    public boolean isOpen() {
        return open;
    }

    public void setDropDownLabel(String dropDownLabel) {
        this.dropDownLabel = dropDownLabel;
    }

    // action listener for enter button
    ActionListener newEnter = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            option = String.valueOf(cb.getSelectedItem());
            buttonHit = true;
        }
    };

    // action listener for logout button
    ActionListener newLogout = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            option = "logout";
            buttonHit = true;
        }
    };

    // window listener to check if the frame gets closed
    WindowListener newExit = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            option = "exit";
            buttonHit = true;
            open = false;
        }
    };

    // frame for when calendar needs to be displayed when user is rescheduling
    public void createCalendarPane(String calendarFileLines) {
        open = true;
        buttonHit = false;
        frame = new JFrame("Boiler Town");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 550);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        frame.add(leftPanel, BorderLayout.NORTH);
        JTextArea textArea = new JTextArea(25, 25);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        leftPanel.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (String line : calendarFileLines.split(";")) {
            textArea.append(line + "\n");
        }

        JLabel lbl = new JLabel(dropDownLabel);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        frame.add(rightPanel, BorderLayout.SOUTH);
        rightPanel.add(lbl);

        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(cb);

        buttonEnter.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(buttonEnter);
        buttonEnter.addActionListener(newEnter);

        frame.addWindowListener(newExit);
        frame.setVisible(true);

    }

    // frame for most general menu panes
    public void createSelectionPane() {
        open = true;
        buttonHit = false;
        frame = new JFrame("Boiler Town");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // added code

        frame.add(panel);

        JLabel lbl = new JLabel(dropDownLabel);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lbl);


        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(cb);

        buttonEnter.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(buttonEnter);
        buttonEnter.addActionListener(newEnter);

        if (buttonLogOut != null) {
            buttonLogOut.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(buttonLogOut);
            buttonLogOut.addActionListener(newLogout);
        }

        frame.addWindowListener(newExit);
        frame.setVisible(true);

    }

    public void showCalendar(String calendarFileLines) { // can be used as show user bio
        open = true;
        buttonHit = false;
        frame = new JFrame("Boiler Town");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 550);
        JTextArea textArea = new JTextArea(25, 25);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        frame.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (String line : calendarFileLines.split(";")) {
            textArea.append(line + "\n");
        }
        frame.addWindowListener(newExit);
        frame.setVisible(true);
    }

    public void showAppointments(String appointments) { // can be used to just view friends
        open = true;
        buttonHit = false;
        frame = new JFrame("Boiler Town");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 550);
        JTextArea textArea = new JTextArea(25, 25);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        frame.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (String line : appointments.split(";")) {
            textArea.append(line + "\n");
        }
        frame.addWindowListener(newExit);
        frame.setVisible(true);
    }
}
