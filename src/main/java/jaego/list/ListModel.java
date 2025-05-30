package jaego.list;

import java.util.ArrayList;
import java.util.List;

import jaego.utils.SampleItem;

public class ListModel {
    private final List<SampleItem> items;

    public ListModel() {
        this.items = new ArrayList<>();
    }

    public void addItem(SampleItem item) {
        items.add(item);
    }

    public void clearItems() {
        items.clear();
    }

    public List<SampleItem> getItems() {
        return new ArrayList<>(items);
    }
}
