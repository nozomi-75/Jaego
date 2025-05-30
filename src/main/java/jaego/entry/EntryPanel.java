package jaego.entry;

import javax.swing.JPanel;

public class EntryPanel {

    private final EntryModel model;
    private final EntryView view;
    @SuppressWarnings("unused")
    private final EntryController controller;

    public EntryPanel() {
        this.model = new EntryModel();
        this.view = new EntryView();
        this.controller = new EntryController(model, view);
    }

    public JPanel getViewPanel() {
        return view;
    }

    public EntryModel getModel() {
        return model;
    }
}
