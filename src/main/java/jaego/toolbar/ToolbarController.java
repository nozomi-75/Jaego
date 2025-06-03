package jaego.toolbar;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import jaego.db.ItemDAO;
import jaego.utils.AboutDialog;
import jaego.utils.CsvExporter;
import jaego.utils.DialogUtils;
import jaego.utils.LafManager;

public class ToolbarController implements ToolbarView.Listener {
    private ToolbarView view;
    private final ItemDAO dao = new ItemDAO();

    public ToolbarController() {
        this.view = new ToolbarView();
        this.view.setListener(this);
    }

    public JToolBar getToolBar() {
        return view.getToolbar();
    }

    @Override
    public void onToggleTheme(boolean darkmode) {
        LafManager.toggleTheme(darkmode, view.getThemeToggleButton(), getToolBar());
    }

    @Override public void onShowAbout() {
        AboutDialog.show();
    }

    public ToolbarView getView() {
        return view;
    }

    @Override
    public void onExportCsv() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export Inventory as CSV");
            fileChooser.setSelectedFile(new File("inventory.csv"));
            int result = fileChooser.showSaveDialog(view.getToolbar());
        
            if (result == JFileChooser. APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
            
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws  Exception {
                        CsvExporter.exportToCsv(file, dao);
                        return null;
                    }
                
                    @Override
                    protected void done() {
                        try {
                            get();
                            DialogUtils.showInfo("Export successful:\n" + file.getAbsolutePath(), "Export Complete");
                        } catch (Exception e) {
                            DialogUtils.showError("Failed to export CSV: " + e.getMessage(), "Export Error");
                        }
                    }
                }.execute();
            }
        });
    }
}
