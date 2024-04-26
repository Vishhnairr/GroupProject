import javax.swing.*;
import java.awt.*;

public class UserProfileDisplay {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel[] dataLabels;
    private String[] profileData;

    public UserProfileDisplay(String profile) {
        this.profileData = profile.split("\n");
        this.dataLabels = new JLabel[profileData.length];
        initComponents();
        createAndShowGUI();
    }

    private void initComponents() {
        frame = new JFrame("User Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a title panel with centered label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // 设置标题的上下边距

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
            rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // 设置每行的左右边距

            JLabel label = new JLabel(profileData[i]);
            dataLabels[i] = label;
            rowPanel.add(label);

            mainPanel.add(rowPanel);
        }
    }



    private void createAndShowGUI() {
        frame.pack();
        frame.setLocationRelativeTo(null); // 设置窗口在屏幕中央显示
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 测试数据
        String profile = "Username: user\nEmail: user@example.com\nPhone: 1234567890";
        new UserProfileDisplay(profile);
    }
}
