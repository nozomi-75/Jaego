package jaego.entry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jaego.utils.DialogUtils;
import jaego.utils.SampleItem;

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

    public List<SampleItem> getItems() {
        return new ArrayList<>(items);
    }

    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    private void loadFromFile() {
        if (!inventoryFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(inventoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length != 5) continue;
                SampleItem item = new SampleItem(
                    tokens[0].trim(),
                    tokens[1].trim(),
                    Double.parseDouble(tokens[2].trim()),
                    Integer.parseInt(tokens[3].trim()),
                    tokens[4].trim()
                );
                items.add(item);
            }
        } catch (IOException | NumberFormatException e) {
            DialogUtils.showError("Failed to load inventory: " + e.getMessage(), "Loading failed");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile))) {
            for (SampleItem item : items) {
                writer.write(String.format("%s,%s,%.2f,%d,%s%n",
                    item.getID(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getCategory()
                ));
            }
        } catch (IOException e) {
            DialogUtils.showError("Failed to save inventory: " + e.getMessage(), "Saving failed");
        }
    }
}
