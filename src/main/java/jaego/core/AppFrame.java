package jaego.core;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import jaego.db.ItemDAO;
import jaego.entry.EntryModel;
import jaego.entry.EntryPanel;
import jaego.list.ListPanel;
import jaego.toolbar.Toolbar;

/**
 * AppFrame is the main app window that contains all components.
 * It initializes the GUI components and sets up the layout.
 *
 * <p><strong>Usage:</strong></p>
 * <pre>new AppFrame("Window Title").showWindow();</pre>
 */
public class AppFrame extends JFrame {
    private Toolbar toolbar;
    private EntryPanel entryPanel;
    private ListPanel listPanel;

    public AppFrame(String title) {
        super(title);
        initFrame();
        initComponents();
        layoutComponents();
        setAppIcon();
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(600, 450));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        toolbar = new Toolbar();
        ItemDAO dao = new ItemDAO();
        EntryModel sharedModel = new EntryModel(dao);
        entryPanel = new EntryPanel(sharedModel);
        listPanel = new ListPanel(sharedModel);
    }

    private void layoutComponents() {
        JPanel leftPanel = entryPanel.getViewPanel();
        JPanel rightPanel = listPanel.getViewPanel();

        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            leftPanel,
            rightPanel
        );
        
        splitPane.setResizeWeight(0);
        splitPane.setDividerLocation(250);
        add(toolbar.getToolBar(), BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    private void setAppIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/48x48.png"));
        setIconImage(icon.getImage());
    }

    public void showWindow() {
        setVisible(true);
    }
}
