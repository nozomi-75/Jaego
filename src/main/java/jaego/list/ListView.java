package jaego.list;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jaego.utils.SampleItem;

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

    public void clearTable() {
        tableModel.setRowCount(0);
    }
}
