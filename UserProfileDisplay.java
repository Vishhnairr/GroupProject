import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User Profile Display
 *
 * This class creates a GUI that displays a specifically
 * searched user’s profile with all of their information,
 * not including their password. It allows the user to
 * look at other users and their information, if that
 * user is friends with them or if that specific user has
 * their profile view on public (true).
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 28, 2024
 *
 */

public class UserProfileDisplay {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel[] dataLabels;
    private String[] profileData;

    public UserProfileDisplay(String profile, Runnable onCloseCallback) {
        this.profileData = profile.split("\n");
        this.dataLabels = new JLabel[profileData.length];
        initComponents(onCloseCallback);
        createAndShowGUI();
    }

    private void initComponents(Runnable onCloseCallback) {
        frame = new JFrame("User Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (onCloseCallback != null) {
                    onCloseCallback.run();  // 调用回调函数，执行关闭后的操作
                }
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        for (int i = 0; i < profileData.length; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

            JLabel label = new JLabel(profileData[i]);
            dataLabels[i] = label;
            rowPanel.add(label);

            mainPanel.add(rowPanel);
        }
    }

    private void createAndShowGUI() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
