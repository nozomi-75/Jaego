package jaego.list;

import jaego.utils.SampleItem;

public class ListController {

    private final ListModel model;
    private final ListView view;

    public ListController(ListModel model, ListView view) {
        this.model = model;
        this.view = view;
    }

    public void addItem(SampleItem item) {
        model.addItem(item);
        view.updateTable(model.getItems());
    }

    public void clearList() {
        model.clearItems();
        view.clearTable();
    }
}
