package jaego.edit;

import java.awt.*;
import javax.swing.*;

import jaego.utils.CategoryOptions;
import jaego.utils.SampleItem;

/**
 * A modal dialog for editing or deleting a SampleItem.
 */
public class EditDialog extends JDialog {
    private final JComboBox<String> categoryBox = new JComboBox<>(CategoryOptions.CATEGORIES);
    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(10);
    private final JTextField qtyField = new JTextField(10);

    private final EditController controller;

    public EditDialog(JFrame parent, SampleItem item) {
        super(parent, "Edit Item - " + item.getID(), true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        controller = new EditController(categoryBox, idField, nameField, priceField, qtyField);
        controller.populateFromItem(item);

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        JButton saveBtn = new JButton("Save");
        JButton deleteBtn = new JButton("Delete");
        JButton cancelBtn = new JButton("Cancel");

        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        saveBtn.addActionListener(e -> {
            controller.markConfirmed();
            dispose();
        });

        deleteBtn.addActionListener(e -> {
            controller.markDeleteRequested();
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        panel.add(saveBtn);
        panel.add(deleteBtn);
        panel.add(cancelBtn);
        return panel;
    }

    // Public accessors (delegating to controller)

    public boolean isConfirmed() {
        return controller.isConfirmed();
    }

    public boolean isDeleteRequested() {
        return controller.isDeleteRequested();
    }

    public String getUpdatedCategory() {
        return controller.getUpdatedCategory();
    }

    public String getUpdatedId() {
        return controller.getUpdatedId();
    }

    public String getUpdatedName() {
        return controller.getUpdatedName();
    }

    public double getUpdatedPrice() {
        return controller.getUpdatedPrice();
    }

    public int getUpdatedQty() {
        return controller.getUpdatedQty();
    }
}
