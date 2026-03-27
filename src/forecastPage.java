import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.SwingWorker; // Used to run API call in background without freezing the UI

import util.appTheme;
import util.weatherService;
import util.weatherService.WeatherData;

public class forecastPage {

    public forecastPage(String city) {
        // Set Nimbus look and feel for a modern UI appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Created the main frame for the weather forecast page
        JFrame frame = new JFrame("Weather Forecast for " + city);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new java.awt.Dimension(900, 500));

        // Show a loading screen immediately so the user knows something is happening in
        // the background
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(appTheme.backgroundColour);

        JLabel loadingLabel = new JLabel("Loading forecast for " + city + "...", JLabel.CENTER);
        loadingLabel.setFont(appTheme.titleFont);
        loadingLabel.setForeground(appTheme.white);
        loadingPanel.add(loadingLabel, BorderLayout.CENTER);

        frame.add(loadingPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // SwingWorker runs the API call in the background
        SwingWorker<WeatherData, Void> worker = new SwingWorker<WeatherData, Void>() {

            @Override
            protected WeatherData doInBackground() {
                return weatherService.getWeather(city);
            }

            // Updating UI with fetched data once background task is complete
            @Override
            protected void done() {
                try {
                    WeatherData data = get();
                    frame.getContentPane().removeAll(); // Cleaning loading screen to update with forecast UI
                    frame.add(buildUI(city, data, frame));
                    frame.revalidate(); // Refreshing layout logic for new components
                    frame.repaint(); // Forcing redraw to ensure all new components are rendered properly
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        worker.execute(); // Starting the background task
    }

    private JPanel buildUI(String city, WeatherData data, JFrame frame) {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(appTheme.backgroundColour);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel headerLabel = new JLabel(city + " Weather Forecast");
        headerLabel.setFont(appTheme.titleFont);
        headerLabel.setForeground(appTheme.white);
        headerPanel.add(headerLabel);

        // Current conditions
        JPanel currentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        currentPanel.setBackground(appTheme.buttonColour);
        currentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        if (data != null) {
            JLabel currentLabel = new JLabel("Current Conditions");
            currentLabel.setFont(appTheme.labelFont);
            currentLabel.setForeground(appTheme.white);
            currentPanel.add(currentLabel);
            JLabel tempLabel = new JLabel("Temperature: " + data.currentTemp + "°C");
            JLabel humidLabel = new JLabel("Humidity: " + data.humidity + "%");

            for (JLabel label : new JLabel[] { tempLabel, humidLabel }) {
                label.setFont(appTheme.labelFont);
                label.setForeground(appTheme.white);
                currentPanel.add(label);
            }
        } else {
            JLabel errorLabel = new JLabel("Could not load weather data. Check your connection.");
            errorLabel.setFont(appTheme.fieldFont);
            errorLabel.setForeground(appTheme.white);
            currentPanel.add(errorLabel);
        }

        // 7 day forecast cards
        JPanel forecastPanel = new JPanel(new GridLayout(7, 1, 0, 10));
        forecastPanel.setBackground(appTheme.backgroundColour);
        forecastPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (data != null) {
            for (int i = 0; i < data.dailyDates.length; i++) {
                JPanel card = new JPanel(new GridLayout(4, 1, 0, 5));
                card.setBackground(appTheme.buttonColour);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(appTheme.borderColour, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                String shortDate = data.dailyDates[i].substring(5, 10);

                JLabel dateLabel = new JLabel(shortDate, JLabel.CENTER);
                JLabel maxLabel = new JLabel("Max temp: " + data.dailyMaxTemps[i] + "°C", JLabel.CENTER);
                JLabel minLabel = new JLabel("Min temp: " + data.dailyMinTemps[i] + "°C", JLabel.CENTER);
                JLabel rainLabel = new JLabel("Rain chance: " + data.rainChance[i] + "%", JLabel.CENTER);

                for (JLabel label : new JLabel[] { dateLabel, maxLabel, minLabel, rainLabel }) {
                    label.setFont(appTheme.fieldFont);
                    label.setForeground(appTheme.white);
                    card.add(label);
                }
                forecastPanel.add(card);
            }
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(appTheme.labelFont);
        backButton.setBackground(appTheme.buttonColour);
        backButton.setForeground(appTheme.white);
        backButton.addActionListener(e -> {
            new homePage();
            frame.dispose();
        });

        JPanel backWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backWrapper.setBackground(appTheme.backgroundColour);
        backWrapper.add(backButton);

        // Assemble top section: header + current conditions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(appTheme.backgroundColour);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(currentPanel, BorderLayout.SOUTH);

        // Added scroll feature, forecast cards take up a lot of vertical space
        JScrollPane scrollPanel = new JScrollPane(forecastPanel);
        scrollPanel.setBackground(appTheme.backgroundColour);
        scrollPanel.getViewport().setBackground(appTheme.backgroundColour);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Assemble main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(appTheme.backgroundColour);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPanel, BorderLayout.CENTER);

        // Final panel assembly
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(appTheme.backgroundColour);
        root.add(mainPanel, BorderLayout.CENTER);
        root.add(backWrapper, BorderLayout.SOUTH);

        return root;
    }
}