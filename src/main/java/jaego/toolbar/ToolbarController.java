package jaego.toolbar;

import javax.swing.JToolBar;

import jaego.utils.AboutDialog;
import jaego.utils.LafManager;

public class ToolbarController implements ToolbarView.Listener {
    private ToolbarView view;

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
}
