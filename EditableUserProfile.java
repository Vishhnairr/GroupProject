import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Editable User Profile
 *
 * This class creates the GUI for the user profile that
 * will be editable and can store the new data in
 * the user's file.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 28, 2024
 *
 */

public class EditableUserProfile {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextField[] dataTextFields;
    private String[] profileData;
    private UserProfileListener listener; // Listener field

    public interface UserProfileListener {
        void onProfileSave(String[] profileData);
        void onProfileClose();
    }

    public EditableUserProfile(String profile) {
        this.profileData = profile.split("\n");
        this.dataTextFields = new JTextField[profileData.length];
        initComponents();
        createAndShowGUI();
    }

    private void initComponents() {
        frame = new JFrame("Edit Your Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Set to directly close the window
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listener != null) {
                    listener.onProfileClose();
                }
            }
        });
        frame.setSize(500, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a title panel with centered label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        JLabel titleLabel = new JLabel("Boiler Town");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 51, 102));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel);

        // Add a non-editable field for the username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel(profileData[0]);
        usernamePanel.add(usernameLabel);
        mainPanel.add(usernamePanel);

        // Create editable text fields for the rest of the attributes
        for (int i = 1; i < profileData.length; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

            String[] parts = profileData[i].split(":", 2);
            JLabel label = new JLabel(parts[0] + ": ");
            label.setFont(new Font("Arial", Font.BOLD, 12));
            rowPanel.add(label);

            JTextField textField = new JTextField(parts.length > 1 ? parts[1].trim() : "", 20);
            textField.setFont(new Font("Arial", Font.PLAIN, 12));
            dataTextFields[i] = textField;
            rowPanel.add(textField);

            mainPanel.add(rowPanel);
        }

        // Add Save and Restore buttons
        addSaveRestoreButtons();

        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    // Additional methods (addSaveRestoreButtons, saveChanges, restoreOriginalData)
    private void addSaveRestoreButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton saveAndCloseButton = new JButton("Save and Close");
        saveAndCloseButton.addActionListener(e -> saveChangesAndClose());

        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(e -> restoreOriginalData());

        buttonPanel.add(saveAndCloseButton);
        buttonPanel.add(restoreButton);

        mainPanel.add(buttonPanel);
    }

    private void saveChangesAndClose() {
        if (saveChanges()) { // Assuming saveChanges now returns boolean indicating success
            if (listener != null) {
                listener.onProfileSave(profileData); // Notify about saving
            }
            closeWindow(); // Close after save
        }
    }
    private boolean saveChanges() {
        StringBuilder errorMessage = new StringBuilder();

        // Check password
        String password = dataTextFields[1].getText();
        if (password.length() < 4) {
            errorMessage.append("Password must be at least 4 characters long.\n");
        }

        // Check first name
        String firstName = dataTextFields[2].getText();
        if (firstName == null || firstName.isEmpty() || firstName.contains(" ")) {
            errorMessage.append("First name cannot be empty or contain spaces.\n");
        }

        // Check last name
        String lastName = dataTextFields[3].getText();
        if (lastName == null || lastName.isEmpty() || lastName.contains(" ")) {
            errorMessage.append("Last name cannot be empty or contain spaces.\n");
        }

        // Check email
        String email = dataTextFields[4].getText();
        if (email == null || email.isEmpty() || !email.contains("@") || email.contains(" ") ||
                email.charAt(email.length() - 4) != '.') {
            errorMessage.append("Email should be in the format 'user@example.___' and contain an '@' and a '.___'.\n");
        }

        // Check bio
        String bio = dataTextFields[5].getText();
        if (bio.length() > 50) {
            errorMessage.append("Bio must not exceed 50 characters. ");
            errorMessage.append("It should be a short summary about yourself.\n");
        }

        // Check profile view
        String profileView = dataTextFields[6].getText().toLowerCase();
        if (!(profileView.equals("true") || profileView.equals("false"))) {
            errorMessage.append("Profile view must be 'true' or 'false'.\n");
        }

        // Check message only
        String messageOnly = dataTextFields[7].getText().toLowerCase();
        if (!(messageOnly.equals("true") || messageOnly.equals("false"))) {
            errorMessage.append("Message only must be 'true' or 'false'.\n");
        }

        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            for (int i = 1; i < dataTextFields.length; i++) {
                profileData[i] = dataTextFields[i].getText();
            }
            return true;
        }
    }

    public void setListener(UserProfileListener listener) {
        this.listener = listener;
    }

    private void restoreOriginalData() {
        for (int i = 1; i < profileData.length; i++) {
            if (dataTextFields[i] != null) {
                dataTextFields[i].setText(profileData[i].split(":", 2)[1].trim());
            }
        }
    }
    private void createAndShowGUI() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void closeWindow() {
        frame.dispose(); // This will close the window
    }

}
