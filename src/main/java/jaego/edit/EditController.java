package jaego.edit;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
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
    private final JSpinner priceSpinner;
    private final JSpinner qtySpinner;

    public EditController(
        JComboBox<String> categoryBox,
        JTextField idField,
        JTextField nameField,
        JSpinner priceSpinner,
        JSpinner qtySpinner
    ) {
        this.categoryBox = categoryBox;
        this.idField = idField;
        this.nameField = nameField;
        this.priceSpinner = priceSpinner;
        this.qtySpinner = qtySpinner;
    }

    public void populateFromItem(SampleItem item) {
        categoryBox.setSelectedItem(item.getCategory());
        idField.setText(item.getID());
        nameField.setText(item.getName());
        priceSpinner.setValue(item.getPrice());
        qtySpinner.setValue(item.getQty());
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
        return (double) priceSpinner.getValue();
    }
    
    public int getUpdatedQty() {
        return (int) qtySpinner.getValue();
    }
}
