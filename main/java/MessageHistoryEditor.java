import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageHistoryEditor {
    private JFrame frame;
    private JPanel mainPanel;
    private List<JCheckBox> checkBoxes;
    private List<String> messages;
    private MessageHistoryListener listener;

    public interface MessageHistoryListener {
        void onWindowClosed();
        void onMessagesDeleted(List<String> remainingMessages);
    }

    public MessageHistoryEditor(String history, MessageHistoryListener listener) {
        if (history.equals("")) {
            messages = new ArrayList<>();
        } else {
            this.messages = new ArrayList<>(Arrays.asList(history.split("\n")));
        }
        this.checkBoxes = new ArrayList<>();
        this.listener = listener;
        initComponents();
        createAndShowGUI();
    }

    private void initComponents() {
        frame = new JFrame("Message History");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (listener != null) {
                    listener.onWindowClosed();
                }
                frame.dispose();
            }
        });
        frame.setSize(500, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (messages.isEmpty()) {
            mainPanel.add(new JLabel("No messages to display."));
        } else {
            for (String message : messages) {
                JPanel messagePanel = new JPanel();
                messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JCheckBox checkBox = new JCheckBox(message);
                checkBoxes.add(checkBox);
                messagePanel.add(checkBox);

                mainPanel.add(messagePanel);
            }
        }

        addDeleteAndCloseButton();

        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }


    private void addDeleteAndCloseButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteAndCloseButton = new JButton("Delete and Close");

        deleteAndCloseButton.setEnabled(!messages.isEmpty());

        deleteAndCloseButton.addActionListener(e -> deleteSelectedMessagesAndClose());
        buttonPanel.add(deleteAndCloseButton);
        mainPanel.add(buttonPanel);
    }


    private void deleteSelectedMessagesAndClose() {
        List<Integer> toRemoveIndices = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            JCheckBox checkBox = checkBoxes.get(i);
            if (checkBox.isSelected()) {
                toRemoveIndices.add(i);
            }
        }

        for (int i = toRemoveIndices.size() - 1; i >= 0; i--) {
            int removeIndex = toRemoveIndices.get(i);
            messages.remove(removeIndex);
        }


        if (listener != null) {
            listener.onMessagesDeleted(new ArrayList<>(messages));
        }
        frame.dispose();
    }


    public void createAndShowGUI() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
