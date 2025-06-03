package jaego.entry;

import java.util.ArrayList;
import java.util.List;

import jaego.db.ItemDAO;
import jaego.utils.DialogUtils;
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
    private final List<Runnable> listeners = new ArrayList<>();
    private ItemDAO dao = new ItemDAO();
    
    public EntryModel(ItemDAO dao) {
        this.dao = dao;
        refresh();
    }

    public void addItem(SampleItem item) {
        try {
            dao.insertProduct(item);
            items.add(item);
            notifyListeners();
        } catch (Exception e) {
            DialogUtils.showError(
                "Failed to add product to database: " + e.getMessage(),
                "Error");
        }
    }

    public void replaceItem(SampleItem oldItem, SampleItem newItem) {
        try {
            dao.updateProduct(newItem);
            int index = items.indexOf(oldItem);
            if (index != -1) {
                items.set(index, newItem);
                notifyListeners();
            }
        } catch (Exception e) {
            DialogUtils.showError(
                "Failed to update product in database: " + e.getMessage(),
                "Error");
        }
    }

    public void deleteItem(SampleItem item) {
        try {
            dao.deleteProduct(item.getID());
            items.remove(item);
            notifyListeners();
        } catch (Exception e) {
            DialogUtils.showError(
                "Failed to delete product from database: " + e.getMessage(),
                "Error");
        }
    }
    
    public List<SampleItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Registers a listener to be notified when the inventory changes.
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
     * Loads items from the {@code inventory.db} file into memory.
     * @see ItemDAO
     */
    private void refresh() {
        try {
            items.clear();
            items.addAll(dao.getAllProducts());
            notifyListeners();
        } catch (Exception e) {
            DialogUtils.showError(
                "Failed to load inventory from database: " + e.getMessage(),
                "Error");
        }
    }
}
