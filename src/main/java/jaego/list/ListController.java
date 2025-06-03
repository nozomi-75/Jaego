package jaego.list;

import java.util.List;

import jaego.edit.EditDialog;
import jaego.entry.EntryModel;
import jaego.utils.SampleItem;

/**
 * {@code ListController} manages the interaction between the {@link EntryModel}
 * and the {@link ListView}.
 * <p>
 * Ensures that the table view remains synchronized
 * with the current list of inventory items.
 * </p>
 *
 * <p><strong>Responsibilities:</strong></p>
 * <ul>
 *   <li>Listens to changes in {@link EntryModel} via listeners</li>
 *   <li>Updates the {@link ListView} table when the inventory changes</li>
 * </ul>
 */
public class ListController {

    private final EntryModel model;
    private final ListView view;

    public ListController(EntryModel model, ListView view) {
        this.model = model;
        this.view = view;
        view.setEditListener(this::onEditRequested);
        view.addToolbarListeners(this::onSearch, this::onResetSearch, this::refreshTable);
    }

    public void refreshTable() {
        List<SampleItem> items = model.getItems();
    
        // Filter
        String category = view.getSelectedCategory();
        if (!"All Categories".equals(category)) {
            items.removeIf(item -> !item.getCategory().equalsIgnoreCase(category));
        }
    
        // Sort
        String sort = view.getSelectedSort();
        items.sort((a, b) -> {
            switch (sort) {
                case "Sort: ID ↑": return a.getID().compareToIgnoreCase(b.getID());
                case "Sort: ID ↓": return b.getID().compareToIgnoreCase(a.getID());
                case "Sort: Name ↑": return a.getName().compareToIgnoreCase(b.getName());
                case "Sort: Name ↓": return b.getName().compareToIgnoreCase(a.getName());
                case "Sort: Qty. ↑": return Integer.compare(a.getQty(), b.getQty());
                case "Sort: Qty. ↓": return Integer.compare(b.getQty(), a.getQty());
                case "Sort: Price ↑": return Double.compare(a.getPrice(), b.getPrice());
                case "Sort: Price ↓": return Double.compare(b.getPrice(), a.getPrice());
                default: return 0;
            }
        });
    
        view.updateTable(items);
    }

    public void onEditRequested(int rowIndex) {
        List<SampleItem> items = model.getItems();
        if (rowIndex < 0 || rowIndex >= items.size()) return;   

        SampleItem selectedItem = items.get(rowIndex);  

        EditDialog dialog = new EditDialog(null, selectedItem);
        dialog.setVisible(true);    

        if (dialog.isDeleteRequested()) {
            model.deleteItem(selectedItem);
        } else if (dialog.isConfirmed()) {
            SampleItem updatedItem = new SampleItem(
                selectedItem.getID(),
                dialog.getUpdatedName(),
                dialog.getUpdatedPrice(),
                dialog.getUpdatedQty(),
                dialog.getUpdatedCategory()
            );
            model.replaceItem(selectedItem, updatedItem);
        }
    }

    public void onSearch() {
        String query = view.getSearchQuery();
        model.search(query);
    }

    public void onResetSearch() {
        model.refreshInBackground();
    }
}
