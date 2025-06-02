package jaego.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import jaego.utils.DialogUtils;
import jaego.utils.SampleItem;

/**
 * EntryController manages user interactions for the EntryPanel.
 * It listens for button clicks, validates input, and updates the model accordingly.
 *
 * <p>Typical usage includes saving a new product or resetting the form fields.</p>
 *
 * <p>It interacts with:
 * <ul>
 *   <li>{@link EntryView} – for retrieving input and setting up event handlers</li>
 *   <li>{@link EntryModel} – for storing newly added items</li>
 * </ul>
 * </p>
 */
public class EntryController {

    private final EntryModel model;
    private final EntryView view;
    private Consumer<SampleItem> onSaveListener;

    public EntryController(EntryModel model, EntryView view) {
        this.model = model;
        this.view = view;
        attachEventHandlers();
    }

    /**
     * Attaches listeners to the Save and Reset buttons on the view.
     */
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

    /**
     * Gathers input from the view, validates it, creates a new item,
     * adds it to the model, and clears the form if successful.
     */
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
    
    /**
     * Registers a callback to be triggered whenever a new item is successfully saved.
     * <p>
     * This allows other components (e.g., the list panel) to react when new entries are added.
     * </p>
     *
     * @param listener a {@code Consumer<SampleItem>} that will be called with the new item
     */
    public void setOnSaveListener(Consumer<SampleItem> listener) {
        this.onSaveListener = listener;
    }
}
