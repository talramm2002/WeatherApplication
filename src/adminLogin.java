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
import javax.swing.UIManager;

import util.appTheme;

public class adminLogin implements ActionListener {

    private static JTextField usernameText;
    private static JPasswordField passwordText;
    private static JLabel success;

    public adminLogin() {
        // Set Nimbus look and feel for a modern UI appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Created the main frame for the admin login
        JFrame frame = new JFrame("Weathermaxxing Admin Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Created a panel with a custom background color and padding
        JPanel panel = new JPanel();
        panel.setBackground(appTheme.backgroundColour);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Used GridBagLayout to make the window fit the components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Added components to the panel with proper layout constraints for both
        // username and passwword fields
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(appTheme.labelFont);
        userLabel.setForeground(appTheme.white);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        usernameText = new JTextField(20);
        usernameText.setFont(appTheme.fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameText, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(appTheme.labelFont);
        passwordLabel.setForeground(appTheme.white);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordText = new JPasswordField(20);
        passwordText.setFont(appTheme.fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordText, gbc);

        // Added the login button with an action listener to handle login attempts
        JButton loginButton = new JButton("Login");
        loginButton.setFont(appTheme.labelFont);
        loginButton.setBackground(appTheme.buttonColour);
        loginButton.setForeground(appTheme.white);
        loginButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(appTheme.labelFont);
        backButton.setBackground(appTheme.buttonColour);
        backButton.setForeground(appTheme.white);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new homePage();
                frame.dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(backButton, gbc);

        // Added a label to display login success or failure messages
        success = new JLabel("", SwingConstants.CENTER);
        success.setFont(appTheme.fieldFont);
        success.setForeground(appTheme.white);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(success, gbc);

        // Finalised the frame setup and made it visible
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    // Implemented the actionPerformed method to handle login logic when the login
    // button is clicked
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
