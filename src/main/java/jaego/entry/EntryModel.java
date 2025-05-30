package jaego.entry;

import java.util.ArrayList;
import java.util.List;
import jaego.utils.SampleItem;

public class EntryModel {

    private final List<SampleItem> items;

    public EntryModel() {
        this.items = new ArrayList<>();
    }

    public void addItem(SampleItem item) {
        items.add(item);
    }

    public List<SampleItem> getAllItems() {
        return new ArrayList<>(items); // Defensive copy
    }

    public void clearItems() {
        items.clear();
    }
}
