package jaego.core;

import javax.swing.SwingUtilities;

/**
 * This is the main entry point for the application.
 * It initializes the GUI and sets up the main app frame.
 * The GUI is created on the EDT to ensure thread safety.
 * 
 * @see AppFrame
 */
public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppFrame("Jaego").showWindow();
        });
    }
}
