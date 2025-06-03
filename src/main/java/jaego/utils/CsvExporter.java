package jaego.utils;

import jaego.db.ItemDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CsvExporter {

    public static void exportToCsv(File file, ItemDAO dao) throws IOException, SQLException {
        List<SampleItem> items = dao.getAllProducts();

        CSVFormat format = CSVFormat.DEFAULT.builder()
            .setHeader("Item ID", "Name", "Price", "Quantity", "Category")
            .setDelimiter(';')
            .setQuote('"')
            .setQuoteMode(QuoteMode.ALL)
            .get();

        try (FileWriter writer = new FileWriter(file);
             CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {

            for (SampleItem item : items) {
                csvPrinter.printRecord(
                    item.getID(),
                    item.getName(),
                    String.format("%.2f", item.getPrice()),
                    item.getQty(),
                    item.getCategory()
                );
            }

            csvPrinter.flush();
        }
    }
}
