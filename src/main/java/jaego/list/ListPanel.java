package jaego.list;

import javax.swing.JPanel;

import jaego.entry.EntryModel;

/**
 * {@code ListPanel} serves as the façade for the inventory listing feature.
 * <p>
 * This class is designed to be embedded in the main application layout (e.g., in a {@code JSplitPane}),
 * and exposes its root panel via {@link #getViewPanel()} for integration.
 * </p>
 *
 * @see ListView
 * @see ListController
 */
public class ListPanel {
    @SuppressWarnings("unused")
    private final EntryModel entryModel;
    private final ListView view;
    private final ListController controller;

    public ListPanel(EntryModel entryModel) {
        this.entryModel = entryModel;
        this.view = new ListView();
        this.controller = new ListController(entryModel, view);

        entryModel.addChangeListener(controller::refreshTable);
        controller.refreshTable();
    }

    public JPanel getViewPanel() {
        return view;
    }
}
