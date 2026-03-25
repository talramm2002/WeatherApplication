import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class adminLogin implements ActionListener {

    private static final Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color backgroundColor = new Color(30, 100, 160);
    private static final Color buttonColor = new Color(70, 130, 180);
    private static final Color textColor = new Color(255, 255, 255);

    private static JLabel userLabel;
    private static JTextField usernameText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton loginButton;
    private static JLabel success;

    public adminLogin() {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Admin Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        usernameText = new JTextField(20);
        usernameText.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameText, gbc);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordText = new JPasswordField(20);
        passwordText.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordText, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(labelFont);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(textColor);
        loginButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        success = new JLabel("", SwingConstants.CENTER);
        success.setFont(fieldFont);
        success.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(success, gbc);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(adminLogin::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = usernameText.getText();
        String password = passwordText.getText();

        if (user.equals("admin") && password.equals("password")) {
            success.setText("Login successful!");
        } else {
            success.setText("Invalid username or password.");
        }
    }

}
