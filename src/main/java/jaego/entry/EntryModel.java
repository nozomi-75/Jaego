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

    /**
     * Adds an item to the inventory, triggers a save to file, and notifies all listeners.
     *
     * @param item the {@link SampleItem} to add
     */
    public void addItem(SampleItem item) {
        items.add(item);
        saveToFile(item);
        notifyListeners();
    }

    /**
     * Returns a copy of the current list of inventory items.
     *
     * @return a new list containing all stored {@link SampleItem}
     */
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
     * <p>
     * Lines are skipped if they do not have exactly 5 comma-separated fields.
     * </p>
     */
    private void loadFromFile() {
        if (!inventoryFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(inventoryFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine && line.startsWith("Product ID")) {
                    isFirstLine = false;
                    continue; // skip header
                }
                isFirstLine = false;

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

    /**
     * Saves the current inventory items to {@code inventory.csv}.
     * <p>
     * Each line is written in CSV format: {@code id,name,price,quantity,category}.
     * </p>
     */
    private void saveToFile(SampleItem item) {
        boolean writeHeader = !inventoryFile.exists() || inventoryFile.length() == 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile, true))) {
            if (writeHeader) {
                writer.write("Product ID,Name,Price,Quantity,Category");
                writer.newLine();
            }

            writer.write(String.format("%s,%s,%.2f,%d,%s%n",
                item.getID(),
                item.getName(),
                item.getPrice(),
                item.getQty(),
                item.getCategory()
            ));
        } catch (IOException e) {
            DialogUtils.showError("Failed to save inventory: " + e.getMessage(), "Saving failed");
        }
    }
}
