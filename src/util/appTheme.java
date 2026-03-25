package util;

import java.awt.Color;
import java.awt.Font;

//This class defines the theme for the application, including colors and fonts
public class appTheme {
    public static final Color backgroundColour = new Color(30, 100, 160);
    public static final Color buttonColour = new Color(20, 75, 120);
    public static final Color borderColour = new Color(200, 225, 245);
    public static final Color white = Color.WHITE;

    public static final Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font smallButtonFont = new Font("Segoe UI", Font.PLAIN, 8);

    // Private constructor to prevent the class being used as an object
    private appTheme() {
    }

}
