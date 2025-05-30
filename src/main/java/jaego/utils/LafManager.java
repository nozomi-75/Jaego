package jaego.utils;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * LafManager is responsible for managing the Look and Feel (Laf) of the application.
 * It applies the FlatLightkLaf theme to the UI components.
 * If the theme fails to initialize, it falls back to the system look and feel.
 * 
 * @see AppFrame
 */
public class LafManager {
    private static boolean dark = false;

    public static boolean isDark() {
        return dark;
    }

    public static void applyLightLaf() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            dark = false;
        } catch (Exception ex) {
            fallback();
        }
    }

    public static void applyDarkLaf() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            dark = true;
        } catch (Exception ex) {
            fallback();
        }
    }

    private static void fallback() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception fallbackException) {
            System.err.println("Fatal error: Could not initialize any Laf.");
        }
    }
}
