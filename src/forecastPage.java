import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import util.appTheme;

public class forecastPage {

    public forecastPage(String city) {
        // Set Nimbus look and feel for a modern UI appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Created the main frame for the weather forecase page
        JFrame frame = new JFrame("Weather Forecast for " + city);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(appTheme.backgroundColour);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(appTheme.backgroundColour);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel(city + " Weather Forecast");
        headerLabel.setFont(appTheme.titleFont);
        headerLabel.setForeground(appTheme.white);
        headerPanel.add(headerLabel);

        // Content
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(appTheme.backgroundColour);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel contentLabel = new JLabel("Weather forecast today for " + city + ":");
        contentLabel.setFont(appTheme.fieldFont);
        contentLabel.setForeground(appTheme.white);
        contentPanel.add(contentLabel, BorderLayout.NORTH);

        // Main layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
