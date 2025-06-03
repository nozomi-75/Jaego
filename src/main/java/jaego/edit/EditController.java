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

    private final JComboBox<String> categoryBox;
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField priceField;
    private final JTextField qtyField;

    public EditController(
        JComboBox<String> categoryBox,
        JTextField idField,
        JTextField nameField,
        JTextField priceField,
        JTextField qtyField
    ) {
        this.categoryBox = categoryBox;
        this.idField = idField;
        this.nameField = nameField;
        this.priceField = priceField;
        this.qtyField = qtyField;
    }

    public void populateFromItem(SampleItem item) {
        categoryBox.setSelectedItem(item.getCategory());
        idField.setText(item.getID());
        nameField.setText(item.getName());
        priceField.setText(String.valueOf(item.getPrice()));
        qtyField.setText(String.valueOf(item.getQty()));
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

    public String getUpdatedCategory() {
        return (String) categoryBox.getSelectedItem();
    }

    public String getUpdatedId() {
        return idField.getText().trim();
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
}
