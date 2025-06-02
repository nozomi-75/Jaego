package jaego.list;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jaego.utils.SampleItem;

/**
 * {@code ListView} is a Swing component that visually represents a list of inventory items
 * using a {@link JTable}. It provides methods to populate or clear the table content.
 *
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>Displays ID, Name, Price, Quantity, and Category of each product</li>
 *   <li>Supports scrollable view for long lists</li>
 *   <li>Table content is managed via {@link DefaultTableModel}</li>
 * </ul>
 */
public class ListView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private static final String[] COLUMN_NAMES = {
        "Product ID", "Name", "Price", "Quantity", "Category"
    };

    public ListView() {
        initView();
    }

    private void initView() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(22);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Populates the table with a list of {@link SampleItem} objects.
     * Clears any previous data before inserting the new rows.
     *
     * @param items the inventory items to display
     */
    public void updateTable(List<SampleItem> items) {
        tableModel.setRowCount(0); // Clear existing rows
        for (SampleItem item : items) {
            Object[] row = {
                item.getID(),
                item.getName(),
                item.getPrice(),
                item.getQty(),
                item.getCategory()
            };
            tableModel.addRow(row);
        }
    }
}
