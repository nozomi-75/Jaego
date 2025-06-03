package jaego.entry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import jaego.utils.DialogUtils;
import jaego.utils.SampleItem;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

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
        saveToFile();
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

        SwingWorker<List<SampleItem>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<SampleItem> doInBackground() {
                List<SampleItem> loadedItems = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
                     CSVParser parser = CSVFormat.DEFAULT
                         .builder()
                         .setHeader("Product ID", "Name", "Price", "Quantity", "Category")
                         .setSkipHeaderRecord(true)
                         .get()
                         .parse(reader)) {

                    for (CSVRecord record : parser) {
                        try {
                            String id = record.get("Product ID").trim();
                            String name = record.get("Name").trim();
                            double price = Double.parseDouble(record.get("Price").trim());
                            int qty = Integer.parseInt(record.get("Quantity").trim());
                            String category = record.get("Category").trim();

                            loadedItems.add(new SampleItem(id, name, price, qty, category));
                        } catch (Exception e) {
                            // skip malformed record
                        }
                    }

                } catch (IOException e) {
                    DialogUtils.showError("Failed to load inventory: " + e.getMessage(), "Loading failed");
                }

                return loadedItems;
            }

            @Override
            protected void done() {
                try {
                    List<SampleItem> parsedItems = get();
                    items.clear();
                    items.addAll(parsedItems);
                    notifyListeners();
                } catch (Exception e) {
                    DialogUtils.showError("Failed to finalize inventory loading.", "Error");
                }
            }
        };

        worker.execute();
    }

    /**
     * Saves the current inventory items to {@code inventory.csv}.
     * <p>
     * Each line is written in CSV format: {@code id,name,price,quantity,category}.
     * </p>
     */
    private void saveToFile() {
        try (OutputStream os = new FileOutputStream(inventoryFile);
             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {

            // Write UTF-8 BOM
            writer.write('\uFEFF');

            CSVFormat format = CSVFormat.Builder.create()
                .setHeader("Product ID", "Name", "Price", "Quantity", "Category")
                .setQuoteMode(QuoteMode.ALL)
                .setDelimiter(';')
                .get();

            try (CSVPrinter printer = new CSVPrinter(writer, format)) {
                for (SampleItem item : items) {
                    printer.printRecord(
                        item.getID(),
                        item.getName(),
                        item.getPrice(),
                        item.getQty(),
                        item.getCategory()
                    );
                }
            }

        } catch (IOException e) {
            DialogUtils.showError("Failed to save inventory: " + e.getMessage(), "Saving failed");
        }
    }
}
