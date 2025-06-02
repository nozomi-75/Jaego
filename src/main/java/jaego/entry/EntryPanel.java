package jaego.entry;

import java.util.function.Consumer;
import javax.swing.JPanel;
import jaego.utils.SampleItem;

/**
 * {@code EntryPanel} is a facade class that assembles the MVC components
 * for the product entry form.
 * <p>
 * It wires together the {@link EntryModel}, {@link EntryView}, and {@link EntryController}
 * and exposes only the view for display and model for external access.
 * </p>
 */
public class EntryPanel {

    private final EntryModel model;
    private final EntryView view;
    private final EntryController controller;

    public EntryPanel(EntryModel model) {
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
