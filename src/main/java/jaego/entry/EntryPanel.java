package jaego.entry;

import java.util.function.Consumer;
import javax.swing.JPanel;
import jaego.utils.SampleItem;

public class EntryPanel {

    private final EntryModel model;
    private final EntryView view;
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

    public void setOnSave(Consumer<SampleItem> listener) {
        controller.setOnSaveListener(listener);
    }
}
