package jaego.list;

import javax.swing.JPanel;

import jaego.entry.EntryModel;

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

    public void refresh() {
        controller.refreshTable();
    }
}
