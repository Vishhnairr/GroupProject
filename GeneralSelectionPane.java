import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GeneralSelectionPane
 *
 * This class creates the GUI for any parts that include dropdown boxes.
 * It allows the user to choose between the options and is used in the
 * client class in order to take data and send it back to the server.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 28, 2024
 *
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


    // frame for most general menu panes
    public void createSelectionPane() {
        open = true;
        buttonHit = false;
        frame = new JFrame("Boiler Town");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Add the title panel at the top of the frame
        addTitlePanel(frame, "Boiler Town"); // Set the appropriate title for the context

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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

    private void addTitlePanel(JFrame frame, String titleText) {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        // Top and bottom margins

        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Font color
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 51, 102)); // Deep blue background
        titlePanel.add(titleLabel);

        frame.add(titlePanel, BorderLayout.NORTH);
    }

}
