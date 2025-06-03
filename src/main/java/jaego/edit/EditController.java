package jaego.edit;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import jaego.utils.SampleItem;

/**
 * Controller for managing item editing logic.
 */
public class EditController {

    private boolean confirmed = false;
    private boolean deleteRequested = false;

    private final JTextField nameField;
    private final JTextField priceField;
    private final JTextField qtyField;
    private final JComboBox<String> categoryBox;

    public EditController(
        JTextField nameField,
        JTextField priceField,
        JTextField qtyField,
        JComboBox<String> categoryBox
    ) {
        this.nameField = nameField;
        this.priceField = priceField;
        this.qtyField = qtyField;
        this.categoryBox = categoryBox;
    }

    public void populateFromItem(SampleItem item) {
        nameField.setText(item.getName());
        priceField.setText(String.valueOf(item.getPrice()));
        qtyField.setText(String.valueOf(item.getQty()));
        categoryBox.setSelectedItem(item.getCategory());
    }

    public void markConfirmed() {
        confirmed = true;
    }

    public void markDeleteRequested() {
        deleteRequested = true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean isDeleteRequested() {
        return deleteRequested;
    }

    public String getUpdatedName() {
        return nameField.getText().trim();
    }

    public double getUpdatedPrice() {
        return Double.parseDouble(priceField.getText().trim());
    }

    public int getUpdatedQty() {
        return Integer.parseInt(qtyField.getText().trim());
    }

    public String getUpdatedCategory() {
        return (String) categoryBox.getSelectedItem();
    }
}
