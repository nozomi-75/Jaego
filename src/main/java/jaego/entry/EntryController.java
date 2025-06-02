package jaego.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import jaego.utils.DialogUtils;
import jaego.utils.SampleItem;

public class EntryController {

    private final EntryModel model;
    private final EntryView view;
    private Consumer<SampleItem> onSaveListener;

    public EntryController(EntryModel model, EntryView view) {
        this.model = model;
        this.view = view;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        view.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearFields();
            }
        });
    }

    private void handleSave() {
        String id = view.getIdInput();
        String name = view.getNameInput();
        double price = view.getPriceInput();
        int quantity = view.getQuantityInput();
        String category = view.getSelectedCategory();

        if (id.isEmpty() || name.isEmpty()) {
            DialogUtils.showError("Product ID and Name cannot be empty.", "Validation error");
            return;
        }

        SampleItem item = new SampleItem(id, name, price, quantity, category);
        model.addItem(item);

        // Notify listener if set
        if (onSaveListener != null) {
            onSaveListener.accept(item);
        }

        DialogUtils.showInfo("Product saved successfully.", "Action successful");
        view.clearFields();
    }

    public void setOnSaveListener(Consumer<SampleItem> listener) {
        this.onSaveListener = listener;
    }
}
