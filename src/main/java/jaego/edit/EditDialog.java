package jaego.edit;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jaego.utils.SampleItem;

public class EditDialog extends JDialog {

    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(10);
    private final JTextField qtyField = new JTextField(10);
    private final JComboBox<String> categoryBox = new JComboBox<>(new String[] {
        "Office Supplies", "Electronics", "Furniture", "Stationery", "Misc"
    });

    private boolean confirmed = false;

    public EditDialog(JFrame parent, SampleItem item) {
        super(parent, "Edit Item - " + item.getID(), true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel fields = new JPanel(new GridLayout(0, 2, 5, 5));
        fields.add(new JLabel("Name:"));
        fields.add(nameField);
        fields.add(new JLabel("Price:"));
        fields.add(priceField);
        fields.add(new JLabel("Quantity:"));
        fields.add(qtyField);
        fields.add(new JLabel("Category:"));
        fields.add(categoryBox);

        nameField.setText(item.getName());
        priceField.setText(String.valueOf(item.getPrice()));
        qtyField.setText(String.valueOf(item.getQty()));
        categoryBox.setSelectedItem(item.getCategory());

        JPanel buttons = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        cancelBtn.addActionListener(e -> dispose());

        add(fields, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
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
