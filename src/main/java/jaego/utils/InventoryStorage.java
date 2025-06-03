package jaego.utils;

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
import java.util.function.Consumer;

import javax.swing.SwingWorker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

public class InventoryStorage {

    public static void load(File file, Consumer<List<SampleItem>> onSuccess, Consumer<Exception> onError) {
        if (!file.exists()) return;

        SwingWorker<List<SampleItem>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<SampleItem> doInBackground() {
                List<SampleItem> loadedItems = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(file));
                     CSVParser parser = CSVFormat.DEFAULT
                         .builder()
                         .setHeader("Product ID", "Name", "Price", "Quantity", "Category")
                         .setDelimiter(';')
                         .setSkipHeaderRecord(true)
                         .setTrim(true)
                         .setIgnoreSurroundingSpaces(true)
                         .setQuote('"')
                         .setIgnoreEmptyLines(true)
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
                    throw new RuntimeException("Loading failed: " + e.getMessage(), e);
                }

                return loadedItems;
            }

            @Override
            protected void done() {
                try {
                    onSuccess.accept(get());
                } catch (Exception e) {
                    onError.accept(e);
                }
            }
        };

        worker.execute();
    }

    public static void save(File file, List<SampleItem> items) {
        try (OutputStream os = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {

            writer.write('\uFEFF'); // UTF-8 BOM

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
