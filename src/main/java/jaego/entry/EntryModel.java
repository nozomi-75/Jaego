package jaego.entry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jaego.utils.DialogUtils;
import jaego.utils.InventoryStorage;
import jaego.utils.SampleItem;

/**
 * EntryModel serves as the shared data model that holds the list of {@link SampleItem}.
 * <p>
 * This model is used by both the entry panel (for adding items) and the list panel (for displaying them),
 * enabling consistent and synchronized access to inventory data.
 * </p>
 */
public class EntryModel {
    private final List<SampleItem> items = new ArrayList<>();
    private static final File inventoryFile = new File("inventory.csv");
    private final List<Runnable> listeners = new ArrayList<>();
    
    public EntryModel() {
        loadFromFile();
    }

    public void addItem(SampleItem item) {
        items.add(item);
        saveToFile();
        notifyListeners();
    }

    public void replaceItem(SampleItem oldItem, SampleItem newItem) {
        int index = items.indexOf(oldItem);
        if (index != -1) {
            items.set(index, newItem);
            saveToFile();
            notifyListeners();
        }
    }

    public List<SampleItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Registers a listener to be notified when the inventory changes.
     *
     * @param listener a {@code Runnable} to call after changes (e.g., to update UI)
     */
    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners of a change to the model.
     */
    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    /**
     * Loads items from the {@code inventory.csv} file into memory.
     * @see InventoryStorage
     */
    private void loadFromFile() {
        InventoryStorage.load(
            inventoryFile,
            loadedItems -> {
                items.clear();
                items.addAll(loadedItems);
                notifyListeners();
            },
            error -> DialogUtils.showError("Failed to finalize inventory loading: " + error.getMessage(), "Error")
        );
    }

    /**
     * Saves the current inventory items to {@code inventory.csv}.
     * @see InventoryStorage
     */
    private void saveToFile() {
        InventoryStorage.save(inventoryFile, items);
    }
}
