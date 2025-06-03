package jaego.toolbar;

import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import jaego.utils.ButtonFactory;

public class ToolbarView {
    private JToolBar toolbar;

    private JToggleButton themeToggleButton;
    private Listener listener;

    public interface Listener {
        void onToggleTheme(boolean darkMode);
        void onShowAbout();
        void onExportCsv();
    }

    public ToolbarView() {
        initializeToolbar();
        initToolbarComponents();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void initializeToolbar() {
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBorder(new EmptyBorder(3, 5, 0, 0));
    }

    private void initToolbarComponents() {
        toolbar.add(ButtonFactory
            .createButton(
                "Export CSV",
                () -> listener.onExportCsv()
            )
        );

        ButtonFactory.createToggleButton("Dark mode", () -> {
            boolean darkMode = themeToggleButton.isSelected();
            listener.onToggleTheme(darkMode);
        }, btn -> themeToggleButton = btn, toolbar);

        toolbar.add(ButtonFactory.createButton("About", () -> listener.onShowAbout()));
    }

    public JToolBar getToolbar() {
        return toolbar;
    }

    public JToggleButton getThemeToggleButton() {
        return themeToggleButton;
    }
}
