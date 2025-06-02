package jaego.list;

import java.util.List;

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
    }

    public void refreshTable() {
        List<SampleItem> items = model.getItems();
        view.updateTable(items);
    }
}
