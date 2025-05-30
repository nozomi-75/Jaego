package jaego.list;

import javax.swing.JPanel;

import jaego.utils.SampleItem;

public class ListPanel {
    private final ListModel model;
    private final ListView view;
    private final ListController controller;

    public ListPanel() {
        this.model = new ListModel();
        this.view = new ListView();
        this.controller = new ListController(model, view);
    }

    public JPanel getViewPanel() {
        return view;
    }

    public void addItem(SampleItem item) {
        controller.addItem(item);
    }

    public void clearAllItems() {
        controller.clearList();
    }
}
