package jaego.utils;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Helper method for encapsulating dialog logic and follows MVC.
 */
public class DialogUtils {
    public static void showError(String message, String windowTitle) {
        JOptionPane.showMessageDialog(null, message, windowTitle, JOptionPane.ERROR_MESSAGE);
    }
    public static void showInfo(String message, String windowTitle) {
        JOptionPane.showMessageDialog(null, message, windowTitle, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showCustomDialog(String message, String windowTitle, Icon icon) {
        JOptionPane.showMessageDialog(null, message, windowTitle, JOptionPane.INFORMATION_MESSAGE, icon);
    }
}
