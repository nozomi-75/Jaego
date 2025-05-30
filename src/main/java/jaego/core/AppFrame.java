package jaego.core;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * AppFrame is the main app window that contains all components.
 * It initializes the GUI components and sets up the layout.
 * 
 * <p><strong>Usage:</strong></p>
 * <pre>new AppFrame("Window Title").showWindow()</pre>
 */
public class AppFrame extends JFrame {
    private JLabel label;

    public AppFrame(String title) {
        super(title);
        initFrame();
        initComponents();
        layoutComponents();
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        label = new JLabel("Lets check the stock.");
    }

    private void layoutComponents() {
        add(label, BorderLayout.CENTER);
    }

    public void showWindow() {
        setVisible(true);
    }
}
