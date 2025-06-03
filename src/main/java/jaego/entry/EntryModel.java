package jaego.entry;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import jaego.db.ItemDAO;
import jaego.utils.DialogUtils;
import jaego.utils.SampleItem;

public class EntryModel {
    private final List<SampleItem> items = new ArrayList<>();
    private final List<Runnable> listeners = new ArrayList<>();
    private ItemDAO dao;

    public EntryModel(ItemDAO dao) {
        this.dao = dao;
        refreshInBackground();
    }

    public void addItem(SampleItem item) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dao.insertProduct(item);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    SwingUtilities.invokeLater(() -> {
                        items.add(item);
                        notifyListeners();
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> DialogUtils.showError(
                        "Failed to add product to database: " + e.getMessage(),
                        "Error"));
                }
            }
        }.execute();
    }

    public void replaceItem(SampleItem oldItem, SampleItem newItem) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dao.updateProduct(newItem);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    SwingUtilities.invokeLater(() -> {
                        int index = items.indexOf(oldItem);
                        if (index != -1) {
                            items.set(index, newItem);
                            notifyListeners();
                        }
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> DialogUtils.showError(
                        "Failed to update product in database: " + e.getMessage(),
                        "Error"));
                }
            }
        }.execute();
    }

    public void deleteItem(SampleItem item) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dao.deleteProduct(item.getID());
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    SwingUtilities.invokeLater(() -> {
                        items.remove(item);
                        notifyListeners();
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> DialogUtils.showError(
                        "Failed to delete product from database: " + e.getMessage(),
                        "Error"));
                }
            }
        }.execute();
    }

    public void refreshInBackground() {
        new SwingWorker<List<SampleItem>, Void>() {
            @Override
            protected List<SampleItem> doInBackground() throws Exception {
                return dao.getAllProducts();
            }

            @Override
            protected void done() {
                try {
                    List<SampleItem> loadedItems = get();
                    SwingUtilities.invokeLater(() -> {
                        items.clear();
                        items.addAll(loadedItems);
                        notifyListeners();
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> DialogUtils.showError(
                        "Failed to load inventory from database: " + e.getMessage(),
                        "Error"));
                }
            }
        }.execute();
    }

    public List<SampleItem> getItems() {
        return new ArrayList<>(items); // Still safe, since items only mutated on EDT
    }

    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
