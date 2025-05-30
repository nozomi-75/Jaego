package jaego.toolbar;

import javax.swing.JToolBar;

/**
 * Toolbar is a facade that assembles the ToolbarView and ToolbarController,
 * and exposes a simple interface to the rest of the application.
 */
public class Toolbar {
    private ToolbarController controller;
    private ToolbarView view;

    public Toolbar() {
        controller = new ToolbarController();
        view = controller.getView();
    }

    public JToolBar getToolBar() {
        return view.getToolbar();
    }
}
