package jaego.list;

import java.util.List;

import jaego.entry.EntryModel;
import jaego.utils.SampleItem;

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
