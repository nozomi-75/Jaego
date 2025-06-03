package jaego.list;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jaego.edit.EditListener;
import jaego.utils.CategoryOptions;
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
    private EditListener editListener;

    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton resetButton;
    private JComboBox<String> filterCombo;
    private JComboBox<String> sortCombo;

    private static final NumberFormat currencyFormat =
        NumberFormat.getCurrencyInstance(Locale.getDefault());
        
    private static final String[] COLUMN_NAMES = {
        "Product ID", "Name", "Price (" + currencyFormat.getCurrency().getCurrencyCode() + ")", "Quantity", "Category"
    };

    public void setEditListener(EditListener listener) {
        this.editListener = listener;
    } 

    public ListView() {
        initView();
    }

    private void initView() {
        setLayout(new BorderLayout());
        initSearchPanel();

        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(22);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    if (editListener != null) {
                        editListener.editRequested(selectedRow);
                    }
                }
            }
        });
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
                currencyFormat.format(item.getPrice()),
                item.getQty(),
                item.getCategory()
            };
            tableModel.addRow(row);
        }
    }

    private void initSearchPanel() {
        JPanel topPanel = new JPanel();

        searchLabel = new JLabel("Search: ");
        searchField = new JTextField(10);
        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");

        filterCombo = new JComboBox<>(CategoryOptions.CATEGORIES);
        sortCombo = new JComboBox<>(new String[] {
            "Sort: Name ↑", "Sort: Name ↓",
            "Sort: Qty. ↑", "Sort: Qty. ↓",
            "Sort: Price ↑", "Sort: Price ↓"
        });

        Stream.of(searchLabel, searchField, searchButton, resetButton, filterCombo, sortCombo).forEach(cmp -> topPanel.add(cmp));
        add(topPanel, BorderLayout.NORTH);
}

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public String getSelectedCategory() {
        return (String) filterCombo.getSelectedItem();
    }

    public String getSelectedSort() {
        return (String) sortCombo.getSelectedItem();
    }

    public void addToolbarListeners(Runnable onSearch, Runnable onReset, Runnable onFilterSortChanged) {
        searchButton.addActionListener(e -> onSearch.run());
        resetButton.addActionListener(e -> onReset.run());
        searchField.addActionListener(e -> onSearch.run());

        filterCombo.addActionListener(e -> onFilterSortChanged.run());
        sortCombo.addActionListener(e -> onFilterSortChanged.run());
    }
}
