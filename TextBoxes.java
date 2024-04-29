import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Text Boxes
 *
 * This class creates the GUI for any panels that deal
 * with user's input in terms of JTextFields
 * and incorporates them into the Clients class.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 28, 2024
 *
 */

public class TextBoxes {

    private ArrayList<JTextField> textFields;
    private ArrayList<JLabel> labels;
    private boolean buttonClicked;
    private JFrame frames;
    private int textFieldHeight;
    private int textFieldLength;
    private String[] options;
    private JButton enterButton;
    private boolean panelOpen;
    private ArrayList<String> collectedInfo;

    private JComboBox<String> dropDownList;

    public TextBoxes() {
        this.textFields = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.buttonClicked = false;
        this.frames = null;
        this.textFieldHeight = 500;
        this.textFieldLength = 500;
        this.options = null;
        this.enterButton = null;
        this.panelOpen = false;
        this.collectedInfo = new ArrayList<>();
    }

    public void addTextBoxes(String labelText) {
        this.textFields.add(new JTextField(5));
        this.labels.add(new JLabel(labelText));
        this.collectedInfo.add(null);
    }

    public void addTextBoxPassword(String labelText) {
        this.textFields.add(new JPasswordField(5));
        this.labels.add(new JLabel(labelText));
        this.collectedInfo.add(null);
    }

    public ArrayList<JTextField> getTextFields() {
        return this.textFields;
    }

    public ArrayList<JLabel> getLabels() {
        return this.labels;
    }

    public boolean getHitButton() {
        return buttonClicked;
    }

    public void setHitButton(boolean hitButton) {
        this.buttonClicked = hitButton;
    }

    public void setTextFieldHeight(int height) {
        this.textFieldHeight = height;
    }

    public void setTextFieldLength(int length) {
        this.textFieldLength = length;
    }

    public boolean isPanelOpen() {
        return this.panelOpen;
    }

    public JFrame getFrame() {
        return this.frames;
    }

    public void setButtonEnter() {
        this.enterButton = new JButton("Enter");
    }

    public void setTextBoxes(int number, String textBoxes) {
        this.textFields.get(number).setText(textBoxes);
    }

    public void setTextBoxesLength(int number) {
        this.textFields = new ArrayList<>(number);
    }

    public void emptyTextFields() {
        for (int i = 0; i < this.textFields.size(); i++) {
            textFields.get(i).setText("");
        }
    }

    ActionListener enterNew = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < textFields.size(); i++) {
                collectedInfo.set(i, textFields.get(i).getText());
            }
            if (dropDownList != null) {
                collectedInfo.set(textFields.size(), String.valueOf(dropDownList.getSelectedItem()));
            }

            buttonClicked = true;

        }
    };

    WindowListener exitNew = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            buttonClicked = true;
            panelOpen = false;

        }
    };

    public void createPane() {
        panelOpen = true;
        buttonClicked = false;
        frames = new JFrame("Boiler Town");
        frames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames.setSize(textFieldLength, textFieldHeight);
        frames.setResizable(false);
        frames.setLocationRelativeTo(null);

        GridLayout gl = new GridLayout();
        gl.setRows(textFields.size());
        gl.setColumns(2);

        JPanel textPanel = new JPanel();
        frames.add(textPanel, BorderLayout.NORTH);
        textPanel.setLayout(gl);

        JLabel currentLabel;
        JTextField currentTextField;
        for (int i = 0; i < textFields.size(); i++) {
            currentLabel = labels.get(i);
            currentTextField = textFields.get(i);
            textPanel.add(currentLabel);
            textPanel.add(currentTextField);
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        frames.add(bottomPanel, BorderLayout.SOUTH);

        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(enterButton);
        enterButton.addActionListener(enterNew);

        frames.addWindowListener(exitNew);
        frames.setVisible(true);

    }

    public String getResponses() {
        String responses = "";
        if (panelOpen) {
            for (int i = 0; i < collectedInfo.size(); i++) {
                responses += collectedInfo.get(i);
                if (i < collectedInfo.size() - 1) {
                    responses += ";";
                }
            }
        } else {
            responses = "exit";
        }

        return responses;
    }

}
