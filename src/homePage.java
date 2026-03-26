import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import util.appTheme;

public class homePage {

    public homePage() {
        // Set Nimbus look and feel for a modern UI appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Weathermaxxing Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(appTheme.backgroundColour);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel("Welcome to Weathermaxxing!");
        headerLabel.setFont(appTheme.titleFont);
        headerLabel.setForeground(appTheme.white);
        headerPanel.add(headerLabel);

        // Content
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(appTheme.backgroundColour);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel contentHeaderLabel = new JLabel(
                "Get accurate weather forecasts and insights in Australia! Select from one of the options below to get started:");
        contentHeaderLabel.setFont(appTheme.fieldFont);
        contentHeaderLabel.setForeground(appTheme.white);
        contentPanel.add(contentHeaderLabel, BorderLayout.NORTH);

        // Radio buttons to select city for weather forecast
        JPanel radioPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        radioPanel.setBackground(appTheme.backgroundColour);

        ButtonGroup cityGroup = new ButtonGroup();

        String[] options = {
                "Adelaide",
                "Brisbane",
                "Cairns",
                "Canberra",
                "Darwin",
                "Esperance",
                "Gold Coast",
                "Hobart",
                "Melbourne",
                "Newcastle",
                "Perth",
                "Sunshine Coast",
                "Sydney",
                "Townsville",
                "Wollongong"
        };

        for (String option : options) {
            JRadioButton rb = new JRadioButton(option);
            rb.setActionCommand(option);
            rb.setFont(appTheme.fieldFont);
            rb.setForeground(appTheme.white);
            rb.setBackground(appTheme.backgroundColour);
            cityGroup.add(rb);
            radioPanel.add(rb);
        }

        contentPanel.add(radioPanel, BorderLayout.CENTER);

        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Footer with button to direct to admin login page or to select city for
        // weather forecast
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(appTheme.backgroundColour);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton getForeCastButton = new JButton("Get Forecast");
        getForeCastButton.setFont(appTheme.labelFont);
        getForeCastButton.setBackground(appTheme.buttonColour);
        getForeCastButton.setForeground(appTheme.white);
        getForeCastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String selectedCity = cityGroup.getSelection() != null ? cityGroup.getSelection().getActionCommand()
                        : null;
                if (selectedCity != null) {
                    new forecastPage(selectedCity);
                    frame.dispose();
                } else {
                    // Show an error message if no city is selected
                    javax.swing.JOptionPane.showMessageDialog(frame, "Please select a city to get the forecast.",
                            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setFont(appTheme.smallButtonFont);
        adminLoginButton.setBackground(appTheme.buttonColour);
        adminLoginButton.setForeground(appTheme.white);
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new adminLogin();
            }

        });

        footerPanel.add(getForeCastButton);
        footerPanel.add(adminLoginButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(homePage::new);
    }
}
