package jaego.core;

import javax.swing.SwingUtilities;

import jaego.db.DatabaseManager;
import jaego.utils.LafManager;

/**
 * This is the main entry point for the application.
 * It initializes the GUI and sets up the main app frame.
 * The GUI is created on the EDT to ensure thread safety.
 * 
 * @see AppFrame
 */
public class Jaego {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseManager.initDB();
            LafManager.applyLightLaf();
            new AppFrame("Jaego").showWindow();
        });
    }
}
